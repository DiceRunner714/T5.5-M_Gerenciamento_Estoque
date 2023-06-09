package view;

import controle.ControleEmpresa;
import controle.ControleEstoque;
import controle.IdRepetidoException;
import modelo.Farmaceutico;
import modelo.Filial;
import modelo.Item;
import modelo.ProdutoQuimico;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

public class DetalheItem implements ActionListener {
    private static ControleEmpresa controleEmpresa;
    // TODO: criar os componentes das classes filhas
    private JFrame janela = new JFrame("Item");
    private JTabbedPane abaPaginada = new JTabbedPane();
    private JPanel formulariosPrincipais = new JPanel();
    private JPanel formulariosFarmaceutico = new JPanel();
    private JPanel formulariosProdutoQuimico = new JPanel();
    private JPanel formulariosSecundarios = new JPanel();
    private JPanel botoes = new JPanel();
    private JTextField valorNome = new JTextField();
    private JTextField valorId = new JTextField();
    private JTextField valorCategoria = new JTextField();
    private JTextField valorQuantidade = new JTextField();
    private JTextField valorValor = new JTextField();
    private JTextField valorPerigoEspecifico = new JTextField();
    private JComboBox<Integer> opcoesPerigoaSaude;
    private JComboBox<Integer> opcoesRiscoDeFogo;
    private JComboBox<Integer> opcoesReatividade;
    private JComboBox<Filial> opcoesFiliais;
    private JTextField valorTarja = new JTextField();
    private JTextField valorComposicao = new JTextField();
    private JCheckBox retencaoDeReceita = new JCheckBox("Retenção de receita");
    private JCheckBox generico = new JCheckBox("Genérico");
    private JCheckBox restrito = new JCheckBox("Restrito");
    private JCheckBox receita = new JCheckBox("Necessita de receita");
    private JButton botaoAtualizar = new JButton("Atualizar");
    private JButton botaoExcluir = new JButton("Excluir");
    private JButton botaoAdicionar = new JButton("Adicionar");
    private JButton botaoCancelar = new JButton("Cancelar");
    private CategoriasItens tipoDeItem;
    private Modos modo;
    private JanelaPesquisa janelaPesquisa;
    private Filial filialdoItem;
    private Item itemEscolhido;
    private ControleEstoque controleEstoque;

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        modo = Modos.ADICIONAR;
        DetalheItem.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;

        formulariosPrincipais.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ArrayList<Filial> filiais = controleEmpresa.getFiliais();
        opcoesFiliais = new JComboBox<>(filiais.toArray(new Filial[filiais.size()]));

        criarJanela();

    }

    // Construtor não vazio, item escolhido para modificar
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item itemEscolhido) {
        this.modo = Modos.EDITAR;
        this.janelaPesquisa = janelaPesquisa;
        this.itemEscolhido = itemEscolhido;
        DetalheItem.controleEmpresa = controleEmpresa;
        filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        controleEstoque = new ControleEstoque(controleEmpresa, filialdoItem);

        criarJanela();
    }

    private void criarJanela() {

        criarFormularioPrincipal();
        criarFormularioProdutoQuimico();
        criarFormularioFarmaceutico();
        criarBotoes();

        janela.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = 0.2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        janela.add(formulariosPrincipais, c);

        c.gridy = 1;
        switch (modo) {
            case ADICIONAR -> {
                abaPaginada.addTab("Produto Químico", formulariosProdutoQuimico);
                abaPaginada.addTab("Farmacêutico", formulariosFarmaceutico);
                janela.add(abaPaginada, c);
            }
            case EDITAR -> {
                if (itemEscolhido instanceof Farmaceutico) {
                    janela.add(formulariosFarmaceutico, c);
                } else if (itemEscolhido instanceof ProdutoQuimico) {
                    janela.add(formulariosProdutoQuimico, c);
                }
            }
        }
        c.weighty = 0.6;
        c.gridy = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        janela.add(botoes, c);

        janela.setSize(600, 600);
        janela.setResizable(false);
        janela.setVisible(true);

    }

    private <T extends JComponent> void criarFormulario(T[] componentesEsquerdos,
                                                        T[] componentesDireitos,
                                                        String titulo,
                                                        JPanel painel) {

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

    private void criarFormularioPrincipal() {

        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelId = new JLabel("ID: ");
        JLabel labelCategoria = new JLabel("Categoria: ");
        JLabel labelQuantidade = new JLabel("Quantidade: ");
        JLabel labelValor = new JLabel("Valor (R$): ");
        JLabel labelFilial = new JLabel("Filial: ");

        JComponent[] esquerdos;
        JComponent[] direitos;
        String titulo;

        if (modo != Modos.ADICIONAR) {
            titulo = "Informações básicas - Filial do item escolhido: " + filialdoItem.getNome();
            esquerdos = new JComponent[]{labelNome, labelId, labelCategoria, labelQuantidade, labelValor};
            direitos = new JComponent[]{valorNome, valorId, valorCategoria, valorQuantidade, valorValor};
        } else {
            titulo = "Adicionar Informações básicas";
            esquerdos = new JComponent[]{labelNome, labelId, labelCategoria, labelQuantidade, labelValor, labelFilial};
            direitos = new JComponent[]{valorNome, valorId, valorCategoria, valorQuantidade, valorValor, opcoesFiliais};
        }
        criarFormulario(esquerdos, direitos, titulo, formulariosPrincipais);

    }

    private void criarFormularioFarmaceutico() {
        JLabel labelNome = new JLabel("Tarja: ");
        JLabel labelComposicao = new JLabel("Composição: ");
        JComponent[] esquerdos = {labelNome, labelComposicao};
        JComponent[] direitos = {valorTarja, valorComposicao, receita, retencaoDeReceita, generico, restrito};
        criarFormulario(esquerdos, direitos, "Detalhes - Farmacêutico", formulariosFarmaceutico);
    }

    private void criarFormularioProdutoQuimico() {
        JLabel labelPerigoaSaude = new JLabel("Risco a saúde: ");
        JLabel labelRiscodeFogo = new JLabel("Risco de fogo: ");
        JLabel labelReatividade = new JLabel("Reatividade: ");
        JLabel labelPerigoEspecifico = new JLabel("Perigo especifico: ");

        Integer[] niveis = {1, 2, 3, 4, 5};
        opcoesReatividade = new JComboBox<>(niveis);
        opcoesRiscoDeFogo = new JComboBox<>(niveis);
        opcoesPerigoaSaude = new JComboBox<>(niveis);

        JComponent[] esquerdos = {labelPerigoaSaude, labelRiscodeFogo,
                labelReatividade, labelPerigoEspecifico};
        JComponent[] direitos = {opcoesPerigoaSaude, opcoesRiscoDeFogo, opcoesReatividade,
                valorPerigoEspecifico, restrito};

        criarFormulario(esquerdos, direitos, "Detalhes - Produto químico", formulariosProdutoQuimico);

    }

    private void criarBotoes() {
        // Botões
        botoes.setLayout(new FlowLayout(FlowLayout.TRAILING));
        switch (modo) {
            case EDITAR -> {
                botoes.add(botaoAtualizar);
                botoes.add(botaoExcluir);

                valorNome.setText(itemEscolhido.getNome());
                valorCategoria.setText(itemEscolhido.getCategoria());
                valorValor.setText(String.valueOf(itemEscolhido.getValor()));
                valorQuantidade.setText(String.valueOf(itemEscolhido.getQuantidade()));
                valorId.setText(String.valueOf(itemEscolhido.getId()));

                botaoAtualizar.addActionListener(this);
                botaoExcluir.addActionListener(this);
            }
            case ADICIONAR -> {
                botoes.add(botaoAdicionar);
                botoes.add(botaoCancelar);

                botaoAdicionar.addActionListener(this);
                botaoCancelar.addActionListener(this);
            }
        }

    }

    private void processarFormularios() {
        try {
            switch (modo) {
                case EDITAR -> {
                    controleEstoque.atualizarItem(
                            valorNome.getText(),
                            valorCategoria.getText(),
                            Double.parseDouble(valorValor.getText()),
                            Integer.parseInt(valorQuantidade.getText()),
                            Integer.parseInt(valorId.getText()),
                            itemEscolhido
                    );
                    janelaPesquisa.refresh();
                }
                case ADICIONAR -> {
                    Component componente = abaPaginada.getSelectedComponent();
                    ControleEstoque estoqueSelecionado =
                            new ControleEstoque(controleEmpresa, (Filial) opcoesFiliais.getSelectedItem());
                    if (componente == formulariosFarmaceutico) {
                        estoqueSelecionado.adicionarFarmaceutico(
                                valorNome.getText(),
                                valorCategoria.getText(),
                                Double.parseDouble(valorValor.getText()),
                                Integer.parseInt(valorQuantidade.getText()),
                                Integer.parseInt(valorId.getText()),
                                valorTarja.getText(),
                                valorComposicao.getText(),
                                receita.isSelected(),
                                retencaoDeReceita.isSelected(),
                                generico.isSelected()
                        );
                    } else if (componente == formulariosProdutoQuimico) {
                        estoqueSelecionado.adicionarProdutoQuimico(
                                valorNome.getText(),
                                valorCategoria.getText(),
                                Double.parseDouble(valorValor.getText()),
                                Integer.parseInt(valorQuantidade.getText()),
                                Integer.parseInt(valorId.getText()),
                                valorPerigoEspecifico.getText(),
                                (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                                (Integer) opcoesReatividade.getSelectedItem(),
                                (Integer) opcoesPerigoaSaude.getSelectedItem()
                        );
                    }
                    janelaPesquisa.refresh();
                }
            }
        } catch (NumberFormatException e1) {
            mensagemErrodeFormatacao();
        } catch (NullPointerException e2) {
            mensagemErroFormularioVazio();
        } catch (IdRepetidoException e3) {
            mensagemErroIdrepetido(e3);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == botaoAtualizar || src == botaoAdicionar) {
            processarFormularios();
        } else {
            if (src == botaoExcluir) {
                controleEstoque.removerItem(itemEscolhido);
                janelaPesquisa.refresh();
            }
            janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        }
    }


    // --POP UPS--
    private void mensagemErroIdrepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(null,
                e3.getMessage(),
                "Erro de indentificação", JOptionPane.ERROR_MESSAGE);
    }

    private void mensagemErrodeFormatacao() {
        JOptionPane.showMessageDialog(null,
                "Erro de formatação: assegure-se que valores numéricos foram inseridos corretamente.",
                "Erro de formatação", JOptionPane.ERROR_MESSAGE);
    }

    private void mensagemErroFormularioVazio() {
        JOptionPane.showMessageDialog(null,
                "Erro de entrada: assegure-se que todos os formulários foram preenchidos.",
                "Erro de entrada", JOptionPane.ERROR_MESSAGE);
    }
}
