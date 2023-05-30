package controle;

import modelo.Empresa;
import modelo.Filial;

import java.util.ArrayList;

public class ControleEstoque {
    private Empresa empresa;
    private ArrayList<Filial> filials;

    public ControleEstoque(ControleEmpresa c) {
        filials = c.getEmpresaMain().getFiliais();
        empresa = c.getEmpresaMain();
    }



}
