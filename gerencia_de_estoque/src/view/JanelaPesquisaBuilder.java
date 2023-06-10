package view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class JanelaPesquisaBuilder {
    public <T> JanelaPesquisaBuilder(Container painel, String stringTitulo, JList<T> listaElementos,
                                     JButton[] botoes, JComponent[] componentesExtras) {

        construirBasico(painel, stringTitulo, listaElementos, botoes, componentesExtras.length);
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 20, 0, 10);
        c.weightx = 0.9;
        c.weighty = 0.01;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        for (JComponent componenteExtra : componentesExtras) {
            c.gridy++;
            painel.add(componenteExtra, c);
        }

    }

    public <T> JanelaPesquisaBuilder(Container painel, String stringTitulo, JList<T> listaElementos, JButton[] botoes) {
        construirBasico(painel, stringTitulo, listaElementos, botoes, 0);
    }

    private <T> void construirBasico(Container painel, String stringTitulo, JList<T> listaElementos, JButton[] botoes, int listaOffset) {

        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Painel principal
        painel.setLayout(new GridBagLayout());

        // adicionar Título
        c.insets = new Insets(0, 20, 5, 10);
        c.weightx = 0.9;
        c.weighty = 0.01;
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_START;
        JLabel titulo = new JLabel(stringTitulo);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painel.add(titulo, c);

        // adicionar Painel de botões
        c.insets = new Insets(0, 0, 0, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridy = 1 + listaOffset;
        c.gridx = 1;
        c.fill = GridBagConstraints.VERTICAL;
        painel.add(criarPainelBotoes(botoes), c);

        // adicionar jlist
        c.insets = new Insets(0, 20, 20, 10);
        c.weightx = 0.9;
        c.weighty = 0.9;
        c.gridy = 1 + listaOffset;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        painel.add(listaElementos, c);


    }

    private JPanel criarPainelBotoes(JButton[] botoes) {
        // Malha de botões
        JPanel malhaBotoes = new JPanel();
        JPanel painelBotoes = new JPanel();
        malhaBotoes.setLayout(new GridLayout(0, 1, 0, 10));
        // Equivalente a botao -> malhabotoes.add(botao)
        Arrays.stream(botoes).forEach(malhaBotoes::add);
        painelBotoes.add(malhaBotoes, BorderLayout.NORTH);
        return painelBotoes;
    }
}
