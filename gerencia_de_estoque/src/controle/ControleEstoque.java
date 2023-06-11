package controle;

import modelo.Item;

import java.util.ArrayList;

public interface ControleEstoque {
    ArrayList<Item> buscarItens(String nome);

    ArrayList<Item> getItensVazios(ArrayList<Item> estoque);

    ArrayList<Item> buscarItensParcial(String nome, boolean caseSensitive);

    ArrayList<Item> getEstoque();

}
