package view;
import modelo.Filial;
import modelo.Item;

import java.awt.Font;
import javax.swing.*;

public class DetalheFilial {
	private JFrame janela = new JFrame("Filial");
	private JLabel descricao = new JLabel("Informações da Filial");
	private JLabel labelNome = new JLabel("Nome: ");
	private JTextField valorNome = new JTextField();
	private JLabel labelLocalizacao = new JLabel("Localização: ");
	private JTextField valorLocalizacao = new JTextField();
	private JLabel labelId = new JLabel("ID: ");
	private JTextField valorId = new JTextField();
	private JButton botaoAtualizar = new JButton("Atualizar");
	private JButton botaoExcluir = new JButton("Excluir");

	// Nenhuma filial escolhida, adicionar uma nova
	public DetalheFilial() {
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
		
		janela.setSize(400, 400);
		janela.setVisible(true);
	}

	// Filial escolhida para modificaç�o
	public DetalheFilial(Filial filial) {

	}
}
