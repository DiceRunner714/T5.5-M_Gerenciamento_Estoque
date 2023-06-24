package view;

import controle.ControleEmpresa;
import modelo.Filial;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Classe PesquisaViewFilial representa janela destinada para pesquisa de 
 * filiais
 * @author André Emanuel Bispo da Silva
 * @author Cássio Sousa dos Reis
 * @version 1.0
 * @see PesquisaView
 * @since 2023
 */
public class PesquisaViewFilial extends PesquisaView {
    private JList<Filial> listaFiliais;

    /**
     * Construtor cria janela para pesquisa de filiais
     * @param controleEmpresa Instância de ControleEmpresa para realizar 
     * a pesquisa de filiais
     */
    public PesquisaViewFilial(ControleEmpresa controleEmpresa) {
        this.controleEmpresa = controleEmpresa;
        iniciarJanelaFiliais();
    }
    
    /**
     * Define os componentes e cria painel para pesquisa de filiais
     */
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

    /**
     * Atualiza a lista de filiais na interface gráfica
     */
    @Override
    public void refresh() {
                listaFiliais.setListData(controleEmpresa.getFiliais().toArray(new Filial[0]));
                listaFiliais.updateUI();
    }
    
    /**
     * Chama método que cria janela para adicionar filial
     */
    @Override
    protected void adicionarElemento() {
        new DetalheViewFilial(controleEmpresa, PesquisaViewFilial.this);
    }
    
    /**
     * Chama método que cria tela para visualizar ou modificar uma filial
     */
    @Override
    protected void visualizarElemento() {
        new DetalheViewFilial(controleEmpresa, PesquisaViewFilial.this, listaFiliais.getSelectedValue());
    }
    
    /**
     * Define os eventos quando o botão de ver estoque (de uma filial) é pressionado
     * @author André Emanuel Bispo da Silva
     * @version 1.0
     * @since 2023
     */
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

    /**
     * Gera uma mensagem de erro quando o usuário não selecionou nenhuma filial,
     * mas tenta visualizar detalhes através do botão de ver filial.
     */
    // --POP UPS--
    @Override
    protected void mensagemErroEscolhaVazia() {
        String mensagem = "Erro de escolha: uma filial não foi selecionada";
        JOptionPane.showMessageDialog(null, mensagem, "Erro de escolha", JOptionPane.ERROR_MESSAGE);
    }

}
