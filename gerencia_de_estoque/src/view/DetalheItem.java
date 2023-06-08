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
    private JLabel descricao = new JLabel("Informações do Item");
    private JLabel labelNome = new JLabel("Nome: ");
    private JTextField valorNome = new JTextField();
    private JLabel labelId = new JLabel("ID: ");
    private JTextField valorId = new JTextField();
    private JLabel labelCategoria = new JLabel("Categoria: ");
    private JTextField valorCategoria = new JTextField();
    private JLabel labelQuantidade = new JLabel("Quantidade: ");
    private JTextField valorQuantidade = new JTextField();
    private JLabel labelValor = new JLabel("Valor (R$): ");
    private JTextField valorValor = new JTextField();
    private JButton botaoAtualizar;
    private JButton botaoExcluir;
    private JLabel labelFilial = new JLabel("Filial: ");
    private JComboBox<Filial> filialJComboBox;
    private CategoriasItens tipoDeItem;
    private Modos modo;
    private JanelaPesquisa janelaPesquisa;
    private Filial filialdoItem;
    private Item itemEscolhido;
    private ControleEstoque controleEstoque;

    // Construtor vazio, adicionar item
    // TODO: como adicionar item se não foi selecionada uma filial?

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {
        modo = Modos.ADICIONAR;
        DetalheItem.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;

        criarElementosBasicos();

        labelFilial.setBounds(10, 220, 80, 30);
        ArrayList<Filial> filiaisDisponiveis = controleEmpresa.getFiliais();
        filialJComboBox = new JComboBox<>(
                filiaisDisponiveis.toArray(new Filial[filiaisDisponiveis.size()])
        );
        filialJComboBox.setBounds(120, 220, 80, 80);

        botaoExcluir = new JButton("Cancelar");
        botaoExcluir.setBounds(220, 300, 80, 30);

        botaoAtualizar = new JButton("Adicionar");
        botaoAtualizar.setBounds(70, 300, 100, 30);

        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);
        janela.add(filialJComboBox);
        janela.add(labelFilial);

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    // Construtor não vazio, item escolhido para modificar
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item itemEscolhido) {
        this.modo = Modos.EDITAR;
        this.janelaPesquisa = janelaPesquisa;
        this.itemEscolhido = itemEscolhido;
        DetalheItem.controleEmpresa = controleEmpresa;
        filialdoItem = controleEmpresa.buscarFilialaPartirdeItem(itemEscolhido);
        controleEstoque = new ControleEstoque(controleEmpresa, filialdoItem);

        descricao.setText("Filial: " + filialdoItem.getNome());

        Farmaceutico farmaceutico;
        ProdutoQuimico produtoQuimico;

        criarElementosBasicos();

        botaoExcluir = new JButton("Excluir");
        botaoExcluir.setBounds(220, 300, 80, 30);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(70, 300, 100, 30);

        valorNome.setText(itemEscolhido.getNome());
        valorCategoria.setText(itemEscolhido.getCategoria());
        valorValor.setText(String.valueOf(itemEscolhido.getValor()));
        valorId.setText(String.valueOf(itemEscolhido.getId()));
        valorQuantidade.setText(String.valueOf(itemEscolhido.getQuantidade()));

        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);

        botaoAtualizar.addActionListener(this);
        botaoExcluir.addActionListener(this);

        if (itemEscolhido instanceof Farmaceutico) {
            // Adicionar campos de farmaceutico
            farmaceutico = (Farmaceutico) itemEscolhido;
            tipoDeItem = CategoriasItens.FARMACEUTICO;
        } else {
            // Adicionar campos de produtoquimico
            produtoQuimico = (ProdutoQuimico) itemEscolhido;
            tipoDeItem = CategoriasItens.PRODUTO_QUIMICO;
        }

        janela.setSize(400, 400);
        janela.setVisible(true);

    }

    public void criarElementosBasicos() {
        descricao.setBounds(90, 10, 200, 30);
        descricao.setFont(new Font("Arial", Font.BOLD, 20));
        labelNome.setBounds(10, 70, 50, 20);
        valorNome.setBounds(120, 70, 150, 18);
        labelId.setBounds(10, 100, 50, 20);
        valorId.setBounds(120, 100, 50, 18);
        labelCategoria.setBounds(10, 130, 80, 20);
        valorCategoria.setBounds(120, 130, 150, 18);
        labelQuantidade.setBounds(10, 160, 80, 20);
        valorQuantidade.setBounds(120, 160, 50, 18);
        labelValor.setBounds(10, 190, 70, 20);
        valorValor.setBounds(120, 190, 50, 18);

        janela.setLayout(null);
        janela.add(descricao);
        janela.add(labelNome);
        janela.add(valorNome);
        janela.add(labelCategoria);
        janela.add(valorCategoria);
        janela.add(labelId);
        janela.add(valorId);
        janela.add(labelQuantidade);
        janela.add(valorQuantidade);
        janela.add(labelValor);
        janela.add(valorValor);

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
