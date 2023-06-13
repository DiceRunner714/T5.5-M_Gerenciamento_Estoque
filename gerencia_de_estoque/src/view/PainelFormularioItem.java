package view;


import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.Filial;
import modelo.Item;
import modelo.NivelRestricaoInadequadoException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PainelFormularioItem extends PainelFormulario {
    private final JTextField valorNome = new JTextField();
    private final JTextField valorCategoria = new JTextField();
    private final JTextField valorValor = new JTextField();
    private final JTextField valorQuantidade = new JTextField();
    private final JTextField valorId = new JTextField();
    private final JCheckBox isRestrito = new JCheckBox("Restrito");
    private JComboBox<Filial> opcoesFiliais;


    // Editar item de uma filial
    public PainelFormularioItem(Filial filialdoItem, ModosDetalhe modo) {
        List<JComponent> direitos = new ArrayList<>(inicializarComponentesDireitos());
        if (modo == ModosDetalhe.EDITAR) {
            direitos.add(isRestrito);
        }
        String titulo = "Informações básicas - Filial do item escolhido: " + filialdoItem.getNome();
        criarFormulario(inicializarComponentesEsquerdos(), direitos, titulo);
    }

    // ADICIONAR ITEM GERAL
    public PainelFormularioItem(List<Filial> filiaisDisponiveis) {

        opcoesFiliais = new JComboBox<>(filiaisDisponiveis.toArray(new Filial[0]));

        List<JComponent> esquerdos = new ArrayList<>(inicializarComponentesEsquerdos());
        List<JComponent> direitos = new ArrayList<>(inicializarComponentesDireitos());
        esquerdos.add(new JLabel("Filial: "));
        direitos.add(opcoesFiliais);

        criarFormulario(esquerdos, direitos, "Adicionar informações básicas");

    }

    // Formulários só podem fazer duas coisas, receber dados, ou colocar dados
    public void atualizarCaracteristicasBasicas(ControleEstoqueFilial controleEstoque, Item itemEscolhido) throws IdRepetidoException, ElementoInexistenteException, NivelRestricaoInadequadoException {
        if (isRestrito.isSelected())
            itemEscolhido.restringir();
        else
            itemEscolhido.liberar();
        controleEstoque.atualizarCaracteristicasBasicas(
                valorNome.getText(),
                valorCategoria.getText(),
                Double.parseDouble(valorValor.getText()),
                Integer.parseInt(valorQuantidade.getText()),
                Integer.parseInt(valorId.getText()),
                itemEscolhido
        );
    }

    public void popularFormularios(Item itemEscolhido) {
        valorNome.setText(itemEscolhido.getNome());
        valorCategoria.setText(itemEscolhido.getCategoria());
        valorValor.setText(String.valueOf(itemEscolhido.getValor()));
        valorQuantidade.setText(String.valueOf(itemEscolhido.getQuantidade()));
        valorId.setText(String.valueOf(itemEscolhido.getId()));
        isRestrito.setSelected(itemEscolhido.isRestrito());
    }

    private List<JComponent> inicializarComponentesEsquerdos() {
        return Arrays.asList(
                new JLabel("Nome: "),
                new JLabel("ID: "),
                new JLabel("Categoria: "),
                new JLabel("Quantidade: "),
                new JLabel("Valor (R$): "));
    }

    private List<JComponent> inicializarComponentesDireitos() {
        return Arrays.asList(
                valorNome,
                valorId,
                valorCategoria,
                valorQuantidade,
                valorValor
            );

    }

    public Filial getSelectedFilial() {
        return (Filial) opcoesFiliais.getSelectedItem();
    }

    public JTextField getValorNome() {
        return valorNome;
    }

    public JTextField getValorCategoria() {
        return valorCategoria;
    }

    public JTextField getValorValor() {
        return valorValor;
    }

    public JTextField getValorQuantidade() {
        return valorQuantidade;
    }

    public JTextField getValorId() {
        return valorId;
    }
}
