package view;
import java.awt.Font;
import javax.swing.*;

public class DetalheItem {
	// TODO: criar os componentes das classes filhas
	private JFrame janela = new JFrame("Item");
	private JLabel descricao = new JLabel("Informações do Item");
	private JLabel labelNome = new JLabel("Nome: ");
	private JTextField valorNome = new JTextField();
	private JLabel labelId = new JLabel("ID: ");
	private JTextField valorId = new JTextField();
	private JLabel labelCategoria = new JLabel("Categoria: ");
	private JTextField valorCategoria = new JTextField();
	private JLabel labelQuantidade = new JLabel("Quantidade: ");
	private JTextField valorQuantidade = new JTextField();
	private JLabel labelValor = new JLabel("Valor (R$): ");
	private JTextField valorValor = new JTextField();
	private JButton botaoAtualizar = new JButton("Atualizar");
	private JButton botaoExcluir = new JButton("Excluir");
	
	
	public DetalheItem() {
		descricao.setBounds(90, 10, 200, 30);
		descricao.setFont(new Font("Arial", Font.BOLD, 20));
		labelNome.setBounds(10, 70, 50, 20);
		valorNome.setBounds(120, 70, 150, 18);
		labelId.setBounds(10, 100, 50, 20);
		valorId.setBounds(120, 100, 50, 18);
		labelCategoria.setBounds(10, 130, 80, 20);
		valorCategoria.setBounds(120, 130, 150, 18);
		labelQuantidade.setBounds(10, 160, 80, 20);
		valorQuantidade.setBounds(120, 160, 50, 18);
		labelValor.setBounds(10, 190, 70, 20);
		valorValor.setBounds(120, 190, 50, 18);
		botaoAtualizar.setBounds(70, 300, 100, 30);
		botaoExcluir.setBounds(220, 300, 80, 30);
		
		janela.setLayout(null);
		janela.add(descricao);
		janela.add(labelNome);
		janela.add(valorNome);
		janela.add(labelCategoria);
		janela.add(valorCategoria);
		janela.add(labelId);
		janela.add(valorId);
		janela.add(labelQuantidade);
		janela.add(valorQuantidade);
		janela.add(labelValor);
		janela.add(valorValor);
		janela.add(botaoAtualizar);
		janela.add(botaoExcluir);
		
		janela.setSize(400, 400);
		janela.setVisible(true);
	}
}
