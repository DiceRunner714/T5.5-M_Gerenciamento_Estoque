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
    private JPanel formularios = new JPanel();
    private JPanel botoes = new JPanel();
    private JTextField valorNome = new JTextField();
    private JTextField valorId = new JTextField();
    private JTextField valorCategoria = new JTextField();
    private JTextField valorQuantidade = new JTextField();
    private JTextField valorValor = new JTextField();
    private JButton botaoAtualizar = new JButton("Atualizar");
    private JButton botaoExcluir = new JButton("Excluir");
    private JButton botaoAdicionar = new JButton("Adicionar");
    private JButton botaoCancelar = new JButton("Cancelar");
    private JComboBox<Filial> filialJComboBox;
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

        formularios.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ArrayList<Filial> filiais = controleEmpresa.getFiliais();
        filialJComboBox = new JComboBox<>(filiais.toArray(new Filial[filiais.size()]));

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

    // Construtor vazio, adicionar item
    // TODO: como adicionar item se não foi selecionada uma filial?
    public void criarJanela() {

        JLabel descricao = new JLabel("Informações da Item");
        JLabel labelNome = new JLabel("Nome: ");
        JLabel labelId = new JLabel("ID: ");
        JLabel labelCategoria = new JLabel("Categoria: ");
        JLabel labelQuantidade = new JLabel("Quantidade: ");
        JLabel labelValor = new JLabel("Valor (R$): ");
        JLabel labelFilial = new JLabel("Filial: ");

        formularios.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Ajuste de título
        c.anchor = GridBagConstraints.CENTER;   // alinhamento dentro das célula
        c.weightx = 1;                          // % do espaço horizontal
        c.gridwidth = 2;                        // quantas células horizontais
        c.gridx = 0;                            // x da célula
        c.gridy = 0;                            // y da célula
        descricao.setFont(new Font("Arial", Font.BOLD, 20));
        formularios.add(descricao, c);

        // Ajuste de labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.3;
        c.gridwidth = 1;
        c.insets = new Insets(5, 5, 5, 5);  // Padding
        c.gridx = 0;

        c.gridy = 1;
        formularios.add(labelNome, c);
        c.gridy = 2;
        formularios.add(labelCategoria, c);
        c.gridy = 3;
        formularios.add(labelValor, c);
        c.gridy = 4;
        formularios.add(labelQuantidade, c);
        c.gridy = 5;
        formularios.add(labelId, c);
        c.gridy = 6;
        if (modo == Modos.ADICIONAR)
            formularios.add(labelFilial, c);


        // Ajuste de campos
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 0, 5, 5);
        c.weightx = 0.6;
        c.gridx = 1;

        c.gridy = 1;
        formularios.add(valorNome, c);
        c.gridy = 2;
        formularios.add(valorCategoria, c);
        c.gridy = 3;
        formularios.add(valorValor, c);
        c.gridy = 4;
        formularios.add(valorQuantidade, c);
        c.gridy = 5;
        formularios.add(valorId, c);
        c.gridy = 6;
        if (modo == Modos.ADICIONAR) formularios.add(filialJComboBox, c);
        

        // Botões
        botoes.setLayout(new FlowLayout());
        switch (modo) {
            case EDITAR -> {
                descricao.setText("Filial: " + filialdoItem.getNome());

                botoes.add(botaoAtualizar);
                botoes.add(botaoExcluir);

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

        janela.add(formularios, BorderLayout.NORTH);
        janela.add(botoes, BorderLayout.LINE_END);
        janela.setSize(400, 400);
        janela.setResizable(false);
        janela.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        switch (modo) {
            case EDITAR:
                if (src == botaoAtualizar) {
                    try {
                        controleEstoque.atualizarItem(
                                valorNome.getText(),
                                valorCategoria.getText(),
                                Double.parseDouble(valorValor.getText()),
                                Integer.parseInt(valorQuantidade.getText()),
                                Integer.parseInt(valorId.getText()),
                                itemEscolhido
                        );
                        janelaPesquisa.refresh();
                    } catch (NumberFormatException e1) {
                        mensagemErrodeFormatacao();
                    } catch (NullPointerException e2) {
                        mensagemErroFormularioVazio();
                    } catch (IdRepetidoException e3) {
                        mensagemErroIdrepetido(e3);
                    }
                } else if (src == botaoExcluir) {
                    controleEstoque.removerItem(itemEscolhido);
                    janelaPesquisa.refresh();
                    janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
                }
                break;
            case ADICIONAR:
                break;
        }
    }


    // --POP UPS--
    public void mensagemErroIdrepetido(IdRepetidoException e3) {
        JOptionPane.showMessageDialog(null,
                e3.getMessage(),
                "Erro de indentificação", JOptionPane.ERROR_MESSAGE);
    }

    public void mensagemErrodeFormatacao() {
        JOptionPane.showMessageDialog(null,
                "Erro de formatação: assegure-se que valores numéricos foram inseridos corretamente.",
                "Erro de formatação", JOptionPane.ERROR_MESSAGE);
    }

    public void mensagemErroFormularioVazio() {
        JOptionPane.showMessageDialog(null,
                "Erro de entrada: assegure-se que todos os formulários foram preenchidos.",
                "Erro de entrada", JOptionPane.ERROR_MESSAGE);
    }
}
