package view;
import java.awt.Font;
import javax.swing.*;

public class JanelaFilial {
	private JLabel texto = new JLabel("Pesquisar filial");
	private JList<String> listaFiliais = new JList<String>();
	private JButton botaoCriarFil = new JButton("Adicionar Filial");
	private JButton botaoVerFil = new JButton("Ver filial");
	private JButton botaoVerEst = new JButton("Ver estoque");
	private JFrame janela = new JFrame("Filiais");
	
	public JanelaFilial() {
		texto.setBounds(20, 10, 150, 20);
		texto.setFont(new Font("Arial", Font.BOLD, 20));
		listaFiliais.setBounds(20, 50, 200, 200);
		listaFiliais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaFiliais.setVisibleRowCount(10);
		botaoCriarFil.setBounds(60, 270, 120, 30);
		botaoVerFil.setBounds(250, 110, 120, 30);
		botaoVerEst.setBounds(250, 170, 120, 30);
		
		janela.setLayout(null);
		janela.add(texto);
		janela.add(listaFiliais);
		janela.add(botaoCriarFil);
		janela.add(botaoVerEst);
		janela.add(botaoVerFil);
		
		janela.setSize(400, 400);
		janela.setVisible(true);
		
	}
}
