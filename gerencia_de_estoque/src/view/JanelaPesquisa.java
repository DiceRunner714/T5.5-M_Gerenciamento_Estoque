package view;

import controle.ControleEstoque;
import controle.ControleFilial;
import modelo.Filial;
import modelo.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class JanelaPesquisa implements ActionListener {
    private JLabel textoGenerico;
    private JList<Filial> listaFiliais;
    private JList<Item> listaEstoque;
    private JButton botaoVerEst;
    private JButton botaoVerDetalhes;
    private JFrame janela;
    private JCheckBox filtroEstoqueVazio;
    private JButton botaoAdicionar;
    private ControleFilial controleFilial;
    private ControleEstoque controleEstoque;

    public JanelaPesquisa(ControleFilial controleFilial) {

        this.controleFilial = controleFilial;

        textoGenerico = new JLabel("Pesquisar Filial");
        textoGenerico.setBounds(20, 10, 150, 20);
        textoGenerico.setFont(new Font("Arial", Font.BOLD, 20));

        listaFiliais = new JList<>();
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
        janela.add(textoGenerico);
        janela.add(listaFiliais);
        janela.add(botaoAdicionar);
        janela.add(botaoVerDetalhes);
        janela.add(botaoVerEst);

        botaoVerDetalhes.addActionListener(this);

        janela.setSize(400, 400);
        janela.setVisible(true);

    }

    public JanelaPesquisa(ControleEstoque controleEstoque) {

        this.controleEstoque = controleEstoque;

        textoGenerico = new JLabel("Pesquisar estoque");
        textoGenerico.setBounds(20, 10, 200, 20);
        textoGenerico.setFont(new Font("Arial", Font.BOLD, 20));

        filtroEstoqueVazio = new JCheckBox("Filtro de estoque vazio");
        filtroEstoqueVazio.setBounds(20, 40, 200, 20);

        listaEstoque = new JList<>();
        listaEstoque.setBounds(20, 70, 200, 200);
        listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEstoque.setVisibleRowCount(10);

        botaoVerDetalhes = new JButton("Ver item");
        botaoVerDetalhes.setBounds(250, 110, 120, 30);

        botaoAdicionar = new JButton("Adicionar item");
        botaoAdicionar.setBounds(250, 170, 120, 30);

        janela = new JFrame("Filiais");
        janela.setLayout(null);
        janela.add(textoGenerico);
        janela.add(listaEstoque);
        janela.add(botaoAdicionar);
        janela.add(botaoVerDetalhes);
        janela.add(filtroEstoqueVazio);

        botaoVerDetalhes.addActionListener(this);

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (controleFilial != null) {
            if (src == botaoVerDetalhes) {
                new DetalheFilial();
            }
        } else {
            if (src == botaoVerDetalhes) {
                new DetalheItem();
            }
        }

    }
}
