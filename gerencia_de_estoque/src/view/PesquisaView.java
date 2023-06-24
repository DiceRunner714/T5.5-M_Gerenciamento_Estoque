package view;

import controle.ControleEmpresa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

public abstract class PesquisaView {
    protected ControleEmpresa controleEmpresa;
    protected JButton botaoVerDetalhes;
    protected JButton botaoAdicionar;
    abstract void refresh();
    abstract protected void mensagemErroEscolhaVazia();
    abstract protected void adicionarElemento();
    abstract protected void visualizarElemento();
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
