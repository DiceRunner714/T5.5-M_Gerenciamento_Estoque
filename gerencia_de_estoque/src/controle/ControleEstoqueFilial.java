package controle;

import modelo.Farmaceutico;
import modelo.Filial;
import modelo.Item;
import modelo.ProdutoQuimico;

import java.util.ArrayList;

public class ControleEstoqueFilial {
    private ArrayList<Item> estoqueFilial;
    private Filial filialEscolhida;
    private ControleEmpresa controleEmpresa;

    /* Coloquei uma controleEmpresa pois essa classe precisa estar
    ciente do estoque inteiro para evitar repetições
     */
    public ControleEstoqueFilial(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.controleEmpresa = controleEmpresa;
        this.filialEscolhida = filialEscolhida;
        estoqueFilial = filialEscolhida.getEstoque();
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor, int quantidade, int id,
                                      String tarja, String composicao, boolean receita, boolean retencaoDeReceita,
                                      boolean generico) throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == id);

        String[] composicaoLista = composicao.split(",");

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(
                    new Farmaceutico(
                            nome, categoria, valor, quantidade, id, tarja, composicaoLista, receita, retencaoDeReceita, generico
                    )
            );
        }
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor,
                                      int quantidade, int id) throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream()
                .anyMatch(item -> item.getId() == id);

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newFarmaceutico = new Farmaceutico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newFarmaceutico);
        }
    }

    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id)
            throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream()
                .anyMatch(item -> item.getId() == id);

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id);
            estoqueFilial.add(newProdutoQuimico);
        }
    }

    public void adicionarProdutoQuimico(String nome, String categoria, double valor, int quantidade, int id,
                                        String perigoEspecifico, int riscoDeFogo, int reatividade, int perigoaSaude)
            throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream()
                .anyMatch(item -> item.getId() == id);

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newProdutoQuimico = new ProdutoQuimico(nome, categoria, valor, quantidade, id,
                    perigoEspecifico, riscoDeFogo, reatividade, perigoaSaude);
            estoqueFilial.add(newProdutoQuimico);
        }
    }

    public void adicionarFarmaceutico(Farmaceutico newFarmaceutico)
            throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream().
                anyMatch(item -> item.getId() == newFarmaceutico.getId());

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            estoqueFilial.add(newFarmaceutico);
        }
    }


    // Altera apenas as caraccterísticas básicas definidas na class Item
    public void atualizarItem(String newNome, String newCategoria, double newValor,
                              int newQuantidade, int newId, Item itemEscolhido) throws IdRepetidoException {
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

    public void removerItem(int id) {
        filialEscolhida.removerItem(id);
    }

    public void removerItem(Item i) {
        filialEscolhida.removerItem(i);
    }

}
