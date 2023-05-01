package main;

public class Farmaceutico extends Item {
	//variaveis da classe
	private String tarja;
    private boolean receita;
    private boolean retencaoDeReceita;
    private  String[] composicao;
    private boolean generico;


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

	//Outros metodos
    //METODO RESTRINGIR
	public void restringir(){
		// É necessário usar um setter para restringir, pois o atributo herdado do pai é privado
    	if(tarja.equals("preta")&&retencaoDeReceita){
        	this.setRestrito(true);
    	}
    }

	//METODO LIBERAR
    public void liberar(){
    	if(tarja.equals("preta")&&retencaoDeReceita){
        	this.setRestrito(false);
    	}
    }
    	
    
    
}
