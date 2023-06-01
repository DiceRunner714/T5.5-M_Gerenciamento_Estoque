package view;
import java.awt.Font;

import javax.swing.*;

public class JanelaEmpresa {
	private static JFrame janela = new JFrame("Empresa");
	private static JButton botao = new JButton("clique aqui");
	private static JButton botao2 = new JButton("clique aqui");
	private static JLabel titulo = new JLabel("Empresa");
	private static JLabel texto = new JLabel("texto");
	
	public JanelaEmpresa() {
		botao.setBounds(100, 100, 250, 50);
		botao.setFont(new Font("Arial", Font.BOLD, 20));
		botao2.setBounds(100, 200, 250, 50);
		botao2.setFont(new Font("Arial", Font.BOLD, 20));
		titulo.setBounds(200, 0, 100, 50);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		texto.setBounds(100, 400, 250, 50);
		texto.setFont(new Font("Arial", Font.BOLD, 20));
		janela.add(botao);
		janela.add(botao2);
		janela.add(titulo);
		janela.add(texto);
		janela.setSize(500, 500);
		janela.setVisible(true);
		
		janela.setLayout(null);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JanelaEmpresa j = new JanelaEmpresa();
	}

}
