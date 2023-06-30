package modelo;

/**
 * Classe Farmaceutico simula um produto do tipo farmacêutico e herda da classe item
 *
 * @author Eduardo Matheus dos Santos Sandes
 * @version 1.0
 * @see Item
 * @since 2023
 */
public class Farmaceutico extends Item {
    //variaveis da classe
    private String tarja;
    private boolean receita;
    private boolean retencaoDeReceita;
    private String composicao;
    private boolean generico;


    /**
     * Cria um Farmaceutico com os atributos de Item e Farmaceutico
     *
     * @param nome              nome do farmacêutico
     * @param categoria         categoria do farmacêutico
     * @param valor             preço/custo do farmacêutico
     * @param quantidade        quantidade disponível em estoque
     * @param id                número que representa o item no estoque
     * @param tarja             tarja do farmacêutico
     * @param composicao        ingredientes presentes no farmacêutico
     * @param receita           indica se o farmacêutico possui receita ou não
     * @param retencaoDeReceita retenção de receita
     * @param generico          indica se o famacêutico é genérico ou de marca
     */
    public Farmaceutico(String nome, String categoria, double valor, int quantidade, int id,
                        String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                        boolean generico) {
        super(nome, categoria, valor, quantidade, id);
        this.tarja = tarja;
        this.receita = receita;
        this.retencaoDeReceita = retencaoDeReceita;
        this.composicao = composicao;
        this.generico = generico;
        ajustarRestricao();
    }

    /**
     * Cria um Farmaceutico apenas com os atributos da classe Item
     *
     * @param nome       nome do farmacêutico
     * @param categoria  categoria do farmacêutico
     * @param valor      preço/custo do farmacêutico
     * @param quantidade quantidade disponível em estoque
     * @param id         número que representa o item no estoque
     */
    public Farmaceutico(String nome, String categoria, double valor, int quantidade, int id) {
        super(nome, categoria, valor, quantidade, id);
        // Assumir que o item é perigoso
        this.tarja = "preta";
        this.composicao = "";
        this.receita = true;
        this.retencaoDeReceita = true;
        this.generico = true;
        this.restrito = true;
    }

    public String listarCaracteristicasBasicas() {

        return super.listarCaracteristicasBasicas() + String.format("""
                        ---Farmacêutico---
                        Tarja: %s
                        Necessita de receita: %b
                        Retenção de receita: %b
                        Composição: %s
                        genérico: %b
                        restrito: %b
                        """, tarja, receita, retencaoDeReceita,
                composicao, generico, restrito);
    }

    //Outros metodos
    //METODO RESTRINGIR

    /**
     * Restringe um Farmaceutico caso seus atributos o caracterizem como perigoso ou controlado
     *
     * @throws NivelRestricaoInadequadoException caso esse item não tenha o estado necessário para ser restrito
     */
    public void restringir() throws NivelRestricaoInadequadoException {
        if (tarja.equals("preta") && retencaoDeReceita) {
            restrito = true;
        } else {
            throw new NivelRestricaoInadequadoException(
                    "Erro ao restringir: o nível de risco desse farmacêutico não é alto o suficiente"
            );
        }
    }

    //METODO LIBERAR

    /**
     * Libera um Farmaceutico caso seus atributos o caracterizem como seguro ou permitido
     *
     * @throws NivelRestricaoInadequadoException caso esse item não tenha o estado necessário para ser liberado
     */
    public void liberar() throws NivelRestricaoInadequadoException {
        if (!(tarja.equals("preta") && retencaoDeReceita)) {
            restrito = false;
        } else {
            throw new NivelRestricaoInadequadoException(
                    "Erro ao liberar: o nível de risco desse farmacêutico não é baixo o suficiente"
            );
        }
    }

    /**
     * Checa previamente se esse farmacêutico pode ser restrito com as características escolhidas
     *
     * @param tarja             tarja escolhida para o teste
     * @param retencaoDeReceita estado de retenção de receita escolhida para o teste
     * @return true se com essas características o farmacêutico poderia ser restrito sem erros
     */
    public boolean checarPodeRestringir(String tarja, boolean retencaoDeReceita) {
        return tarja.equals("preta") && retencaoDeReceita;
    }

    /**
     * Checa previamente se esse farmacêutico pode ser liberado com as características escolhidas
     *
     * @param tarja             tarja escolhida para o teste
     * @param retencaoDeReceita estado de retenção de receita escolhida para o teste
     * @return true se com essas características o farmacêutico poderia ser liberado sem erros
     */
    public boolean checarPodeLiberar(String tarja, boolean retencaoDeReceita) {
        return !checarPodeRestringir(tarja, retencaoDeReceita);
    }

    @Override
    protected void ajustarRestricao() {
        restrito = tarja.equals("preta") && retencaoDeReceita;
    }

    public boolean isRestrito() {
        return restrito;
    }

    //gets & sets
    public String getTarja() {
        return tarja;
    }

    public void setTarja(String tarja) {
        this.tarja = tarja;
    }

    public boolean isReceita() {
        return receita;
    }

    public void setReceita(boolean receita) {
        this.receita = receita;
    }

    public boolean isRetencaoDeReceita() {
        return retencaoDeReceita;
    }

    public void setRetencaoDeReceita(boolean retencaoDeReceita) {
        this.retencaoDeReceita = retencaoDeReceita;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public boolean isGenerico() {
        return generico;
    }

    public void setGenerico(boolean generico) {
        this.generico = generico;
    }


}
