package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Filial implements LeitordeEstoque {
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

    // -- CRUD DE ITEM --
    public void adicionarItem(Item i) {
        estoque.add(i);
    }

    public void removerItem(int itemId) {
        estoque.removeIf(item -> item.getId() == id);
    }

    public void removerItem(Item itemEscolhido) {
        estoque.removeIf(item -> item.equals(itemEscolhido));
    }

    // -- LEITURA DE ITEM --
    @Deprecated
    public Item buscarItem(String nome) {
        return estoque
                .stream()
                .filter(item -> Objects.equals(item.getNome(), nome)) // TODO: null safe, mas não sei se é boa idéia
                .findFirst()
                .orElse(null);
    }

    @Override
    public Item buscarItem(int id) {
        return estoque
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public ArrayList<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome().contains(nomeParcial))
                        .toList());
        else return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .contains(nomeParcial.toLowerCase()))
                        .toList());
    }

    @Override
    public ArrayList<Item> buscarItens(String nome, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome().equals(nome))
                        .toList());
        else return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .equals(nome.toLowerCase()))
                        .toList());
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

    public ArrayList<Item> buscaParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome().contains(nomeParcial))
                        .toList());
        else return new ArrayList<>(
                estoque.stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .contains(nomeParcial.toLowerCase()))
                        .toList());
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
