package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DetalheViewItem extends DetalheView {
    //TODO: reintroduzir restrição
    private PainelFormularioItem painelFormularioItem;
    private PainelFormularioFarmaceutico painelFormularioFarmaceutico;
    private PainelFormularioQuimico painelItemQuimico;
    private final JTabbedPane abaPaginada = new JTabbedPane();
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;
    private TipodeItem tipodeItem;
    private final TipoDeEstoque tipoDeEstoque;

    private enum TipoDeEstoque {
        GERAL, FILIAL
    }

    // Construtor para modificar um item
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, Item itemEscolhido) {
        super(ModosDetalhe.EDITAR, pesquisaView, controleEmpresa);

        tipoDeEstoque = TipoDeEstoque.FILIAL;

        // Descobrir que tipo de Item é esse
        this.itemEscolhido = itemEscolhido;
        if (itemEscolhido instanceof Farmaceutico) {
            tipodeItem = TipodeItem.FARMACEUTICO;
        } else if (itemEscolhido instanceof  ProdutoQuimico) {
            tipodeItem = TipodeItem.PRODUTO_QUIMICO;
        }

        try {
            filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        } catch (ElementoInexistenteException e) {
            mensagemElementoInexistente(e);
        }
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialdoItem);

        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
        popularFormularios();
    }

    // Construtor para adicionar item a uma filial
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, ControleEstoqueFilial controleEstoqueFilial) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        tipoDeEstoque = TipoDeEstoque.FILIAL;
        this.controleEstoque = controleEstoqueFilial;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    // Construtor para adicionar um item geral
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        tipoDeEstoque = TipoDeEstoque.GERAL;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    @Override
    protected ArrayList<JComponent> agruparTodosFormularios() {

        // Criar formularios principais
        painelFormularioFarmaceutico = new PainelFormularioFarmaceutico();
        painelItemQuimico = new PainelFormularioQuimico();

        ArrayList<JComponent> formularios = new ArrayList<>();
        criarPainelItem();
        formularios.add(painelFormularioItem);

        switch (modo) {
            // Mostrar todas as opções de itens para adicionar
            case ADICIONAR -> {
                abaPaginada.addTab("Produto Químico", painelItemQuimico);
                abaPaginada.addTab("Farmacêutico", painelFormularioFarmaceutico);
                formularios.add(abaPaginada);
            }
            // Mostrar só o formulário do tipo de item escolhido
            case EDITAR -> {
                switch (tipodeItem) {
                    case FARMACEUTICO -> formularios.add(painelFormularioFarmaceutico);
                    case PRODUTO_QUIMICO -> formularios.add(painelItemQuimico);
                }
            }
        }
        return formularios;
    }

    private void criarPainelItem() {
        if (modo == ModosDetalhe.EDITAR) {
            //Editar
            painelFormularioItem = new PainelFormularioItem(filialdoItem, modo);
        } else if (modo == ModosDetalhe.ADICIONAR && tipoDeEstoque == TipoDeEstoque.FILIAL) {
            // adicionar estoque de filial
            painelFormularioItem = new PainelFormularioItem(controleEstoque.getFilialEscolhida(), modo);
        } else {
            // adicionar estoque geral
            painelFormularioItem = new PainelFormularioItem(controleEmpresa.getFiliais());
        }
    }

    @Override
    protected void excluirElemento() throws ElementoInexistenteException {
        controleEstoque.removerItem(itemEscolhido);
        pesquisaView.refresh();
    }

    @Override
    protected void atualizarElemento() throws IdRepetidoException, ElementoInexistenteException {
            controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
            painelFormularioItem.atualizarCaracteristicasBasicas(controleEstoque, itemEscolhido);
            switch (tipodeItem) {
                case PRODUTO_QUIMICO -> {
                    painelItemQuimico.atualizarProdutoQuimico(controleEstoque, (ProdutoQuimico) itemEscolhido);
                }
                case FARMACEUTICO ->{
                    painelFormularioFarmaceutico.atualizarFarmaceutico(controleEstoque, (Farmaceutico) itemEscolhido);
                }
            }
    }

    @Override
    protected void popularFormularios() {
        painelFormularioItem.popularFormularios(itemEscolhido);
        switch (tipodeItem) {
            case PRODUTO_QUIMICO -> painelItemQuimico.popularFormularios((ProdutoQuimico) itemEscolhido);
            case FARMACEUTICO -> painelFormularioFarmaceutico.popularFormularios((Farmaceutico) itemEscolhido);
        }
    }

    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        if (tipoDeEstoque == TipoDeEstoque.GERAL) {
            controleEstoque = new ControleEstoqueFilial(controleEmpresa, painelFormularioItem.getSelectedFilial());
        }
        Component componente = abaPaginada.getSelectedComponent();
        try {
            if (componente == painelFormularioFarmaceutico) {
                painelFormularioFarmaceutico.adicionarFarmaceutico(painelFormularioItem, controleEstoque);
            } else if (componente == painelItemQuimico) {
                painelItemQuimico.adicionarProdutoQuimico(painelFormularioItem, controleEstoque);
            }
        } catch (ElementoInexistenteException e) {
            mensagemElementoInexistente(e);
            pesquisaView.refresh();
            janela.dispose();
        }
    }

}
