package controle;

public class ElementoInexistenteException extends Exception{
    public ElementoInexistenteException(String mensagemdeErro) {
        super(mensagemdeErro);
    }
}
