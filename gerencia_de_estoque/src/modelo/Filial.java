package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**Classe Filial representa uma filial associada a uma empresa
 * @author André Emanuel Bispo da Silva
 * @since 2023
 * @version 1.0
 * @see Item
 * @see Empresa
 */
public class Filial implements LeitordeEstoque {
    private String nome;
    private String local;
    private int id;
    private List<Item> estoque;

    /**Cria uma filial com o nome, local geográfico e número de identificação especificados,
     * criando um estoque vazio para a tal
     * @param nome nome da filial
     * @param local localização geográfica da filial
     * @param id identificação da filial
     */
    public Filial(String nome, String local, int id) {
        this.nome = nome;
        this.local = local;
        this.id = id;
        estoque = new ArrayList<>();
    }

    /**Retora uma string formatada com as características básicas da filial,
     * destinada ao uso na linha de comando
     * @return uma string com todas as características básicas da filial
     */
    public String listarCaracteristicasBasicacs() {
        return String.format("""
                ---Filial---
                nome: %s
                local: %s
                id: %d
                """, nome, local, id);
    }

    // -- CRUD DE ITEM --

    /**Adiciona o item criado ao estoque da filial
     * @param i item a ser adicionado
     */
    public void adicionarItem(Item i) {
        estoque.add(i);
    }

    /**Remove o item com o número de identificação especificado do estoque da filial
     * @param itemId número de identificação do item a ser removido
     */
    public void removerItem(int itemId) {
        estoque.removeIf(item -> item.getId() == id);
    }

    /**Remove o item com as mesmas características do item especificado do estoque da filial
     * @param itemEscolhido item a ser removido do estqoue da filial
     */
    public void removerItem(Item itemEscolhido) {
        estoque.removeIf(item -> item.equals(itemEscolhido));
    }

    // -- LEITURA DE ITEM --

    /**Busca um item no estoque com o nome exato especificado
     * @param nome nome do item a buscar
     * @return primeiro item com esse mesmo nome, caso não exista retorna null
     */
    @Deprecated
    public Item buscarItem(String nome) {
        return estoque.stream().filter(item -> Objects.equals(item.getNome(), nome)).findFirst().orElse(null);
    }

    /**Busca um item no estoque com o número de indentificação especificado
     * @param id número de identificação do item a ser buscado
     * @return o primeiro item com esse número de identificação
     * @throws java.util.NoSuchElementException quando nenhum item é encontrado
     */
    @Override
    public Item buscarItem(int id) {
        return estoque.stream().filter(item -> item.getId() == id).findFirst().orElseThrow();
    }

    /**Busca todos os itens do estoque com o nome que contém uma certa string
     * @param nomeParcial string a ser comparada com o nome dos itens
     * @param caseSensitive sensibilidade a letras maiúsculas e minúsculas
     * @return todos os itens com o nome que contém o nome parcial
     */
    @Override
    public List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive) return
                estoque.stream().filter(item -> item.getNome().contains(nomeParcial)).toList();
        else return
                estoque.stream()
                        .filter(item -> item.getNome().toLowerCase().contains(nomeParcial.toLowerCase())).toList();
    }

    /**Busca todos os itens do estoque com o mesmo nome especificado
     * @param nome nome dos itens a serem pesquisados
     * @param caseSensitive sensibilidade a letras maiúsculas e minúsculas
     * @return todos os itens com o mesmo nome especificado
     */
    @Override
    public List<Item> buscarItens(String nome, boolean caseSensitive) {
        if (caseSensitive) return
                estoque.stream().filter(item -> item.getNome().equals(nome)).toList();
        else return
                estoque.stream().filter(item -> item.getNome().equalsIgnoreCase(nome)).toList();
    }

    /**Override da classe Object, representa a filial com o número de identificação e seu nome
     * @return uma string com o id da filial e seu nome
     */
    @Override
    public String toString() {
        return String.format("%d_%s", id, nome);
    }

    /**Retorna o estoque da filial
     * @return uma lista com todos os itens da filial
     */
    public List<Item> getEstoque() {
        return estoque;
    }

    /**Substitui o estoque da filial
     * @param estoque lista de itens a ser utilizada na substituição
     */
    public void setEstoque(List<Item> estoque) {
        this.estoque = estoque;
    }

    /**Retorna os itens sem quantidade no estoque
     * @return uma lista com todos os itens com quantidade 0 no estoque
     */
    public List<Item> getEstoqueVazio() {
        return estoque.stream().filter(item -> item.getQuantidade() == 0).toList();
    }

    /**Override da classe Object, permite compara se duas filiais contém os campos
     * com valores iguais
     * @param o objeto a ser comparado
     * @return true se a filial contém as mesmas características, caso contrário false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Filial outraFilial)) {
            return false;
        }
        return this.nome.equals(outraFilial.getNome()) &&
                this.local.equals(outraFilial.getLocal()) &&
                this.id == outraFilial.getId();
    }

    /**Busca todos os itens do estoque com um nome contendo a string especificada
     * @param nomeParcial string a compara com os nomes dos itens
     * @param caseSensitive sensibilidade a letras maiúsculas e minúsculas
     * @return todos os itens com o nome contendo a string especificada
     */
    public List<Item> buscaParcial(String nomeParcial, boolean caseSensitive) {
        if (caseSensitive)  {
            return estoque.stream().filter(item -> item.getNome().contains(nomeParcial)).toList();
        } else  {
            return estoque.stream()
                            .filter(item -> item.getNome().toLowerCase().contains(nomeParcial.toLowerCase()))
                            .toList();
        }

    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
