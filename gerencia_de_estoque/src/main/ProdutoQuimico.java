package main;

public class ProdutoQuimico extends Item {
		//variaveis da classe
	    private int perigoaSaude;
	    private int riscoDeFogo;
	    private int reatividade;
	    private String perigoEspecifico;

	    //metodo construtor
	    public ProdutoQuimico(String nome, int quantidade, String categoria, double valor, int id, int perigoaSaude, int riscoDeFogo, int reatividade, String perigoEspecifico) {
			super(nome, quantidade, categoria, valor, id);
	    	//atributos do filho
	    	this.perigoaSaude = perigoaSaude;
	    	this.riscoDeFogo = riscoDeFogo;
	    	this.reatividade = reatividade;
	    	this.perigoEspecifico = perigoEspecifico;
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




		//Outros metodos
	    //METODO RESTRINGIR
		public void restringir(){
	    	if(perigoaSaude>=3||riscoDeFogo>=3||reatividade>=3){
	        	 setRestrito(true);
	    	}
	    }
		
		//METODO LIBERAR
	    public void liberar(){
	    	if(perigoaSaude<=2&&riscoDeFogo<=2&&reatividade<=2){
	        	setRestrito(false);
	    	}
	    }
	    	
	    
	    
}
