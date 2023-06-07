package modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class Filial {
    private String nome;
    private String local;
    private int id;
    private ArrayList<Item> estoque;

    public Filial(String nome, String local, int id) {
        this.nome = nome;
        this.local = local;
        this.id = id;
        estoque = new ArrayList<>();
    }

    public void adicionarItem(Item i) {
        // Adds an item to branch item list
        estoque.add(i);
    }

    public void removerItem(int itemId) {
        // Removes an item from branch item list, given its ID
        Iterator<Item> itr = estoque.iterator();

        while (itr.hasNext()) {
            Item currentItem = itr.next();

            if (currentItem.getId() == itemId) {
            	itr.remove();
            	break;
            }	          
        }
    }
    
    public Item buscarItem(String nome) {
        // Returns an item given its name
        // returns null if item is not found
        for (Item item : estoque) {
            if (item.getNome().equals(nome)) {
                return item;
            }
        }
        return null;
    }
    
    public Item buscarItem(int id) {
        // Returns a specific item given its ID
        // returns null if item is not found
        for (Item item : estoque) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public String listarCaracteristicasBasicacs() {
        return String.format("""
                ---Filial---
                nome: %s
                local: %s
                id: %d
                """, nome, local, id);
    }

    @Override
    public String toString() {
        return String.format("%d_%s", id, nome);
    }

    public ArrayList<Item> getEstoque() {
        // Returns an item list of this branch
        return estoque;
    }

    public ArrayList<Item> getEstoqueVazio() {
        // Returns a list of all items out of stock
        ArrayList<Item> itensVazios = new ArrayList<>();
        for (Item item : estoque) {
            if (item.getQuantidade() == 0) {
                itensVazios.add(item);
            }
        }
        return itensVazios;
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
