package view;
import java.awt.Font;
import javax.swing.*;

public class JanelaEstoque {
	private JFrame janela = new JFrame("Estoque");
	private JLabel descricao = new JLabel("Pesquisar item");
	private JList<String> listaEstoque = new JList<String>();
	private JCheckBox filtroEstoqueVazio = new JCheckBox("Filtro de estoque vazio");
	private JButton createItem = new JButton("Adicionar Item");
	private JButton botaoVerItem = new JButton("Ver Item");

	public JanelaEstoque() {
		descricao.setBounds(20, 10, 150, 20);
		descricao.setFont(new Font("Arial", Font.BOLD, 20));
		filtroEstoqueVazio.setBounds(20, 40, 200, 20);
		listaEstoque.setBounds(20, 70, 200, 200);
		listaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEstoque.setVisibleRowCount(10);
		botaoVerItem.setBounds(250, 110, 120, 30);
		createItem.setBounds(250, 170, 120, 30);

		janela.setLayout(null);
		janela.add(descricao);
		janela.add(listaEstoque);
		janela.add(createItem);
		janela.add(botaoVerItem);
		janela.add(filtroEstoqueVazio);
		
		janela.setSize(400, 400);
		janela.setVisible(true);
	}
}
