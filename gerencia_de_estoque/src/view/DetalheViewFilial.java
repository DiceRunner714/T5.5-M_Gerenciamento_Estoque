package view;

import controle.ControleEmpresa;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.Filial;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected List<JComponent> agruparTodosFormularios() {
        List<JComponent> paineis = new ArrayList<>();

        paineis.add(new PainelFormulario(
                Arrays.asList(
                        new JLabel("Nome: "),
                        new JLabel("Localização: "),
                        new JLabel("ID: ")),
                Arrays.asList(
                        valorNome,
                        valorLocalizacao,
                        valorId),
                "Informações da Filial:")
        );
        return paineis;
    }

    @Override
    protected void excluirElemento() throws ElementoInexistenteException {
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

    @Override
    protected void atualizarElemento() throws IdRepetidoException, ElementoInexistenteException {
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
