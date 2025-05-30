package org.calculator.controllers;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Aquí sí se establece todo correctamente
            new Relations().establishRelationships();
        });
    }
}
