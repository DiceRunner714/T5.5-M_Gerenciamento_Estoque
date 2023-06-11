package view;

import controle.ControleEmpresa;
import javax.swing.*;

abstract class PesquisaView {
    protected ControleEmpresa controleEmpresa;
    protected JButton botaoVerDetalhes;
    protected JButton botaoAdicionar;
    abstract void refresh();
    abstract protected void mensagemErroEscolhaVazia();
}
