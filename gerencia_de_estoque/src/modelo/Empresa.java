package modelo;

import java.util.ArrayList;

public class Empresa {

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

    public ArrayList<Item> lerTodoEstoque() {
        ArrayList<Item> todosItens = new ArrayList<>();
        filiais.forEach(filial -> todosItens.addAll(filial.getEstoque()));
        return todosItens;
    }

    @Deprecated
    public Item buscarItem(String nome) {
        return lerTodoEstoque()
                .stream()
                .filter(item -> item.getNome().equals(nome))
                .findFirst()
                .orElse(null);
        // TODO: orElse null, ver se é uma boa idéia
    }

    public void adicionarFilial(String nome, String local, int id) {
        filiais.add(new Filial(nome, local, id));
    }

    // Overloading que permite adicionar filial criada externamente
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

    public ArrayList<Item> buscaParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return new ArrayList<>(
                lerTodoEstoque().stream()
                        .filter(item -> item.getNome().contains(nomeParcial))
                        .toList());
        else return new ArrayList<>(
                lerTodoEstoque().stream()
                        .filter(item -> item.getNome()
                                .toLowerCase()
                                .contains(nomeParcial.toLowerCase()))
                        .toList());
    }

    public ArrayList<Item> buscarItens(String nome) {
        return new ArrayList<>(
                lerTodoEstoque().stream().filter(item -> item.getNome().equals(nome)).toList()
        );
    }

    // Retorna filial com id igual ao parâmetro, caso contrário
    // retorna null
    public Filial lerFilial(int id) {
        return filiais
                .stream()
                .filter(filial -> filial.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // GETS E SETS
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
