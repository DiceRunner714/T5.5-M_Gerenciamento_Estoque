package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;
import modelo.Filial;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class DetalheFilial implements ActionListener {
    private static ControleEmpresa controleEmpresa;
    private JFrame janela = new JFrame("Filial");
    private JPanel formularios = new JPanel();
    private JPanel botoes = new JPanel();
    private JTextField valorNome = new JTextField();
    private JTextField valorLocalizacao = new JTextField();
    private JTextField valorId = new JTextField();
    private JButton botaoAtualizar = new JButton("Atualizar");
    private JButton botaoExcluir = new JButton("Excluir");
    private JButton botaoAdicionar = new JButton("Adicionar");
    private JButton botaoCancelar = new JButton("Cancelar");
    private JanelaPesquisa janelaPesquisa;
    private Filial filialEscolhida;
    private Modos modo;


    // Construtor para adicionar uma filial nova
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        DetalheFilial.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = Modos.ADICIONAR;
        criarJanela();
    }

    //Cria elementos comuns as duas janelas
    public void criarJanela() {
        criarFormularioFilial();
        criarPainelBotoes();

        janela.add(formularios, BorderLayout.NORTH);
        janela.add(botoes, BorderLayout.LINE_END);
        janela.setSize(400, 200);
        janela.setResizable(false);
        janela.setVisible(true);
    }

    private void criarFormularioFilial() {
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelLocalizacao = new JLabel("Localização: ");
        JLabel labelId = new JLabel("ID: ");

        JComponent[] componentesEsquerdos = {labelNome, labelLocalizacao, labelId};
        JComponent[] compontentesDireitos = {valorNome, valorLocalizacao, valorId};
        new FormularioBuilder(formularios, componentesEsquerdos, compontentesDireitos, "Informações da Filial:");
    }

    private void criarPainelBotoes() {
        botoes.setLayout(new FlowLayout());
        switch (modo) {
            case EDITAR -> {
                botoes.add(botaoAtualizar);
                botoes.add(botaoExcluir);

                botaoAtualizar.addActionListener(this);
                botaoExcluir.addActionListener(this);
            }
            case ADICIONAR -> {
                botoes.add(botaoAdicionar);
                botoes.add(botaoCancelar);

                botaoAdicionar.addActionListener(this);
                botaoCancelar.addActionListener(this);
            }
        }

    }

    // Construtor para editar uma filial
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Filial filialEscolhida) {
        DetalheFilial.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = Modos.EDITAR;

        this.filialEscolhida = filialEscolhida;

        valorLocalizacao.setText(filialEscolhida.getLocal());
        valorNome.setText(filialEscolhida.getNome());
        valorId.setText(String.valueOf(filialEscolhida.getId()));

        criarJanela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == botaoAdicionar || src == botaoAtualizar) {
            enviarFormulario();
        } else if (src == botaoCancelar) {
            // Fechar a janela atual
            janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        } else if (src == botaoExcluir) {
            controleEmpresa.excluirFilial(filialEscolhida);
            janelaPesquisa.refresh();
            janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        }

    }

    // TODO: essa função deveria ser privada devido ao encapsulamento, perguntar pra professora
    public void enviarFormulario() {
        try {
            switch (modo) {
                case ADICIONAR -> {
                    Filial f = new Filial(
                            valorNome.getText(),
                            valorLocalizacao.getText(),
                            Integer.parseInt(valorId.getText())
                    );
                    controleEmpresa.adicionarFilial(f);
                }
                case EDITAR -> controleEmpresa.atualizarFilial(
                        valorNome.getText(),
                        valorLocalizacao.getText(),
                        Integer.parseInt(valorId.getText()),
                        filialEscolhida);
            }
            janelaPesquisa.refresh();
        } catch (NumberFormatException e1) {
            mensagemErrodeFormatacao();
        } catch (NullPointerException e2) {
            mensagemErroFormularioVazio();
        } catch (IdRepetidoException e3) {
            mensagemErroIdrepetido(e3);
        }
    }

    // --POP UPS--

    public void mensagemErrodeFormatacao() {
        JOptionPane.showMessageDialog(null,
                "Erro de formatação: assegure-se que valores numéricos foram inseridos corretamente.",
                "Erro de formatação", JOptionPane.ERROR_MESSAGE);
    }

    public void mensagemErroFormularioVazio() {
        JOptionPane.showMessageDialog(null,
                "Erro de entrada: assegure-se que todos os formulários foram preenchidos.",
                "Erro de entrada", JOptionPane.ERROR_MESSAGE);
    }

    public void mensagemErroIdrepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(null,
                e3.getMessage(),
                "Erro de indentificação", JOptionPane.ERROR_MESSAGE);
    }
}
