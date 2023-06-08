package controle;

import modelo.Farmaceutico;
import modelo.Filial;
import modelo.Item;

import java.util.ArrayList;

public class ControleEstoque {
    private ArrayList<Item> estoqueFilial;
    private ControleEmpresa controleEmpresa;

    /* Coloquei um controleEmpresa pois essa classe precisa estar ciente do estoque inteiro
       E não só o estoque da filial escolhida
     */
    public ControleEstoque(ControleEmpresa controleEmpresa, Filial filialEscolhida) {
        this.controleEmpresa = controleEmpresa;
        estoqueFilial = filialEscolhida.getEstoque();
    }

    public void adicionarFarmaceutico(String nome, String categoria, double valor, int quantidade, int id)
            throws IdRepetidoException {

        boolean idRepetido = controleEmpresa
                .getEstoque()
                .stream()
                .anyMatch(item -> item.getId() == id);

        if (idRepetido) {
            throw new IdRepetidoException("Id repetido: a empresa já contém um item com esse id");
        } else {
            Item newFarmaceutico = new Farmaceutico(nome, quantidade, categoria, valor, id);
            estoqueFilial.add(newFarmaceutico);
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

}
