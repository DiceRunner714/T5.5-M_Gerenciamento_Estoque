package controle;

import modelo.Item;

import java.util.ArrayList;

public interface LeitorEstoque {
    public ArrayList<Item> buscarItens(String nome);

    public ArrayList<Item> getItensVazios(ArrayList<Item> estoque);

    public ArrayList<Item> buscarItensParcial(String nome, boolean caseSensitive);

    public ArrayList<Item> getEstoque();

}
