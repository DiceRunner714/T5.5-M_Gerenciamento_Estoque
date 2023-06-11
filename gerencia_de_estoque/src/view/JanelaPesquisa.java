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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class JanelaPesquisa {
    private static ControleEmpresa controleEmpresa;
    private final JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private final ModosPesquisa modo;
    private final JTextField valorPesquisaNomeDeItem = new JTextField();
    private JFrame janela;
    private ControleEstoque controleEstoque;
    private JList<Filial> listaFiliais;
    private JList<Item> listaEstoque;
    private JButton botaoVerEstoque;
    private JButton botaoVerDetalhes;
    private JButton botaoAdicionar;

    public JanelaPesquisa(ControleEmpresa controleEmpresa, ModosPesquisa modo) {
        this.modo = modo;
        JanelaPesquisa.controleEmpresa = controleEmpresa;
        switch (modo) {
            // operação 1 - filial
            case LISTAR_FILIAIS -> iniciarJanelaFiliais();
            case LISTAR_ESTOQUE_GERAL -> {
                this.controleEstoque = controleEmpresa;
                iniciarJanelaEstoque("Estoque", "Estoque geral");
            }
        }
    }

    // Construtor para visualização de estoque de uma filial
    public JanelaPesquisa(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.modo = ModosPesquisa.LISTAR_ESTOQUE_FILIAL;
        JanelaPesquisa.controleEmpresa = controleEmpresa;
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialEscolhida);
        String tituloJanela = ("Visualizando Estoque de: " + filialEscolhida);
        String tituloPainel = ("Estoque de " + filialEscolhida);
        iniciarJanelaEstoque(tituloJanela, tituloPainel);
    }

    private void iniciarJanelaFiliais() {
        janela = new JFrame("Filial");

        botaoAdicionar = new JButton("Adicionar filial");
        botaoVerDetalhes = new JButton("Ver filial");
        botaoVerEstoque = new JButton("Ver estoque");

        // Painel principal
        listaFiliais = new JList<>(controleEmpresa.getFiliais().toArray(new Filial[0]));
        listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFiliais.setVisibleRowCount(50);

       janela.add(new PainelPesquisa("Pesquisa em filiais", listaFiliais,
                new JButton[]{botaoAdicionar, botaoVerDetalhes, botaoVerEstoque}));

        botaoVerEstoque.addActionListener(new listarFiliaisListener());
        botaoVerDetalhes.addActionListener(new listarFiliaisListener());
        botaoAdicionar.addActionListener(new listarFiliaisListener());

        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    private void iniciarJanelaEstoque(String tituloJanela, String tituloPainel) {
        // Definição dos componentes
        valorPesquisaNomeDeItem.getDocument().addDocumentListener(new FiltrosListener());

        botaoAdicionar = new JButton("Adicionar Item");
        botaoVerDetalhes = new JButton("Ver Item");
        switch (modo) {
            case LISTAR_ESTOQUE_GERAL ->{
                botaoVerDetalhes.addActionListener(new listarEstoqueGeralListener());
                botaoAdicionar.addActionListener(new listarEstoqueGeralListener());
            }
            case LISTAR_ESTOQUE_FILIAL -> {
                botaoAdicionar.addActionListener(new listarEstoqueFilialListener());
                botaoAdicionar.addActionListener(new listarEstoqueFilialListener());
            }
        }

        listaEstoque = new JList<>(
                controleEstoque.getEstoque().toArray(new Item[0])
        );
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
        janela = new JFrame(tituloJanela);
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

    public void refresh() {
        switch (modo) {
            case LISTAR_FILIAIS -> {
                listaFiliais.setListData(
                        controleEmpresa.getFiliais().toArray(new Filial[0])
                );
                listaFiliais.updateUI();
            }
            case LISTAR_ESTOQUE_GERAL, LISTAR_ESTOQUE_FILIAL -> {
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
            // Aplicar dois filtros, primeiro o de pesquisa por nome e depois o de itens vazios
        }
    }

    // --POP UPS--
    private void mensagemErroEscolhaVazia() {
        String mensagem = switch (modo) {
            case LISTAR_FILIAIS -> "Erro de escolha: uma filial não foi selecionada";
            case LISTAR_ESTOQUE_GERAL -> "Erro de escolha: um item não foi selecionado";
            default -> null;
        };
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }

    private class listarFiliaisListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            try {
                if (src == botaoAdicionar) {
                    new DetalheFilial(controleEmpresa, JanelaPesquisa.this);
                } else {
                    Filial filialSelecionada = Objects.requireNonNull(listaFiliais.getSelectedValue());
                    if (src == botaoVerDetalhes) {
                        new DetalheFilial(controleEmpresa, JanelaPesquisa.this, filialSelecionada);
                    } else if (src == botaoVerEstoque) {
                        new JanelaPesquisa(controleEmpresa, listaFiliais.getSelectedValue());
                    }
                }
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
    }

    private class listarEstoqueGeralListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            try {
                if (src == botaoAdicionar) {
                    new DetalheItem(controleEmpresa, JanelaPesquisa.this);
                } else if (src == botaoVerDetalhes) {
                    Item itemEscolhido = listaEstoque.getSelectedValue();
                    new DetalheItem(controleEmpresa, JanelaPesquisa.this, itemEscolhido);
                }
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
    }

    private class listarEstoqueFilialListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            try {
                if (src == botaoVerDetalhes) {
                    Item itemEscolhido = listaEstoque.getSelectedValue();
                    new DetalheItem(controleEmpresa, JanelaPesquisa.this, itemEscolhido);
                } else if (src == botaoAdicionar) {
                    ControleEstoqueFilial filialGerenciada = (ControleEstoqueFilial) controleEstoque;
                    new DetalheItem(controleEmpresa, JanelaPesquisa.this, filialGerenciada);
                }
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
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


}
