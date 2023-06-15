package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
class PainelPesquisa extends JPanel {
    public <T> PainelPesquisa(String stringTitulo, JList<T> listaElementos,
                                     List<JButton> botoes, List<? extends JComponent> componentesExtras) {

        construirBasico(stringTitulo, listaElementos, botoes, componentesExtras.size());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 20, 10, 10);
        c.weightx = 0.9;
        c.weighty = 0.01;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        for (JComponent componenteExtra : componentesExtras) {
            c.gridy++;
            this.add(componenteExtra, c);
        }

    }

    public <T> PainelPesquisa(String stringTitulo, JList<T> listaElementos, List<JButton> botoes) {
        construirBasico(stringTitulo, listaElementos, botoes, 0);
    }

    private <T> void construirBasico(String stringTitulo, JList<T> listaElementos, List<JButton> botoes, int listaOffset) {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Painel principal
        this.setLayout(new GridBagLayout());

        // adicionar Título
        c.insets = new Insets(0, 20, 10, 10);
        c.weightx = 0.9;
        c.weighty = 0.01;
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_START;
        JLabel titulo = new JLabel(stringTitulo);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titulo, c);

        // adicionar Painel de botões
        c.insets = new Insets(0, 0, 0, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridy = 1 + listaOffset;
        c.gridx = 1;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(criarPainelBotoes(botoes), c);

        // adicionar jlist
        c.insets = new Insets(0, 20, 20, 10);
        c.weightx = 0.9;
        c.weighty = 0.9;
        c.gridy = 1 + listaOffset;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(listaElementos, c);

    }

    private JPanel criarPainelBotoes(List<JButton> botoes) {
        // Malha de botões
        JPanel malhaBotoes = new JPanel();
        JPanel painelBotoes = new JPanel();
        malhaBotoes.setLayout(new GridLayout(0, 1, 0, 10));
        // Equivalente a botao -> malhabotoes.add(botao)
        botoes.forEach(malhaBotoes::add);
        painelBotoes.add(malhaBotoes, BorderLayout.NORTH);
        return painelBotoes;
    }
}
