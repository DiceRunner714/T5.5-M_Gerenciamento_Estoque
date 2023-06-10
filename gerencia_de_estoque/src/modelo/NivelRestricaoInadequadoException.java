package modelo;

public class NivelRestricaoInadequadoException extends Exception {
    public NivelRestricaoInadequadoException(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
