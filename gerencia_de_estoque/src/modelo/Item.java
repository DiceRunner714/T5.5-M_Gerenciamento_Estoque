package modelo;

/**
 * Classe abstrata Item define atributos e metodos para as classes filhas: Farmaceutico e Produto Quimico
 *
 * @author Cássio Sousa dos Reis
 * @version 1.0
 * @see Farmaceutico
 * @see ProdutoQuimico
 * @since 2023
 */
public abstract class Item {

    protected boolean restrito;
    private String nome;
    private int quantidade;
    private String categoria;
    private double valor;
    private int id;

    /**
     * Construtor da classe Item
     *
     * @param nome       nome do item
     * @param categoria  categoria do item
     * @param valor      preço/custo do item
     * @param quantidade quantidade disponível em estoque
     * @param id         número que representa o item no estoque
     */
    public Item(String nome, String categoria, double valor, int quantidade, int id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.valor = valor;
        this.id = id;
    }

    /**
     * Metodo usado para atualizar os atributos de um item
     *
     * @param newNome       novo nome do item
     * @param newCategoria  nova categoria do item
     * @param newValor      novo valor do item
     * @param newQuantidade nova quantidade do item
     * @param newId         novo id do item
     */
    public void atualizarCaracteristicasBasicas(String newNome, String newCategoria, double newValor, int newQuantidade, int newId) {
        nome = newNome;
        quantidade = newQuantidade;
        categoria = newCategoria;
        valor = newValor;
        id = newId;
    }

    /**
     * Método usado para restringir um item.
     * Gera uma exceção caso o item nao possa ser restringido com base em seus atributos
     *
     * @throws NivelRestricaoInadequadoException caso esse item não tenha o estado necessário para ser restrito
     */
    public abstract void restringir() throws NivelRestricaoInadequadoException;

    /**
     * Método usado para liberar um item.
     * Gera uma exceção caso o item nao possa ser liberado com base em seus atributos
     *
     * @throws NivelRestricaoInadequadoException caso esse item não tenha o estado necessário para ser liberado
     */
    public abstract void liberar() throws NivelRestricaoInadequadoException;

    // Criado para automaticamente ajustar a restrição ao criar um item

    /**
     * Método para automaticamente ajustar a restrição ao criar um item
     */
    protected abstract void ajustarRestricao();

    abstract public boolean isRestrito();

    /**
     * Lista as características básicas desse item, destinado para uso em linha de comando
     *
     * @return uma string com as características básicas desse item
     */
    public String listarCaracteristicasBasicas() {
        return String.format("""
                        ---Produto----
                        ID: %d
                        Nome: %s
                        Categoria: %s
                        Qtd: %d
                        Valor: R$%.2f
                        """, id, nome,
                categoria, quantidade, valor);
    }

    public String toString() {
        return String.format("%d_%s", id, nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
