package modelo;

import java.util.List;

public interface LeitordeEstoque {
    List<Item> getEstoque();

    Item buscarItem(int id);

    List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive);

    List<Item> buscarItens(String nome, boolean caseSensitive);
}
