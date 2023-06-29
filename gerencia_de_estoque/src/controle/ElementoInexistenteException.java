package controle;

/**
 * Exceção para tentativas de operação em elementos não existentes dentro da classe que os agrega
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @since 2023
 */
public class ElementoInexistenteException extends Exception {
    /**
     * Cria essa exceção com uma mensagem de erro
     *
     * @param mensagemdeErro mensagem de erro associada a essa exceção
     */
    public ElementoInexistenteException(String mensagemdeErro) {
        super(mensagemdeErro);
    }
}
