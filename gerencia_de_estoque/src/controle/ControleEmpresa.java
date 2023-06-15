package controle;

import modelo.Empresa;
import modelo.Filial;
import modelo.Item;

import java.util.List;

public class ControleEmpresa implements LeitorEstoque {
    private final Empresa empresa;
    private final List<Filial> filiais;

    public ControleEmpresa(String nome) {
        empresa = new Empresa(nome);
        filiais = empresa.getFiliais();
    }



    // ___CONTROLE DE FILIAIS___

    public void checkFilialNaoExiste(Filial f) throws ElementoInexistenteException {
        if (filiais.stream().noneMatch(f::equals)) {
            throw new ElementoInexistenteException("A filial escolhida não existe");
        }
    }

    public void checkFilialNaoExiste(int id) throws ElementoInexistenteException {
        if (filiais.stream().noneMatch(filial -> filial.getId()==id)) {
            throw new ElementoInexistenteException("A filial escolhida não existe");
        }
    }

    // --CRIAR--
    public void adicionarFilial(Filial f) throws IdRepetidoException {
        boolean idRepetido = filiais.stream().anyMatch(filial -> filial.getId() == f.getId());
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(f);
        }
    }

    public void adicionarFilial(String nome, String local, int id) throws IdRepetidoException {
        boolean idRepetido = filiais.stream().anyMatch(filial -> filial.getId() == id);
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(nome, local, id);
        }
    }

    // --LER/BUSCAR--
    public Filial buscarFilial(int id) {
        return empresa.lerFilial(id);
    }

    public Filial buscarFilialaPartirdeItem(Item item) throws ElementoInexistenteException {
        return filiais.stream()
                .filter(filial -> filial.getEstoque().contains(item))
                .findFirst()
                .orElseThrow(() -> new ElementoInexistenteException("a filial escolhida não existe"));
    }

    public List<Filial> getFiliais() {
        return filiais;
    }

    // --ATUALIZAR--
    public void atualizarFilial(String newNome, String newLocal, int newId, Filial f) throws IdRepetidoException, ElementoInexistenteException {
        checkFilialNaoExiste(f);
        boolean idRepetido = filiais
                .stream()
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

    // --REMOVER--
    public void excluirFilial(int id) throws ElementoInexistenteException {
        checkFilialNaoExiste(id);
        empresa.removerFilial(id);
    }

    public void excluirFilial(Filial f) throws ElementoInexistenteException {
        checkFilialNaoExiste(f);
        empresa.removerFilial(f);
    }

    //__CONTROLE DE ITENS DO ESTOQUE GERAL__

    // --BUSCA--
    public List<Item> buscarItensParcial(String nome, boolean caseSensitive) {
        return empresa.buscarItensParcial(nome, caseSensitive);
    }

    @Override
    public List<Item> buscarItens(String nome) {
        return empresa.buscarItens(nome, false);
    }

    //--FILTROS--
    public List<Item> getItensVazios(List<Item> estoque) {
        return estoque.stream().filter(item -> item.getQuantidade() == 0).toList();
    }

    public List<Item> getEstoqueVazio() {
        return  empresa
                .getEstoque()
                .stream()
                .filter(item -> item.getQuantidade() == 0).toList();
    }

    // GETTERS
    public String getNome() {
        return empresa.getNome();
    }

    public List<Item> getEstoque() {
        return empresa.getEstoque();
    }

}
