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
        for (Filial filial : filiais) {
            todosItens.addAll(filial.getEstoque());
        }
        return todosItens;
    }

    // TODO: remover buscarItem
    public Item buscarItem(String nome) {
        ArrayList<Item> todoEstoque = this.lerTodoEstoque();
        Item itemBuscado = null;
        for (Item item : todoEstoque) {
            if (item.getNome().equals(nome))
                itemBuscado = item;
        }

        return itemBuscado;
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
//        for (Filial filial : filiais) {
//            if (filial.getId() == id) {
//                filiais.remove(filial);
//                break;
//            }
//        }
    }

    // Overloading para remover filial por referência
    public void removerFilial(Filial f) {
        filiais.removeIf(filial -> f.equals(filial));
    }

    public void atualizarFilial(String nome, String local, int novoId, int id) {
        for (Filial filial : filiais) {
            if (filial.getId() == id) {
                filial.setId(novoId);
                filial.setLocal(local);
                filial.setNome(nome);
                break;
            }
        }
    }

    // Retorna filial com id igual ao parâmetro, caso contrário
    // retorna null
    public Filial lerFilial(int id) {
        Filial filialLida = null;
        for (Filial filial : filiais) {
            if (filial.getId() == id)
                filialLida = filial;
        }
        return filialLida;
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
