package view;

import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.Farmaceutico;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PainelFormularioFarmaceutico extends JPanel {
    private final JTextField valorTarja = new JTextField();
    private final JTextField valorComposicao = new JTextField();
    private final JCheckBox isRetencaoDeReceita = new JCheckBox("Retenção de receita");
    private final JCheckBox isGenerico = new JCheckBox("Medicamento genérico");
    private final JCheckBox isReceita = new JCheckBox("Necessita de receita");

    public PainelFormularioFarmaceutico() {
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
        new PainelFormularioBuilder(this ,esquerdos, direitos, "Detalhes - Farmacêutico");
    }

    public void atualizarFarmaceutico(ControleEstoqueFilial controleEstoque, Farmaceutico itemEscolhido) {
        controleEstoque.atualizarFarmaceutico(
                valorTarja.getText(),
                valorComposicao.getText(),
                isReceita.isSelected(),
                isRetencaoDeReceita.isSelected(),
                isGenerico.isSelected(),
                itemEscolhido
        );
    }

    public void adicionarFarmaceutico(PainelFormularioItem painelFormularioItem, ControleEstoqueFilial controleEstoque) throws IdRepetidoException {
        controleEstoque.adicionarFarmaceutico(
                painelFormularioItem.getValorNome().getText(),
                painelFormularioItem.getValorCategoria().getText(),
                Double.parseDouble(painelFormularioItem.getValorValor().getText()),
                Integer.parseInt(painelFormularioItem.getValorQuantidade().getText()),
                Integer.parseInt(painelFormularioItem.getValorID().getText()),
                valorTarja.getText(),
                valorComposicao.getText(),
                isReceita.isSelected(),
                isRetencaoDeReceita.isSelected(),
                isGenerico.isSelected()
        );
    }

    public void popularFormularios(Farmaceutico itemEscolhido) {
        valorComposicao.setText(itemEscolhido.getComposicao());
        valorTarja.setText(itemEscolhido.getTarja());
        isGenerico.setSelected(itemEscolhido.isGenerico());
        isReceita.setSelected(itemEscolhido.isReceita());
        isRetencaoDeReceita.setSelected(itemEscolhido.isRetencaoDeReceita());
    }
}
