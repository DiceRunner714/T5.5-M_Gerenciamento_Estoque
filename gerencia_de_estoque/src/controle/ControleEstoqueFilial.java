package controle;

import modelo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe ControleEstoqueFilial manipula o estoque de uma filial específica
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see Filial
 * @since 2023
 */
public class ControleEstoqueFilial implements LeitorEstoque {
    private final Filial filialEscolhida;
    private final ControleEmpresa controleEmpresa;

    /* Coloquei uma controleEmpresa, pois essa classe precisa estar
    ciente do estoque inteiro para evitar repetições
     */

    /**
     * Método construtor da classe ControleEstoqueFilial
     * @param controleEmpresa
     * @param filialEscolhida
     */
    public ControleEstoqueFilial(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.controleEmpresa = controleEmpresa;
        this.filialEscolhida = filialEscolhida;
    }

    // __GERENCIAMENTO DE ITENS__

    // --Métodos de checagem--

    /**
     * Método usado para checar se existe algum item no estoque da empresa com o mesmo id
     * @param id id do item a ser verificado
     * @return true, se for encontrado um item em estoque com mesmo id ou false, se não for encontrado nenhum
     */
    private boolean checkIdRepetido(int id) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == id);
    }

    /**
     * Método usado para checar se existe algum item no estoque da empresa através de seu nome
     * @param i item a ser verificado
     * @return true, se for encontrado um item em estoque com mesmo id ou false, se não for encontrado nenhum
     */
    private boolean checkIdRepetido(Item i) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == i.getId());
    }

    /**
     * Método que checa se um item existe
     * @param i item a ser verificado
     * @throws ElementoInexistenteException gera uma exceção caso o item não exista
     */
    private void checkItemNaoExiste(Item i) throws ElementoInexistenteException {
        if (controleEmpresa.getEstoque().stream().noneMatch(i::equals)) {
            throw new ElementoInexistenteException("O item escolhido não existe");
        }
    }

    /**
     * Método que checa se um item existe através de seu id
     * @param id id do item a ser verificado
     * @throws ElementoInexistenteException gera uma exceção caso o item não exista
     */
    private void checkItemNaoExiste(int id) throws ElementoInexistenteException {
        if (controleEmpresa.getEstoque().stream().noneMatch(item -> item.getId()==id)) {
            throw new ElementoInexistenteException("O item escolhido não existe");
        }
    }

    // --Adicionar item--

    /**
     * Adiciona um novo Farmaceutico e verifica se o id do novo item é único
     * @param newFarmaceutico novo Farmaceutico
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */
    // farmaceutico
    public void adicionarFarmaceutico(Farmaceutico newFarmaceutico) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(newFarmaceutico)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            filialEscolhida.adicionarItem(newFarmaceutico);
        }
    }

    /**
     * Adiciona um novo Farmaceutico com os atributos da classe Item
     * @param nome nome do novo Farmaceutico
     * @param categoria categoria do novo Farmaceutico
     * @param valor preço/custo do novo Farmaceutico
     * @param quantidade quantidade do produto disponível em estoque
     * @param id id do novo Farmaceutico
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */
    public void adicionarFarmaceutico(String nome, String categoria, double valor,
                                      int quantidade, int id) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newFarmaceutico = new Farmaceutico(nome, categoria, valor, quantidade, id);
            filialEscolhida.adicionarItem(newFarmaceutico);
        }
    }

    /**
     * Adiciona um novo Farmaceutico com os atributos da classe Item e da classe filha Farmaceutico
     * @param nome nome do novo Farmaceutico
     * @param categoria categoria do novo Farmaceutico
     * @param valor preço/custo do novo Farmaceutico
     * @param quantidade quantidade do produto disponível em estoque
     * @param id id do novo Farmaceutico
     * @param tarja tarja do farmacêutico
     * @param composicao ingredientes presentes no novo farmacêutico
     * @param receita indica se o novo farmacêutico possui receita ou não
     * @param retencaoDeReceita retenção de receita
     * @param generico indica se o novo famacêutico é genérico ou de marca
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */

    public void adicionarFarmaceutico(String nome, String categoria, double valor, int quantidade, int id,
                                      String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            filialEscolhida.adicionarItem(
                    new Farmaceutico(
                            nome, categoria, valor, quantidade, id, tarja, composicao, receita, retencaoDeReceita, generico
                    )
            );
        }
    }

    // produto quimico

    /**
     * Adiciona um novo ProdutoQuimico com os atributos da classe Item
     * @param nome nome do novo produto químico
     * @param categoria categoria do novo produto químico
     * @param valor preço/custo do novo produto químico
     * @param quantidade quantidade do novo produto químico disponível em estoque
     * @param id id do novo produto químico
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */
    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id)
            throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id);
            filialEscolhida.adicionarItem(newProdutoQuimico);
        }
    }

    /**
     * Adiciona um novo ProdutoQuimico com os atributos da classe Item e da classe filha Farmaceutico
     * @param nome nome do novo produto químico
     * @param categoria categoria do novo produto químico
     * @param valor preço/custo do novo produto químico
     * @param quantidade quantidade em disponível em estoque
     * @param id id do novo produto químico
     * @param perigoEspecifico perigo específico do novo produto químico
     * @param riscoDeFogo numero que indica quão inflamável é o novo produto quimico
     * @param reatividade numero que indica a reatividade do novo produto químico
     * @param perigoaSaude numero que indica o quão nocivo o novo produto químico é
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */
    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id,
                                        String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude)
            throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id,
                    perigoEspecifico, riscoDeFogo, reatividade, perigoaSaude);
            filialEscolhida.adicionarItem(newProdutoQuimico);
        }
    }

    /**
     * Adiciona um novo ProdutoQuimico e verifica se o id do novo item é único
     * @param newProdutoQuimico novo produto químico
     * @throws IdRepetidoException gera uma exceção caso o id do novo item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso a filial escolhida não exista
     */
    public void adicionarProdutoQuimico(ProdutoQuimico newProdutoQuimico) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(newProdutoQuimico)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            filialEscolhida.adicionarItem(newProdutoQuimico);
        }
    }


    // --Remover item--

    /**
     * Remove um item do estoque baseado em seu id
     * @param id id do item a ser removido
     * @throws ElementoInexistenteException gera uma exceção caso o item escolhido não exista
     */
    public void removerItem(int id) throws ElementoInexistenteException {
        checkItemNaoExiste(id);
        filialEscolhida.removerItem(id);
    }

    /**
     * Remove um item do estoque baseado em seu nome
     * @param i item a ser removido
     * @throws ElementoInexistenteException gera uma exceção caso o item escolhido não exista
     */
    public void removerItem(Item i) throws ElementoInexistenteException {
        checkItemNaoExiste(i);
        filialEscolhida.removerItem(i);
    }

    // --Atualizar Item--

    /**
     * Atualiza os atributos de um item
     * @param newNome novo nome do item
     * @param newCategoria nova categoria do item
     * @param newValor novo valor do item
     * @param newQuantidade nova quantidade do item no estoque
     * @param newId novo id do item
     * @param itemEscolhido item a ser atualizado
     * @throws IdRepetidoException gera uma exceção caso o novo id do item seja igual ao de um existente
     * @throws ElementoInexistenteException gera uma exceção caso o item escolhido não exista
     */
    public void atualizarCaracteristicasBasicas(String newNome, String newCategoria, double newValor,
                                                int newQuantidade, int newId, Item itemEscolhido)
            throws IdRepetidoException, ElementoInexistenteException {
        checkItemNaoExiste(itemEscolhido);
        boolean idRepetido = controleEmpresa.getEstoque()
                .stream()
                .anyMatch(
                        item -> (item.getId() == newId) && !item.equals(itemEscolhido)
                );
        if (!idRepetido) {
            itemEscolhido.atualizarCaracteristicasBasicas(
                    newNome, newCategoria, newValor, newQuantidade, newId
            );
        } else {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        }
    }

    /**
     * Atualiza os atributos de um farmacêutico
     * @param tarja nova tarja do farmacêutico
     * @param composicao nova composição do farmacêutico
     * @param receita nova receita do farmacêutico
     * @param retencaoDeReceita nova retenção de receita do farmacêutico
     * @param generico novo genérico do farmacêutico
     * @param f farmacêutico a ser atualizado
     * @throws ElementoInexistenteException gera uma exceção caso o farmacêutico escolhido não exista
     */
    public void atualizarFarmaceutico(String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico, Farmaceutico f) throws ElementoInexistenteException {
        checkItemNaoExiste(f);
        f.setComposicao(composicao);
        f.setTarja(tarja);
        f.setGenerico(generico);
        f.setReceita(receita);
        f.setRetencaoDeReceita(retencaoDeReceita);
    }

    /**
     * Atualiza os atributos de um produto químico
     * @param perigoEspecifico novo perigo específico
     * @param riscoDeFogo novo risco de fogo
     * @param reatividade nova reatividade
     * @param perigoaSaude novo perigo à saúde
     * @param p produto químico a ser atualizado
     * @throws ElementoInexistenteException gera uma exceção caso o produto químico escolhido não exista
     */
    public void atualizarProdutoQuimico(String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude,
                                        ProdutoQuimico p) throws ElementoInexistenteException {
        checkItemNaoExiste(p);
        p.setReatividade(reatividade);
        p.setPerigoaSaude(perigoaSaude);
        p.setPerigoEspecifico(perigoEspecifico);
        p.setRiscoDeFogo(riscoDeFogo);
    }

    /**
     * Limpa o estoque de uma filial escolhida
     */
    public void limparEstoque() {
        filialEscolhida.setEstoque(new ArrayList<>());
    }

    public void restringirItem(Item i) throws NivelRestricaoInadequadoException {
        i.restringir();
    }

    public void liberarItem(Item i) throws NivelRestricaoInadequadoException {
        i.liberar();
    }

    /**
     * Busca um item no estoque através de seu nome, case insensitive
     * @param nome
     * @return item buscado
     */
    @Override
    public List<Item> buscarItens(String nome) {
        return filialEscolhida.buscarItens(nome, false);
    }

    /**
     * Busca um item no estoque através de seu nome, case sensitive
     * @param nome
     * @return item buscado
     */
    @Override
    public List<Item> buscarItensParcial(String nome, boolean caseSensitive) {
        return filialEscolhida.buscaParcial(nome, caseSensitive);
    }

    public List<Item> getEstoque() {
        return filialEscolhida.getEstoque();
    }

    public void setEstoque(ArrayList<Item> estoque) {
        filialEscolhida.setEstoque(estoque);
    }

    public Filial getFilialEscolhida() {
        return filialEscolhida;
    }
    @Override
    public List<Item> getItensVazios(List<Item> estoque) {
        return estoque.stream().filter(item -> item.getQuantidade() == 0).toList();
    }

}
