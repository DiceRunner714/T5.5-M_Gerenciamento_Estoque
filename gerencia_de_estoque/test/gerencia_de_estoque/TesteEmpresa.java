/**
 * 
 */
package gerencia_de_estoque;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import modelo.Empresa;

/**
 * @author andre
 *
 */
class TesteEmpresa {

	/**
	 * Test method for {@link modelo.Empresa#Empresa(java.lang.String)}.
	 */
	@Test
	void testEmpresa() {
		Empresa empresa = new Empresa("teste");
	}

	/**
	 * Test method for {@link modelo.Empresa#buscarItem(int)}.
	 */
	@Test
	void testBuscarItemInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link modelo.Empresa#buscarItensParcial(java.lang.String, boolean)}.
	 */
	@Test
	void testBuscarItensParcial() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link modelo.Empresa#buscarItens(java.lang.String, boolean)}.
	 */
	@Test
	void testBuscarItens() {
		fail("Not yet implemented");
	}

}
