package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.Filial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaEmpresa implements ActionListener {
    private static final JFrame janela = new JFrame("Empresa");
    private static final JButton verFil = new JButton("Ver Filiais");
    private static final JButton verEst = new JButton("Ver Estoque");
    private static final ControleEmpresa controleEmpresa = new ControleEmpresa("ACME inc.");

    public JanelaEmpresa() {
        JLabel titulo = new JLabel("Empresa: " + controleEmpresa.getNome());
        JLabel texto = new JLabel("<html>Um empreendimento de "
                + "vendas on-line necessita de um sistema de controle e "
                + "gerenciamento de seu estoque. Eles precisam gerenciar"
                + " os itens de estoque e as filiais responsáveis e para isso"
                + " precisam poder cadastrar, remover, alterar e ler dados sobre"
                + " as filiais e os itens. </html>");

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        janela.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 0.2;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        janela.add(titulo, c);

        c.insets = new Insets(0, 30, 0, 30);
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 0.5;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        janela.add(texto, c);

        c.weighty = 0.5;
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.NONE;
        janela.add(verFil, c);

        c.gridx = 1;
        janela.add(verEst, c);

        janela.setSize(400, 400);
        janela.setVisible(true);
        janela.setResizable(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        verFil.addActionListener(this);
        verEst.addActionListener(this);
    }

    public static void main(String[] args) {

        new JanelaEmpresa();
        try {
            controleEmpresa.adicionarFilial(
                    new Filial("abr", "brasil", 6)
            );
            controleEmpresa.adicionarFilial(
                    new Filial("sussy baki", "xina", 7)
            );
            ControleEstoqueFilial meuEstoque = new ControleEstoqueFilial(controleEmpresa, controleEmpresa.buscarFilial(7));
            Farmaceutico farmaco = new Farmaceutico("Ablublublé", "né", 50.99, 5, 9);
            farmaco.setGenerico(true);
            meuEstoque.adicionarFarmaceutico(farmaco);
            meuEstoque.adicionarFarmaceutico("vazio", "né", 50.99, 0, 12);
            ControleEstoqueFilial meuEstoque2 = new ControleEstoqueFilial(controleEmpresa, controleEmpresa.buscarFilial(6));
            meuEstoque2.adicionarProdutoQuimico("be", "né", 50.99, 5, 56);
            meuEstoque2.adicionarFarmaceutico("asdf", "né", 50.99, 0, 0xFF);
        } catch (IdRepetidoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == verFil) {
            new JanelaPesquisa(controleEmpresa, ModosPesquisa.LISTAR_FILIAIS);
        } else if (src == verEst) {
            new JanelaPesquisa(controleEmpresa, ModosPesquisa.LISTAR_ESTOQUE_GERAL);
        }
    }
}
