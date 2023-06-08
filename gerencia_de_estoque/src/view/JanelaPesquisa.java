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

public class JanelaPesquisa implements ActionListener, ItemListener {
    private static ControleEmpresa controleEmpresa;
    private JLabel textoGenerico;
    private JList<Filial> listaFiliais;
    private JList<Item> listaEstoque;
    private JButton botaoVerEst;
    private JButton botaoVerDetalhes;
    private JFrame janela;
    private JCheckBox filtroEstoqueVazio;
    private JButton botaoAdicionar;
    private ArrayList<Item> estoque;
    private ArrayList<Filial> filiais;
    private int modo;

    public JanelaPesquisa(ControleEmpresa controleEmpresa, int modo) {
        this.modo = modo;
        this.controleEmpresa = controleEmpresa;

        // Escolha de modo
        switch (modo) {
            // operação 1 - filial
            case 1:

                filiais = controleEmpresa.getFiliais();

                textoGenerico = new JLabel("Pesquisar Filial");
                textoGenerico.setBounds(20, 10, 150, 20);
                textoGenerico.setFont(new Font("Arial", Font.BOLD, 20));

                listaFiliais = new JList<>(filiais.toArray(new Filial[filiais.size()]));
                listaFiliais.setBounds(20, 50, 200, 200);
                listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listaFiliais.setVisibleRowCount(10);

                botaoAdicionar = new JButton("Adicionar filial");
                botaoAdicionar.setBounds(60, 270, 120, 30);

                botaoVerDetalhes = new JButton("Ver filial");
                botaoVerDetalhes.setBounds(250, 110, 120, 30);

                botaoVerEst = new JButton("Ver estoque");
                botaoVerEst.setBounds(250, 170, 120, 30);

                janela = new JFrame("Filial");
                janela.setLayout(null);
                janela.add(botaoVerEst);
                janela.add(listaFiliais);

                botaoVerEst.addActionListener(this);

                break;
            case 2:

                estoque = controleEmpresa.getEstoque();
                // Operação 2 - estoque
                textoGenerico = new JLabel("Pesquisar estoque");
                textoGenerico.setBounds(20, 10, 200, 20);
                textoGenerico.setFont(new Font("Arial", Font.BOLD, 20));

                filtroEstoqueVazio = new JCheckBox("Filtro de estoque vazio");
                filtroEstoqueVazio.setBounds(20, 40, 200, 20);

                listaEstoque = new JList<>(estoque.toArray(new Item[estoque.size()]));
                listaEstoque.setBounds(20, 70, 200, 200);
                listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                listaEstoque.setVisibleRowCount(10);

                botaoVerDetalhes = new JButton("Ver item");
                botaoVerDetalhes.setBounds(250, 110, 120, 30);

                botaoAdicionar = new JButton("Adicionar item");
                botaoAdicionar.setBounds(250, 170, 120, 30);

                filtroEstoqueVazio.addItemListener(this);

                janela = new JFrame("Filiais");
                janela.setLayout(null);
                janela.add(listaEstoque);
                janela.add(filtroEstoqueVazio);


                break;
        }
        janela.add(textoGenerico);
        janela.add(botaoAdicionar);
        janela.add(botaoVerDetalhes);

        botaoVerDetalhes.addActionListener(this);
        botaoAdicionar.addActionListener(this);

        janela.setSize(400, 400);
        janela.setVisible(true);

    }

    public void refresh() {
        switch (modo) {
            //Modo filial
            case 1:
                listaFiliais.setListData(filiais.toArray(new Filial[filiais.size()]));
                listaFiliais.updateUI();
                break;

            //Modo estoque
            case 2:
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


        switch (modo) {
            case 1:
                if (src == botaoAdicionar) {
                    new DetalheFilial(controleEmpresa, this);
                } else {
                    try {
                        Filial filialSelecionada = listaFiliais.getSelectedValue();
                        if (src == botaoVerDetalhes) {
                            new DetalheFilial(controleEmpresa, this, filialSelecionada);
                        } else if (src == botaoVerEst) {
                            // TODO: adicionar visualização de estoque
                        }
                    } catch (NullPointerException exc1) {
                        mensagemErroEscolhaVazia();
                    }
                }
                break;
            case 2:
                // Modo estoque
                if (src == botaoVerDetalhes) {
                    new DetalheItem();
                    // new DetalheItem(listaFiliais.getSelected);
                } else if (src == botaoAdicionar) {
                    new DetalheItem();
                }
                break;
        }

    }

    // Não sei se devo usar um actionPerformed ou ItemstateChanged pro checkbox
    // TODO: actionPerformed ou ItemstateChanged?
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object src = e.getSource();
        switch (modo) {
            case 2:
                if (src == filtroEstoqueVazio) {
                    refresh();
                }
                break;
        }
    }

    // --POP UPS--
    public void mensagemErroEscolhaVazia() {
        String mensagem = null;
        switch (modo) {
            case 1:
                mensagem = "Erro de escolha: uma filial não foi selecionada";
                break;
            case 2:
                mensagem = "Erro de escolha: um item não foi selecionado";
                break;
        }
        JOptionPane.showMessageDialog(null,
                mensagem,
                "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }


}
