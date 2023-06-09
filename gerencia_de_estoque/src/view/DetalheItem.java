package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.Filial;
import modelo.Item;
import modelo.ProdutoQuimico;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class DetalheItem extends Detalhe {
    private JTabbedPane abaPaginada = new JTabbedPane();
    private JPanel formularioPrincipal = new JPanel();
    private JPanel formularioFarmaceutico = new JPanel();
    private JPanel formularioProdutoQuimico = new JPanel();
    private JTextField valorNome = new JTextField();
    private JTextField valorId = new JTextField();
    private JTextField valorCategoria = new JTextField();
    private JTextField valorQuantidade = new JTextField();
    private JTextField valorValor = new JTextField();
    private JTextField valorPerigoEspecifico = new JTextField();
    private JTextField valorTarja = new JTextField();
    private JTextField valorComposicao = new JTextField();
    private JComboBox<Integer> opcoesPerigoaSaude = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private JComboBox<Integer> opcoesRiscoDeFogo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private JComboBox<Integer> opcoesReatividade = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private JComboBox<Filial> opcoesFiliais;
    private JCheckBox retencaoDeReceita = new JCheckBox("Retenção de receita");
    private JCheckBox generico = new JCheckBox("Medicamento genérico");
    private JCheckBox restrito = new JCheckBox("restrito");
    private JCheckBox receita = new JCheckBox("Necessita de receita");
    private Filial filialdoItem;
    private ControleEstoqueFilial controleEstoque;
    private Item itemEscolhido;

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        super(Modos.ADICIONAR, janelaPesquisa, controleEmpresa);
        ArrayList<Filial> filiaisDisponivels = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(
                filiaisDisponivels.toArray(new Filial[filiaisDisponivels.size()])
        );
        criarJanela(criarPaineisFormularios(), 600, 600, "Item:");
    }

    // Construtor não vazio, item escolhido para modificar
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item itemEscolhido) {
        super(Modos.EDITAR, janelaPesquisa, controleEmpresa);
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

        if (modo == Modos.EDITAR) {
            titulo = "Informações básicas - Filial do item escolhido: " + filialdoItem.getNome();
        } else if (modo == Modos.ADICIONAR) {
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

        JComponent[] esquerdos = {labelPerigoaSaude, labelRiscodeFogo,
                labelReatividade, labelPerigoEspecifico};
        JComponent[] direitos = {opcoesPerigoaSaude, opcoesRiscoDeFogo, opcoesReatividade,
                valorPerigoEspecifico, restrito};

        new PainelFormulariosBuilder(formularioProdutoQuimico, esquerdos, direitos, "Detalhes - Produto químico");

    }

    private void criarFormularioFarmaceutico() {
        JLabel labelNome = new JLabel("Tarja: ");
        JLabel labelComposicao = new JLabel("Composição: ");

        JComponent[] esquerdos = {labelNome, labelComposicao};
        JComponent[] direitos = {valorTarja, valorComposicao, receita, retencaoDeReceita, generico, restrito};
        new PainelFormulariosBuilder(formularioFarmaceutico, esquerdos, direitos, "Detalhes - Farmacêutico");
    }


    @Override
    protected void excluirElemento() {
        controleEstoque.removerItem(itemEscolhido);
        janelaPesquisa.refresh();
    }

    @Override
    protected void atualizarElemento() throws IdRepetidoException {
        controleEstoque.atualizarItem(
                valorNome.getText(),
                valorCategoria.getText(),
                Double.parseDouble(valorValor.getText()),
                Integer.parseInt(valorQuantidade.getText()),
                Integer.parseInt(valorId.getText()),
                itemEscolhido
        );
    }

    @Override
    protected void popularFormularios() {
        valorNome.setText(itemEscolhido.getNome());
        valorCategoria.setText(itemEscolhido.getCategoria());
        valorValor.setText(String.valueOf(itemEscolhido.getValor()));
        valorQuantidade.setText(String.valueOf(itemEscolhido.getQuantidade()));
        valorId.setText(String.valueOf(itemEscolhido.getId()));
        ArrayList<Filial> filiais = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(filiais.toArray(new Filial[filiais.size()]));
    }

    @Override
    protected void adicionarElemento() throws IdRepetidoException {
        Component componente = abaPaginada.getSelectedComponent();
        ControleEstoqueFilial estoqueSelecionado =
                new ControleEstoqueFilial(controleEmpresa, (Filial) opcoesFiliais.getSelectedItem());
        if (componente == formularioFarmaceutico) {
            estoqueSelecionado.adicionarFarmaceutico(
                    valorNome.getText(),
                    valorCategoria.getText(),
                    Double.parseDouble(valorValor.getText()),
                    Integer.parseInt(valorQuantidade.getText()),
                    Integer.parseInt(valorId.getText()),
                    valorTarja.getText(),
                    valorComposicao.getText(),
                    receita.isSelected(),
                    retencaoDeReceita.isSelected(),
                    generico.isSelected()
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

}
