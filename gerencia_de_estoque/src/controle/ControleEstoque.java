package controle;

import modelo.Item;

import java.util.List;

public interface ControleEstoque {
    List<Item> buscarItens(String nome);

    List<Item> getItensVazios(List<Item> estoque);

    List<Item> buscarItensParcial(String nome, boolean caseSensitive);

    List<Item> getEstoque();

}
