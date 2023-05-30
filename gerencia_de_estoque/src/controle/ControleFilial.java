package controle;

import modelo.*;

import java.util.ArrayList;

public class ControleFilial {
    private ArrayList<Filial> filiais;

    public ControleFilial(ControleEmpresa c) {
        filiais = c.getEmpresaMain().getFiliais();
    }

    public boolean adicionarFilial(String nome, String local, int id) {
        boolean valido = true;

        // Validar id
        for (Filial filial : filiais) {
            if (filial.getId() == id) valido = false;
        }

        if (valido) {
            filiais.add(new Filial(nome, local, id));
        }

        return valido;
    }

    public void removerFilial(int id) {
        filiais.removeIf((Filial filial) -> filial.getId() == id);
    }

    // TODO: adicionar validacao de ID
    public void editarFilial(String novoNome, String novoLocal, int novoId, int id) {
        for (Filial filial : filiais) {
            if (filial.getId() == id) {
                filial.setNome(novoNome);
                filial.setLocal(novoLocal);
                filial.setId(novoId);
            }
        }
    }

    public int getQtdItensEmFilial(int id) {
        Filial filialSelecionada = null;
        for (Filial filial : filiais) {
            if (filial.getId() == id)
                filialSelecionada = filial;
        }
        return filialSelecionada.getEstoque().size();
    }

    public int getQtdItensVaziosEmFilial(int id) {
        Filial filialSelecionada = null;
        for (Filial filial : filiais) {
            if (filial.getId() == id)
                filialSelecionada = filial;
        }
        return filialSelecionada.getEstoqueVazio().size();
    }
    public ArrayList<String> buscarNomeFiliais() {
        ArrayList<String> nomeFiliais = new ArrayList<String>();
        for (Filial filial: filiais) {
            nomeFiliais.add(filial.getNome());
        }
        return nomeFiliais;
    }

    public ArrayList<Filial> getFiliais() {
        return filiais;
    }

    public void setFiliais(ArrayList<Filial> filiais) {
        this.filiais = filiais;
    }

}
