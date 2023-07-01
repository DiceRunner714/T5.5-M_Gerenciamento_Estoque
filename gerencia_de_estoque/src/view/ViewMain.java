package view;

import controle.ControleEmpresa;
import controle.ControleEstoqueFilial;
import controle.ElementoInexistenteException;
import controle.IdRepetidoException;

import javax.swing.*;

/**
 * Ponto de entrada da inicialização da view
 *
 * @author André Emanuel Bispo da Silva
 * @version 1.0
 * @since 2023
 */
public class ViewMain {

    /**
     * Método de entrada do programa
     *
     * @param args opções de linha de comando
     */
    public static void main(String[] args) {
        // Tentar colocar tema customizado
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro de tema: esse ambiente não tem o tema necessário instalado. usando tema padrão...",
                    "Erro de tema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Escolher nome da empresa
        String nome = JOptionPane.showInputDialog(
                null,
                "Escolha um nome para a empresa:",
                "Escolha de nome",
                JOptionPane.INFORMATION_MESSAGE);

        // Decidir se está em modo de dados aleatórios
        if (args.length > 0) {
            if (args[0].equals("--dados-aleatorios")) {
                new EmpresaView(criarEmpresaDadosAleatorios(nome));
            }
        } else {
            new EmpresaView(nome);
        }
    }

    /**
     * Cria um controle empresa com dados pré-existentes
     *
     * @param nomeEmpresa nome da empresa a ser criada
     * @return um ControleEmpresa com dados inseridos
     */
    private static ControleEmpresa criarEmpresaDadosAleatorios(String nomeEmpresa) {
        ControleEmpresa controleEmpresa = new ControleEmpresa(nomeEmpresa);

        // Adicionar filiais
        try {
            controleEmpresa.adicionarFilial("ACME inc.", "Brazil", 1);
            controleEmpresa.adicionarFilial("Xilinx .co.", "USA", 2);
            controleEmpresa.adicionarFilial("Amazon .exe", "USA", 3);
            controleEmpresa.adicionarFilial("Xiang .co", "Xina", 4);
            controleEmpresa.adicionarFilial("Jacinto Comidas .inc", "UnB-FGA", 5);
        } catch (IdRepetidoException e) {
            e.printStackTrace();
        }


        ControleEstoqueFilial controleACME =
                new ControleEstoqueFilial(controleEmpresa, controleEmpresa.buscarFilial(1));
        ControleEstoqueFilial controleXilinx =
                new ControleEstoqueFilial(controleEmpresa, controleEmpresa.buscarFilial(2));
        // Adicionar itens aos estoques
        try {
            controleACME.adicionarFarmaceutico(
                    "SchizoMeds",
                    "mental-health",
                    69.99,
                    99,
                    1,
                    "preta",
                    "segredo",
                    true,
                    true,
                    true);
            controleACME.adicionarProdutoQuimico(
                    "Uranio enriquecido",
                    "Minério",
                    9999.9999,
                    0,
                    2,
                    "Radioativo",
                    0,
                    0,
                    5);

            controleXilinx.adicionarFarmaceutico(
                    "Benadryl",
                    "antihistamines",
                    12.45,
                    0,
                    3,
                    "nenhuma",
                    "elixir anti alergia",
                    false,
                    false,
                    false);
            controleXilinx.adicionarFarmaceutico(
                    "Aspirin",
                    "analgesic",
                    1.99,
                    2,
                    4,
                    "amarela",
                    "remedio",
                    false,
                    false,
                    true);
            controleACME.adicionarProdutoQuimico(
                    "Red phosphor",
                    "light emitting mineral",
                    50.00,
                    5999,
                    5,
                    "nenhum",
                    0,
                    0,
                    0);
        } catch (IdRepetidoException | ElementoInexistenteException e) {
            e.printStackTrace();
        }

        return controleEmpresa;
    }

}
