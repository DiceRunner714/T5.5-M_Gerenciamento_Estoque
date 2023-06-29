package modelo;

/**
 * Classe ProdutoQuimico simula um produto do tipo produto quimico e herda da classe item
 *
 * @author Eduardo Matheus dos Santos Sandes
 * @version 1.0
 * @see Item
 * @since 2023
 */
public class ProdutoQuimico extends Item {

    //variáveis da classe
    private int perigoaSaude;
    private int riscoDeFogo;
    private int reatividade;
    private String perigoEspecifico;

    //metodo construtor

    /**
     * Cria um ProdutoQuimico com os atributos de Item e de ProdutoQuimico
     *
     * @param nome             nome do produto químico
     * @param categoria        categoria do produto químico
     * @param valor            preço/custo do produto químico
     * @param quantidade       quantidade em disponível em estoque
     * @param id               numero que representa o item no estoque
     * @param perigoEspecifico perigo específico do produto químico
     * @param riscoDeFogo      numero que indica quão inflamável é o produto quimico
     * @param reatividade      numero que indica a reatividade do produto químico
     * @param perigoaSaude     numero que indica o quão nocivo o produto químico é
     */
    public ProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id,
                          String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude) {
        super(nome, categoria, valor, quantidade, id);
        //atributos do filho
        this.perigoaSaude = perigoaSaude;
        this.riscoDeFogo = riscoDeFogo;
        this.reatividade = reatividade;
        this.perigoEspecifico = perigoEspecifico;
        ajustarRestricao();
    }

    /**
     * Cria um ProdutoQuimico apenas com os atributos da classe Item
     *
     * @param nome       nome do produto químico
     * @param categoria  categoria do produto químico
     * @param valor      preço/custo do produto químico
     * @param quantidade quantidade disponível em estoque
     * @param id         numero que representa o item no estoque
     */
    public ProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id) {
        super(nome, categoria, valor, quantidade, id);
        // Assumir que um item é perigoso quando não especificado é menos arriscado
        riscoDeFogo = 5;
        reatividade = 5;
        perigoaSaude = 5;
        perigoEspecifico = "";
        this.restrito = true;
    }

    @Override
    protected void ajustarRestricao() {
        // TODO: se essas qualidades não forem complementares então esse método irá causar problemas
        restrito = perigoaSaude >= 3 || riscoDeFogo >= 3 || reatividade >= 3;
    }

    public boolean isRestrito() {
        return restrito;
    }

    public String listarCaracteristicasBasicas() {
        return super.listarCaracteristicasBasicas() + String.format("""
                        ---Produto Químico---
                        Perigo à saúde: %d
                        Risco de Fogo: %d
                        Reatividade : %d
                        Perigo específico: %s
                        restrito: %b
                        """, perigoaSaude, riscoDeFogo, reatividade,
                perigoEspecifico, restrito);
    }

    //Outros metodos
    //METODO RESTRINGIR

    /**
     * Restringe um ProdutoQuimico caso seus atributos o caracterizem como nocivo ou perigoso
     *
     * @throws NivelRestricaoInadequadoException caso o nível de perigo desse produto químico seja abaixo do mínimo
     */
    @Override
    public void restringir() throws NivelRestricaoInadequadoException {
        if (perigoaSaude >= 3 || riscoDeFogo >= 3 || reatividade >= 3) {
            restrito = true;
        } else {
            throw new NivelRestricaoInadequadoException(
                    "Erro ao restringir: o nível de risco desse Produto Químico não é alto o suficiente"
            );
        }
    }

    //METODO LIBERAR

    /**
     * Libera um ProdutoQuimico caso seus atributos o caracterizem como seguro
     *
     * @throws NivelRestricaoInadequadoException caso o nível de perigo desse produto químico seja alto demais
     */
    @Override
    public void liberar() throws NivelRestricaoInadequadoException {
        if (perigoaSaude <= 2 && riscoDeFogo <= 2 && reatividade <= 2) {
            restrito = false;
        } else {
            throw new NivelRestricaoInadequadoException(
                    "Erro ao liberar: o nível de risco desse Produto Químico não é baixo o suficiente"
            );
        }
    }

    //gets & sets
    public int getPerigoaSaude() {
        return perigoaSaude;
    }

    public void setPerigoaSaude(int perigoaSaude) {
        this.perigoaSaude = perigoaSaude;
    }

    public int getRiscoDeFogo() {
        return riscoDeFogo;
    }

    public void setRiscoDeFogo(int riscoDeFogo) {
        this.riscoDeFogo = riscoDeFogo;
    }

    public int getReatividade() {
        return reatividade;
    }

    public void setReatividade(int reatividade) {
        this.reatividade = reatividade;
    }

    public String getPerigoEspecifico() {
        return perigoEspecifico;
    }

    public void setPerigoEspecifico(String perigoEspecifico) {
        this.perigoEspecifico = perigoEspecifico;
    }


}
