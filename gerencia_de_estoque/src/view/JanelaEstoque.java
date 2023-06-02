package view;
import java.awt.Font;
import javax.swing.*;

public class JanelaEstoque {
	private JLabel textoPesquisa = new JLabel("Pesquisar item");
	private JList<String> listaEstoque = new JList<String>();
	private JButton createItem = new JButton("Adicionar Item");
	private JButton refreshItem = new JButton("Atualizar Item");
	private JCheckBox filtroEstoqueVazio = new JCheckBox("Estoque Vazio");
	private JFrame janela = new JFrame("Itens");
	
	public JanelaEstoque() {
		textoPesquisa.setBounds(10, 10, 150, 20);
		textoPesquisa.setFont(new Font("Arial", Font.BOLD, 20));
		listaEstoque.setBounds(180, 10, 200, 20);
		listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEstoque.setVisibleRowCount(10);
		createItem.setBounds(50, 300, 120, 30);
		refreshItem.setBounds(200, 300, 120, 30);
		filtroEstoqueVazio.setBounds(10, 40, 150, 20);
		
		janela.setLayout(null);
		janela.add(textoPesquisa);
		janela.add(listaEstoque);
		janela.add(createItem);
		janela.add(refreshItem);
		janela.add(filtroEstoqueVazio);
		
		janela.setSize(400, 400);
		janela.setVisible(true);
	}
}
