package main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Empresa dreamworks = new Empresa("DreamWorks inc.");
		dreamworks.adicionarFilial("ACME inc.", "Brazil", 0);
		dreamworks.adicionarFilial("Lockheed martin inc.", "US", 1);
		dreamworks.adicionarFilial("Modestos inc.", "Mexico", 6);
		System.out.println(dreamworks.getFiliaisDescricao());
	}

}
