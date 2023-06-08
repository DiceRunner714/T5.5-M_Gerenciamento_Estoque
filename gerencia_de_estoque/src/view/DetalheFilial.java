package view;

import controle.ControleEmpresa;
import controle.IdRepetidoException;
import modelo.Filial;
import modelo.Item;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class DetalheFilial implements ActionListener {
    private static ControleEmpresa controleEmpresa;
    private JFrame janela = new JFrame("Filial");
    private JLabel descricao = new JLabel("Informações da Filial");
    private JLabel labelNome = new JLabel("Nome: ");
    private JLabel labelLocalizacao = new JLabel("Localização: ");
    private JLabel labelId = new JLabel("ID: ");
    private JTextField valorNome = new JTextField();
    private JTextField valorLocalizacao = new JTextField();
    private JTextField valorId = new JTextField();
    private JButton botaoAtualizar;
    private JButton botaoExcluir;
    private JButton botaoAdicionar;
    private JButton botaoCancelar;
    private JanelaPesquisa janelaPesquisa;
    private Filial filialEscholida;
    private Modos modo;


    // Construtor para adicionar uma filial nova
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        // Modo de adicionar
        this.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = Modos.ADICIONAR;

        criarElementosBasicos();

        botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.setBounds(70, 170, 100, 30);

        // TODO: este botão está com o tamanho errado para o texto
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(220, 170, 80, 30);

        janela.add(botaoAdicionar);
        janela.add(botaoCancelar);

        botaoAdicionar.addActionListener(this);
        botaoCancelar.addActionListener(this);

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    // Construtor para editar uma filial
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Filial filialEscholida) {
        // Modo de atualizar
        this.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        this.filialEscholida = filialEscholida;
        modo = Modos.EDITAR;

        criarElementosBasicos();

        valorNome.setText(filialEscholida.getNome());
        valorLocalizacao.setText(filialEscholida.getLocal());
        valorId.setText(String.valueOf(filialEscholida.getId()));

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(70, 170, 100, 30);

        // TODO: botão está com o tamanho errado para o texto
        botaoExcluir = new JButton("Excluir");
        botaoExcluir.setBounds(220, 170, 80, 30);

        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);

        botaoAtualizar.addActionListener(this);
        botaoExcluir.addActionListener(this);

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    //Cria elementos comuns as duas janelas
    public void criarElementosBasicos() {
        descricao.setBounds(90, 10, 200, 30);
        descricao.setFont(new Font("Arial", Font.BOLD, 20));

        labelNome.setBounds(10, 70, 50, 10);

        valorNome.setBounds(120, 70, 150, 18);

        labelLocalizacao.setBounds(10, 100, 80, 10);

        valorLocalizacao.setBounds(120, 100, 150, 18);

        labelId.setBounds(10, 130, 50, 10);

        valorId.setBounds(120, 130, 50, 18);


        janela.setLayout(null);
        janela.add(descricao);
        janela.add(labelNome);
        janela.add(valorNome);
        janela.add(labelLocalizacao);
        janela.add(valorLocalizacao);
        janela.add(labelId);
        janela.add(valorId);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == botaoAdicionar) {
            try {
                Filial f = new Filial(
                        valorNome.getText(),
                        valorLocalizacao.getText(),
                        Integer.parseInt(valorId.getText())
                );
                controleEmpresa.adicionarFilial(f);
                janelaPesquisa.refresh();
            } catch (NumberFormatException e1) {
                mensagemErrodeFormatacao();
            } catch (NullPointerException e2) {
                mensagemErroFormularioVazio();
            } catch (IdRepetidoException e3) {
                mensagemErroIdrepetido(e3);
            }
        } else if (src == botaoCancelar) {
            // Fechar a janela atual
            janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        } else if (src == botaoAtualizar) {
            try {
                controleEmpresa.atualizarFilial(
                        valorNome.getText(),
                        valorLocalizacao.getText(),
                        Integer.parseInt(valorId.getText()),
                        filialEscholida
                );
                janelaPesquisa.refresh();
            } catch (NumberFormatException e1) {
                mensagemErrodeFormatacao();
            } catch (NullPointerException e2) {
                mensagemErroFormularioVazio();
            } catch (IdRepetidoException e3) {
                mensagemErroIdrepetido(e3);
            }
        } else if (src == botaoExcluir) {
            controleEmpresa.excluirFilial(filialEscholida);
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
