package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;
import modelo.Filial;

import javax.swing.*;
import java.util.ArrayList;

public class DetalheViewFilial extends DetalheView {
    private final JTextField valorNome = new JTextField();
    private final JTextField valorLocalizacao = new JTextField();
    private final JTextField valorId = new JTextField();
    private Filial filialEscolhida;

    // Construtor para adicionar uma filial nova
    public DetalheViewFilial(ControleEmpresa controleEmpresa, PesquisaView pesquisaView) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        criarJanela(agruparTodosFormularios(), 400, 200, "Filial:");
    }

    public DetalheViewFilial(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, Filial filialEscolhida) {
        super(ModosDetalhe.EDITAR, pesquisaView, controleEmpresa);
        this.filialEscolhida = filialEscolhida;
        criarJanela(agruparTodosFormularios(), 400, 200, "Filial:");
        popularFormularios();
    }

    @Override
    protected ArrayList<JComponent> agruparTodosFormularios() {
        ArrayList<JComponent> paineis = new ArrayList<>();

        JComponent[] componentesEsquerdos = {
                new JLabel("Nome: "),
                new JLabel("Localização: "),
                new JLabel("ID: ")};
        JComponent[] compontentesDireitos = {
                valorNome,
                valorLocalizacao,
                valorId};
        JPanel formularios = new JPanel();
        new PainelFormularioBuilder(formularios, componentesEsquerdos, compontentesDireitos, "Informações da Filial:");

        paineis.add(formularios);
        return paineis;
    }

    @Override
    protected void excluirElemento() {
        controleEmpresa.excluirFilial(filialEscolhida);
        pesquisaView.refresh();
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
