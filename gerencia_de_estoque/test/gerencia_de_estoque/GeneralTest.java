/**
 * 
 */
package gerencia_de_estoque;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.Filial;
import modelo.NivelRestricaoInadequadoException;
import modelo.ProdutoQuimico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author andre
 *
 */
class GeneralTest {

	/**Testes para os métodos que retornam ElementoInexistenteException para filiais
	 * não presentes na empresa, evitando situações conflitantes
	 */
	@Test
	void TesteFilialInexistente() {
		//Filial inexistente
		ControleEmpresa controleEmpresa = new ControleEmpresa("teste-filial");

		Filial filialInexistente = new Filial("null", "null",  0);

		//Atualizar filial
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEmpresa.atualizarFilial("teste", "teste", 5, filialInexistente));

		//Excluir filial (id)
		Assertions.assertThrows(ElementoInexistenteException.class, () -> controleEmpresa.excluirFilial(5));

		//Excluir filial (objeto)
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEmpresa.excluirFilial(filialInexistente));

		//Buscar filial de um item
		// Item sem filial:
		Farmaceutico ItemSemFilial = new Farmaceutico("ItemSemFilial", "nao", 5.99, 5, 5);
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEmpresa.buscarFilialaPartirdeItem(ItemSemFilial));

		//Setup estoque de filial
		Filial f = new Filial("filial", "local", 1);
		try {
			controleEmpresa.adicionarFilial(f);
		} catch (IdRepetidoException e) {
			throw new RuntimeException(e);
		}
		ControleEstoqueFilial controleEstoqueFilial = new ControleEstoqueFilial(controleEmpresa, f);

		//remover filial, empresa não tem mais filiais
		try {
			controleEmpresa.excluirFilial(f);
		} catch (ElementoInexistenteException e) {
			throw new RuntimeException(e);
		}

		//Adicionar um item a uma filial não existente
		Farmaceutico farmaSemFilial = new Farmaceutico("teste", "teste", 0, 0, 0);

		//Farmaceutico
		// por objeto
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoqueFilial.adicionarFarmaceutico(farmaSemFilial));

		// por parâmetros
		// --básicos--
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoqueFilial.adicionarFarmaceutico("nome","categoria",0,0,0));

		// --completos--
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoqueFilial.adicionarFarmaceutico(
						"nome",
						"categoria",
						0,
						0,
						0,
						"tarja",
						"composicao",
						false,
						false,
						false));


		//Produto quimico

		// por parâmetros
		// --básicos--
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoqueFilial.adicionarProdutoQuimico("nome","categoria",0,0,0));

		// --completos--
		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoqueFilial.adicionarProdutoQuimico(
						"nome",
						"categoria",
						0,
						0,
						0,
						"perigoEspecifico",
						0,
						0,
						0));
	}
	
	@Test
	void testeNivelRestricao() {
		// Farmaceutico não restrito
		Farmaceutico farmaNaoRestrito = new Farmaceutico("nome", "categoria", 10.0, 30, 1, "preta",
				"composicao", true, false, false);

		// Farmaceutico restrito
		Farmaceutico farmaRestrito = new Farmaceutico("nome", "categoria", 10.0, 30, 1, "preta",
				"composicao", true, true, false);

		// Restringir farmaceutico
		Assertions.assertThrows(NivelRestricaoInadequadoException.class, farmaNaoRestrito::restringir);

		Assertions.assertDoesNotThrow(farmaRestrito::restringir);

		// Liberar Farmaceutico
		Assertions.assertDoesNotThrow(farmaNaoRestrito::liberar);

		Assertions.assertThrows(NivelRestricaoInadequadoException.class, farmaRestrito::liberar);

		// Produto Químico não restrito
		ProdutoQuimico pqNaoRestrito = new ProdutoQuimico("nome", "categoria",
				15.1, 50, 2, "perigo especifico", 2, 1, 0);

		// Produto Químico restrito
		ProdutoQuimico pqRestrito = new ProdutoQuimico("nome", "categoria",
				15.1, 50, 2, "perigo especifico", 5, 5, 5);


		// Restringir produto químico
		Assertions.assertThrows(NivelRestricaoInadequadoException.class,
				pqNaoRestrito::restringir);

		Assertions.assertDoesNotThrow(pqRestrito::restringir);

		// Liberar produto químico
		Assertions.assertDoesNotThrow(pqNaoRestrito::liberar);

		Assertions.assertThrows(NivelRestricaoInadequadoException.class, pqRestrito::liberar);
	}

	@Test
	void testeItemInexistente() throws IdRepetidoException, ElementoInexistenteException {


		// Adicionar uma filial a ter seu esstoque gerenciado
		ControleEmpresa controleEmpresa = new ControleEmpresa("empresa");
		Filial filialGerenciada = new Filial("nome", "local", 0);
		controleEmpresa.adicionarFilial(filialGerenciada);

		// Criar um controle do estoque dessa filial
		ControleEstoqueFilial controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialGerenciada);


		// TESTE DE ATUALIZAÇÃO

		// -- Características básicas --

		Farmaceutico itemInexistente = new Farmaceutico(
				"não presente",
				"categoria",
				1.0,
				0,
				0,
				"preta",
				"composicao",
				true,
				true,
				false);

		Farmaceutico itemExistente = new Farmaceutico(
				"presente",
				"categoria",
				1.0,
				0,
				0,
				"preta",
				"composicao",
				true,
				true,
				false);
		controleEstoque.adicionarFarmaceutico(itemExistente);

		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoque.atualizarCaracteristicasBasicas(
						"novo nome",
						"nova categoria",
						5.9,
						5,
						5,
						itemInexistente));

		Assertions.assertDoesNotThrow(() -> controleEstoque.atualizarCaracteristicasBasicas(
				"novo nome",
				"nova categoria",
				5.9,
				5,
				5,
				itemExistente));

		// -- Campos Farmacêutico --
		controleEstoque.limparEstoque();

		Farmaceutico farmaceuticoInexistente = new Farmaceutico(
				"Farma inexistente",
				"categoria",
				1.0,
				0,
				0,
				"preta",
				"composicao",
				true,
				true,
				false);

		Farmaceutico farmaceuticoExistente = new Farmaceutico(
				"Farma existente",
				"categoria",
				1.0,
				10,
				0,
				"preta",
				"composicao",
				true,
				true,
				false);
		controleEstoque.adicionarFarmaceutico(farmaceuticoExistente);

		Assertions.assertThrows(ElementoInexistenteException.class,() -> controleEstoque.atualizarFarmaceutico(
				"tarja",
				"composicao",
				true,
				true,
				true,
				farmaceuticoInexistente));

		Assertions.assertDoesNotThrow(() -> controleEstoque.atualizarFarmaceutico(
				"tarja",
				"composicao",
				true,
				true,
				true,
				farmaceuticoExistente));

		// -- Campos produto químico --

		controleEstoque.limparEstoque();
		ProdutoQuimico quimicoInexistente = new ProdutoQuimico(
				"Inexistente",
				"categoria",
				10.0,
				0,
				0,
				"perigo",
				1 ,
				1,
				1);

		ProdutoQuimico quimicoExistente = new ProdutoQuimico(
				"Existente",
				"categoria",
				10.0,
				0,
				0,
				"perigo",
				1 ,
				1,
				1);
		controleEstoque.adicionarProdutoQuimico(quimicoExistente);


		Assertions.assertThrows(ElementoInexistenteException.class,
				() -> controleEstoque.atualizarProdutoQuimico(
						"radioativo",
						5,
						5,
						5,
						quimicoInexistente
				));

		Assertions.assertDoesNotThrow(() -> controleEstoque.atualizarProdutoQuimico(
						"radioativo",
						5,
						5,
						5,
						quimicoExistente
				));

		// TESTE DE EXCLUSÃO
		controleEstoque.limparEstoque();

		controleEstoque.adicionarFarmaceutico(itemExistente);
		Assertions.assertDoesNotThrow(() -> controleEstoque.removerItem(itemExistente));

		Assertions.assertThrows(ElementoInexistenteException.class,() -> controleEstoque.removerItem(itemInexistente));

	}
}
