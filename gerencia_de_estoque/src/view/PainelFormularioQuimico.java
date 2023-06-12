package view;

import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.ProdutoQuimico;

import javax.swing.*;

public class PainelFormularioQuimico extends PainelFormulario {
    private final JComboBox<Integer> opcoesPerigoaSaude = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesRiscoDeFogo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesReatividade = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JTextField valorPerigoEspecifico = new JTextField();

    public PainelFormularioQuimico() {
        criarFormulario(
                new JComponent[]{
                new JLabel("Risco a saúde: "),
                new JLabel("Risco de fogo: "),
                new JLabel("Reatividade: "),
                new JLabel("Perigo especifico: ")},
                new JComponent[]{
                        opcoesPerigoaSaude,
                        opcoesRiscoDeFogo,
                        opcoesReatividade,
                        valorPerigoEspecifico},
                "Detalhes - Produto químico");
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

    public void adicionarProdutoQuimico(PainelFormularioItem painelFormularioItem, ControleEstoqueFilial controleEstoqueFilial) throws IdRepetidoException {
        controleEstoqueFilial.adicionarProdutoQuimico(
                painelFormularioItem.getValorNome().getText(),
                painelFormularioItem.getValorCategoria().getText(),
                Double.parseDouble(painelFormularioItem.getValorValor().getText()),
                Integer.parseInt(painelFormularioItem.getValorQuantidade().getText()),
                Integer.parseInt(painelFormularioItem.getValorId().getText()),
                valorPerigoEspecifico.getText(),
                (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                (Integer) opcoesReatividade.getSelectedItem(),
                (Integer) opcoesPerigoaSaude.getSelectedItem()
        );
    }
}
