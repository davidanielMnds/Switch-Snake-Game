package br.jogo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import br.jogo.ui.SwitchPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Switch GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            SwitchPanel painel = new SwitchPanel();
            frame.add(painel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}