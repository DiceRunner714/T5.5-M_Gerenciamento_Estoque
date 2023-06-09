package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;
import modelo.Filial;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

public class DetalheFilial extends Detalhe {
    private JTextField valorNome = new JTextField();
    private JTextField valorLocalizacao = new JTextField();
    private JTextField valorId = new JTextField();
    private Filial filialEscolhida;

    // Construtor para adicionar uma filial nova
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        super(Modos.ADICIONAR, janelaPesquisa, controleEmpresa);
        criarJanela(criarPaineisFormularios(), 400, 200, "Filial:");
    }

    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Filial filialEscolhida) {
        super(Modos.EDITAR, janelaPesquisa, controleEmpresa);
        this.filialEscolhida = filialEscolhida;
        criarJanela(criarPaineisFormularios(), 400, 200, "Filial:");
        popularFormularios();
    }

    @Override
    protected ArrayList<JComponent> criarPaineisFormularios() {
        ArrayList<JComponent> paineis = new ArrayList<>();
        JPanel formularios = new JPanel();
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelLocalizacao = new JLabel("Localização: ");
        JLabel labelId = new JLabel("ID: ");

        JComponent[] componentesEsquerdos = {labelNome, labelLocalizacao, labelId};
        JComponent[] compontentesDireitos = {valorNome, valorLocalizacao, valorId};
        new PainelFormulariosBuilder(formularios, componentesEsquerdos, compontentesDireitos, "Informações da Filial:");

        paineis.add(formularios);
        return paineis;
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

    @Override
    protected void popularFormularios() {
        valorLocalizacao.setText(filialEscolhida.getLocal());
        valorNome.setText(filialEscolhida.getNome());
        valorId.setText(String.valueOf(filialEscolhida.getId()));
    }


}
