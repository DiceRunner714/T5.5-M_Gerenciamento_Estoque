package view;

import controle.ControleEstoque;
import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.Item;
import modelo.ProdutoQuimico;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class PainelProdutoQuimico extends JPanel {
    private final JComboBox<Integer> opcoesPerigoaSaude = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesRiscoDeFogo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesReatividade = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JTextField valorPerigoEspecifico = new JTextField();

    public PainelProdutoQuimico() {
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
                valorPerigoEspecifico
        ));
        new PainelFormularioBuilder(this,esquerdos, direitos, "Detalhes - Produto químico");
    }

    public void atualizarProdutoQuimico(ControleEstoqueFilial controleEstoqueFilial, ProdutoQuimico itemEscolhido) {
        controleEstoqueFilial.atualizarProdutoQuimico(
                valorPerigoEspecifico.getText(),
                (int) opcoesRiscoDeFogo.getSelectedItem(),
                (int) opcoesReatividade.getSelectedItem(),
                (int) opcoesPerigoaSaude.getSelectedItem(),
                itemEscolhido
        );
    }

    public void popularFormularios(ProdutoQuimico itemEscolhido) {
        valorPerigoEspecifico.setText((itemEscolhido).getPerigoEspecifico());
        opcoesPerigoaSaude.setSelectedItem((itemEscolhido).getPerigoaSaude());
        opcoesRiscoDeFogo.setSelectedItem((itemEscolhido).getRiscoDeFogo());
        opcoesReatividade.setSelectedItem((itemEscolhido).getReatividade());
    }

    public void adicionarProdutoQuimico(PainelItem painelItem, ControleEstoqueFilial controleEstoqueFilial) throws IdRepetidoException {
        controleEstoqueFilial.adicionarProdutoQuimico(
                painelItem.getValorNome().getText(),
                painelItem.getValorCategoria().getText(),
                Double.parseDouble(painelItem.getValorValor().getText()),
                Integer.parseInt(painelItem.getValorQuantidade().getText()),
                Integer.parseInt(painelItem.getValorID().getText()),
                valorPerigoEspecifico.getText(),
                (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                (Integer) opcoesReatividade.getSelectedItem(),
                (Integer) opcoesPerigoaSaude.getSelectedItem()
        );
    }
}
