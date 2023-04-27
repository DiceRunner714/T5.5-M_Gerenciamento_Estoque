package main;

public class Item {
	
	private String nome;
	private int quantidade;
	private String categoria;
	private double valor;
	private int id;
	
	public Item(String nome, int quantidade, String categoria, double valor, int id) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.categoria = categoria;
		this.valor = valor;
		this.id = id;
	}
	
	public void atualizarItem(String newNome, int newQuantidade, 
			String newCategoria, double newValor, int newId) {
		nome = newNome;
		quantidade = newQuantidade;
		categoria = newCategoria;
		valor = newValor;
		id = newId;
	}
	
	public void listarCaracteristicasBasicas() {
		System.out.println(String.format("---Produto----\nID: %d\nNome: %s\nCategoria: %s\nQtd: "
				+ "%d\nValor: R$%.2f\n", id, nome,  
				categoria, quantidade, valor));
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
