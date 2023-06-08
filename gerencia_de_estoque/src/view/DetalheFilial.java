package view;

import controle.ControleEmpresa;
import modelo.Filial;
import modelo.Item;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton botaoAtualizar = new JButton("Atualizar");
    private JButton botaoExcluir = new JButton("Excluir");
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
        botaoAtualizar.setBounds(70, 170, 100, 30);
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

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    // Filial escolhida para modificação
    public DetalheFilial(Filial filial) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // TODO:
        switch (modo) {
            // Modo adicionar filial
            case 1:
                if (src == botaoAtualizar) {
                    Filial f = new Filial(
                            valorNome.getText(), valorLocalizacao.getText(), Integer.parseInt(valorId.getText())
                    );
                    controleEmpresa.adicionarFilial(f);
                    janelaPesquisa.refresh();
                }
                break;
        }
    }
}
