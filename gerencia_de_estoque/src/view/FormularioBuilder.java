package view;

import javax.swing.*;
import java.awt.*;

public class FormularioBuilder extends JPanel {

    public <T extends JComponent> FormularioBuilder(JPanel painel, T[] componentesEsquerdos,
                                                    T[] componentesDireitos, String titulo) {

        painel.setLayout(new GridBagLayout());
        GridBagConstraints cInterno = new GridBagConstraints();

        JLabel labelTitulo = new JLabel(titulo);

        // Ajuste de título
        cInterno.anchor = GridBagConstraints.CENTER;   // alinhamento dentro das célula
        cInterno.weightx = 1;                          // % do espaço horizontal
        cInterno.gridwidth = 2;                        // quantas células horizontais
        cInterno.gridx = 0;                            // x da célula
        cInterno.gridy = 0;                            // y da célula
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painel.add(labelTitulo, cInterno);

        // Ajuste de labels
        cInterno.fill = GridBagConstraints.HORIZONTAL;
        cInterno.anchor = GridBagConstraints.LINE_START;
        cInterno.weightx = 0.3;
        cInterno.gridwidth = 1;
        cInterno.insets = new Insets(5, 5, 5, 5);  // Padding
        cInterno.gridx = 0;
        int currentGridyEsquerdo = 0;
        for (T componenteEsquerdo : componentesEsquerdos) {
            currentGridyEsquerdo++;
            cInterno.gridy = currentGridyEsquerdo;
            painel.add(componenteEsquerdo, cInterno);
        }

        // Ajuste de campos
        cInterno.anchor = GridBagConstraints.LINE_END;
        cInterno.insets = new Insets(5, 0, 5, 5);
        cInterno.weightx = 0.6;
        cInterno.gridx = 1;

        int currentGridyDireito = 0;
        for (T componenteDireito : componentesDireitos) {
            currentGridyDireito++;
            cInterno.gridy = currentGridyDireito;
            painel.add(componenteDireito, cInterno);
        }

    }

}
