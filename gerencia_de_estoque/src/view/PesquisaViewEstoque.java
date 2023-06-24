package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.LeitorEstoque;
import modelo.Filial;
import modelo.Item;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

/**
 * Classe PesquisaViewEstoque representa janela destinada para pesquisa de 
 * itens no estoque geral ou de uma filial escolhida, herda de PesquisaView
 * @author André Emanuel Bispo da Silva
 * @author Cássio Sousa dos Reis
 * @version 1.0
 * @see PesquisaView
 * @since 2023
 *
 */
public class PesquisaViewEstoque extends PesquisaView {
    private final JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private final ModoListarEstoque modo;
    private final JTextField valorPesquisaNomeItem = new JTextField();
    private final LeitorEstoque leitorEstoque;
    private JList<Item> listaEstoque;

    /**
     * Construtor cria uma janela que contém o estoque geral da empresa
     * @param controleEmpresa Instância de ControleEmpresa, necessário
     * para pesquisar o estoque geral da empresa
     */
    public PesquisaViewEstoque(ControleEmpresa controleEmpresa) {
        modo = ModoListarEstoque.LISTAR_ESTOQUE_GERAL;
        this.controleEmpresa = controleEmpresa;
        this.leitorEstoque = controleEmpresa;
        iniciarJanelaEstoque("Estoque", "Estoque geral");

    }
    
    /**
     * Construtor cria uma janela que contém o estoque da filial escolhida,
     * utiliza a classe ControleEstoqueFilial para realizar a leitura do estoque
     * @param controleEmpresa Instância de ControleEmpresa, necessário
     * para pesquisar o estoque da filial escolhida
     * @param filialEscolhida Filial cujo estoque será pesquisado
     */
    public PesquisaViewEstoque(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        modo = ModoListarEstoque.LISTAR_ESTOQUE_FILIAL;
        leitorEstoque = new ControleEstoqueFilial(controleEmpresa, filialEscolhida);
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaEstoque(
                "Visualizando Estoque de: " + filialEscolhida,
                "Estoque de " + filialEscolhida
        );
    }

    /**
     * Define os componentes e cria o painel de pesquisa de estoque
     * @param tituloJanela Título da janela
     * @param tituloPainel Título do painel
     */
    private void iniciarJanelaEstoque(String tituloJanela, String tituloPainel) {
        // Definição dos componentes
        valorPesquisaNomeItem.getDocument().addDocumentListener(new FiltrosListener());

        botaoAdicionar = new JButton("Adicionar Item");
        botaoVerDetalhes = new JButton("Ver Item");
        botaoVerDetalhes.addActionListener(new ManipularElementoListener());
        botaoAdicionar.addActionListener(new ManipularElementoListener());

        listaEstoque = new JList<>(leitorEstoque.getEstoque().toArray(new Item[0]));
        listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEstoque.setVisibleRowCount(10);

        filtroEstoqueVazio.addItemListener(new FiltrosListener());

        // -- CRIAÇÃO DO PAINEL DE PESQUISA POR NOME --
        // Nome: [                  ]
        JPanel painelPesquisa = new JPanel();
        painelPesquisa.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.1;
        c.anchor = GridBagConstraints.LINE_START;
        painelPesquisa.add(new JLabel("Nome: "), c);

        c.weightx = 0.9;
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        painelPesquisa.add(valorPesquisaNomeItem, c);

        // Criar janela de pesquisa
        JFrame janela = new JFrame(tituloJanela);
        janela.add(
                new PainelPesquisa(
                        tituloPainel,
                        listaEstoque,
                        Arrays.asList(botaoAdicionar, botaoVerDetalhes),
                        Arrays.asList(filtroEstoqueVazio, painelPesquisa)
                )
        );

        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);

    }
    /**
     * Atualiza a listagem de itens do estoque na interface gráfica 
     */
    @Override
    public void refresh() {
        List<Item> estoqueApenasVazios;
        String nomePesquisado = valorPesquisaNomeItem.getText();
        List<Item> estoquePrincipal = leitorEstoque.buscarItensParcial(nomePesquisado, false);

        if (filtroEstoqueVazio.isSelected()) {
            estoqueApenasVazios = leitorEstoque.getItensVazios(estoquePrincipal);
            listaEstoque.setListData(estoqueApenasVazios.toArray(new Item[0]));
        } else {
            listaEstoque.setListData(estoquePrincipal.toArray(new Item[0]));
        }
        listaEstoque.updateUI();
    }

    // --POP UPS--
    /**
     * Gera uma mensagem de erro quando o usuário não selecionou nenhum item,
     * mas tenta visualizar detalhes através do botão de ver item.
     */
    @Override
    protected void mensagemErroEscolhaVazia() {
        String mensagem = "Erro de escolha: um item não foi selecionado";
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Chama método que cria tela para adicionar item ao estoque
     */
    @Override
    protected void adicionarElemento() {
        switch (modo) {
            case LISTAR_ESTOQUE_FILIAL -> {
                ControleEstoqueFilial filialGerenciada = (ControleEstoqueFilial) leitorEstoque;
                new DetalheViewItem(controleEmpresa, PesquisaViewEstoque.this, filialGerenciada);
            }
            case LISTAR_ESTOQUE_GERAL -> {
                new DetalheViewItem(controleEmpresa, PesquisaViewEstoque.this);
            }
        }
    }
    
    /**
     * Chama método que cria tela para visualizar ou modificar um item
     */
    @Override
    protected void visualizarElemento() {
        Item itemEscolhido = listaEstoque.getSelectedValue();
        new DetalheViewItem(controleEmpresa, PesquisaViewEstoque.this, itemEscolhido);
    }
    
    /**
     * Modos de listagem de estoque
     * @author André Emanuel Bispo da Silva
     * @version 1.0
     * @since 2023
     */
    private enum ModoListarEstoque {
        LISTAR_ESTOQUE_GERAL,
        LISTAR_ESTOQUE_FILIAL,
    }
    
    /**
     * Define os eventos dos filtros, atualizando a janela quando ocorre alguma
     * mudança
     * @author André Emanuel Bispo da Silva
     * @version 1.0
     * @since 2023
     */
    private class FiltrosListener implements DocumentListener, ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            refresh();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            refresh();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            refresh();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            refresh();
        }
    }


}
