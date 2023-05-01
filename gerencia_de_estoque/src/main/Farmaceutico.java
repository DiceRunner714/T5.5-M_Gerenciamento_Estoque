package main;

public class Farmaceutico extends Item {
	//variaveis da classe
	private String tarja;
    private boolean receita;
    private boolean retencaoDeReceita;
    private composicao String[];
    private boolean generico;

    //metodo construtor
    public ProdutoQuimico(String nome, int quantidade, String categoria, double valor, int id, boolean restrito, String tarja, boolean receita, boolean retencaoDeReceita, String[], generico boolean) {
    	//atributos do pai
    	this.setNome(nome);
    	this.setQuantidade(quantidade);
    	this.setCategoria(categoria);
    	this.setValor(valor);
    	this.setId(id);
    	this.setRestrito(restrito);
    	//atributos do filho
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

	public composicao[] getString() {
		return String;
	}

	public void setString(composicao[] string) {
		String = string;
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
    	if(tarja string.equals('preta')&&retencaoDeReceita == true){
        	restrito = true;
    	}
    }

	//METODO LIBERAR
    public void liberar(){
    	if(tarja string.equals('preta')&&retencaoDeReceita == false){
        	restrito = false;
    	}
    }
    	
    
    
}
