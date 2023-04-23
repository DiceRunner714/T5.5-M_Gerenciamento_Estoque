package main;

import java.util.ArrayList;

public class Empresa {
	


	private String nome;
	private ArrayList<Filial> filiais;
	
	public Empresa(String nome) {
		this.nome = nome;
		filiais = new ArrayList<Filial>();
	}
	
	public ArrayList<Item> getAllEstoque() {
		return null;
	}

	public void adicionarFilial(String nome, String local, int id) {
		filiais.add(new Filial(nome, local, id));
	}
	
	public void removerFilial(int id) {
		for (Filial filial : filiais) {
			if (filial.getId() == id) {
				filiais.remove(filial);
				break;
			}
		}
	}
	
	public void atualizarFilial(String nome, String local, int id) {
		for (Filial filial : filiais) {
			if (filial.getId() == id) {
				filial.setId(id);
				filial.setLocal(local);
				filial.setNome(nome);
				break;
			}
		}
	}
	
	public String lerFilial() {
		return null;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(ArrayList<Filial> filiais) {
		this.filiais = filiais;
	}
	
	public String getFiliaisDescricao() {
		String descricao_filiais = "____FILIAIS____\n\n";
		for (Filial filial : filiais) {
			descricao_filiais += filial.toString();
		}
		return descricao_filiais;
	}
}
