package org.calculator.controllers;

import org.calculator.views.*;
import org.calculator.models.dao.PersonDAO;
import org.calculator.models.data.dto.PersonDTO;
import org.calculator.models.Process;

import java.util.List;

public class Coordinator {
    private MainWindow mainWindow;
    private RegisterWindow registerWindow;
    private ReadWindow readWindow;
    private ShowListWindow showListWindow;
    private OperationsWindow operationsWindow;

    private Process process;
    private PersonDAO myPersonDAO;

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setRegisterWindow(RegisterWindow registerWindow) {
        this.registerWindow = registerWindow;
    }

    public void setReadWindow(ReadWindow readWindow) {
        this.readWindow = readWindow;
    }

    public void setShowListWindow(ShowListWindow showListWindow) {
        this.showListWindow = showListWindow;
    }

    public void setWindowOperations(OperationsWindow operationsWindow) {
        this.operationsWindow = operationsWindow;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setMyPersonDAO(PersonDAO myPersonDAO) {
        this.myPersonDAO = myPersonDAO;
    }

    /**
     * Valida que el valor sea numérico positivo (incluye 0).
     * @param value texto a validar
     * @return true si es válido, false si no o si el proceso no está inicializado
     */
    public boolean numValidator(String value) {
        if (process == null) {
            System.err.println("Error: process no está inicializado");
            return false;
        }
        return process.numValidation(value);
    }

    /**
     * Valida que el texto no sea numérico y no esté vacío.
     * @param value texto a validar
     * @return true si es válido, false si no o si el proceso no está inicializado
     */
    public boolean textValidatorData(String value) {
        if (process == null) {
            System.err.println("Error: process no está inicializado");
            return false;
        }
        return process.nameValidator(value);
    }

    /**
     * Calcula el IMC llamando al proceso.
     * @param peso peso en kg
     * @param estatura altura en metros
     * @return resultado del cálculo o mensaje de error
     */
    public String imcCalc(double peso, double estatura) {
        if (process == null) {
            return "Error: Proceso no inicializado";
        }
        return process.imcCalc(peso, estatura);
    }

    /**
     * Obtiene datos personalizados desde el proceso.
     * @param name nombre para saludo
     * @return mensaje personalizado
     */
    public String dataCalc(String name) {
        if (process == null) {
            return "";
        }
        return process.getData(name);
    }

    /**
     * Registra una persona en el DAO y actualiza el botón en la ventana principal.
     * @param person DTO con datos de la persona
     * @return mensaje resultado de la operación
     */
    public String personRegister(PersonDTO person) {
        if (myPersonDAO == null) {
            System.err.println("Error: myPersonDAO no está inicializado");
            return "Error: base de datos no disponible";
        }
        String result = myPersonDAO.personRegister(person);
        if (mainWindow != null) {
            mainWindow.btnUpdateList(); // actualiza visibilidad del botón
        }
        return result;
    }

    /**
     * Consulta una persona por documento.
     * @param document documento de la persona
     * @return DTO de la persona o null si no existe
     */
    public PersonDTO consultPersonDocument(String document) {
        if (myPersonDAO == null) {
            System.err.println("Error: myPersonDAO no está inicializado");
            return null;
        }
        return myPersonDAO.consultPersonDocument(document);
    }

    /**
     * Actualiza datos de persona en el DAO.
     * @param person DTO con datos actualizados
     * @return mensaje resultado de la operación
     */
    public String personUpdate(PersonDTO person) {
        if (myPersonDAO == null) {
            System.err.println("Error: myPersonDAO no está inicializado");
            return "Error: base de datos no disponible";
        }
        return myPersonDAO.personUpdate(person);
    }

    /**
     * Elimina una persona por documento.
     * @param document documento de la persona
     * @return mensaje resultado de la operación
     */
    public String deletePerson(String document) {
        if (myPersonDAO == null) {
            System.err.println("Error: myPersonDAO no está inicializado");
            return "Error: base de datos no disponible";
        }
        return myPersonDAO.personDelete(document);
    }

    /**
     * Consulta la lista completa de personas.
     * @return lista de DTOs o lista vacía si DAO no está inicializado
     */
    public List<PersonDTO> consultListPersons() {
        if (myPersonDAO == null) {
            System.err.println("Error: myPersonDAO no está inicializado");
            return List.of();
        }
        return myPersonDAO.consultListPersons();
    }

    // Método privado para mostrar ventanas evitando código repetido
    private void showWindow(java.awt.Window window, String windowName) {
        if (window != null) {
            window.setVisible(true);
        } else {
            System.err.println("Error: " + windowName + " no ha sido inicializada.");
        }
    }

    public void showMainWindow() {
        showWindow(mainWindow, "ventanaPrincipal");
        if (mainWindow != null) {
            mainWindow.btnUpdateList();
        }
    }

    public void showRegisterWindow() {
        showWindow(registerWindow, "ventanaRegistro");
    }

    public void showReadWindow() {
        showWindow(readWindow, "ventanaConsultaIndividual");
    }

    public void showShowListWindow() {
        showWindow(showListWindow, "ventanaConsultarLista");
    }

    public void showOperationsWindow() {
        showWindow(operationsWindow, "ventanaOperaciones");
    }
}
