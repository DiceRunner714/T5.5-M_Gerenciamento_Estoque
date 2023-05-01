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
		
		Item item1 = new Farmaceutico("Benzodiapeno", 15, "analgésico", 110, 0);
		Item item2 = new ProdutoQuimico("Ácido sulfúrico", 34, "ácido", 3860, 4);
		
		filial1.adicionarItem(item1);
		System.out.println(filial1.getItem(0).getCaracteristicasBasicas());
		filial1.adicionarItem(item2);
		System.out.println(filial1.getItem(4).getCaracteristicasBasicas());
		filial1.getItem(4).atualizarCaracteristicasBasicas("Soda cáustica", 345,
				"Base", 2.10, 4);
		System.out.println(filial1.getItem(4).getCaracteristicasBasicas());

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
		System.out.println(dreamworks.buscarItem("Soda cáustica"));
		System.out.println("BUSCA DE ITEM POR NOME FINALIZADO");

		// Filtro de todos os itens de estoque de uma filial
		System.out.println("LISTAGEM DE ITEMS POR FILIAL INICIADO");

		dreamworks.lerFilial(6).adicionarItem(
				new Farmaceutico("aspirina", 5, "analgésico", 599.5, 60)
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
				new ProdutoQuimico("Mercúrio", 0, "Metal", 500,90)
		);
		filialEstoqueVazio.adicionarItem(
				new Farmaceutico("Fluoxetina", 0, "Venlafaxina", 500,150)
		);
		filialEstoqueVazio.adicionarItem(
				new Farmaceutico("Minoxidil", 5, "Loção capilar", 500,120)
		);

		System.out.println("TODOS OS ITEMS:");
		System.out.println(filialEstoqueVazio.getAllItens());
		System.out.println("APENAS ESTOQUE VAZIO:");
		System.out.println(filialEstoqueVazio.getEstoqueVazio());
		System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO FINALIZADO");

		// CRUD DE ITEM
		System.out.println("CRUD DE ITEM INICIADO");

		dreamworks.adicionarFilial("CRUD ITEM", "CRUDlandia", 69);
		Filial crudlandia = dreamworks.lerFilial(69);

		// adicionar item
		System.out.println("ADICIONAR ITEM");
		crudlandia.adicionarItem(new ProdutoQuimico("Urânio Enriquecido", 123,"Metal", 90, 69));
		System.out.println(crudlandia.getAllItens());

		// atualizar item
		System.out.println("ATUALIZAR ITEM");
		ProdutoQuimico uranio = (ProdutoQuimico) crudlandia.getItem(69); // Usar typecasting aqui talvez não seja a melhor idéia
		uranio.atualizarCaracteristicasBasicas("Urânio esgotado", 100,
				"Metal", 90, 69);
		uranio.setReatividade(0);
		uranio.setPerigoaSaude(5);
		uranio.setRiscoDeFogo(0);
		uranio.setPerigoEspecifico("RAD");
		System.out.println(crudlandia.getAllItens());

		// ler item
		System.out.println("LER ITEM");
		System.out.println(crudlandia.getItem(69));

		// remover item
		System.out.println("DELETAR ITEM");
		crudlandia.removerItem(69);
		System.out.println(crudlandia);
		System.out.println("CRUD DE ITEM FINALIZADO");


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