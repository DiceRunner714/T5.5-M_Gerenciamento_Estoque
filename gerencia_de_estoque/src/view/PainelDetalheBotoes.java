package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class PainelDetalheBotoes extends JPanel {
    public PainelDetalheBotoes(JButton botaoAdicionar, JButton botaoCancelar,
                               JButton botaoAtualizar, JButton botaoExcluir, ModosDetalhe modo,
                               ActionListener botaoListener) {

        this.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 0));
        switch (modo) {
            case EDITAR -> {
                this.add(botaoAtualizar);
                this.add(botaoExcluir);

                botaoAtualizar.addActionListener(botaoListener);
                botaoExcluir.addActionListener(botaoListener);
            }
            case ADICIONAR -> {
                this.add(botaoAdicionar);
                this.add(botaoCancelar);

                botaoAdicionar.addActionListener(botaoListener);
                botaoCancelar.addActionListener(botaoListener);
            }
        }
    }
}
