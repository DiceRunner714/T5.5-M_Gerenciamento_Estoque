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

    //Cria elementos comuns as duas janelas
    public void criarJanela() {

        JLabel descricao = new JLabel("Informações da Filial");
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelLocalizacao = new JLabel("Localização: ");
        JLabel labelId = new JLabel("ID: ");

        formularios.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Ajuste de título
        c.anchor = GridBagConstraints.CENTER;   // alinhamento dentro das célula
        c.weightx = 1;                          // % do espaço horizontal
        c.gridwidth = 2;                        // quantas células horizontais
        c.gridx = 0;                            // x da célula
        c.gridy = 0;                            // y da célula
        descricao.setFont(new Font("Arial", Font.BOLD, 20));
        formularios.add(descricao, c);

        // Ajuste de labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.3;
        c.gridwidth = 1;
        c.insets = new Insets(5, 5, 5, 5);  // Padding
        c.gridx = 0;

        c.gridy = 1;
        formularios.add(labelNome, c);
        c.gridy = 2;
        formularios.add(labelLocalizacao, c);
        c.gridy = 3;
        formularios.add(labelId, c);

        // Ajuste de campos
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 0, 5, 5);
        c.weightx = 0.6;
        c.gridx = 1;

        c.gridy = 1;
        formularios.add(valorNome, c);
        c.gridy = 2;
        formularios.add(valorLocalizacao, c);
        c.gridy = 3;
        formularios.add(valorId, c);

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

        janela.add(formularios, BorderLayout.NORTH);
        janela.add(botoes, BorderLayout.LINE_END);
        janela.setSize(400, 200);
        janela.setResizable(false);
        janela.setVisible(true);
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

    // --POP UPS--

    public void mensagemErroIdrepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(null,
                e3.getMessage(),
                "Erro de indentificação", JOptionPane.ERROR_MESSAGE);
    }

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
}
