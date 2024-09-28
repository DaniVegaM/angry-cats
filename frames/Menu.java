package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import frames.*;

public class Menu extends JFrame {

    private JButton Level1, Level2, Level3;

    public Menu() {

        setTitle("Menú");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new JLabel(new ImageIcon("./resources/mainBackground.png")));
        setLayout(new BorderLayout());

        // Crear el botón
        Image btn = Toolkit.getDefaultToolkit().getImage("./resources/Boton1.png");
        btn = btn.getScaledInstance(100, 100, 20);

// Crear el nuevo ImageIcon con la imagen redimensionada
        ImageIcon resizedIcon1 = new ImageIcon(btn);
        Level1 = new JButton(resizedIcon1);
        Level1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Level1.setContentAreaFilled(false);
        Level1.setBorderPainted(false);
        Level1.setFocusPainted(false);

        // Crear el botón
        // Crear el botón
// Redimensionar la imagen
        btn = Toolkit.getDefaultToolkit().getImage("./resources/Boton2_.png");
        btn = btn.getScaledInstance(150, 150, 20);

// Crear el nuevo ImageIcon con la imagen redimensionada
        resizedIcon1 = new ImageIcon(btn);
        Level2 = new JButton(resizedIcon1);
        Level2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Level2.setContentAreaFilled(false);
        Level2.setBorderPainted(false);
        Level2.setFocusPainted(false);

        // Crear el botón
        // Crear el botón
// Redimensionar la imagen
        btn = Toolkit.getDefaultToolkit().getImage("./resources/Boton3.png");
        btn = btn.getScaledInstance(100, 100, 20);

// Crear el nuevo ImageIcon con la imagen redimensionada
        resizedIcon1 = new ImageIcon(btn);
        Level3 = new JButton(resizedIcon1);
        Level3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Level3.setContentAreaFilled(false);
        Level3.setBorderPainted(false);
        Level3.setFocusPainted(false);
        // Agregar el listener de clic al botón
        Level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el JFrame actual
                //setDefaultCloseOperation(Menu.DISPOSE_ON_CLOSE);

                Level1 n1 = new Level1();
                JFrame frame = new JFrame("AngryCats");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(n1);

                // Establecer level1 como el GatoPositionListener de gato
                n1.setGatoPositionListener(n1.gato);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                dispose();
            }
        });

        Level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el JFrame actual
                //setDefaultCloseOperation(Menu.DISPOSE_ON_CLOSE);

                Level2 n1 = new Level2();
                JFrame frame = new JFrame("AngryCats");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(n1);

                // Establecer level1 como el GatoPositionListener de gato
                n1.setGatoPositionListener(n1.gato);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                dispose();
            }
        });

        Level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el JFrame actual
                //setDefaultCloseOperation(Menu.DISPOSE_ON_CLOSE);

                Level3 n1 = new Level3();
                JFrame frame = new JFrame("AngryCats");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(n1);

                // Establecer level1 como el GatoPositionListener de gato
                n1.setGatoPositionListener(n1.gato);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                dispose();
            }
        });

        // Centrar el botón en el JFrame
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(Level1);
        buttonPanel.add(Level2);
        buttonPanel.add(Level3);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
}
