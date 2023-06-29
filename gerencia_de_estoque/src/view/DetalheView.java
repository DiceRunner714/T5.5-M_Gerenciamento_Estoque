package view;

import controle.ControleEmpresa;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;

/**
 * Classe abstrata detalheView representa a visualização de detalhes de um objeto qualquer,
 * com um ou mais painéis contendo formulários, que permitem a atualização e exclusão de objetos
 * para uma lista, ou criação de um objeto novo e sua inserção.
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
public abstract class DetalheView {
    protected final ControleEmpresa controleEmpresa;
    protected final JFrame janela = new JFrame();
    protected final PesquisaView pesquisaView;
    protected final ModosDetalhe modo;
    protected final JButton botaoAtualizar = new JButton("Atualizar");
    protected final JButton botaoExcluir = new JButton("Excluir");
    protected final JButton botaoAdicionar = new JButton("Adicionar");
    protected final JButton botaoCancelar = new JButton("Cancelar");


    /**
     * Construtor define popup de confirmação de saída e campos protegidos
     *
     * @param modo            Modo de operação da janela detalhe
     * @param pesquisaView    Janela de pesquisa da qual advém esse objeto
     * @param controleEmpresa classe para permitir as operações de controle
     */
    protected DetalheView(ModosDetalhe modo, PesquisaView pesquisaView, ControleEmpresa controleEmpresa) {
        janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mensagemConfirmarSaida()) {
                    janela.dispose();
                }
            }
        });
        this.modo = modo;
        this.pesquisaView = pesquisaView;
        this.controleEmpresa = controleEmpresa;
    }

    /**
     * Cria a janela detalhe com painéis de formulários colocados na vertical sequencialmente
     *
     * @param formularios    Painéis com os formulários representando detalhes do objeto
     * @param width          largura da janela
     * @param height         altura da janela
     * @param tituloDaJanela título da janela
     */
    protected void criarJanela(Collection<? extends JComponent> formularios, int width, int height, String tituloDaJanela) {
        janela.setTitle(tituloDaJanela);
        janela.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // FORMULARIOS PRINCIPAIS
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        for (JComponent formulario : formularios) {
            janela.add(formulario, c);
            c.gridy++;
        }

        // PAINEL DE BOTÕES
        c.weighty = 0.6;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.insets = new Insets(10, 0, 0, 5);
        janela.add(
                new PainelDetalheBotoes(
                        botaoAdicionar,
                        botaoCancelar, botaoAtualizar,
                        botaoExcluir,
                        modo,
                        new GerenciarElementoListener()
                )
                , c);

        // HABILITAR JANELA
        janela.setSize(width, height);
        janela.setResizable(false);
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);

    }

    /**
     * Defina a criação todos os formulários necessários e os agrupa em uma lista para facilitar a
     * criação da janela
     *
     * @return uma lista com os painéis de formlários
     */
    abstract protected List<JComponent> agruparTodosFormularios();

    /**
     * Define a operação a ser executada ao excluir um elemento
     *
     * @throws ElementoInexistenteException caso o elemento a ser excluido não exista na classe controle
     */
    abstract protected void excluirElemento() throws ElementoInexistenteException;

    /**
     * Define a operação a ser executada ao adicionar um elemento
     *
     * @throws IdRepetidoException caso o elemento a ser adicionado tenha um Id já existente na classe controle
     */
    abstract protected void adicionarElemento() throws IdRepetidoException;

    /**
     * Define a operação a ser executada ao modificar os campos de um elemento
     *
     * @throws IdRepetidoException          caso o novo Id do elemento já seja existente na classe controle
     * @throws ElementoInexistenteException caso o elemento a ser atualizado não exista na classe controle
     */
    abstract protected void atualizarElemento() throws IdRepetidoException, ElementoInexistenteException;

    /**
     * Define a operação a ser executada ao carregar os detalhes de um item aos formulários
     */
    abstract protected void popularFormularios();

    /**
     * Processa os dados dos formulários dependendo do modo da janela atual, executa
     * uma atualização dos dados da PesquisaView a qual essa detalhe pertence
     *
     * @throws IdRepetidoException   caso o item a ser adicionado ou atualizado tenha um Id já existente na classe controle
     * @throws NumberFormatException caso a formatação dos dados numéricos esteja incorreta
     * @throws NullPointerException  caso algum formulário não tenha sido preenchido
     */
    protected void enviarFormularios() throws IdRepetidoException, NumberFormatException, NullPointerException {
        switch (modo) {
            case ADICIONAR -> adicionarElemento();
            case EDITAR -> {
                try {
                    atualizarElemento();
                } catch (ElementoInexistenteException e) {
                    mensagemElementoInexistente(e);
                    pesquisaView.refresh();
                    janela.dispose();
                }
            }

        }
        pesquisaView.refresh();
    }

    /**
     * Envia uma mensagem de confirmação de saída para evitar perda de dados
     *
     * @return true se o usuário confirmou a saída, caso contrário, false
     */
    protected boolean mensagemConfirmarSaida() {
        String[] botoes = {"Sim", "Não"};
        int escolhaPrompt = JOptionPane.showOptionDialog(null,
                "Confirmar saída? Dados não salvos serão descartados",
                "Confirmar saída",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                botoes,
                botoes[1]);
        return escolhaPrompt == JOptionPane.YES_OPTION;
    }

    /**
     * Envia uma mensagem de confirmação de exclusão de objeto para evitar perda de dados
     *
     * @return true se o usuário confirmou a exclusão, caso contrário, false
     */
    protected boolean mensagemConfirmarExclusao() {
        String[] botoes = {"Sim", "Não"};
        int escolhaPrompt = JOptionPane.showOptionDialog(null,
                "Confirmar exclusão?",
                "Confirmar saída",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                botoes,
                botoes[1]);
        return escolhaPrompt == JOptionPane.YES_OPTION;
    }

    /**
     * Mensagem representando o caso de o
     * elemento a ser modificado ou excluido não exista na classe controle
     *
     * @param e exceção jogada ao tentar executar a modificação ou exclusão
     */
    protected void mensagemElementoInexistente(ElementoInexistenteException e) {
        JOptionPane.showMessageDialog(null,
                "Elemento inexistente: " + e.getMessage(),
                "Elemento inexistente", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mensagem representando o caso de houver um erro de formatação numérica
     */
    protected void mensagemErrodeFormatacao() {
        JOptionPane.showMessageDialog(null,
                "Erro de formatação: assegure-se que valores numéricos foram inseridos corretamente.",
                "Erro de formatação", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mensagem representando o caso de houver um erro de formulário vazio
     */
    protected void mensagemErroFormularioVazio() {
        JOptionPane.showMessageDialog(null,
                "Erro de entrada: assegure-se que todos os formulários foram preenchidos.",
                "Erro de entrada", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mensagem representando o caso de houver um erro de Id repetido na classe controle
     *
     * @param e3 exceção jogada ao tentar executar a operação de controle
     */
    protected void mensagemErroIdRepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(
                null, e3.getMessage(), "Erro de indentificação", JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Listener representado o gerenciamento de eventos dos quatro botões principais
     *
     * @author André Emanuel bispo da Silva
     * @version 1.0
     * @since 2023
     */
    protected class GerenciarElementoListener implements ActionListener {
        /**
         * Override do método ActionPerformed da interface ActionListener,
         * escolhe a operação correta dependendo do botão selecionado:
         * adicionar, cancelar, atualizar ou excluir
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();

            if (src == botaoAtualizar || src == botaoAdicionar) {
                try {
                    enviarFormularios();
                } catch (NumberFormatException e1) {
                    mensagemErrodeFormatacao();
                    e1.printStackTrace();
                } catch (NullPointerException e2) {
                    mensagemErroFormularioVazio();
                    e2.printStackTrace();
                } catch (IdRepetidoException e3) {
                    mensagemErroIdRepetido(e3);
                    e3.printStackTrace();
                }
            } else {
                if (src == botaoExcluir) {
                    if (mensagemConfirmarExclusao()) {
                        try {
                            excluirElemento();
                            janela.dispose();
                        } catch (ElementoInexistenteException e1) {
                            mensagemElementoInexistente(e1);
                        }
                    }
                } else {
                    if (mensagemConfirmarSaida()) {
                        janela.dispose();
                    }
                }
            }
        }
    }
}
