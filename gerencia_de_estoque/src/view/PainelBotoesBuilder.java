package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PainelBotoesBuilder {

    public PainelBotoesBuilder(JPanel painel, JButton botaoAdicionar, JButton botaoCancelar,
                               JButton botaoAtualizar, JButton botaoExcluir, Modos modo,
                               ActionListener botaoListener) {

        painel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        switch (modo) {
            case EDITAR -> {
                painel.add(botaoAtualizar);
                painel.add(botaoExcluir);

                botaoAtualizar.addActionListener(botaoListener);
                botaoExcluir.addActionListener(botaoListener);
            }
            case ADICIONAR -> {
                painel.add(botaoAdicionar);
                painel.add(botaoCancelar);

                botaoAdicionar.addActionListener(botaoListener);
                botaoCancelar.addActionListener(botaoListener);
            }
        }
    }

}
