package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DetalheItem extends Detalhe {
    private JPanel formularioFarmaceutico;
    private JPanel formularioProdutoQuimico;
    private final JTabbedPane abaPaginada = new JTabbedPane();
    private final JTextField valorPerigoEspecifico = new JTextField();
    private final JTextField valorTarja = new JTextField();
    private final JTextField valorComposicao = new JTextField();
    private final JComboBox<Integer> opcoesPerigoaSaude = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesRiscoDeFogo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesReatividade = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JCheckBox isRetencaoDeReceita = new JCheckBox("Retenção de receita");
    private final JCheckBox isGenerico = new JCheckBox("Medicamento genérico");
    private final JCheckBox isRestrito = new JCheckBox("restrito");
    private final JCheckBox isReceita = new JCheckBox("Necessita de receita");
    private final EnumMap<CamposItem, JTextField> valoresItem = new EnumMap<>(CamposItem.class);
    private JComboBox<Filial> opcoesFiliais;
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;

    private enum CamposItem {
        NOME, ID, CATEGORIA, VALOR, QUANTIDADE
    }

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        super(ModosDetalhe.ADICIONAR, janelaPesquisa, controleEmpresa);

        ArrayList<Filial> filiaisDisponivels = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(filiaisDisponivels.toArray(new Filial[0]));// Java infere o tamanho do array

        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    // Construtor não vazio, item escolhido para modificar
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item itemEscolhido) {
        super(ModosDetalhe.EDITAR, janelaPesquisa, controleEmpresa);

        this.itemEscolhido = itemEscolhido;
        filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialdoItem);

        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
        popularFormularios();
    }

    // Construtor para adicionar item a uma filial
    public DetalheItem(ControleEmpresa controleEmpresa,
                       JanelaPesquisa janelaPesquisa, ControleEstoqueFilial controleEstoqueFilial) {
        super(ModosDetalhe.ADICIONAR, janelaPesquisa, controleEmpresa);
        this.controleEstoque = controleEstoqueFilial;
        criarJanela(agruparTodosFormularios(), 600, 600, "Item:");
    }

    @Override
    protected ArrayList<JComponent> agruparTodosFormularios() {

        // Criar formularios principais
        JPanel formularioPrincipal = criarFormularioPrincipal();
        formularioFarmaceutico = criarFormularioFarmaceutico();
        formularioProdutoQuimico = criarFormularioProdutoQuimico();

        ArrayList<JComponent> formularios = new ArrayList<>();
        formularios.add(formularioPrincipal);

        switch (modo) {
            // Mostrar todas as opções de itens para adicionar
            case ADICIONAR -> {
                abaPaginada.addTab("Produto Químico", formularioProdutoQuimico);
                abaPaginada.addTab("Farmacêutico", formularioFarmaceutico);
                formularios.add(abaPaginada);
            }
            // Mostrar só o formulário do tipo de item escolhido
            case EDITAR -> {
                if (itemEscolhido instanceof Farmaceutico) {
                    formularios.add(formularioFarmaceutico);
                } else if (itemEscolhido instanceof ProdutoQuimico) {
                    formularios.add(formularioProdutoQuimico);
                }
            }
        }
        return formularios;
    }

    private JPanel criarFormularioPrincipal() {

        for (CamposItem campo : CamposItem.values()) {
            valoresItem.put(campo, new JTextField());
        }

        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                new JLabel("Nome: "),
                new JLabel("ID: "),
                new JLabel("Categoria: "),
                new JLabel("Quantidade: "),
                new JLabel("Valor (R$): ")));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                valoresItem.get(CamposItem.NOME),
                valoresItem.get(CamposItem.ID),
                valoresItem.get(CamposItem.CATEGORIA),
                valoresItem.get(CamposItem.QUANTIDADE),
                valoresItem.get(CamposItem.VALOR)));

        JPanel painelFormularios;
        if (modo == ModosDetalhe.EDITAR) {
            String titulo = "Informações básicas - Filial do item escolhido: " + filialdoItem.getNome();
            painelFormularios = new PainelFormulario(esquerdos, direitos, titulo);
        } else {
            // TODO: como checar se existem filiais
            esquerdos.add(new JLabel("Filial: "));
            direitos.add(opcoesFiliais);
            painelFormularios = new PainelFormulario(esquerdos, direitos, "Adicionar informações básicas");
        }

        return painelFormularios;

    }

    private JPanel criarFormularioProdutoQuimico() {

        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                new JLabel("Risco a saúde: "),
                new JLabel("Risco de fogo: "),
                new JLabel("Reatividade: "),
                new JLabel("Perigo especifico: ")
        ));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                opcoesPerigoaSaude,
                opcoesRiscoDeFogo,
                opcoesReatividade,
                valorPerigoEspecifico,
                isRestrito
        ));
        if (modo == ModosDetalhe.EDITAR) direitos.add(isRestrito);
        return new PainelFormulario(esquerdos, direitos, "Detalhes - Produto químico");

    }

    private JPanel criarFormularioFarmaceutico() {
        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                new JLabel("Tarja: "),
                new JLabel("Composição: ")
        ));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                valorTarja,
                valorComposicao,
                isReceita,
                isRetencaoDeReceita,
                isGenerico
        ));
        if (modo == ModosDetalhe.EDITAR) direitos.add(isRestrito);
        return new PainelFormulario(esquerdos, direitos, "Detalhes - Farmacêutico");
    }

    @Override
    protected void excluirElemento() {
        controleEstoque.removerItem(itemEscolhido);
        janelaPesquisa.refresh();
    }

    @Override
    protected void atualizarElemento() throws IdRepetidoException {
        controleEstoque.atualizarCaracteristicasBasicas(
                valoresItem.get(CamposItem.NOME).getText(),
                valoresItem.get(CamposItem.CATEGORIA).getText(),
                Double.parseDouble(valoresItem.get(CamposItem.VALOR).getText()),
                Integer.parseInt(valoresItem.get(CamposItem.QUANTIDADE).getText()),
                Integer.parseInt(valoresItem.get(CamposItem.ID).getText()),
                itemEscolhido
        );
        if (itemEscolhido instanceof ProdutoQuimico) {
            controleEstoque.atualizarProdutoQuimico(
                    valorPerigoEspecifico.getText(),
                    (int) opcoesRiscoDeFogo.getSelectedItem(),
                    (int) opcoesReatividade.getSelectedItem(),
                    (int) opcoesPerigoaSaude.getSelectedItem(),
                    (ProdutoQuimico) itemEscolhido
            );
        } else if (itemEscolhido instanceof Farmaceutico) {
            controleEstoque.atualizarFarmaceutico(
                    valorTarja.getText(),
                    valorComposicao.getText(),
                    isReceita.isSelected(),
                    isRetencaoDeReceita.isSelected(),
                    isGenerico.isSelected(),
                    (Farmaceutico) itemEscolhido
            );
        }
        try {
            if (isRestrito.isSelected()) {
                controleEstoque.restringirItem(itemEscolhido);
            } else {
                controleEstoque.liberarItem(itemEscolhido);
            }
        } catch (NivelRestricaoInadequadoException e) {
            isRestrito.setSelected(itemEscolhido.isRestrito());
            mensagemErroRestricao(e);
        }
    }

    @Override
    protected void popularFormularios() {
        isRestrito.setSelected(itemEscolhido.isRestrito());
        valoresItem.get(CamposItem.NOME).setText(itemEscolhido.getNome());
        valoresItem.get(CamposItem.ID).setText(itemEscolhido.getCategoria());
        valoresItem.get(CamposItem.CATEGORIA).setText(String.valueOf(itemEscolhido.getValor()));
        valoresItem.get(CamposItem.QUANTIDADE).setText(String.valueOf(itemEscolhido.getQuantidade()));
        valoresItem.get(CamposItem.VALOR).setText(String.valueOf(itemEscolhido.getId()));
        ArrayList<Filial> filiais = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(filiais.toArray(new Filial[0]));
        if (itemEscolhido instanceof ProdutoQuimico) {
            valorPerigoEspecifico.setText(((ProdutoQuimico) itemEscolhido).getPerigoEspecifico());
            opcoesPerigoaSaude.setSelectedItem(((ProdutoQuimico) itemEscolhido).getPerigoaSaude());
            opcoesRiscoDeFogo.setSelectedItem(((ProdutoQuimico) itemEscolhido).getRiscoDeFogo());
            opcoesReatividade.setSelectedItem(((ProdutoQuimico) itemEscolhido).getReatividade());
        } else if (itemEscolhido instanceof Farmaceutico) {
            valorComposicao.setText(((Farmaceutico) itemEscolhido).getComposicao());
            valorTarja.setText(((Farmaceutico) itemEscolhido).getTarja());
            isGenerico.setSelected(((Farmaceutico) itemEscolhido).isGenerico());
            isReceita.setSelected(((Farmaceutico) itemEscolhido).isReceita());
            isRetencaoDeReceita.setSelected(((Farmaceutico) itemEscolhido).isRetencaoDeReceita());
        }
    }

    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        Component componente = abaPaginada.getSelectedComponent();
        if (opcoesFiliais != null) {
            controleEstoque = new ControleEstoqueFilial(controleEmpresa, (Filial) Objects.requireNonNull(opcoesFiliais.getSelectedItem()));
        }
        if (componente == formularioFarmaceutico) {
            controleEstoque.adicionarFarmaceutico(
                    valoresItem.get(CamposItem.NOME).getText(),
                    valoresItem.get(CamposItem.ID).getText(),
                    Double.parseDouble(valoresItem.get(CamposItem.VALOR).getText()),
                    Integer.parseInt(valoresItem.get(CamposItem.QUANTIDADE).getText()),
                    Integer.parseInt(valoresItem.get(CamposItem.ID).getText()),
                    valorTarja.getText(),
                    valorComposicao.getText(),
                    isReceita.isSelected(),
                    isRetencaoDeReceita.isSelected(),
                    isGenerico.isSelected()
            );
        } else if (componente == formularioProdutoQuimico) {
            controleEstoque.adicionarProdutoQuimico(
                    valoresItem.get(CamposItem.NOME).getText(),
                    valoresItem.get(CamposItem.ID).getText(),
                    Double.parseDouble(valoresItem.get(CamposItem.VALOR).getText()),
                    Integer.parseInt(valoresItem.get(CamposItem.QUANTIDADE).getText()),
                    Integer.parseInt(valoresItem.get(CamposItem.ID).getText()),
                    valorPerigoEspecifico.getText(),
                    (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                    (Integer) opcoesReatividade.getSelectedItem(),
                    (Integer) opcoesPerigoaSaude.getSelectedItem()
            );
        }
    }

    private void mensagemErroRestricao(NivelRestricaoInadequadoException e) {
        JOptionPane.showMessageDialog(
                null, e.getMessage(), "Erro de restrição:", JOptionPane.ERROR_MESSAGE
        );
    }

}
