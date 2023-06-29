package controle;

/**
 * Exceção criada para casos de erro dois elementos tem o mesmo id
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @since 2023
 */
public class IdRepetidoException extends Exception {

    /**
     * Cria uma IdRepetidoException com essa mensagem
     *
     * @param mensagemdeErro mensagem de erro associada a essa exceção
     */
    public IdRepetidoException(String mensagemdeErro) {
        super(mensagemdeErro);
    }
}
