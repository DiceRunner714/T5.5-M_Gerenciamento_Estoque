package modelo;

/**
 * Classe que representa um erro ao operar sobre o nível de restrição
 * de um item sob condições inadequadas
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see Empresa
 * @see view.EmpresaView
 * @since 2023
 */
public class NivelRestricaoInadequadoException extends Exception {
    public NivelRestricaoInadequadoException(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
