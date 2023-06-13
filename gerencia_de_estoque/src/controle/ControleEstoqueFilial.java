package controle;

import modelo.*;

import java.util.ArrayList;

public class ControleEstoqueFilial implements ControleEstoque {
    private final ArrayList<Item> estoqueFilial;
    private final Filial filialEscolhida;
    private final ControleEmpresa controleEmpresa;

    /* Coloquei uma controleEmpresa pois essa classe precisa estar
    ciente do estoque inteiro para evitar repetições
     */


    public ControleEstoqueFilial(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.controleEmpresa = controleEmpresa;
        this.filialEscolhida = filialEscolhida;
        estoqueFilial = filialEscolhida.getEstoque();
    }

    // __GERENCIAMENTO DE ITENS__

    // --Métodos de checagem--
    private boolean checkIdRepetido(int id) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == id);
    }

    private boolean checkIdRepetido(Item i) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == i.getId());
    }

    private void checkItemNaoExiste(Item i) throws ElementoInexistenteException {
        if (controleEmpresa.getEstoque().stream().noneMatch(i::equals)) {
            throw new ElementoInexistenteException("O item escolhido não existe");
        }
    }

    private void checkItemNaoExiste(int id) throws ElementoInexistenteException {
        if (controleEmpresa.getEstoque().stream().noneMatch(item -> item.getId()==id)) {
            throw new ElementoInexistenteException("O item escolhido não existe");
        }
    }

    // --Adicionar item--

    // farmaceutico
    public void adicionarFarmaceutico(Farmaceutico newFarmaceutico) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(newFarmaceutico)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(newFarmaceutico);
        }
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor,
                                      int quantidade, int id) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newFarmaceutico = new Farmaceutico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newFarmaceutico);
        }
    }
    public void adicionarFarmaceutico(String nome, String categoria, double valor, int quantidade, int id,
                                      String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico) throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(
                    new Farmaceutico(
                            nome, categoria, valor, quantidade, id, tarja, composicao, receita, retencaoDeReceita, generico
                    )
            );
        }
    }

    // produto quimico
    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id)
            throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newProdutoQuimico);
        }
    }

    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id,
                                        String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude)
            throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.checkFilialNaoExiste(filialEscolhida);
        if (checkIdRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id,
                    perigoEspecifico, riscoDeFogo, reatividade, perigoaSaude);
            estoqueFilial.add(newProdutoQuimico);
        }
    }


    // --Remover item--

    public void removerItem(int id) throws ElementoInexistenteException {
        checkItemNaoExiste(id);
        filialEscolhida.removerItem(id);
    }

    public void removerItem(Item i) throws ElementoInexistenteException {
        checkItemNaoExiste(i);
        filialEscolhida.removerItem(i);
    }

    // --Atualizar Item--
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

    public void atualizarFarmaceutico(String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico, Farmaceutico f) throws ElementoInexistenteException {
        checkItemNaoExiste(f);
        f.setComposicao(composicao);
        f.setTarja(tarja);
        f.setGenerico(generico);
        f.setReceita(receita);
        f.setRetencaoDeReceita(retencaoDeReceita);
    }

    public void atualizarProdutoQuimico(String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude,
                                        ProdutoQuimico p) throws ElementoInexistenteException {
        checkItemNaoExiste(p);
        p.setReatividade(reatividade);
        p.setPerigoaSaude(perigoaSaude);
        p.setPerigoEspecifico(perigoEspecifico);
        p.setRiscoDeFogo(riscoDeFogo);
    }

    public void restringirItem(Item i) throws NivelRestricaoInadequadoException {
        i.restringir();
    }

    public void liberarItem(Item i) throws NivelRestricaoInadequadoException {
        i.liberar();
    }

    @Override
    public ArrayList<Item> buscarItens(String nome) {
        return filialEscolhida.buscarItens(nome, false);
    }

    @Override
    public ArrayList<Item> buscarItensParcial(String nome, boolean caseSensitive) {
        return filialEscolhida.buscaParcial(nome, caseSensitive);
    }

    public ArrayList<Item> getEstoque() {
        return filialEscolhida.getEstoque();
    }

    public void setEstoque(ArrayList<Item> estoque) {
        filialEscolhida.setEstoque(estoque);
    }

    public Filial getFilialEscolhida() {
        return filialEscolhida;
    }
    @Override
    public ArrayList<Item> getItensVazios(ArrayList<Item> estoque) {
        return new ArrayList<>(
                estoque.stream().filter(item -> item.getQuantidade() == 0).toList()
        );
    }

}
