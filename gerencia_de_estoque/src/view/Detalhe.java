package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public abstract class Detalhe implements ActionListener {
    protected ControleEmpresa controleEmpresa;
    protected JFrame janela = new JFrame();
    protected JanelaPesquisa janelaPesquisa;
    protected JPanel botoes = new JPanel();
    protected Modos modo;
    protected JButton botaoAtualizar = new JButton("Atualizar");
    protected JButton botaoExcluir = new JButton("Excluir");
    protected JButton botaoAdicionar = new JButton("Adicionar");
    protected JButton botaoCancelar = new JButton("Cancelar");

    public Detalhe(Modos modo, JanelaPesquisa janelaPesquisa, ControleEmpresa controleEmpresa) {
        this.modo = modo;
        this.janelaPesquisa = janelaPesquisa;
        this.controleEmpresa = controleEmpresa;
    }

    abstract protected ArrayList<JComponent> criarPaineisFormularios();

    abstract protected void excluirElemento();

    abstract protected void adicionarElemento() throws IdRepetidoException;

    abstract protected void atualizarElemento() throws IdRepetidoException;

    abstract protected void popularFormularios();

    protected void enviarFormularios() throws IdRepetidoException, NumberFormatException, NullPointerException {
        switch (modo) {
            case ADICIONAR -> adicionarElemento();
            case EDITAR -> atualizarElemento();
        }
        janelaPesquisa.refresh();
    }


    protected void criarJanela(JComponent[] formularios, int width, int height, String tituloDaJanela) {
        janela.setTitle(tituloDaJanela);

        janela.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // FORMULARIOS PRINCIPAIS
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        for (JComponent formulario : formularios) {
            janela.add(formulario, c);
            c.gridy++;
        }

        // PAINEL DE BOTÕES
        new PainelBotoesBuilder(botoes, botaoAdicionar, botaoCancelar,
                botaoAtualizar, botaoExcluir, modo, this);
        c.weighty = 0.6;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        janela.add(botoes, c);

        // HABILITAR JANELA
        janela.setSize(width, height);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    protected void criarJanela(ArrayList<JComponent> formularios, int width, int height, String tituloDaJanela) {
        janela.setTitle(tituloDaJanela);

        janela.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // FORMULARIOS PRINCIPAIS
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        for (JComponent formulario : formularios) {
            janela.add(formulario, c);
            c.gridy++;
        }

        // PAINEL DE BOTÕES
        new PainelBotoesBuilder(botoes, botaoAdicionar, botaoCancelar,
                botaoAtualizar, botaoExcluir, modo, this);
        c.weighty = 0.6;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        janela.add(botoes, c);

        // HABILITAR JANELA
        janela.setSize(width, height);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == botaoAtualizar || src == botaoAdicionar) {
            try {
                enviarFormularios();
            } catch (NumberFormatException e1) {
                mensagemErrodeFormatacao();
            } catch (NullPointerException e2) {
                mensagemErroFormularioVazio();
            } catch (IdRepetidoException e3) {
                mensagemErroIdrepetido(e3);
            }
        } else {
            if (src == botaoExcluir) excluirElemento();
            // tanto o botão de excluir quanto o de cancelar fecham a janela no final da operação
            janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        }
    }

    protected void mensagemErrodeFormatacao() {
        JOptionPane.showMessageDialog(null,
                "Erro de formatação: assegure-se que valores numéricos foram inseridos corretamente.",
                "Erro de formatação", JOptionPane.ERROR_MESSAGE);
    }

    protected void mensagemErroFormularioVazio() {
        JOptionPane.showMessageDialog(null,
                "Erro de entrada: assegure-se que todos os formulários foram preenchidos.",
                "Erro de entrada", JOptionPane.ERROR_MESSAGE);
    }

    // --POP UPS--
    protected void mensagemErroIdrepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(null,
                e3.getMessage(),
                "Erro de indentificação", JOptionPane.ERROR_MESSAGE);
    }
}
