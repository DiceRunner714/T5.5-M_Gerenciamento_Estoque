package controle;

import modelo.Empresa;
import modelo.Filial;
import modelo.Item;

import java.util.List;

/**
 * Controle da classe Empresa
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see Empresa
 * @see view.EmpresaView
 * @since 2023
 */
public class ControleEmpresa implements LeitorEstoque {
    private final Empresa empresa;
    private final List<Filial> filiais;

    /**
     * Construtor principal da controleEmpresa
     *
     * @param nome nome da empresa a ser controlada
     */
    public ControleEmpresa(String nome) {
        empresa = new Empresa(nome);
        filiais = empresa.getFiliais();
    }

    // ___CONTROLE DE FILIAIS___

    /**
     * Checa se uma filial pesquisada não existe na Empresa por uma instância de Filial
     *
     * @param f filial pesquisada
     * @throws ElementoInexistenteException exceção de elemento inexistente
     */
    void checkFilialNaoExiste(Filial f) throws ElementoInexistenteException {
        if (filiais.stream().noneMatch(f::equals)) {
            throw new ElementoInexistenteException("A filial escolhida não existe");
        }
    }

    /**
     * Checa se uma filial pesquisada não existe na Empresa pelo seu ID
     *
     * @param id id pesquisado
     * @throws ElementoInexistenteException exceção de elemento inexistente
     */
    void checkFilialNaoExiste(int id) throws ElementoInexistenteException {
        if (filiais.stream().noneMatch(filial -> filial.getId() == id)) {
            throw new ElementoInexistenteException("A filial escolhida não existe");
        }
    }

    // --CRIAR--

    /**
     * Adiciona uma filial à Empresa
     *
     * @param f filial a ser adicionada
     * @throws IdRepetidoException exceção gerada caso já exista uma filial com mesmo id
     */
    public void adicionarFilial(Filial f) throws IdRepetidoException {
        boolean idRepetido = filiais.stream().anyMatch(filial -> filial.getId() == f.getId());
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(f);
        }
    }

    /**
     * Adiciona uma filial à Empresa
     *
     * @param nome  filial a ser adicionada
     * @param id    id da filial a ser adicionada
     * @param local da filial a ser adicionada
     * @throws IdRepetidoException exceção gerada caso já exista uma filial com mesmo id
     */
    public void adicionarFilial(String nome, String local, int id) throws IdRepetidoException {
        boolean idRepetido = filiais.stream().anyMatch(filial -> filial.getId() == id);
        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém uma filial com esse Id");
        } else {
            empresa.adicionarFilial(nome, local, id);
        }
    }

    // --LER/BUSCAR--

    /**
     * Busca uma filial por seu id
     *
     * @param id id da filial a ser encontrada
     * @return filial
     */
    public Filial buscarFilial(int id) {
        return empresa.lerFilial(id);
    }

    /**
     * Busca uma filial através de um item de seu estoque
     *
     * @param item item através do qual a filal será buscada
     * @return filial
     * @throws ElementoInexistenteException gera uma exceção caso a filial não exista
     */
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

    /**
     * Atualiza os atributos de uma filial
     *
     * @param newNome  novo nome da filial
     * @param newLocal novo endereço da filial
     * @param newId    novo id da filial
     * @param f        filial
     * @throws IdRepetidoException          gera uma exceção caso o id seja repetido
     * @throws ElementoInexistenteException gera uma exceção caso a filial não exista
     */
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

    /**
     * Exclui uma filial da Empresa por meio do id
     *
     * @param id id da filial a ser excluida
     * @throws ElementoInexistenteException gera uma exceção caso a filial não exista na Empresa
     */
    public void excluirFilial(int id) throws ElementoInexistenteException {
        checkFilialNaoExiste(id);
        empresa.removerFilial(id);
    }

    /**
     * Exclui uma filial da Empresa por meio do id
     *
     * @param f filial a ser excluida
     * @throws ElementoInexistenteException gera uma exceção caso a filial não exista
     */
    public void excluirFilial(Filial f) throws ElementoInexistenteException {
        checkFilialNaoExiste(f);
        empresa.removerFilial(f);
    }

    //__CONTROLE DE ITENS DO ESTOQUE GERAL__

    // --BUSCA--
    public List<Item> buscarItensParcial(String nomeParcial, boolean caseSensitive) {
        return empresa.buscarItensParcial(nomeParcial, caseSensitive);
    }

    @Override
    public List<Item> buscarItens(String nome) {
        return empresa.buscarItens(nome, false);
    }

    //--FILTROS--
    public List<Item> getItensVazios(List<Item> estoque) {
        return estoque.stream().filter(item -> item.getQuantidade() == 0).toList();
    }

    /**
     * Busca no estoque inteiro e filtra os itens em falta ONDE?
     *
     * @return lista de itens com estoque vazio
     */
    public List<Item> getEstoqueVazio() {
        return empresa
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
