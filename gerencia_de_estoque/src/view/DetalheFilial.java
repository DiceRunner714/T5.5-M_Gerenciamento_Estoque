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
    private ControleEmpresa controleEmpresa;
    private JanelaPesquisa janelaPesquisa;
    private int modo;


    // Nenhuma filial escolhida, adicionar uma nova
    public DetalheFilial(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        // Modo de adicionar
        this.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;
        modo = 1;

        descricao.setBounds(90, 10, 200, 30);
        descricao.setFont(new Font("Arial", Font.BOLD, 20));

        labelNome.setBounds(10, 70, 50, 10);

        valorNome.setBounds(120, 70, 150, 18);

        labelLocalizacao.setBounds(10, 100, 80, 10);

        valorLocalizacao.setBounds(120, 100, 150, 18);

        labelId.setBounds(10, 130, 50, 10);

        valorId.setBounds(120, 130, 50, 18);

        botaoAtualizar = new JButton("Adicionar");
        botaoAtualizar.setBounds(70, 170, 100, 30);

        // TODO: botão está com o tamanho errado para o texto
        botaoExcluir = new JButton("Cancelar");
        botaoExcluir.setBounds(220, 170, 80, 30);

        janela.setLayout(null);
        janela.add(descricao);
        janela.add(labelNome);
        janela.add(valorNome);
        janela.add(labelLocalizacao);
        janela.add(valorLocalizacao);
        janela.add(labelId);
        janela.add(valorId);
        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);

        botaoAtualizar.addActionListener(this);
        botaoExcluir.addActionListener(this);


        janela.setSize(400, 400);
        janela.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // TODO: implementar checagem de entrada válida
        switch (modo) {
            // Modo adicionar filial, mas o botão está com o nome errado
            case 1:
                if (src == botaoAtualizar) {
                    try {
                        Filial f = new Filial(
                                valorNome.getText(), valorLocalizacao.getText(), Integer.parseInt(valorId.getText())
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
                } else if (src == botaoExcluir) {
                    // Fechar a janela atual
                    janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
                }
                break;
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
