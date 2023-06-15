package view;

import controle.ControleEmpresa;
import controle.LeitorEstoque;
import controle.ControleEstoqueFilial;
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
public class PesquisaViewEstoque extends PesquisaView {
    private final JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private final ModosPesquisa modo;
    private final JTextField valorPesquisaNomeItem = new JTextField();
    private final LeitorEstoque leitorEstoque;
    private JList<Item> listaEstoque;

    public PesquisaViewEstoque(ControleEmpresa controleEmpresa) {
        modo = ModosPesquisa.LISTAR_ESTOQUE_GERAL;
        this.controleEmpresa = controleEmpresa;
        this.leitorEstoque = controleEmpresa;
        iniciarJanelaEstoque("Estoque", "Estoque geral");

    }

    public PesquisaViewEstoque(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        modo = ModosPesquisa.LISTAR_ESTOQUE_FILIAL;
        leitorEstoque = new ControleEstoqueFilial(controleEmpresa, filialEscolhida);
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaEstoque(
                "Visualizando Estoque de: " + filialEscolhida,
                "Estoque de " + filialEscolhida
        );
    }

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

    @Override
    protected void visualizarElemento() {
        Item itemEscolhido = listaEstoque.getSelectedValue();
        new DetalheViewItem(controleEmpresa, PesquisaViewEstoque.this, itemEscolhido);
    }

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

    // --POP UPS--
    @Override
    protected void mensagemErroEscolhaVazia() {
        String mensagem = "Erro de escolha: um item não foi selecionado";
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }


}
