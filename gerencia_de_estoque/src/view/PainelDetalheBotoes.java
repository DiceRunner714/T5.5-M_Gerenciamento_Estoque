package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Painel com os botões de controle da DetalheView
 * criado dependendo do modo em que a view se encontra
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
class PainelDetalheBotoes extends JPanel {

    /**
     * Cria um painel com botões de controle
     *
     * @param botaoAdicionar botão de adicionar elemento
     * @param botaoCancelar  botão de cancelar operação
     * @param botaoAtualizar botão de atualizar elemento
     * @param botaoExcluir   botão de excluir elemento
     * @param modo           modo da DetalheView
     * @param botaoListener  classe Listener para controle dos botões
     */
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
