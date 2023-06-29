package controle;

import modelo.Item;

import java.util.List;

/**
 * Interface LeitorEstoque define métodos de leitura de um estoque qualquer
 * * @author André Emanuel Bipo da Silva
 * * @version 1.0
 * * @since 2023
 */
public interface LeitorEstoque {
    /**
     * Busca por itens pelos seus nomes completos
     *
     * @param nome nome completo do item a ser pesquisado
     * @return itens com o nome escolhido
     */
    List<Item> buscarItens(String nome);

    /**
     * Filtra um estoque qualquer pelos seus itens vazios
     *
     * @param estoque estoque a ser filtrado
     * @return lista com itens de quantidade zero no estoque
     */
    List<Item> getItensVazios(List<Item> estoque);

    /**
     * Busca itens com apenas uma parte de seu nomeParcial
     *
     * @param nomeParcial   parte do nome dos itens a serem buscados
     * @param caseSensitive sensibilidade a letras maiúsculas e minúsculas
     * @return Uma lista com todos os items que contém o nomeParcial em seu nome
     */
    List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive);

    List<Item> getEstoque();

}
