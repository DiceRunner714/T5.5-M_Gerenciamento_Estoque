package view;

import controle.ControleEmpresa;
import modelo.Filial;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PesquisaFiliaisView extends PesquisaView{
    private JList<Filial> listaFiliais;
    private JButton botaoVerEstoque;

    public PesquisaFiliaisView(ControleEmpresa controleEmpresa) {
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaFiliais();
    }

    private void iniciarJanelaFiliais() {
        JFrame janela = new JFrame("Filial");

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

    @Override
    public void refresh() {
                listaFiliais.setListData(controleEmpresa.getFiliais().toArray(new Filial[0]));
                listaFiliais.updateUI();
    }

    // --POP UPS--
    @Override
    protected void mensagemErroEscolhaVazia() {
        String mensagem = "Erro de escolha: uma filial não foi selecionada";
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }

    private class listarFiliaisListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            try {
                if (src == botaoAdicionar) {
                    new DetalheFilial(controleEmpresa, PesquisaFiliaisView.this);
                } else {
                    Filial filialSelecionada = Objects.requireNonNull(listaFiliais.getSelectedValue());
                    if (src == botaoVerDetalhes) {
                        new DetalheFilial(controleEmpresa, PesquisaFiliaisView.this, filialSelecionada);
                    } else if (src == botaoVerEstoque) {
                        new PesquisaEstoqueView(controleEmpresa, listaFiliais.getSelectedValue());
                    }
                }
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
    }

}
