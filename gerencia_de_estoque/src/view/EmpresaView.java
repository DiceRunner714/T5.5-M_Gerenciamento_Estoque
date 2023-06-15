package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.Filial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmpresaView  {
    private static final JFrame janela = new JFrame("Empresa");
    private static final JButton verFiliais = new JButton("Ver Filiais");
    private static final JButton verEstoque = new JButton("Ver Estoque");
    private static final ControleEmpresa controleEmpresa = new ControleEmpresa("ACME inc.");

    public EmpresaView() {
        JLabel titulo = new JLabel("Empresa: " + controleEmpresa.getNome());
        JLabel texto = new JLabel("""
                <html>Um empreendimento de
                vendas on-line necessita de um sistema de controle e
                gerenciamento de seu estoque. Eles precisam gerenciar
                os itens de estoque e as filiais responsáveis e para isso
                precisam poder cadastrar, remover, alterar e ler dados sobre
                as filiais e os itens. </html>
                """
                );

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
        janela.add(verFiliais, c);

        c.gridx = 1;
        janela.add(verEstoque, c);

        janela.setSize(400, 400);
        janela.setVisible(true);
        janela.setResizable(true);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        verFiliais.addActionListener(new estoqueOuFilialListener());
        verEstoque.addActionListener(new estoqueOuFilialListener());
    }

    class estoqueOuFilialListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == verFiliais) {
                new PesquisaViewFilial(controleEmpresa);
            } else if (src == verEstoque) {
                new PesquisaViewEstoque(controleEmpresa);
            }
        }
    }

}
