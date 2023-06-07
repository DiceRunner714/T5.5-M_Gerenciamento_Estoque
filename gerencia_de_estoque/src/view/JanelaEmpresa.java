package view;
import controle.ControleEmpresa;
import controle.ControleEstoque;
import controle.ControleFilial;
import modelo.Filial;
import modelo.Item;
import modelo.ProdutoQuimico;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class JanelaEmpresa implements ActionListener {
	private static JFrame janela = new JFrame("Empresa");
	private static JButton verFil = new JButton("Ver Filiais");
	private static JButton verEst = new JButton("Ver Estoque");
	private static JLabel titulo = new JLabel("Empresa");
	private static JLabel texto = new JLabel("<html>Um empreendimento de "
			+ "vendas on-line necessita de um sistema de controle e "
			+ "gerenciamento de seu estoque. Eles precisam gerenciar"
			+ " os itens de estoque e as filiais responsáveis e para isso"
			+ " precisam poder cadastrar, remover, alterar e ler dados sobre"
			+ " as filiais e os itens. </html>");

	private static ControleEmpresa controleEmpresa = new ControleEmpresa("Lixo");
	private static ControleFilial controleFilial = new ControleFilial(controleEmpresa);

	public JanelaEmpresa() {
		verFil.setBounds(40, 200, 120, 30);
		verEst.setBounds(200, 200, 120, 30);
		titulo.setBounds(140, 0, 90, 50);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		texto.setBounds(20, 70, 330, 90);
		
		janela.setLayout(null);
		
		janela.add(verFil);
		janela.add(verEst);
		janela.add(titulo);
		janela.add(texto);
		janela.setSize(400, 400);
		janela.setVisible(true);
		
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JanelaEmpresa empresa = new JanelaEmpresa();
		verFil.addActionListener(empresa);
		verEst.addActionListener(empresa);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == verFil) {
			new JanelaPesquisa(new ControleFilial(controleEmpresa));
		}

		if (src == verEst) {
			new JanelaPesquisa(new ControleEstoque(controleEmpresa));
		}
	}
}
