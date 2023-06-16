package view;

import javax.swing.*;

public class ViewMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro de tema: esse ambiente não tem o tema necessário instalado. usando tema padrão...",
                    "Erro de tema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        String nome = JOptionPane.showInputDialog(
                null,
                "Escolha um nome para a empresa:",
                "Escolha de nome",
                JOptionPane.INFORMATION_MESSAGE);

        new EmpresaView(nome);

    }

}
