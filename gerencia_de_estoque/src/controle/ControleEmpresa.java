package controle;

import modelo.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ControleEmpresa {
    private Empresa empresa;
    private ArrayList<Filial> filiais;

    public ControleEmpresa(String nome) {
        empresa = new Empresa(nome);
        filiais = empresa.getFiliais();
    }

    public ArrayList<Filial> getFiliais() {
        return filiais;
    }

    public ArrayList<Item> getEstoque() {
        return empresa.lerTodoEstoque();
    }

    //TODO: essa função é de teste
    public void adicionarFilial(Filial f) throws IdRepetidoException {
        boolean idRepetido = filiais.stream().anyMatch(filial -> filial.getId() == f.getId());
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(f);
        }
    }

    public void atualizarFilial(String newNome, String newLocal, int newId, Filial f) throws IdRepetidoException {
        boolean idRepetido = filiais
                .stream()
                // Atualizar se não houver uma filial na lista com o mesmo id, *Exceto ela mesma*
                .anyMatch(filial -> (filial.getId() == newId) && (!filial.equals(f)));
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            /* Se eu fosse utilizar o método atualizarFilial pela empresa
            o código ficaria redundante, pois o método iria buscar a filial pelo ID sendo que já
            temos uma referência a filial escolhida
             */
            f.setId(newId);
            f.setNome(newNome);
            f.setLocal(newLocal);
        }

    }

    public Filial buscarFilial(int id) {
        return empresa.lerFilial(id);
    }

    public void excluirFilial(Filial f) {
        empresa.removerFilial(f);
    }

    public void excluirFilial(int id) {
        empresa.removerFilial(id);
    }

}
