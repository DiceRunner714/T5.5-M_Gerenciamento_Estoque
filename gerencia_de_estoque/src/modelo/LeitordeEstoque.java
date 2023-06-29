package modelo;

import java.util.List;

/**
 * Interface definindo métodos genéricos para leitura de um estoque arbitrário
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @since 2023
 */
public interface LeitordeEstoque {
    List<Item> getEstoque();

    /**
     * Busca um item pelo seu id
     *
     * @param id id do item a ser buscado
     * @return item com o id escolhido
     */
    Item buscarItem(int id);

    /**
     * Busca itens com apenas uma parte de seu nomeParcial
     *
     * @param nomeParcial   parte do nome dos itens a serem buscados
     * @param caseSensitive sensibilidade a letras maiúsculas e minúsculas
     * @return Uma lista com todos os items que contém o nomeParcial em seu nome
     */
    List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive);

    /**
     * Busca por itens pelos seus nomes completos
     *
     * @param nome          nome completo do item a ser pesquisado
     * @param caseSensitive sensibilidade à letras maiúsculas e minúsculas
     * @return itens com o nome escolhido
     */
    List<Item> buscarItens(String nome, boolean caseSensitive);
}
