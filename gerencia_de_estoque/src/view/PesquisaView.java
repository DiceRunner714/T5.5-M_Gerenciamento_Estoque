package view;

import controle.ControleEmpresa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

/**
 * Classe abstrata PesquisaView possui componentes que realizam as operações
 * de adicionar, visualizar e modificar algum elemento
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see PesquisaViewFilial
 * @see PesquisaViewEstoque
 * @since 2023
 */
public abstract class PesquisaView {
    protected ControleEmpresa controleEmpresa;
    protected JButton botaoVerDetalhes;
    protected JButton botaoAdicionar;
    /**
     * Atualiza elementos na interface gráfica
     */
    abstract void refresh();
    /**
     * Gera mensagem de erro quando o usuário não seleciona um elemento
     */
    abstract protected void mensagemErroEscolhaVazia();
    /**
     * Cria janela para adição de novo elemento
     */
    abstract protected void adicionarElemento();
    /**
     * Cria janela para visualizar ou modificar um elemento existente
     */
    abstract protected void visualizarElemento();
    
    /**
     * Controla os eventos de adicionar e visualizar elemento
	 * @author André Emanuel Bipo da Silva
	 * @version 1.0
	 * @since 2023
     */
    protected class ManipularElementoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            try {
                if (src == botaoAdicionar) {
                    adicionarElemento();
                } else {
                    visualizarElemento();
                }
            } catch (NullPointerException | NoSuchElementException exc) {
                mensagemErroEscolhaVazia();
            }
        }
    }
}
