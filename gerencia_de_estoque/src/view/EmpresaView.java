package view;

import controle.ControleEmpresa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe EmpresaView representa a tela de menu da empresa, por meio da qual 
 * é possível abrir tela de pesquisa das filiais cadastradas ou tela de pesquisa
 * de seu estoque inteiro
 * @author André Emanuel Bipo da Silva
 * @author Cássio Sousa dos Reis
 * @version 1.0
 * @see controle.ControleEmpresa
 * @since 2023
 */
public class EmpresaView  {
    private static final JFrame janela = new JFrame("Empresa");
    private static final JButton verFiliais = new JButton("Ver Filiais");
    private static final JButton verEstoque = new JButton("Ver Estoque");
    private final ControleEmpresa controleEmpresa;
    
    /**
     * Construtor cria tela de menu da empresa
     * @param nome Nome da empresa
     */
    public EmpresaView(String nome) {
        controleEmpresa = new ControleEmpresa(nome);
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
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        verFiliais.addActionListener(new estoqueOuFilialListener());
        verEstoque.addActionListener(new estoqueOuFilialListener());
    }
    
    /**
     * Controla os eventos dos botões de ver filiais e de ver estoque
     * @author André Emanuel Bipo da Silva
     * @version 1.0
     * @since 2023
     */
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
