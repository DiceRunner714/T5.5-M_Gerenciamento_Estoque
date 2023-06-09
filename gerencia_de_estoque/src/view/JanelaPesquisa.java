package view;

import controle.ControleEmpresa;
import modelo.Filial;
import modelo.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class JanelaPesquisa implements ActionListener, ItemListener {
    private static ControleEmpresa controleEmpresa;
    private JFrame janela;
    private JPanel malhaBotoes = new JPanel();
    private JPanel painelBotoes = new JPanel();
    private JLabel titulo;
    private JList<Filial> listaFiliais;
    private JList<Item> listaEstoque;
    private JButton botaoVerEstoque;
    private JButton botaoVerDetalhes;
    private JCheckBox filtroEstoqueVazio = new JCheckBox("Filtrar por estoque vazio");
    private JButton botaoAdicionar;
    private ArrayList<Item> estoque;
    private ArrayList<Filial> filiais;
    private Modos modo;

    public JanelaPesquisa(ControleEmpresa controleEmpresa, Modos modo) {
        this.modo = modo;
        this.controleEmpresa = controleEmpresa;


        GridBagConstraints c = new GridBagConstraints();
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
                listaFiliais = new JList<>(filiais.toArray(new Filial[filiais.size()]));
                listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listaFiliais.setVisibleRowCount(50);

                new JanelaPesquisaBuilder(janela, modo, listaFiliais,
                        new JButton[]{botaoAdicionar, botaoVerDetalhes, botaoVerEstoque});

                botaoVerEstoque.addActionListener(this);

            }

            case LISTAR_ESTOQUE_GERAL -> {

                estoque = controleEmpresa.getEstoque();
//
//                // Malha de botões
//                malhaBotoes.setLayout(new GridLayout(0, 1, 0, 10));
                janela = new JFrame("Item");

                botaoAdicionar = new JButton("Adicionar Item");
                botaoVerDetalhes = new JButton("Ver Item");

                listaEstoque = new JList<>(estoque.toArray(new Item[estoque.size()]));
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
            case LISTAR_FILIAIS:
                listaFiliais.setListData(filiais.toArray(new Filial[filiais.size()]));
                listaFiliais.updateUI();
                break;

            case LISTAR_ESTOQUE_GERAL:
                ArrayList<Item> estoqueEmDisplay;
                if (filtroEstoqueVazio.isSelected()) {
                    estoqueEmDisplay = controleEmpresa.getEstoqueVazio();
                } else {
                    estoqueEmDisplay = controleEmpresa.getEstoque();
                }
                listaEstoque.setListData(estoqueEmDisplay.toArray(new Item[estoque.size()]));
                listaEstoque.updateUI();
                break;
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
        switch (modo) {
            case LISTAR_ESTOQUE_GERAL:
                if (src == filtroEstoqueVazio) {
                    refresh();
                }
                break;
        }
    }

    // --POP UPS--
    private void mensagemErroEscolhaVazia() {
        String mensagem = null;
        switch (modo) {
            case LISTAR_FILIAIS:
                mensagem = "Erro de escolha: uma filial não foi selecionada";
                break;
            case LISTAR_ESTOQUE_GERAL:
                mensagem = "Erro de escolha: um item não foi selecionado";
                break;
        }
        JOptionPane.showMessageDialog(null,
                mensagem,
                "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }


}
