package main;

import java.util.ArrayList;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Empresa dreamworks = new Empresa("DreamWorks inc.");
		
		Filial filial1 = new Filial("ACME inc.", "Brazil", 0);
		Filial filial2 = new Filial("Lockheed martin inc.", "US", 1);
		Filial filial3 = new Filial("Modestos inc.", "Mexico", 6);
		
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

		// <---Testes de empresa-->


		// --CRUD de filial--
		// Adicionar Filial
		System.out.println("ADICIONAR FILIAL INICIADO");
		System.out.println(dreamworks);
		dreamworks.adicionarFilial(filial1);
		dreamworks.adicionarFilial(filial2);
		dreamworks.adicionarFilial(filial3);
		System.out.println(dreamworks);
		System.out.println("ADICIONAR FILIAL FINALIZADO");

		// Ler filial
		System.out.println("LER FILIAL INICIADO");
		System.out.println(dreamworks.lerFilial(0));
		System.out.println("LER FILIAL FINALIZADO");

		// Ler filiais
		System.out.println("LER FILIAIS INICIADO");
		ArrayList<Filial> filiais = dreamworks.getFiliais();
		for (Filial f : filiais) {
			System.out.println(f);
		}
		System.out.println("LER FILIAIS FINALIZADO");

		// Atualizar filial
		System.out.println("ATUALIZAR FILIAIS INICIADO");
		System.out.println(dreamworks.lerFilial(0));
		dreamworks.atualizarFilial("NewName", "NewLocation", 0, 0);
		System.out.println(dreamworks.lerFilial(0));
		System.out.println("ATUALIZAR FILIAIS FINALIZADO");

		// Remover filial
		System.out.println("REMOVER FILIAL INICIADO");
		System.out.println(dreamworks);
		dreamworks.removerFilial(6);
		System.out.println(dreamworks);
		System.out.println("REMOVER FILIAL FINALIZADO");


		// --REQUISITOS MINIMOS--

		// Ler todo o estoque
		System.out.println("LER TODO O ESTOQUE INICIADO");
		System.out.println(dreamworks.lerTodoEstoque());
		System.out.println("LER TODO O ESTOQUE FINALIZADO");

		// Busca de item por nome
		System.out.println("BUSCA DE ITEM POR NOME INICIADO");
		System.out.println(dreamworks.buscarItem("Tênis"));
		System.out.println("BUSCA DE ITEM POR NOME FINALIZADO");

		// Filtro de todos itens de estoque de uma filial
		System.out.println("LISTAGEM DE ITEMS POR FILIAL INICIADO");
		dreamworks.lerFilial(0).adicionarItem(
				new Item("sapatênis", 5, "lixo", 599.5, 60)
		);

	}
	
}
