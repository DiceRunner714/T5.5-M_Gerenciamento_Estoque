package modelo;

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
        Item item1 = new Farmaceutico("Benzodiapeno", "analgésico", 110, 15, 0);
        Item item2 = new ProdutoQuimico("Ácido sulfúrico", "ácido", 3860, 34, 4);
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

        dreamworks.lerFilial(6).adicionarItem(new Farmaceutico("aspirina",
                "analgésico", 599.5, 5, 60));

        System.out.println("<--ITEMS DA FILIAL 0-->");
        System.out.println(dreamworks.lerFilial(0).getEstoque());

        System.out.println("<--ITEMS DA FILIAL 6-->");
        System.out.println(dreamworks.lerFilial(6).getEstoque());
        System.out.println("LISTAGEM DE ITEMS POR FILIAL FINALIZADO");


        // Filtro de todos os itens de estoque vazio em uma determinada filial
        System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO INICIADO");
        dreamworks.adicionarFilial("TESTE ESTOQUE VAZIO", "NULLÂNDIA", 235);
        Filial filialEstoqueVazio = dreamworks.lerFilial(235);
        filialEstoqueVazio.adicionarItem(new ProdutoQuimico("Mercúrio", "Metal", 500, 0, 90));
        filialEstoqueVazio.adicionarItem(new Farmaceutico("Fluoxetina", "Venlafaxina", 500, 0, 150));
        filialEstoqueVazio.adicionarItem(new Farmaceutico("Minoxidil", "Loção capilar", 500, 5, 120));

        System.out.println("TODOS OS ITEMS:");
        System.out.println(filialEstoqueVazio.getEstoque());
        System.out.println("APENAS ESTOQUE VAZIO:");
        System.out.println(filialEstoqueVazio.getEstoqueVazio());
        System.out.println("FILTRO DE ITENS COM ESTOQUE VAZIO FINALIZADO");

        // CRUD DE ITEM
        System.out.println("CRUD DE ITEM INICIADO");

        dreamworks.adicionarFilial("CRUD ITEM", "CRUDlandia", 69);
        Filial crudlandia = dreamworks.lerFilial(69);

        // adicionar item
        System.out.println("ADICIONAR ITEM");
        crudlandia.adicionarItem(new ProdutoQuimico("Urânio Enriquecido", "Metal", 90, 123, 69));
        System.out.println(crudlandia.getEstoque());

        // atualizar item
        System.out.println("ATUALIZAR ITEM");
        ProdutoQuimico uranio = (ProdutoQuimico) crudlandia.buscarItem(69); // Usar typecasting aqui talvez não seja a melhor idéia
        uranio.atualizarCaracteristicasBasicas("Urânio esgotado", "Metal", 90, 100, 69);
        uranio.setReatividade(0);
        uranio.setPerigoaSaude(5);
        uranio.setRiscoDeFogo(0);
        uranio.setPerigoEspecifico("RAD");
        System.out.println(crudlandia.getEstoque());

        // ler item pelo id
        System.out.println("LER ITEM PELO ID");
        System.out.println(crudlandia.buscarItem(69));

        // ler item pelo nome
        System.out.println("LER ITEM PELO NOME");
        System.out.println(crudlandia.buscarItem("Urânio esgotado"));

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
                new Farmaceutico("Risperidon", "antipsicótico", 53.94, 5, 0,
                        "preta", "Risperidona 1mg, azdio 2mg", true, true, false)
        );
        restricoes.adicionarItem(
                new ProdutoQuimico("Azidoazide azide", "composto inorgânico", 999.99, 1,
                        1, null, 5, 5, 5)
        );

        Farmaceutico risperidon = (Farmaceutico) restricoes.buscarItem(0);
        ProdutoQuimico azido = (ProdutoQuimico) restricoes.buscarItem(1);
        System.out.println(restricoes.getEstoque());
        try {
            risperidon.restringir();    // Produtos perigosos serão restritos
            azido.restringir();
            System.out.println(restricoes.getEstoque());

            risperidon.liberar();       //Produtos não serão liberados, pois ainda são classificados como perigosos
            azido.liberar();
            System.out.println(restricoes.getEstoque());

            System.out.println("TESTE DE RESTRIÇÕES FINALIZADO");
        } catch (NivelRestricaoInadequadoException e) {
            System.out.println("Bruh");
        }
    }

}