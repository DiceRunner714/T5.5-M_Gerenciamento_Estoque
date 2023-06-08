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
    public void adicionarFilial(Filial f) throws IdRepetidoException {
        boolean idRepetido = empresa.getFiliais().stream().anyMatch(filial -> filial.getId() == f.getId());
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(f);
        }
    }
}
