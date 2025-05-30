package org.calculator.controllers;

import org.calculator.models.dao.PersonDAO;
import org.calculator.models.Process;
import org.calculator.views.*;

public class Relations {

    public void establishRelationships() {
        MainWindow mainWindow = new MainWindow();
        RegisterWindow registerWindow = new RegisterWindow(mainWindow, true);
        ReadWindow readWindow = new ReadWindow(mainWindow, true);
        ShowListWindow showListWindow = new ShowListWindow(mainWindow, true);
        OperationsWindow operationsWindow = new OperationsWindow(mainWindow, true);

        Coordinator coordinator = new Coordinator();
        Process process = new Process();
        PersonDAO myPersonDAO = new PersonDAO();

        // Establecer coordinador en vistas
        mainWindow.setCoordinator(coordinator);
        registerWindow.setCoordinator(coordinator);
        readWindow.setCoordinator(coordinator);
        showListWindow.setCoordinator(coordinator);
        operationsWindow.setCoordinator(coordinator);

        // Establecer coordinador en lógica/modelo
        process.setCoordinator(coordinator);
        myPersonDAO.setCoordinator(coordinator);

        // Establecer vistas en el coordinador
        coordinator.setMainWindow(mainWindow);
        coordinator.setRegisterWindow(registerWindow);
        coordinator.setShowListWindow(showListWindow);
        coordinator.setReadWindow(readWindow);
        coordinator.setWindowOperations(operationsWindow);
        coordinator.setProcess(process);
        coordinator.setMyPersonDAO(myPersonDAO);

        coordinator.showMainWindow();
    }
}
