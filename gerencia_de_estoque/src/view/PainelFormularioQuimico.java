package view;

import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;
import modelo.NivelRestricaoInadequadoException;
import modelo.ProdutoQuimico;

import javax.swing.*;
import java.util.Arrays;

/**
 * Painel formulário com os campos de ProdutoQuimico, herda da classe PainelFormulario
 *
 * @author André Emanuel Bipo da Silva
 * @version 1.0
 * @see PainelFormulario
 * @since 2023
 */
public class PainelFormularioQuimico extends PainelFormulario {
    private final JComboBox<Integer> opcoesPerigoaSaude = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesRiscoDeFogo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JComboBox<Integer> opcoesReatividade = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JTextField valorPerigoEspecifico = new JTextField();

    /**
     * Cria um novo Painel com os atributos de produto químico
     */
    public PainelFormularioQuimico() {
        criarFormulario(
                Arrays.asList(
                        new JLabel("Risco a saúde: "),
                        new JLabel("Risco de fogo: "),
                        new JLabel("Reatividade: "),
                        new JLabel("Perigo especifico: ")),
                Arrays.asList(
                        opcoesPerigoaSaude,
                        opcoesRiscoDeFogo,
                        opcoesReatividade,
                        valorPerigoEspecifico),
                "Detalhes - Produto químico");
    }

    /**
     * Atualiza as informações de um produto químico no estoque
     *
     * @param controleEstoqueFilial controle do estoque da filial escolhida
     * @param itemEscolhido         produto químico a ser atualizado
     * @throws ElementoInexistenteException gera uma exceção caso produto químico escolhido não exista
     */
    @Deprecated
    public void atualizarProdutoQuimico(ControleEstoqueFilial controleEstoqueFilial, ProdutoQuimico itemEscolhido) throws ElementoInexistenteException {
        controleEstoqueFilial.atualizarProdutoQuimico(
                valorPerigoEspecifico.getText(),
                (int) opcoesRiscoDeFogo.getSelectedItem(),
                (int) opcoesReatividade.getSelectedItem(),
                (int) opcoesPerigoaSaude.getSelectedItem(),
                itemEscolhido
        );
    }

    /**
     * Atualiza todos os campos de um produto químico
     *
     * @param painelFormularioItem  painel com os formulários das características básicas
     * @param controleEstoqueFilial controlador do estoque da filial escolhida
     * @param itemEscolhido         produto químico a ser atualizado
     * @throws ElementoInexistenteException quando o produto químico não constar no estoque
     */
    public void atualizarProdutoQuimico(PainelFormularioItem painelFormularioItem, ControleEstoqueFilial controleEstoqueFilial, ProdutoQuimico itemEscolhido) throws ElementoInexistenteException, IdRepetidoException, NivelRestricaoInadequadoException {
        boolean podeRestringir = itemEscolhido.checarPodeRestringir(
                (int) opcoesPerigoaSaude.getSelectedItem(),
                (int) opcoesRiscoDeFogo.getSelectedItem(),
                (int) opcoesReatividade.getSelectedItem());

        boolean podeLiberar = itemEscolhido.checarPodeLiberar(
                (int) opcoesPerigoaSaude.getSelectedItem(),
                (int) opcoesRiscoDeFogo.getSelectedItem(),
                (int) opcoesReatividade.getSelectedItem());

        boolean restritoEscolhido = painelFormularioItem.getIsRestrito().isSelected();

        if ((restritoEscolhido && !podeRestringir) || (!restritoEscolhido && podeLiberar)) {
            throw new NivelRestricaoInadequadoException(
                    "Erro de restrição: O nível de risco escolhido não é adequado"
            );
        }

        controleEstoqueFilial.atualizarCaracteristicasBasicas(
                painelFormularioItem.getValorNome().getText(),
                painelFormularioItem.getValorCategoria().getText(),
                Double.parseDouble(painelFormularioItem.getValorValor().getText()),
                Integer.parseInt(painelFormularioItem.getValorQuantidade().getText()),
                Integer.parseInt(painelFormularioItem.getValorId().getText()),
                itemEscolhido
        );
        controleEstoqueFilial.atualizarProdutoQuimico(
                valorPerigoEspecifico.getText(),
                (int) opcoesRiscoDeFogo.getSelectedItem(),
                (int) opcoesReatividade.getSelectedItem(),
                (int) opcoesPerigoaSaude.getSelectedItem(),
                itemEscolhido
        );

        if (restritoEscolhido) {
            itemEscolhido.restringir();
        } else {
            itemEscolhido.liberar();
        }


    }

    /**
     * Preenche os campos do formulário com as informações de um produto químico específico
     *
     * @param itemEscolhido produto químico escolhido
     */
    public void popularFormularios(ProdutoQuimico itemEscolhido) {
        valorPerigoEspecifico.setText((itemEscolhido).getPerigoEspecifico());
        opcoesPerigoaSaude.setSelectedItem((itemEscolhido).getPerigoaSaude());
        opcoesRiscoDeFogo.setSelectedItem((itemEscolhido).getRiscoDeFogo());
        opcoesReatividade.setSelectedItem((itemEscolhido).getReatividade());
    }

    /**
     * Adiciona um novo produto químico ao estoque
     *
     * @param painelFormularioItem  painel do item
     * @param controleEstoqueFilial controle do estoque da filial escolhida
     * @throws IdRepetidoException          gera uma exceção caso id do novo item seja igual a um existente
     * @throws ElementoInexistenteException gera uma exceção caso produto químico escolhido não exista
     */
    public void adicionarProdutoQuimico(PainelFormularioItem painelFormularioItem, ControleEstoqueFilial controleEstoqueFilial) throws IdRepetidoException, ElementoInexistenteException {
        controleEstoqueFilial.adicionarProdutoQuimico(
                painelFormularioItem.getValorNome().getText(),
                painelFormularioItem.getValorCategoria().getText(),
                Double.parseDouble(painelFormularioItem.getValorValor().getText()),
                Integer.parseInt(painelFormularioItem.getValorQuantidade().getText()),
                Integer.parseInt(painelFormularioItem.getValorId().getText()),
                valorPerigoEspecifico.getText(),
                (Integer) opcoesRiscoDeFogo.getSelectedItem(),
                (Integer) opcoesReatividade.getSelectedItem(),
                (Integer) opcoesPerigoaSaude.getSelectedItem()
        );
    }
}
