package modelo;

import java.util.ArrayList;

public class Empresa implements LeitordeEstoque {

    private String nome;
    private ArrayList<Filial> filiais;

    public Empresa(String nome) {
        this.nome = nome;
        filiais = new ArrayList<>();
    }

    public String toString() {
        return String.format("""
                ___EMPRESA___
                Nome: %s
                Filiais:
                %s
                """, nome, filiais.toString());
    }

    // -- CRUD DE FILIAL --
    public Filial lerFilial(int id) {
        return filiais
                .stream()
                .filter(filial -> filial.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    public void adicionarFilial(String nome, String local, int id) {
        filiais.add(new Filial(nome, local, id));
    }

    public void adicionarFilial(Filial f) {
        filiais.add(f);
    }

    public void removerFilial(int id) {
        filiais.removeIf(filial -> filial.getId() == id);
    }

    // Overloading para remover filial por referência
    public void removerFilial(Filial f) {
        filiais.removeIf(f::equals);
    }

    public void atualizarFilial(String nome, String local, int novoId, int id) {
        Filial filial = lerFilial(id);
        filial.setId(novoId);
        filial.setLocal(local);
        filial.setNome(nome);
    }


    // -- LEITURA DE ESTOQUE GERAL --
    @Override
    public ArrayList<Item> getEstoque() {
        ArrayList<Item> todosItens = new ArrayList<>();
        filiais.forEach(filial -> todosItens.addAll(filial.getEstoque()));
        return todosItens;
    }

    @Deprecated
    public Item buscarItem(String nome) {
        return getEstoque()
                .stream()
                .filter(item -> item.getNome().equals(nome))
                .findFirst()
                .orElse(null);
        // TODO: orElse null, ver se é uma boa idéia
    }

    @Override
    public Item buscarItem(int id) {
        return getEstoque()
                .stream()
                .filter(item -> item.getNome().equals(nome))
                .findFirst()
                .orElseThrow();
    }


    @Override
    public ArrayList<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                getEstoque().stream()
                        .filter(item -> item.getNome().contains(nomeParcial))
                        .toList());
        else return new ArrayList<>(
                getEstoque().stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .contains(nomeParcial.toLowerCase()))
                        .toList());
    }

    @Override
    public ArrayList<Item> buscarItens(String nome, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                getEstoque().stream()
                        .filter(item -> item.getNome().equals(nome))
                        .toList());
        else return new ArrayList<>(
                getEstoque().stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .equals(nome.toLowerCase()))
                        .toList());
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

}
