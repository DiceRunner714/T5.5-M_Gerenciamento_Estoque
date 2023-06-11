package modelo;

public class ProdutoQuimico extends Item {

    //variáveis da classe
    private int perigoaSaude;
    private int riscoDeFogo;
    private int reatividade;
    private String perigoEspecifico;

    //metodo construtor
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
