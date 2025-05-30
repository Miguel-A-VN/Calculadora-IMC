package org.calculator.views;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.calculator.controllers.Coordinator;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OperationsWindow extends JDialog {

    private JTextField txtWeight, txtStature;
    private JLabel lblResult;
    private Coordinator coordinator;

    private final JFXPanel fxPanel = new JFXPanel();
    private MediaPlayer currentMediaPlayer;

    public OperationsWindow(MainWindow parent, boolean modal) {
        super(parent, modal);
        setTitle("Cálculo de IMC");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel izquierdo: formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setPreferredSize(new Dimension(340, 300));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblWeight = new JLabel("Peso (kg):");
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblWeight, gbc);

        txtWeight = new JTextField();
        gbc.gridy = 1;
        panelForm.add(txtWeight, gbc);

        JLabel lblStature = new JLabel("Talla (m):");
        gbc.gridy = 2;
        panelForm.add(lblStature, gbc);

        txtStature = new JTextField();
        gbc.gridy = 3;
        panelForm.add(txtStature, gbc);

        JButton btnCalculate = new JButton("Calcular IMC");
        gbc.gridy = 4;
        panelForm.add(btnCalculate, gbc);

        lblResult = new JLabel("Resultado: ");
        lblResult.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        panelForm.add(lblResult, gbc);

        add(panelForm, BorderLayout.WEST);
        add(fxPanel, BorderLayout.CENTER);

        // Botón de cerrar
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Cerrar");
        panelBottom.add(btnClose);
        add(panelBottom, BorderLayout.SOUTH);

        // Eventos
        btnCalculate.addActionListener(e -> calcularIMC());
        btnClose.addActionListener(e -> {
            detenerVideo();
            dispose();
        });

        // Limpieza de medios al cerrar con la X
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                detenerVideo();
                Platform.runLater(() -> fxPanel.setScene(null));
            }
        });
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    private void calcularIMC() {
        String weightStr = txtWeight.getText().trim();
        String statureStr = txtStature.getText().trim();

        if (weightStr.isEmpty() || statureStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa peso y talla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!coordinator.numValidator(weightStr) || !coordinator.numValidator(statureStr)) {
            JOptionPane.showMessageDialog(this, "Ingresa valores numéricos válidos y positivos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double peso = Double.parseDouble(weightStr);
        double estatura = Double.parseDouble(statureStr);
        String resultado = coordinator.imcCalc(peso, estatura);

        lblResult.setText("Resultado: " + resultado);

        if (resultado.contains("Bajo peso")) {
            reproducirVideo("src/main/resources/videos/002.mp4");
        } else if (resultado.contains("Obesidad")) {
            reproducirVideo("src/main/resources/videos/001.mp4");
        } else {
            detenerVideo();
        }
    }

    private void reproducirVideo(String rutaVideo) {
        detenerVideo();

        Platform.runLater(() -> {
            try {
                Media media = new Media(new File(rutaVideo).toURI().toString());
                currentMediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(currentMediaPlayer);

                mediaView.setFitWidth(fxPanel.getWidth());
                mediaView.setFitHeight(fxPanel.getHeight());
                mediaView.setPreserveRatio(true);

                Group root = new Group(mediaView);
                Scene scene = new Scene(root, fxPanel.getWidth(), fxPanel.getHeight());
                fxPanel.setScene(scene);

                currentMediaPlayer.play();

                currentMediaPlayer.setOnEndOfMedia(() -> {
                    currentMediaPlayer.stop();
                    currentMediaPlayer.dispose();
                    currentMediaPlayer = null;
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void detenerVideo() {
        if (currentMediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    currentMediaPlayer.stop();
                    currentMediaPlayer.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentMediaPlayer = null;
            });
        }
    }
}
