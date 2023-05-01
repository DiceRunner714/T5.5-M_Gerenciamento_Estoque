package main;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // --REQUISITOS MÍNIMOS--

        // <---Testes de empresa-->
        Empresa dreamworks = new Empresa("DreamWorks inc.");


        // --CRUD de filial--
        // Adicionar Filial
        System.out.println("ADICIONAR FILIAL INICIADO");
        System.out.println(dreamworks);
        Filial filial1 = new Filial("ACME inc.", "Brazil", 0);
        Filial filial2 = new Filial("Lockheed martin inc.", "US", 1);
        Filial filial3 = new Filial("Modestos inc.", "Mexico", 6);
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
        System.out.println(dreamworks.getFiliais());
        System.out.println("LER FILIAIS FINALIZADO");

        // Atualizar filial
        System.out.println("ATUALIZAR FILIAIS INICIADO");
        System.out.println(dreamworks.lerFilial(0));
        dreamworks.atualizarFilial("Novo nome", "Novo local", 0, 0);
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
        Item item1 = new Farmaceutico("Benzodiapeno", 15, "analgésico", 110, 0);
        Item item2 = new ProdutoQuimico("Ácido sulfúrico", 34, "ácido", 3860, 4);
        filial1.adicionarItem(item1);
        filial2.adicionarItem(item2);
        System.out.println(dreamworks.lerTodoEstoque());
        System.out.println("LER TODO O ESTOQUE FINALIZADO");

        // Busca de item por nome
        System.out.println("BUSCA DE ITEM POR NOME INICIADO");
        System.out.println(dreamworks.buscarItem("Ácido sulfúrico"));
        System.out.println("BUSCA DE ITEM POR NOME FINALIZADO");

        // Filtro de todos os itens de estoque de uma filial
        System.out.println("LISTAGEM DE ITEMS POR FILIAL INICIADO");

        dreamworks.lerFilial(6).adicionarItem(new Farmaceutico("aspirina", 5, "analgésico", 599.5, 60));

        System.out.println("<--ITEMS DA FILIAL 0-->");
        System.out.println(dreamworks.lerFilial(0).getItens());

        System.out.println("<--ITEMS DA FILIAL 6-->");
        System.out.println(dreamworks.lerFilial(6).getItens());
        System.out.println("LISTAGEM DE ITEMS POR FILIAL FINALIZADO");


        // Filtro de todos os itens de estoque vazio em uma determinada filial
        System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO INICIADO");
        dreamworks.adicionarFilial("TESTE ESTOQUE VAZIO", "NULLÂNDIA", 235);
        Filial filialEstoqueVazio = dreamworks.lerFilial(235);
        filialEstoqueVazio.adicionarItem(new ProdutoQuimico("Mercúrio", 0, "Metal", 500, 90));
        filialEstoqueVazio.adicionarItem(new Farmaceutico("Fluoxetina", 0, "Venlafaxina", 500, 150));
        filialEstoqueVazio.adicionarItem(new Farmaceutico("Minoxidil", 5, "Loção capilar", 500, 120));

        System.out.println("TODOS OS ITEMS:");
        System.out.println(filialEstoqueVazio.getItens());
        System.out.println("APENAS ESTOQUE VAZIO:");
        System.out.println(filialEstoqueVazio.getEstoqueVazio());
        System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO FINALIZADO");

        // CRUD DE ITEM
        System.out.println("CRUD DE ITEM INICIADO");

        dreamworks.adicionarFilial("CRUD ITEM", "CRUDlandia", 69);
        Filial crudlandia = dreamworks.lerFilial(69);

        // adicionar item
        System.out.println("ADICIONAR ITEM");
        crudlandia.adicionarItem(new ProdutoQuimico("Urânio Enriquecido", 123, "Metal", 90, 69));
        System.out.println(crudlandia.getItens());

        // atualizar item
        System.out.println("ATUALIZAR ITEM");
        ProdutoQuimico uranio = (ProdutoQuimico) crudlandia.lerItem(69); // Usar typecasting aqui talvez não seja a melhor idéia
        uranio.atualizarCaracteristicasBasicas("Urânio esgotado", 100, "Metal", 90, 69);
        uranio.setReatividade(0);
        uranio.setPerigoaSaude(5);
        uranio.setRiscoDeFogo(0);
        uranio.setPerigoEspecifico("RAD");
        System.out.println(crudlandia.getItens());

        // ler item
        System.out.println("LER ITEM");
        System.out.println(crudlandia.lerItem(69));

        // remover item
        System.out.println("DELETAR ITEM");
        crudlandia.removerItem(69);
        System.out.println(crudlandia);
        System.out.println("CRUD DE ITEM FINALIZADO");

        // TODO: Teste restringir
        System.out.println("TESTE DE RESTRIÇÕES INICIADO");

        dreamworks.setFiliais(new ArrayList<>());
        dreamworks.adicionarFilial("Teste de restrições", "Brasil", 0);
        Filial restricoes = dreamworks.lerFilial(0);

        restricoes.adicionarItem(
                new Farmaceutico("Risperidon", 5, "antipsicótico", 53.94, 0,
                        "preta", true, true, new String[]{"Risperidona 1mg"}, false)
        );
        restricoes.adicionarItem(
                new ProdutoQuimico("Azidoazide azide", 1, "composto inorgânico",
                        999.99, 1, 5, 5, 5, null)
        );

        Farmaceutico risperidon = (Farmaceutico) restricoes.lerItem(0);
        ProdutoQuimico azido = (ProdutoQuimico) restricoes.lerItem(1);
        System.out.println(restricoes.getItens());

        risperidon.restringir();    // Produtos perigosos serão restritos
        azido.restringir();
        System.out.println(restricoes.getItens());

        risperidon.liberar();       //Produtos não serão liberados, pois ainda são classificados como perigosos
        azido.liberar();
        System.out.println(restricoes.getItens());

        System.out.println("TESTE DE RESTRIÇÕES FINALIZADO");
    }

}