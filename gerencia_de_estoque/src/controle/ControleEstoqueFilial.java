package controle;

import modelo.*;

import java.util.ArrayList;
import java.util.List;

public class ControleEstoqueFilial implements ControleEstoque {
    private final List<Item> estoqueFilial;
    private final Filial filialEscolhida;
    private final ControleEmpresa controleEmpresa;

    /* Coloquei uma controleEmpresa, pois essa classe precisa estar
    ciente do estoque inteiro para evitar repetições
     */
    public ControleEstoqueFilial(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.controleEmpresa = controleEmpresa;
        this.filialEscolhida = filialEscolhida;
        estoqueFilial = filialEscolhida.getEstoque();
    }

    private boolean idRepetido(int id) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == id);
    }

    private boolean idRepetido(Item i) {
        return controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == i.getId());
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor, int quantidade, int id,
                                      String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico) throws IdRepetidoException {

        if (idRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(
                    new Farmaceutico(
                            nome, categoria, valor, quantidade, id, tarja, composicao, receita, retencaoDeReceita, generico
                    )
            );
        }
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor,
                                      int quantidade, int id) throws IdRepetidoException {

        if (idRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newFarmaceutico = new Farmaceutico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newFarmaceutico);
        }
    }

    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id)
            throws IdRepetidoException {
        if (idRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newProdutoQuimico);
        }
    }

    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id,
                                        String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude)
            throws IdRepetidoException {

        if (idRepetido(id)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id,
                    perigoEspecifico, riscoDeFogo, reatividade, perigoaSaude);
            estoqueFilial.add(newProdutoQuimico);
        }
    }

    public void adicionarFarmaceutico(Farmaceutico newFarmaceutico)
            throws IdRepetidoException {
        if (idRepetido(newFarmaceutico)) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(newFarmaceutico);
        }
    }


    // Altera apenas as caraccterísticas básicas definidas na class Item
    public void atualizarCaracteristicasBasicas(String newNome, String newCategoria, double newValor,
                                                int newQuantidade, int newId, Item itemEscolhido)
            throws IdRepetidoException {
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
                                      boolean generico, Farmaceutico f) {
        f.setComposicao(composicao);
        f.setTarja(tarja);
        f.setGenerico(generico);
        f.setReceita(receita);
        f.setRetencaoDeReceita(retencaoDeReceita);
    }

    public void atualizarProdutoQuimico(String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude,
                                        ProdutoQuimico p) {
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
    public List<Item> buscarItens(String nome) {
        return filialEscolhida.buscarItens(nome, false);
    }

    @Override
    public List<Item> buscarItensParcial(String nome, boolean caseSensitive) {
        return filialEscolhida.buscaParcial(nome, caseSensitive);
    }

    public void removerItem(int id) {
        filialEscolhida.removerItem(id);
    }

    public void removerItem(Item i) {
        filialEscolhida.removerItem(i);
    }

    public List<Item> getEstoque() {
        return filialEscolhida.getEstoque();
    }

    public void setEstoque(ArrayList<Item> estoque) {
        filialEscolhida.setEstoque(estoque);
    }

    @Override
    public List<Item> getItensVazios(List<Item> estoque) {
        return estoque.stream().filter(item -> item.getQuantidade() == 0).toList();
    }

}
