package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DetalheItem extends Detalhe {
    private final JTabbedPane abaPaginada = new JTabbedPane();
    private final JPanel formularioPrincipal = new JPanel();
    private final JPanel formularioFarmaceutico = new JPanel();
    private final JPanel formularioProdutoQuimico = new JPanel();
    private final JTextField valorNome = new JTextField();
    private final JTextField valorId = new JTextField();
    private final JTextField valorCategoria = new JTextField();
    private final JTextField valorQuantidade = new JTextField();
    private final JTextField valorValor = new JTextField();
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
    private JComboBox<Filial> opcoesFiliais;
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        super(ModosDetalhe.ADICIONAR, janelaPesquisa, controleEmpresa);
        ArrayList<Filial> filiaisDisponivels = controleEmpresa.getFiliais();
        // Java infere o tamanho do array nesse caso
        opcoesFiliais = new JComboBox<>(
                filiaisDisponivels.toArray(new Filial[0])
        );
        criarJanela(criarPaineisFormularios(), 600, 600, "Item:");
    }

    // Construtor não vazio, item escolhido para modificar
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item itemEscolhido) {
        super(ModosDetalhe.EDITAR, janelaPesquisa, controleEmpresa);
        this.itemEscolhido = itemEscolhido;
        filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        controleEstoque = new ControleEstoqueFilial(controleEmpresa, filialdoItem);
        criarJanela(criarPaineisFormularios(), 600, 600, "Item:");
        popularFormularios();
    }

    @Override
    protected ArrayList<JComponent> criarPaineisFormularios() {

        ArrayList<JComponent> formularios = new ArrayList<>();

        criarFormularioPrincipal();
        formularios.add(formularioPrincipal);

        switch (modo) {
            // Mostrar todas as opções de itens para adicionar
            case ADICIONAR -> {
                criarFormularioProdutoQuimico();
                criarFormularioFarmaceutico();
                abaPaginada.addTab("Produto Químico", formularioProdutoQuimico);
                abaPaginada.addTab("Farmacêutico", formularioFarmaceutico);
                formularios.add(abaPaginada);
            }
            // Mostrar só o formulário do tipo de item escolhido
            case EDITAR -> {
                if (itemEscolhido instanceof Farmaceutico) {
                    criarFormularioFarmaceutico();
                    formularios.add(formularioFarmaceutico);
                } else if (itemEscolhido instanceof ProdutoQuimico) {
                    criarFormularioProdutoQuimico();
                    formularios.add(formularioProdutoQuimico);
                }
            }
        }
        return formularios;
    }

    private void criarFormularioPrincipal() {
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelId = new JLabel("ID: ");
        JLabel labelCategoria = new JLabel("Categoria: ");
        JLabel labelQuantidade = new JLabel("Quantidade: ");
        JLabel labelValor = new JLabel("Valor (R$): ");
        JLabel labelFilial = new JLabel("Filial: ");

        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                labelNome, labelId, labelCategoria, labelQuantidade, labelValor
        ));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                valorNome, valorId, valorCategoria, valorQuantidade, valorValor
        ));
        String titulo = "Adicionar Informações básicas";

        if (modo == ModosDetalhe.EDITAR) {
            titulo = "Informações básicas - Filial do item escolhido: " + filialdoItem.getNome();
        } else if (modo == ModosDetalhe.ADICIONAR) {
            esquerdos.add(labelFilial);
            direitos.add(opcoesFiliais);
        }

        new PainelFormulariosBuilder(formularioPrincipal, esquerdos, direitos, titulo);

    }

    private void criarFormularioProdutoQuimico() {

        JLabel labelPerigoaSaude = new JLabel("Risco a saúde: ");
        JLabel labelRiscodeFogo = new JLabel("Risco de fogo: ");
        JLabel labelReatividade = new JLabel("Reatividade: ");
        JLabel labelPerigoEspecifico = new JLabel("Perigo especifico: ");

        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                labelPerigoaSaude, labelRiscodeFogo, labelReatividade, labelPerigoEspecifico
        ));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                opcoesPerigoaSaude, opcoesRiscoDeFogo, opcoesReatividade, valorPerigoEspecifico, isRestrito
        ));
        if (modo == ModosDetalhe.EDITAR) {
            direitos.add(isRestrito);
        }

        new PainelFormulariosBuilder(formularioProdutoQuimico, esquerdos, direitos, "Detalhes - Produto químico");

    }

    private void criarFormularioFarmaceutico() {
        JLabel labelNome = new JLabel("Tarja: ");
        JLabel labelComposicao = new JLabel("Composição: ");

        ArrayList<JComponent> esquerdos = new ArrayList<>(Arrays.asList(
                labelNome, labelComposicao
        ));
        ArrayList<JComponent> direitos = new ArrayList<>(Arrays.asList(
                valorTarja, valorComposicao, isReceita, isRetencaoDeReceita, isGenerico
        ));
        if (modo == ModosDetalhe.EDITAR) {
            direitos.add(isRestrito);
        }
        new PainelFormulariosBuilder(formularioFarmaceutico, esquerdos, direitos, "Detalhes - Farmacêutico");
    }


    @Override
    protected void excluirElemento() {
        controleEstoque.removerItem(itemEscolhido);
        janelaPesquisa.refresh();
    }

    @Override
    protected void atualizarElemento() throws IdRepetidoException {
        controleEstoque.atualizarCaracteristicasBasicas(
                valorNome.getText(),
                valorCategoria.getText(),
                Double.parseDouble(valorValor.getText()),
                Integer.parseInt(valorQuantidade.getText()),
                Integer.parseInt(valorId.getText()),
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
        valorNome.setText(itemEscolhido.getNome());
        valorCategoria.setText(itemEscolhido.getCategoria());
        valorValor.setText(String.valueOf(itemEscolhido.getValor()));
        valorQuantidade.setText(String.valueOf(itemEscolhido.getQuantidade()));
        valorId.setText(String.valueOf(itemEscolhido.getId()));
        ArrayList<Filial> filiais = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(filiais.toArray(new Filial[0]));
        isRestrito.setSelected(itemEscolhido.isRestrito());
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
        ControleEstoqueFilial estoqueSelecionado =
                new ControleEstoqueFilial(controleEmpresa, (Filial) Objects.requireNonNull(opcoesFiliais.getSelectedItem()));
        if (componente == formularioFarmaceutico) {
            estoqueSelecionado.adicionarFarmaceutico(
                    valorNome.getText(),
                    valorCategoria.getText(),
                    Double.parseDouble(valorValor.getText()),
                    Integer.parseInt(valorQuantidade.getText()),
                    Integer.parseInt(valorId.getText()),
                    valorTarja.getText(),
                    valorComposicao.getText(),
                    isReceita.isSelected(),
                    isRetencaoDeReceita.isSelected(),
                    isGenerico.isSelected()
            );
        } else if (componente == formularioProdutoQuimico) {
            estoqueSelecionado.adicionarProdutoQuimico(
                    valorNome.getText(),
                    valorCategoria.getText(),
                    Double.parseDouble(valorValor.getText()),
                    Integer.parseInt(valorQuantidade.getText()),
                    Integer.parseInt(valorId.getText()),
                    valorPerigoEspecifico.getText(),
                    (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                    (Integer) opcoesReatividade.getSelectedItem(),
                    (Integer) opcoesPerigoaSaude.getSelectedItem()
            );
        }
    }

    private void mensagemErroRestricao(NivelRestricaoInadequadoException e) {
        JOptionPane.showMessageDialog(null,
                e.getMessage(),
                "Erro de restrição:", JOptionPane.ERROR_MESSAGE);
    }

}
