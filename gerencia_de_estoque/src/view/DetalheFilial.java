package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;
import modelo.Filial;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class DetalheFilial extends Detalhe {
    private JPanel formularios = new JPanel();
    private JTextField valorNome = new JTextField();
    private JTextField valorLocalizacao = new JTextField();
    private JTextField valorId = new JTextField();
    private Filial filialEscolhida;

    // Construtor para adicionar uma filial nova
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        DetalheFilial.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = Modos.ADICIONAR;
        criarJanelaFilial();
    }

    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Filial filialEscolhida) {
        DetalheFilial.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = Modos.EDITAR;

        this.filialEscolhida = filialEscolhida;

        valorLocalizacao.setText(filialEscolhida.getLocal());
        valorNome.setText(filialEscolhida.getNome());
        valorId.setText(String.valueOf(filialEscolhida.getId()));

        criarJanelaFilial();
    }

    //Cria elementos comuns as duas janelas
    private void criarJanelaFilial() {
        criarFormularioFilial();
        criarJanela(new JComponent[]{formularios}, 400, 200, "Filial:");
    }

    private void criarFormularioFilial() {
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelLocalizacao = new JLabel("Localização: ");
        JLabel labelId = new JLabel("ID: ");

        JComponent[] componentesEsquerdos = {labelNome, labelLocalizacao, labelId};
        JComponent[] compontentesDireitos = {valorNome, valorLocalizacao, valorId};
        new PainelFormulariosBuilder(formularios, componentesEsquerdos, compontentesDireitos, "Informações da Filial:");
    }

    @Override
    protected void excluirElemento() {
        controleEmpresa.excluirFilial(filialEscolhida);
        janelaPesquisa.refresh();
    }

    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        controleEmpresa.adicionarFilial(
                valorNome.getText(),
                valorLocalizacao.getText(),
                Integer.parseInt(valorId.getText())
        );
    }

    protected void atualizarElemento() throws IdRepetidoException {
        controleEmpresa.atualizarFilial(
                valorNome.getText(),
                valorLocalizacao.getText(),
                Integer.parseInt(valorId.getText()),
                filialEscolhida);
    }


}
