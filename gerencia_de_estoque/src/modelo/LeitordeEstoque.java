package modelo;

import java.util.ArrayList;

public interface LeitordeEstoque {
    ArrayList<Item> getEstoque();

    Item buscarItem(int id);

    ArrayList<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive);

    ArrayList<Item> buscarItens(String nome, boolean caseSensitive);
}
