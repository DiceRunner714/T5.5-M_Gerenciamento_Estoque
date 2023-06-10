package modelo;

import java.util.ArrayList;
import java.util.Objects;

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
        estoque.removeIf(item -> item.getId() == id);
    }

    public void removerItem(Item itemEscolhido) {
        estoque.removeIf(item -> item.equals(itemEscolhido));
    }

    @Deprecated
    public Item buscarItem(String nome) {
        return estoque
                .stream()
                .filter(item -> Objects.equals(item.getNome(), nome)) // TODO: null safe, mas não sei se é boa idéia
                .findFirst()
                .orElse(null);
    }

    public Item buscarItem(int id) {
        return estoque
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Item> buscarItens(String nome) {
        return new ArrayList<>(
                estoque.stream().filter(item -> item.getNome().equals(nome)).toList()
        );
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
        return estoque;
    }

    public void setEstoque(ArrayList<Item> estoque) {
        this.estoque = estoque;
    }

    public ArrayList<Item> getEstoqueVazio() {
        return new ArrayList<>(
                estoque.stream().filter(item -> item.getQuantidade() == 0).toList()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Filial outraFilial)) {
            return false;
        }
        return this.nome.equals(outraFilial.getNome()) &&
                this.local.equals(outraFilial.getLocal()) &&
                this.id == outraFilial.getId();
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
