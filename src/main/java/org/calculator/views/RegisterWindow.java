package org.calculator.views;

import org.calculator.controllers.Coordinator;
import org.calculator.models.data.dto.PersonDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JDialog implements ActionListener {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    private Coordinator coordinator;

    private JTextField txtName, txtDocument, txtAge;
    private JLabel lblResult;

    private JButton btnRegister;

    public RegisterWindow(MainWindow mainWindow, boolean modal) {
        super(mainWindow, modal);
        setTitle("Registro de Persona");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("REGISTRAR USUARIO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 10, WIDTH, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Nombre:");
        lblName.setBounds(50, 50, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 50, 200, 25);
        add(txtName);

        JLabel lblDocument = new JLabel("Documento:");
        lblDocument.setBounds(50, 90, 100, 25);
        add(lblDocument);

        txtDocument = new JTextField();
        txtDocument.setBounds(150, 90, 200, 25);
        add(txtDocument);

        JLabel lblAge = new JLabel("Edad:");
        lblAge.setBounds(50, 130, 100, 25);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(150, 130, 60, 25);
        add(txtAge);

        btnRegister = new JButton("Registrar");
        btnRegister.setBounds(200, 180, 100, 30);
        btnRegister.addActionListener(this);
        add(btnRegister);

        lblResult = new JLabel("");
        lblResult.setBounds(50, 220, 400, 25);
        lblResult.setForeground(Color.RED);
        add(lblResult);
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            handleRegistration();
        }
    }

    private void handleRegistration() {
        boolean validName = coordinator.textValidatorData(txtName.getText());
        boolean validDoc = coordinator.numValidator(txtDocument.getText());
        boolean validAge = coordinator.numValidator(txtAge.getText());

        highlightField(txtName, validName);
        highlightField(txtDocument, validDoc);
        highlightField(txtAge, validAge);

        if (validName && validDoc && validAge) {
            String calcResult = coordinator.dataCalc(txtName.getText());

            if (registerPerson()) {
                JOptionPane.showMessageDialog(this,
                        "Registro exitoso. " + calcResult,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                coordinator.showMainWindow();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar la persona.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            lblResult.setText("Valide todos los campos antes de continuar.");
        }
    }

    private boolean registerPerson() {
        PersonDTO person = new PersonDTO();
        person.setName(txtName.getText());
        person.setDocument(txtDocument.getText());
        person.setAge(Integer.parseInt(txtAge.getText()));

        String result = coordinator.personRegister(person);
        return "si".equalsIgnoreCase(result);
    }

    public void clearForm() {
        txtName.setText("");
        txtDocument.setText("");
        txtAge.setText("");
        lblResult.setText("");
        resetFieldColors();
    }

    private void highlightField(JTextField field, boolean isValid) {
        field.setBackground(isValid ? Color.WHITE : new Color(255, 204, 204));
    }

    private void resetFieldColors() {
        txtName.setBackground(Color.WHITE);
        txtDocument.setBackground(Color.WHITE);
        txtAge.setBackground(Color.WHITE);
    }
}
