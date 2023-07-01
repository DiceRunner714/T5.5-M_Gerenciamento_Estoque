package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Criar um Painel de Pesquisa
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
class PainelPesquisa extends JPanel {
    /**
     * Constrói um painel de pesquisa para certo tipo de objeto com os componentes
     * escolhidos
     *
     * @param stringTitulo      Título do painel
     * @param listaElementos    JList com os elementos a pesquisar
     * @param botoes            botões de controle de elementos
     * @param componentesExtras componentes para adicionar funcionalidades extras para o painél
     * @param <T>               Classe do elemento a ser pesquisado
     */
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

    /**
     * Constrói um painel de pesquisa simplificado, sem componentes extras
     *
     * @param stringTitulo   título do painel
     * @param listaElementos Jlist contendo os elementos a serem pesquisados
     * @param botoes         botões para controle de elementos
     * @param <T>            classe do elemento a ser pesquisado
     */
    public <T> PainelPesquisa(String stringTitulo, JList<T> listaElementos, List<JButton> botoes) {
        construirBasico(stringTitulo, listaElementos, botoes, 0);
    }

    /**
     * Constrói o painel básico com o título, a lista de elementos e os botões
     *
     * @param stringTitulo   titulo do painel
     * @param listaElementos
     * @param botoes
     * @param listaOffset
     * @param <T>
     */
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

    /**
     * Cria um painel de botões a partir de uma lista de botões fornecida
     *
     * @param botoes
     * @return
     */
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
