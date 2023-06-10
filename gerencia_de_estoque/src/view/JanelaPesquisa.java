package view;

import controle.ControleEmpresa;
import modelo.Filial;
import modelo.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class JanelaPesquisa implements ActionListener, ItemListener {
    private static ControleEmpresa controleEmpresa;
    private final JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private final Modos modo;
    private JFrame janela;
    private JList<Filial> listaFiliais;
    private JList<Item> listaEstoque;
    private JButton botaoVerEstoque;
    private JButton botaoVerDetalhes;
    private JButton botaoAdicionar;
    private ArrayList<Item> estoque;
    private ArrayList<Filial> filiais;

    public JanelaPesquisa(ControleEmpresa controleEmpresa, Modos modo) {
        this.modo = modo;
        JanelaPesquisa.controleEmpresa = controleEmpresa;


        // Escolha de modo
        switch (modo) {
            // operação 1 - filial
            case LISTAR_FILIAIS -> {
                filiais = controleEmpresa.getFiliais();

                janela = new JFrame("Filial");

                botaoAdicionar = new JButton("Adicionar filial");
                botaoVerDetalhes = new JButton("Ver filial");
                botaoVerEstoque = new JButton("Ver estoque");

                // Painel principal
                listaFiliais = new JList<>(filiais.toArray(new Filial[0]));
                listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listaFiliais.setVisibleRowCount(50);

                new JanelaPesquisaBuilder(janela, modo, listaFiliais,
                        new JButton[]{botaoAdicionar, botaoVerDetalhes, botaoVerEstoque});

                botaoVerEstoque.addActionListener(this);

            }

            case LISTAR_ESTOQUE_GERAL -> {

                estoque = controleEmpresa.getEstoque();

                janela = new JFrame("Item");

                botaoAdicionar = new JButton("Adicionar Item");
                botaoVerDetalhes = new JButton("Ver Item");

                listaEstoque = new JList<>(estoque.toArray(new Item[0]));
                listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listaEstoque.setVisibleRowCount(10);

                new JanelaPesquisaBuilder(janela, modo, listaEstoque,
                        new JButton[]{botaoAdicionar, botaoVerDetalhes},
                        new JComponent[]{filtroEstoqueVazio});

                filtroEstoqueVazio.addItemListener(this);
            }
        }

        botaoVerDetalhes.addActionListener(this);
        botaoAdicionar.addActionListener(this);

        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);

    }

    public void refresh() {
        switch (modo) {
            case LISTAR_FILIAIS -> {
                listaFiliais.setListData(filiais.toArray(new Filial[0]));
                listaFiliais.updateUI();
            }
            case LISTAR_ESTOQUE_GERAL -> {
                ArrayList<Item> estoqueEmDisplay;
                if (filtroEstoqueVazio.isSelected()) {
                    estoqueEmDisplay = controleEmpresa.getEstoqueVazio();
                } else {
                    estoqueEmDisplay = controleEmpresa.getEstoque();
                }
                listaEstoque.setListData(estoqueEmDisplay.toArray(new Item[estoque.size()]));
                listaEstoque.updateUI();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        try {
            switch (modo) {
                case LISTAR_FILIAIS -> {
                    if (src == botaoAdicionar) {
                        new DetalheFilial(controleEmpresa, this);
                    } else {
                        Filial filialSelecionada = listaFiliais.getSelectedValue();
                        if (src == botaoVerDetalhes) {
                            new DetalheFilial(controleEmpresa, this, filialSelecionada);
                        } else if (src == botaoVerEstoque) {
                            // TODO: adicionar visualização de estoque
                        }
                    }
                }
                case LISTAR_ESTOQUE_GERAL -> {
                    if (src == botaoAdicionar) {
                        new DetalheItem(controleEmpresa, this);
                    } else if (src == botaoVerDetalhes) {
                        Item itemEscolhido = listaEstoque.getSelectedValue();
                        new DetalheItem(controleEmpresa, this, itemEscolhido);
                    }
                }
            }
        } catch (NullPointerException | NoSuchElementException exc) {
            mensagemErroEscolhaVazia();
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object src = e.getSource();
        if (Objects.requireNonNull(modo) == Modos.LISTAR_ESTOQUE_GERAL) {
            if (src == filtroEstoqueVazio) {
                refresh();
            }
        }
    }

    // --POP UPS--
    private void mensagemErroEscolhaVazia() {
        String mensagem = switch (modo) {
            case LISTAR_FILIAIS -> "Erro de escolha: uma filial não foi selecionada";
            case LISTAR_ESTOQUE_GERAL -> "Erro de escolha: um item não foi selecionado";
            default -> null;
        };
        JOptionPane.showMessageDialog(null,
                mensagem,
                "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }


}
