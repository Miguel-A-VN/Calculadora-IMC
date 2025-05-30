package org.calculator.models;

import org.calculator.controllers.Coordinator;

public class Process {

    private Coordinator coordinator;

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Devuelve un mensaje de bienvenida con el nombre en mayúsculas.
     * @param nombre nombre de la persona
     * @return mensaje personalizado
     */
    public String getData(String nombre) {
        return "Bienvenido " + nombre.toUpperCase();
    }

    /**
     * Valida que el nombre no contenga solo números ni esté vacío.
     * No acepta nombres con dígitos en ninguna posición.
     * @param value texto a validar
     * @return true si es válido, false si no
     */
    public boolean nameValidator(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        // No acepta si contiene dígitos
        return !value.matches(".*\\d.*");
    }

    /**
     * Valida que el texto sea un número positivo o cero.
     * @param value texto a validar
     * @return true si es un número >= 0, false si no
     */
    public boolean numValidation(String value) {
        try {
            double num = Double.parseDouble(value);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calcula el IMC y devuelve un mensaje con el resultado y clasificación.
     * @param weight peso en kilogramos
     * @param stature altura en metros
     * @return cadena con resultado o mensaje de error si datos inválidos
     */
    public String imcCalc(double weight, double stature) {
        if (weight <= 0) {
            return "Peso inválido";
        }
        if (stature <= 0) {
            return "Talla inválida";
        }
        double imc = weight / (stature * stature);
        String qualification;
        if (imc < 18.5) {
            qualification = "Bajo peso";
        } else if (imc < 25) {
            qualification = "Normal";
        } else if (imc < 30) {
            qualification = "Sobrepeso";
        } else {
            qualification = "Obesidad";
        }
        return String.format("IMC: %.2f - %s", imc, qualification);
    }
}
