package modelo;

public abstract class Item {

    private String nome;
    private int quantidade;
    private String categoria;
    private double valor;
    private int id;

    public Item(String nome, String categoria, double valor, int quantidade, int id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.valor = valor;
        this.id = id;
    }

    public void atualizarCaracteristicasBasicas(String newNome, String newCategoria, double newValor, int newQuantidade, int newId) {
        nome = newNome;
        quantidade = newQuantidade;
        categoria = newCategoria;
        valor = newValor;
        id = newId;
    }

    public abstract void restringir();

    public abstract void liberar();

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
