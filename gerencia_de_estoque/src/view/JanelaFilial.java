package view;
import java.awt.Font;

import javax.swing.*;

public class JanelaFilial {
	private JButton createFilial = new JButton("Adicionar Filial");
	private JButton refreshFilial = new JButton("Atualizar Filial");
	private JLabel texto = new JLabel("Pesquisar filial");
	private JTextField pesquisa = new JTextField();
	private JFrame janela = new JFrame("Filiais");
	
	public JanelaFilial() {
		texto.setBounds(10, 10, 150, 20);
		texto.setFont(new Font("Arial", Font.BOLD, 20));
		pesquisa.setBounds(180, 10, 200, 20);
		createFilial.setBounds(140, 50, 120, 30);
		refreshFilial.setBounds(140, 300, 120, 30);
		
		janela.setLayout(null);
		janela.add(texto);
		janela.add(pesquisa);
		janela.add(createFilial);
		janela.add(refreshFilial);
		
		janela.setSize(400, 400);
		janela.setVisible(true);
		
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
