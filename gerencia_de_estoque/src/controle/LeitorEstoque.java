package controle;

import modelo.Item;

import java.util.List;

/**
 * Interface LeitorEstoque define métodos de leitura de um estoque qualquer
 *  * @author André Emanuel Bipo da Silva
 *  * @version 1.0
 *  * @since 2023
 */
public interface LeitorEstoque {
    List<Item> buscarItens(String nome);

    List<Item> getItensVazios(List<Item> estoque);

    List<Item> buscarItensParcial(String nome, boolean caseSensitive);

    List<Item> getEstoque();

}
