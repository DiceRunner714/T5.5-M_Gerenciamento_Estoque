package view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JanelaPesquisaBuilder {

    public <T> JanelaPesquisaBuilder(Container painel, ModosPesquisa modo, JList<T> listaElementos,
                                     JButton[] botoes, JComponent[] componentesExtras) {

        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Painel principal
        painel.setLayout(new GridBagLayout());

        // adicionar Painel de botões
        c.insets = new Insets(0, 0, 0, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridy = 2;
        c.gridx = 1;
        c.fill = GridBagConstraints.VERTICAL;
        painel.add(criarPainelBotoes(botoes), c);

        // adicionar Título
        c.insets = new Insets(0, 20, 0, 10);
        c.weightx = 0.9;
        c.weighty = 0.1;
        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        JLabel titulo = new JLabel();
        switch (modo) {
            case LISTAR_ESTOQUE_GERAL -> titulo.setText("Pesquisar estoque");
            case LISTAR_FILIAIS -> titulo.setText("Pesquisar filiais");
        }
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painel.add(titulo, c);

        c.insets = new Insets(0, 20, 0, 10);
        c.weightx = 0.9;
        c.weighty = 0.05;
        c.gridy = 1;
        for (JComponent componenteExtra : componentesExtras) {
            painel.add(componenteExtra, c);
            c.gridy++;
        }

        // adicionar jlist
        c.insets = new Insets(0, 20, 20, 10);
        c.weightx = 0.9;
        c.weighty = 0.95;
        c.fill = GridBagConstraints.BOTH;
        painel.add(listaElementos, c);


    }

    public <T> JanelaPesquisaBuilder(Container painel, ModosPesquisa modo, JList<T> listaElementos, JButton[] botoes) {

        painel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Painel principal
        painel.setLayout(new GridBagLayout());

        // adicionar Painel de botões
        c.insets = new Insets(0, 0, 0, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridy = 1;
        c.gridx = 1;
        c.fill = GridBagConstraints.VERTICAL;
        painel.add(criarPainelBotoes(botoes), c);

        // adicionar Título
        c.insets = new Insets(0, 20, 5, 10);
        c.weightx = 0.9;
        c.weighty = 0.1;
        c.gridy = 0;
        c.gridx = 0;
        JLabel titulo = new JLabel();
        switch (modo) {
            case LISTAR_ESTOQUE_GERAL -> titulo.setText("Pesquisar estoque");
            case LISTAR_FILIAIS -> titulo.setText("Pesquisar filiais");
        }
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painel.add(titulo, c);

        // adicionar jlist
        c.insets = new Insets(0, 20, 20, 10);
        c.weightx = 0.9;
        c.weighty = 0.9;
        c.gridy = 1;
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
