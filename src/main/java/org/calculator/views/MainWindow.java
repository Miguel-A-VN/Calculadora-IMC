package org.calculator.views;

import org.calculator.controllers.Coordinator;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private Coordinator coordinator;
    private JButton btnShowList;

    public MainWindow() {
        setTitle("Calculadora de IMC");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Título principal
        JLabel tituloLabel = new JLabel("ÍNDICE DE MASA CORPORAL (IMC)");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón registrar
        JButton registrarBtn = new JButton("Registrar");
        registrarBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        registrarBtn.addActionListener(e -> {
            if (coordinator != null) {
                coordinator.showRegisterWindow();
            }
        });

        // Botón mostrar lista (oculto inicialmente)
        btnShowList = new JButton("Mostrar lista de personas");
        btnShowList.setFont(new Font("Arial", Font.PLAIN, 16));
        btnShowList.setVisible(false);
        btnShowList.addActionListener(e -> {
            if (coordinator != null) {
                coordinator.showShowListWindow();
            }
        });

        // Panel con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título (fila 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(tituloLabel, gbc);

        // Botón Registrar (fila 1)
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(registrarBtn, gbc);

        // Botón Mostrar Lista (fila 1, columna 1)
        gbc.gridx = 1;
        panel.add(btnShowList, gbc);

        add(panel);
    }

    /**
     * Muestra el botón de lista solo si hay personas registradas.
     */
    public void btnUpdateList() {
        if (coordinator != null && !coordinator.consultListPersons().isEmpty()) {
            btnShowList.setVisible(true);
        }
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}
