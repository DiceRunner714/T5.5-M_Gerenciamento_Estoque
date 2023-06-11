package view;

import controle.ControleEstoqueFilial;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.ProdutoQuimico;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

public class PainelFarmaceutico extends JPanel {
    private final JTextField valorTarja = new JTextField();
    private final JTextField valorComposicao = new JTextField();
    private final JCheckBox isRetencaoDeReceita = new JCheckBox("Retenção de receita");
    private final JCheckBox isGenerico = new JCheckBox("Medicamento genérico");
    private final JCheckBox isReceita = new JCheckBox("Necessita de receita");

    public PainelFarmaceutico() {
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

    public void adicionarFarmaceutico(EnumMap<DetalheItem.CamposItem, JTextField> camposItem, ControleEstoqueFilial controleEstoque) throws IdRepetidoException {
        controleEstoque.adicionarFarmaceutico(
                camposItem.get(DetalheItem.CamposItem.NOME).getText(),
                camposItem.get(DetalheItem.CamposItem.ID).getText(),
                Double.parseDouble(camposItem.get(DetalheItem.CamposItem.VALOR).getText()),
                Integer.parseInt(camposItem.get(DetalheItem.CamposItem.QUANTIDADE).getText()),
                Integer.parseInt(camposItem.get(DetalheItem.CamposItem.ID).getText()),
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
