package view;

import controle.ControleEmpresa;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.Filial;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementação concreta de uma DetalheView destinada ao gerenciamento de detalhes
 * de uma filial
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
public class DetalheViewFilial extends DetalheView {
    private final JTextField valorNome = new JTextField();
    private final JTextField valorLocalizacao = new JTextField();
    private final JTextField valorId = new JTextField();
    private Filial filialEscolhida;

    /**
     * Construtor destinado a adicionar uma filial nova a classe de controle
     *
     * @param controleEmpresa classe de controle para gerenciamento de filiais
     * @param pesquisaView    PesquisaView da qual essa janela se origina
     */
    public DetalheViewFilial(ControleEmpresa controleEmpresa, PesquisaView pesquisaView) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        criarJanela(agruparTodosFormularios(), 400, 250, "Filial:");
    }

    /**
     * Construtor destinado a modificar ou excluir uma filial da classe de controle
     *
     * @param controleEmpresa classe de controle para gerenciamento da filial
     * @param pesquisaView    PesquisaView da qual essa janela se origina
     * @param filialEscolhida Filial escolhida para ser modificada ou excluida
     */
    public DetalheViewFilial(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, Filial filialEscolhida) {
        super(ModosDetalhe.EDITAR, pesquisaView, controleEmpresa);
        this.filialEscolhida = filialEscolhida;
        criarJanela(agruparTodosFormularios(), 400, 250, "Filial:");
        popularFormularios();
    }

    /**
     * Cria apenas um painel de formulário com os campos de Nome, Localização e Id e o insere em uma lista
     *
     * @return uma lista contendo o único painel a ser adicionado
     */
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

    /**
     * Exclui a filial escolhida da determinada ControleEmpresa
     *
     * @throws ElementoInexistenteException caso o elemento a ser exlcuido não exista
     */
    @Override
    protected void excluirElemento() throws ElementoInexistenteException {
        controleEmpresa.excluirFilial(filialEscolhida);
        pesquisaView.refresh();
    }

    /**
     * Adiciona uma filial a determinada ControleEmpresa
     *
     * @throws IdRepetidoException caso o Id da nova filial já seja existente na classe controle
     */
    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        controleEmpresa.adicionarFilial(
                valorNome.getText(),
                valorLocalizacao.getText(),
                Integer.parseInt(valorId.getText())
        );
    }

    /**
     * Atualiza os campos da Filial escolhida baseado nos valores presentes nos formulários
     *
     * @throws IdRepetidoException          Caso o novo Id seja existente na ControleEmpresa
     * @throws ElementoInexistenteException Caso a filial a ser modificada não exista na ControleEmpresa
     */
    @Override
    protected void atualizarElemento() throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.atualizarFilial(
                valorNome.getText(),
                valorLocalizacao.getText(),
                Integer.parseInt(valorId.getText()),
                filialEscolhida);
    }

    /**
     * Insere os valores da Filial escolhida nos formulários
     */
    @Override
    protected void popularFormularios() {
        valorLocalizacao.setText(filialEscolhida.getLocal());
        valorNome.setText(filialEscolhida.getNome());
        valorId.setText(String.valueOf(filialEscolhida.getId()));
    }


}
