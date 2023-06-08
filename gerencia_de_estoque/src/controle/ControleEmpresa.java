package controle;
import modelo.*;

import java.util.ArrayList;

public class ControleEmpresa {
    private Empresa empresa;

    public ControleEmpresa(String nome) {
        empresa = new Empresa(nome);
    }

    public ArrayList<Filial> getFiliais() {
        return empresa.getFiliais();
    }
    public ArrayList<Item> getEstoque() {
        return empresa.lerTodoEstoque();
    }

    //TODO: essa função é de teste
    public void adicionarFilial (Filial f) {
        empresa.adicionarFilial(f);
    }
}
