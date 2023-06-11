package view;

import controle.ControleEmpresa;
import controle.ControleEstoque;
import controle.ControleEstoqueFilial;
import modelo.Filial;
import modelo.Item;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class PesquisaViewEstoque extends PesquisaView {
    private final JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private final ModosPesquisa modo;
    private final JTextField valorPesquisaNomeDeItem = new JTextField();
    private final ControleEstoque controleEstoque;
    private JList<Item> listaEstoque;

    public PesquisaViewEstoque(ControleEmpresa controleEmpresa) {
        modo = ModosPesquisa.LISTAR_ESTOQUE_GERAL;
        this.controleEmpresa = controleEmpresa;
        this.controleEstoque = controleEmpresa;
        iniciarJanelaEstoque("Estoque", "Estoque geral");

    }

    public PesquisaViewEstoque(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        modo = ModosPesquisa.LISTAR_ESTOQUE_FILIAL;
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialEscolhida);
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaEstoque(
                "Visualizando Estoque de: " + filialEscolhida,
                "Estoque de " + filialEscolhida
        );
    }

    private void iniciarJanelaEstoque(String tituloJanela, String tituloPainel) {
        // Definição dos componentes
        valorPesquisaNomeDeItem.getDocument().addDocumentListener(new FiltrosListener());

        botaoAdicionar = new JButton("Adicionar Item");
        botaoVerDetalhes = new JButton("Ver Item");
        botaoVerDetalhes.addActionListener(new ManipularElementoListener());
        botaoAdicionar.addActionListener(new ManipularElementoListener());

        listaEstoque = new JList<>(controleEstoque.getEstoque().toArray(new Item[0]));
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
        painelPesquisa.add(valorPesquisaNomeDeItem, c);

        // Criar janela de pesquisa
        JFrame janela = new JFrame(tituloJanela);
        janela.add(
                new PainelPesquisa(
                        tituloPainel,
                        listaEstoque,
                        new JButton[]{botaoAdicionar, botaoVerDetalhes},
                        new JComponent[]{filtroEstoqueVazio, painelPesquisa}
                )
        );

        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    @Override
    public void refresh() {
            ArrayList<Item> estoqueApenasVazios;
            String nomePesquisado = valorPesquisaNomeDeItem.getText();
            ArrayList<Item> estoquePrincipal = controleEstoque.buscarItensParcial(nomePesquisado, false);

            if (filtroEstoqueVazio.isSelected()) {
                estoqueApenasVazios = controleEstoque.getItensVazios(estoquePrincipal);
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
                ControleEstoqueFilial filialGerenciada = (ControleEstoqueFilial) controleEstoque;
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
