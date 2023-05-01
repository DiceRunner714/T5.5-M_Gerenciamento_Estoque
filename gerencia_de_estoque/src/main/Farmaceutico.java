package main;

import java.util.Arrays;

public class Farmaceutico extends Item {
    //variaveis da classe
    private String tarja;
    private boolean receita;
    private boolean retencaoDeReceita;
    private String[] composicao;
    private boolean generico;
    private boolean restrito;

    // método construtor
    public Farmaceutico(String nome, int quantidade, String categoria, double valor, int id,
                        String tarja, boolean receita, boolean retencaoDeReceita, String[] composicao, boolean generico) {
        // Construtor da classe geral
        super(nome, quantidade, categoria, valor, id);
        // atributos da classe filha
        this.tarja = tarja;
        this.receita = receita;
        this.retencaoDeReceita = retencaoDeReceita;
        this.composicao = composicao;
        this.generico = generico;
        this.restrito = false;
    }

    public Farmaceutico(String nome, int quantidade, String categoria, double valor, int id) {
        super(nome, quantidade, categoria, valor, id);
        this.restrito = false;
    }


    public String toString() {
        /*  Operador ternário para não travar o programa quando não houver composição:
                composição == null ? "" : Arrays.toString(composição)
            é equivalente a:
                if (composição == null) {
                    return ""
                else {
                    return Arrays.toString(composição)
                }
         */
        return super.toString() + String.format("""
                        ---Farmacêutico---
                        Tarja: %s
                        Necessita de receita: %b
                        Retenção de receita: %b
                        Composição: %s
                        genérico: %b
                        restrito: %b
                        """, tarja, receita, retencaoDeReceita,
                composicao == null ? "" : Arrays.toString(composicao), generico, restrito);
    }

    //Outros metodos
    //METODO RESTRINGIR
    public void restringir() {
        if (tarja.equals("preta") && retencaoDeReceita) {
            restrito = true;
        }
    }

    //METODO LIBERAR
    public void liberar() {
        if (tarja.equals("preta") && retencaoDeReceita) {
            restrito = false;
        }
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

    public String[] getComposicao() {
        return composicao;
    }

    public void setComposicao(String[] composicao) {
        this.composicao = composicao;
    }

    public boolean isGenerico() {
        return generico;
    }

    public void setGenerico(boolean generico) {
        this.generico = generico;
    }


}
