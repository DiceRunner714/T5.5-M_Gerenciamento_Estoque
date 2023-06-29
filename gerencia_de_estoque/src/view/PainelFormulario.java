package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * JPanel formulário com os campos de Farmaceutico, herda da classe JPanel
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see JPanel
 * @see PainelFormularioItem
 * @see PainelFormularioFarmaceutico
 * @see PainelFormularioQuimico
 * @since 2023
 */
public class PainelFormulario extends JPanel {

    /**
     * Construtor vazio permite subclasses inicializarem seus componentes antes de criar o formulário
     */
    public PainelFormulario() {

    }

    /**
     * Construtor que cria o formulário com os componentes especificados
     *
     * @param componentesEsquerdos componentes à esquerda no formulário
     * @param componentesDireitos  componentes à direita no formulário
     * @param titulo               título do formulário
     * @param <T>                  tipo dos componentes, deve ser subclasse de JComponent
     */
    public <T extends JComponent> PainelFormulario(List<T> componentesEsquerdos, List<T> componentesDireitos, String titulo) {
        criarFormulario(componentesEsquerdos, componentesDireitos, titulo);
    }

    /**
     * Define o layout e adicionar os componentes ao painel
     *
     * @param componentesEsquerdos componentes á esquerda da malha
     * @param componentesDireitos  componentes à direita da malha
     * @param titulo               título do formulário
     * @param <T>                  tipo do componente, deve ser subclasse de JCompoentn
     */
    public <T extends JComponent> void criarFormulario(List<T> componentesEsquerdos, List<T> componentesDireitos, String titulo) {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitulo = new JLabel(titulo);

        int padding_lateral = 15;

        // Ajuste de título
        c.insets = new Insets(10, 0, 10, 0);
        c.anchor = GridBagConstraints.CENTER;   // alinhamento dentro das célula
        c.weightx = 1;                          // % do espaço horizontal
        c.gridwidth = 2;                        // quantas células horizontais
        c.gridx = 0;                            // x da célula
        c.gridy = 0;                            // y da célula
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(labelTitulo, c);

        // Ajuste de labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.3;
        c.gridwidth = 1;
        c.insets = new Insets(5, padding_lateral, 5, 5);  // Padding
        for (T componenteEsquerdo : componentesEsquerdos) {
            c.gridy++;
            this.add(componenteEsquerdo, c);
        }

        // Ajuste de campos
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 0, 5, padding_lateral);
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        for (T componenteDireito : componentesDireitos) {
            c.gridy++;
            this.add(componenteDireito, c);
        }
    }


}
