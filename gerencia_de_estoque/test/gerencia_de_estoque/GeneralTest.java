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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


}
