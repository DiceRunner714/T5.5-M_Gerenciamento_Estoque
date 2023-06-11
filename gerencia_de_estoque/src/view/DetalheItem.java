package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DetalheItem extends Detalhe {
    //TODO: reintroduzir restrição
    private PainelItem painelItem;
    private PainelFarmaceutico painelFarmaceutico;
    private PainelProdutoQuimico painelProdutoQuimico;
    private final JTabbedPane abaPaginada = new JTabbedPane();
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;
    private TipodeItem tipodeItem;
    private TipoDeEstoque tipoDeEstoque;

    private enum TipoDeEstoque {
        GERAL, FILIAL
    }

    // Construtor para modificar um item
    public DetalheItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, Item itemEscolhido) {
        super(ModosDetalhe.EDITAR, pesquisaView, controleEmpresa);

        tipoDeEstoque = TipoDeEstoque.FILIAL;

        // Descobrir que tipo de Item é esse
        this.itemEscolhido = itemEscolhido;
        if (itemEscolhido instanceof Farmaceutico) {
            tipodeItem = TipodeItem.FARMACEUTICO;
        } else if (itemEscolhido instanceof  ProdutoQuimico) {
            tipodeItem = TipodeItem.PRODUTOQUIMICO;
        }

        filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialdoItem);

        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
        popularFormularios();
    }

    // Construtor para adicionar item a uma filial
    public DetalheItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, ControleEstoqueFilial controleEstoqueFilial) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        this.controleEstoque = controleEstoqueFilial;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    // Construtor para adicionar um item geral
    public DetalheItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        tipoDeEstoque = TipoDeEstoque.GERAL;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    @Override
    protected ArrayList<JComponent> agruparTodosFormularios() {

        // Criar formularios principais
        painelFarmaceutico = new PainelFarmaceutico();
        painelProdutoQuimico = new PainelProdutoQuimico();

        ArrayList<JComponent> formularios = new ArrayList<>();
        criarPainelItem();
        formularios.add(painelItem);

        switch (modo) {
            // Mostrar todas as opções de itens para adicionar
            case ADICIONAR -> {
                abaPaginada.addTab("Produto Químico", painelProdutoQuimico);
                abaPaginada.addTab("Farmacêutico", painelFarmaceutico);
                formularios.add(abaPaginada);
            }
            // Mostrar só o formulário do tipo de item escolhido
            case EDITAR -> {
                switch (tipodeItem) {
                    case FARMACEUTICO -> formularios.add(painelFarmaceutico);
                    case PRODUTOQUIMICO -> formularios.add(painelProdutoQuimico);
                }
            }
        }
        return formularios;
    }

    private void criarPainelItem() {
        if (modo == ModosDetalhe.EDITAR) {
            //Editar
            painelItem = new PainelItem(filialdoItem);
        } else if (modo == ModosDetalhe.ADICIONAR && tipoDeEstoque == TipoDeEstoque.FILIAL) {
            // adicionar estoque de filial
            painelItem = new PainelItem();
        } else {
            // adicionar estoque geral
            painelItem = new PainelItem(controleEmpresa.getFiliais());
        }
    }

    @Override
    protected void excluirElemento() {
        controleEstoque.removerItem(itemEscolhido);
        pesquisaView.refresh();
    }

    @Override
    protected void atualizarElemento() throws IdRepetidoException {
        painelItem.atualizarCaracteristicasBasicas(controleEstoque, itemEscolhido);
        switch (tipodeItem) {
            case PRODUTOQUIMICO -> {
                painelProdutoQuimico.atualizarProdutoQuimico(controleEstoque, (ProdutoQuimico) itemEscolhido);
            }
            case FARMACEUTICO ->{
                painelFarmaceutico.atualizarFarmaceutico(controleEstoque, (Farmaceutico) itemEscolhido);
            }
        }
    }

    @Override
    protected void popularFormularios() {
        painelItem.popularFormularios(itemEscolhido);
        switch (tipodeItem) {
            case PRODUTOQUIMICO -> painelProdutoQuimico.popularFormularios((ProdutoQuimico) itemEscolhido);
            case FARMACEUTICO -> painelFarmaceutico.popularFormularios((Farmaceutico) itemEscolhido);
        }
    }

    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        if (tipoDeEstoque == TipoDeEstoque.GERAL) {
            controleEstoque = new ControleEstoqueFilial(controleEmpresa, painelItem.getSelectedFilial());
        }
        Component componente = abaPaginada.getSelectedComponent();
        if (componente == painelFarmaceutico) {
            painelFarmaceutico.adicionarFarmaceutico(painelItem, controleEstoque);
        } else if (componente == painelProdutoQuimico) {
            painelProdutoQuimico.adicionarProdutoQuimico(painelItem, controleEstoque);
        }
    }

}
