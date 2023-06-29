package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Empresa representa uma empresa que agrega filiais com números de indentificação distintas
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see Filial
 * @since 2023
 */
public class Empresa implements LeitordeEstoque {

    private String nome;
    private List<Filial> filiais;

    /**
     * Cria uma empresa com o nome especificado
     *
     * @param nome nome da empresa
     */
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

    // -- CRUD DE FILIAL --

    /**
     * Retorna a primeira filial da lista de filiais com o id escolhido, caso não exista joga uma exceção
     *
     * @param id id da filial a ser pesquisada
     * @return primeira filial com o mesmo id presente na lista de filiais
     * @throws java.util.NoSuchElementException caso não exista uma filial com esse Id
     */
    public Filial lerFilial(int id) {
        return filiais.stream().filter(filial -> filial.getId() == id).findFirst().orElseThrow();
    }

    /**
     * Cria uma filial com as características especificadas e insere na lista de filiais
     *
     * @param nome  nome da filial a ser adicionada
     * @param local local geográfico da filial a ser adicionada
     * @param id    número de indentificação da filial a ser adicionada
     */
    public void adicionarFilial(String nome, String local, int id) {
        filiais.add(new Filial(nome, local, id));
    }

    /**
     * Adiciona a filial criada na lista de filiais
     *
     * @param f filial a ser adicionada na lista
     */
    public void adicionarFilial(Filial f) {
        filiais.add(f);
    }

    /**
     * Remove a filial com o número de indentificação especificado da lista de filiais
     *
     * @param id número de indenfiticação da filial a ser removida
     */
    public void removerFilial(int id) {
        filiais.removeIf(filial -> filial.getId() == id);
    }

    /**
     * Remove a filial que é igual a filial especificada da lista de filiais
     *
     * @param f filial a ser removida da lista
     */
    public void removerFilial(Filial f) {
        filiais.removeIf(f::equals);
    }

    /**
     * Modifica os campos da filial com o número de indentificação especificado na lista
     *
     * @param nome   novo nome da filial
     * @param local  novo local geográfico da filial
     * @param novoId novo id da filial
     * @param id     id antigo da filial
     */
    public void atualizarFilial(String nome, String local, int novoId, int id) {
        Filial filial = lerFilial(id);
        filial.setId(novoId);
        filial.setLocal(local);
        filial.setNome(nome);
    }


    // -- LEITURA DE ESTOQUE GERAL --
    @Override
    public List<Item> getEstoque() {
        List<Item> todosItens = new ArrayList<>();
        filiais.forEach(filial -> todosItens.addAll(filial.getEstoque()));
        return todosItens;
    }

    /**
     * Busca no estoque inteiro o primeiro item com o id definido, joga uma exceção caso não seja encontrado
     * um item
     *
     * @param id identificação do item a ser pesquisado
     * @return primeiro item com o id escolhido
     * @throws java.util.NoSuchElementException caso não exista uma filial com esse Id
     */
    @Override
    public Item buscarItem(int id) {
        return getEstoque().stream().filter(item -> item.getNome().equals(nome)).findFirst().orElseThrow();
    }

    /**
     * Busca no estoque inteiro todos os itens com nomes que contenham a string definida
     *
     * @param nomeParcial   string parcial a ser usada na pesquisa
     * @param caseSensitive sensibilidade a letras maiúsculas ou minúsculas
     * @return lista de itens com nomes que contém a string definida
     */
    @Override
    public List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return
                getEstoque().stream().filter(item -> item.getNome().contains(nomeParcial)).toList();
        else return
                getEstoque().stream()
                        .filter(item -> item.getNome().toLowerCase().contains(nomeParcial.toLowerCase())).toList();
    }

    /**
     * Busca no estoque inteiro todos os itens com nomes iguais à string definida
     *
     * @param nome          nome do item a ser pesquisado
     * @param caseSensitive sensibilidade a letras maiúsculas ou minúsculas
     * @return lista de itens com nomes iguais ao definido
     */
    @Override
    public List<Item> buscarItens(String nome, boolean caseSensitive) {
        if (caseSensitive) return
                getEstoque().stream().filter(item -> item.getNome().equals(nome)).toList();
        else return
                getEstoque().stream().filter(item -> item.getNome().equalsIgnoreCase(nome)).toList();
    }

    /**
     * Busca no estoque inteiro o primeiro item com nome definido, sensível a maiúsculas e minúsculas
     *
     * @param nome nome a ser pesquisado
     * @return primeiro item com esse mesmo nome
     */
    @Deprecated
    public Item buscarItem(String nome) {
        return getEstoque().stream().filter(item -> item.getNome().equals(nome)).findFirst().orElse(null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Filial> getFiliais() {
        return filiais;
    }

    public void setFiliais(ArrayList<Filial> filiais) {
        this.filiais = filiais;
    }

}
