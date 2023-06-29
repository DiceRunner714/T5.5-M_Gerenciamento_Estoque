package modelo;

/**
 * Classe que representa um erro ao operar sobre o nível de restrição
 * de um item sob condições inadequadas
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see Empresa
 * @see view.EmpresaView
 * @since 2023
 */
public class NivelRestricaoInadequadoException extends Exception {
    /**
     * Cria essa exceção com uma messagem de erro escolhida
     *
     * @param mensagemDeErro mensagem de erro associada a essa exceção
     */
    public NivelRestricaoInadequadoException(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
