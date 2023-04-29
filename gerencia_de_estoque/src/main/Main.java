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

		// --REQUISITOS MINIMOS--

		// <---Testes de empresa-->



		// --CRUD de filial--
		// Adicionar Filial
		System.out.println("ADICIONAR FILIAL INICIADO");
		System.out.println(dreamworks);
		dreamworks.adicionarFilial(filial1);
		dreamworks.adicionarFilial(filial2);
		dreamworks.adicionarFilial(filial3);
		dreamworks.adicionarFilial("ACME .CO", "Estados Unidos", 950);
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
		dreamworks.removerFilial(950);
		System.out.println(dreamworks);
		System.out.println("REMOVER FILIAL FINALIZADO");


		// Ler estoque completo
		System.out.println("LER TODO O ESTOQUE INICIADO");
		System.out.println(dreamworks.lerTodoEstoque());
		System.out.println("LER TODO O ESTOQUE FINALIZADO");

		// Busca de item por nome
		System.out.println("BUSCA DE ITEM POR NOME INICIADO");
		System.out.println(dreamworks.buscarItem("Tênis"));
		System.out.println("BUSCA DE ITEM POR NOME FINALIZADO");

		// Filtro de todos os itens de estoque de uma filial
		System.out.println("LISTAGEM DE ITEMS POR FILIAL INICIADO");

		dreamworks.lerFilial(6).adicionarItem(
				new Item("sapatênis", 5, "lixo", 599.5, 60)
		);

		System.out.println("<--ITEMS DA FILIAL 0-->");
		System.out.println(dreamworks.lerFilial(0).getAllItens());

		System.out.println("<--ITEMS DA FILIAL 6-->");
		System.out.println(dreamworks.lerFilial(6).getAllItens());
		System.out.println("LISTAGEM DE ITEMS POR FILIAL FINALIZADO");


		// Filtro de todos os itens de estoque vazio em uma determinada filial
		System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO INICIADO");
		dreamworks.adicionarFilial("TESTE ESTQOUE VAZIO", "NULLÂNDIA", 235);
		Filial filialEstoqueVazio = dreamworks.lerFilial(235);
		filialEstoqueVazio.adicionarItem(
				new Item("Televisor", 0, "Eletrônico", 500,90)
		);
		filialEstoqueVazio.adicionarItem(
				new Item("Servidor Rack", 0, "Computação", 500,150)
		);
		filialEstoqueVazio.adicionarItem(
				new Item("Raspberry PI pico", 5, "Eletrônico", 500,120)
		);

		System.out.println("TODOS OS ITEMS:");
		System.out.println(filialEstoqueVazio.getAllItens());
		System.out.println("APENAS ESTOQUE VAZIO:");
		System.out.println(filialEstoqueVazio.getEstoqueVazio());
		System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO FINALIZADO");

		// Gets e sets de Empresa
		// GETS
		System.out.println("TESTE GETS E SETS DE EMPRESA INICIADO");
		System.out.println("Get nome");
		System.out.println(dreamworks.getNome());
		System.out.println("Get filiais");
		System.out.println(dreamworks.getFiliais());


		// SETS
		System.out.println("Set nome");
		dreamworks.setNome("Alameda 59.5");
		System.out.println(dreamworks.getNome());

		System.out.println("Set filiais");
		ArrayList<Filial> novasFiliais = new ArrayList<>();
		novasFiliais.add(
				new Filial("Boeing inc.", "USA", 123)
		);
		novasFiliais.add(
			new Filial("Airbus inc.", "Europa", 124)
		);
		dreamworks.setFiliais(novasFiliais);
		System.out.println(dreamworks);

		System.out.println("TESTE GETS E SETS DE EMPRESA FINALIZADO");
	}

}