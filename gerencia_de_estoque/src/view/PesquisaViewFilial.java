package view;

import controle.ControleEmpresa;
import modelo.Filial;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PesquisaViewFilial extends PesquisaView {
    private JList<Filial> listaFiliais;

    public PesquisaViewFilial(ControleEmpresa controleEmpresa) {
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaFiliais();
    }

    private void iniciarJanelaFiliais() {
        JFrame janela = new JFrame("Filial");

        botaoAdicionar = new JButton("Adicionar filial");
        botaoVerDetalhes = new JButton("Ver filial");
        JButton botaoVerEstoque = new JButton("Ver estoque");

        // Painel principal
        listaFiliais = new JList<>(controleEmpresa.getFiliais().toArray(new Filial[0]));
        listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFiliais.setVisibleRowCount(50);

        janela.add(new PainelPesquisa("Pesquisa em filiais", listaFiliais,
                Arrays.asList(botaoAdicionar, botaoVerDetalhes, botaoVerEstoque)));

        botaoVerEstoque.addActionListener(new VerEstoqueListener());
        botaoVerDetalhes.addActionListener(new ManipularElementoListener());
        botaoAdicionar.addActionListener(new ManipularElementoListener());

        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
    }

    @Override
    public void refresh() {
                listaFiliais.setListData(controleEmpresa.getFiliais().toArray(new Filial[0]));
                listaFiliais.updateUI();
    }

    @Override
    protected void adicionarElemento() {
        new DetalheViewFilial(controleEmpresa, PesquisaViewFilial.this);
    }

    @Override
    protected void visualizarElemento() {
        if (listaFiliais.getSelectedValue() != null) {
            new DetalheViewFilial(controleEmpresa, PesquisaViewFilial.this, listaFiliais.getSelectedValue());
        } else {
            mensagemErroEscolhaVazia();
        }
    }

    private class VerEstoqueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new PesquisaViewEstoque(controleEmpresa, listaFiliais.getSelectedValue());
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
    }

    // --POP UPS--
    @Override
    protected void mensagemErroEscolhaVazia() {
        String mensagem = "Erro de escolha: uma filial não foi selecionada";
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }

}
