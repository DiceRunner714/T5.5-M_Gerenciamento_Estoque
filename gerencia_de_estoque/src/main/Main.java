package main;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Empresa dreamworks = new Empresa("DreamWorks inc.");
		
		Filial filial1 = new Filial("ACME inc.", "Brazil", 0);
		Filial filial2 = new Filial("Lockheed martin inc.", "US", 1);
		Filial filial3 = new Filial("Modestos inc.", "Mexico", 6);
		
		dreamworks.adicionarFilial("ACME inc.", "Brazil", 0);
		dreamworks.adicionarFilial("Lockheed martin inc.", "US", 1);
		dreamworks.adicionarFilial("Modestos inc.", "Mexico", 6);
		System.out.println(dreamworks.getFiliaisDescricao());
		
		/*dreamworks.adicionarFilial("ACME inc.", "Brazil", 0);
		dreamworks.adicionarFilial("Lockheed martin inc.", "US", 1);
		dreamworks.adicionarFilial("Modestos inc.", "Mexico", 6);
		System.out.println(dreamworks.getFiliaisDescricao());*/
		
		Item item1 = new Item("Tênis", 15, "Calçados", 110, 0);
		Item item2 = new Item("Computador", 34, "Eletrônico", 3860, 4);
		
		filial1.adicionarItem(item1);
		filial1.adicionarItem(item2);
		filial1.getItem(0).listarCaracteristicasBasicas();
		filial1.getItem(4).listarCaracteristicasBasicas();
		filial1.getItem(4).atualizarItem("Laranja", 345, "Alimentos", 2.10, 4);
		filial1.getItem(4).listarCaracteristicasBasicas();
	}
	
}
