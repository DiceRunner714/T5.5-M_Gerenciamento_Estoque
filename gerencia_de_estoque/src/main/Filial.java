package main;
import java.util.ArrayList;

public class Filial {
	private String nome;
	private String local;
	private int id;
	
	public Filial(String nome, String local, int id) {
		this.nome = nome;
		this.local = local;
		this.id = id;
	}
	
	public ArrayList<Item> getAllItens() {
		return null;
	}
	
	public ArrayList<Item> getEstoqueVazio() {
		return null;
	}
	
	public String toString() {
		return String.format("---Filial---\nid: %d\nnome: %s\nlocal: %s\n", id, nome, local);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
