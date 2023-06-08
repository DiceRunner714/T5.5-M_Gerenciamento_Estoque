package view;

import controle.ControleEmpresa;
import modelo.Farmaceutico;
import modelo.Item;
import modelo.ProdutoQuimico;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DetalheItem implements ActionListener {
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
    private CategoriasItens tipoDeItem;
    private Modos modo;
    private ControleEmpresa controleEmpresa;
    private JanelaPesquisa janelaPesquisa;
    private Item itemEscolhido;

    // Construtor vazio, adicionar item
    // TODO: como adicionar item se não foi selecionada uma filial?

    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa) {

        this.controleEmpresa = controleEmpresa;
        this.janelaPesquisa = janelaPesquisa;

        criarElementosBasicos();

        botaoExcluir = new JButton("Cancelar");
        botaoExcluir.setBounds(220, 300, 80, 30);

        botaoAtualizar = new JButton("Adicionar");
        botaoAtualizar.setBounds(70, 300, 100, 30);

        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);

        janela.setSize(400, 400);
        janela.setVisible(true);
    }

    // Construtor n�o vazio, item escolhido para modificar
    // modificar depois para utilizar apenas classes controle
    public DetalheItem(ControleEmpresa controleEmpresa, JanelaPesquisa janelaPesquisa, Item item) {
        this.modo = Modos.EDITAR;
        Farmaceutico farmaceutico;
        ProdutoQuimico produtoQuimico;

        criarElementosBasicos();

        botaoExcluir = new JButton("Excluir");
        botaoExcluir.setBounds(220, 300, 80, 30);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(70, 300, 100, 30);

        valorNome.setText(item.getNome());
        valorCategoria.setText(item.getCategoria());
        valorValor.setText(String.valueOf(item.getValor()));
        valorId.setText(String.valueOf(item.getId()));
        valorQuantidade.setText(String.valueOf(item.getQuantidade()));

        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);

        if (item instanceof Farmaceutico) {
            // Adicionar campos de farmaceutico
            farmaceutico = (Farmaceutico) item;
            tipoDeItem = CategoriasItens.FARMACEUTICO;
        } else {
            // Adicionar campos de produtoquimico
            produtoQuimico = (ProdutoQuimico) item;
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

                }
        }
    }
}
