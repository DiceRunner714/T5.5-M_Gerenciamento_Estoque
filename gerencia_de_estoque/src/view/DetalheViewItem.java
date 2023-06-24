package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação concreta de uma DetalheView destinada ao gerenciamento de detalhes
 * de um item
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
public class DetalheViewItem extends DetalheView {
    private final JTabbedPane abaPaginada = new JTabbedPane();
    private final TipoDeEstoque tipoDeEstoque;
    //TODO: reintroduzir restrição
    private PainelFormularioItem painelFormularioItem;
    private PainelFormularioFarmaceutico painelFormularioFarmaceutico;
    private PainelFormularioQuimico painelFormularioQuimico;
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;
    private TipodeItem tipodeItem;

    
    // Construtor para modificar um item
    /**
     * Construtor destinado a modificar ou excluir um item da classe de controle
     * @param controleEmpresa classe de controle para gerenciamento de itens
     * @param pesquisaView PesquisaView da qual essa janela se origina
     * @param itemEscolhido item escolhido
     */
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, Item itemEscolhido) {
        super(ModosDetalhe.EDITAR, pesquisaView, controleEmpresa);

        tipoDeEstoque = TipoDeEstoque.FILIAL;

        // Descobrir que tipo de Item é esse
        this.itemEscolhido = itemEscolhido;
        if (itemEscolhido instanceof Farmaceutico) {
            tipodeItem = TipodeItem.FARMACEUTICO;
        } else if (itemEscolhido instanceof ProdutoQuimico) {
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
    /**
     * Construtor destinado a adicionar um item a uma filial
     * @param controleEmpresa classe de controle para gerenciamento de itens
     * @param pesquisaView PesquisaView da qual essa janela se origina
     * @param controleEstoqueFilial classe de controle para manipular estoque de uma filial
     */
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView, ControleEstoqueFilial controleEstoqueFilial) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        tipoDeEstoque = TipoDeEstoque.FILIAL;
        this.controleEstoque = controleEstoqueFilial;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    // Construtor para adicionar um item geral
    /**
     * Construtor destinado a adicionar um item geral
     * @param controleEmpresa classe de controle para gerenciamento de itens e
     * de filiais
     * @param pesquisaView PesquisaView da qual essa janela se origina
     */
    public DetalheViewItem(ControleEmpresa controleEmpresa, PesquisaView pesquisaView) {
        super(ModosDetalhe.ADICIONAR, pesquisaView, controleEmpresa);
        tipoDeEstoque = TipoDeEstoque.GERAL;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    /**
     * Cria os formulários principais
     * @return formularios
     */
    @Override
    protected List<JComponent> agruparTodosFormularios() {

        // Criar formularios principais
        painelFormularioFarmaceutico = new PainelFormularioFarmaceutico();
        painelFormularioQuimico = new PainelFormularioQuimico();

        List<JComponent> formularios = new ArrayList<>();
        criarPainelItem();
        formularios.add(painelFormularioItem);

        switch (modo) {
            // Mostrar todas as opções de itens para adicionar
            case ADICIONAR -> {
                abaPaginada.addTab("Produto Químico", painelFormularioQuimico);
                abaPaginada.addTab("Farmacêutico", painelFormularioFarmaceutico);
                formularios.add(abaPaginada);
            }
            // Mostrar só o formulário do tipo de item escolhido
            case EDITAR -> {
                switch (tipodeItem) {
                    case FARMACEUTICO -> formularios.add(painelFormularioFarmaceutico);
                    case PRODUTO_QUIMICO -> formularios.add(painelFormularioQuimico);
                }
            }
        }
        return formularios;
    }
    
    /**
     * Exclui um item
     * @throws ElementoInexistenteException exceção gerada caso o item não
     * exista no estoque da empresa
     */
    @Override
    
    protected void excluirElemento() throws ElementoInexistenteException {
        controleEstoque.removerItem(itemEscolhido);
        pesquisaView.refresh();
    }
    /**
     * Adiciona um item ao estoque
     * @throws IdRepetidoException exceção gerada caso já exista um item com
     * mesmo id
     */
    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        if (tipoDeEstoque == TipoDeEstoque.GERAL) {
            controleEstoque = new ControleEstoqueFilial(controleEmpresa, painelFormularioItem.getSelectedFilial());
        }
        Component componente = abaPaginada.getSelectedComponent();
        try {
            if (componente == painelFormularioFarmaceutico) {
                painelFormularioFarmaceutico.adicionarFarmaceutico(painelFormularioItem, controleEstoque);
            } else if (componente == painelFormularioQuimico) {
                painelFormularioQuimico.adicionarProdutoQuimico(painelFormularioItem, controleEstoque);
            }
        } catch (ElementoInexistenteException e) {
            mensagemElementoInexistente(e);
            pesquisaView.refresh();
            janela.dispose();
        }
    }
    
    /**
     * Atualiza um item
     * @throws IdRepetidoException exceção gerada caso o usuário tente atualizar
     * o id para um que já existe no estoque
     * @throws ElementoInexistenteException exceção gerada ao tentar atualizar um
     * item que não existe no estoque
     */
    @Override
    protected void atualizarElemento() throws IdRepetidoException, ElementoInexistenteException {
        controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        try {
            painelFormularioItem.atualizarCaracteristicasBasicas(controleEstoque, itemEscolhido);
            switch (tipodeItem) {
                case PRODUTO_QUIMICO -> {
                    painelFormularioQuimico.atualizarProdutoQuimico(controleEstoque, (ProdutoQuimico) itemEscolhido);
                }
                case FARMACEUTICO -> {
                    painelFormularioFarmaceutico.atualizarFarmaceutico(controleEstoque, (Farmaceutico) itemEscolhido);
                }
            }
        } catch (NivelRestricaoInadequadoException e) {
            mensagemErroRestricao(e);
        }
    }
    /**
     * Popular formulários com informações do item escolhido
     */
    @Override
    protected void popularFormularios() {
        painelFormularioItem.popularFormularios(itemEscolhido);
        switch (tipodeItem) {
            case PRODUTO_QUIMICO -> painelFormularioQuimico.popularFormularios((ProdutoQuimico) itemEscolhido);
            case FARMACEUTICO -> painelFormularioFarmaceutico.popularFormularios((Farmaceutico) itemEscolhido);
        }
    }
    
    /**
     * Cria painel do item
     */
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

    /**
     * Gera mensagem de erro de restrição
     * @param e
     */
    private void mensagemErroRestricao(NivelRestricaoInadequadoException e) {
        JOptionPane.showMessageDialog(
                null, e.getMessage(), "Erro de restrição:", JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Tipo de Estoque
	 * @author André Emanuel Bispo da Silva
	 * @version 1.0
	 * @since 2023
     */
    private enum TipoDeEstoque {
        GERAL, FILIAL
    }

    /**
     * Tipo de Item
	 * @author André Emanuel Bispo da Silva
	 * @version 1.0
	 * @since 2023
     */
    private enum TipodeItem {
        FARMACEUTICO, PRODUTO_QUIMICO

    }
}
