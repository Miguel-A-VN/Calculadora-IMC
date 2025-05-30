package org.calculator.views;

import org.calculator.controllers.Coordinator;
import org.calculator.models.data.dto.PersonDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadWindow extends JDialog implements ActionListener {

    private Coordinator coordinator;

    private JTextField txtDocument, txtName, txtAge;
    private JButton btnQuery, btnUpdate, btnDelete;

    public ReadWindow(MainWindow ventanaPrincipal, boolean modal) {
        super(ventanaPrincipal, modal);
        setTitle("Consulta de Persona");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("CONSULTA DE USUARIOS");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(0, 10, 400, 30);
        add(lblTitle);

        JLabel lblDocument = new JLabel("Documento:");
        lblDocument.setBounds(30, 50, 100, 25);
        add(lblDocument);

        txtDocument = new JTextField();
        txtDocument.setBounds(130, 50, 150, 25);
        add(txtDocument);

        btnQuery = new JButton("Consultar");
        btnQuery.setBounds(290, 50, 90, 25);
        btnQuery.addActionListener(this);
        add(btnQuery);

        JLabel lblName = new JLabel("Nombre:");
        lblName.setBounds(30, 90, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 90, 200, 25);
        add(txtName);

        JLabel lblAge = new JLabel("Edad:");
        lblAge.setBounds(30, 130, 100, 25);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(130, 130, 60, 25);
        add(txtAge);

        btnUpdate = new JButton("Actualizar");
        btnUpdate.setBounds(40, 190, 120, 30);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnDelete = new JButton("Eliminar");
        btnDelete.setBounds(220, 190, 120, 30);
        btnDelete.addActionListener(this);
        add(btnDelete);
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    private void clearForm() {
        txtDocument.setText("");
        txtName.setText("");
        txtAge.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnQuery) {
            consultPerson();
        } else if (source == btnUpdate) {
            updatePerson();
        } else if (source == btnDelete) {
            deletePerson();
        }
    }

    private void consultPerson() {
        String doc = txtDocument.getText().trim();

        if (doc.isEmpty()) {
            showMessage("Ingrese un documento para consultar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PersonDTO person = coordinator.consultPersonDocument(doc);

        if (person != null) {
            txtName.setText(person.getName());
            txtAge.setText(String.valueOf(person.getAge()));
        } else {
            showMessage("No se encontró la persona.", "No registrado", JOptionPane.ERROR_MESSAGE);
            clearForm();
        }
    }

    private void updatePerson() {
        String doc = txtDocument.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();

        if (doc.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
            showMessage("Todos los campos son obligatorios para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PersonDTO person = coordinator.consultPersonDocument(doc);
        if (person != null) {
            try {
                int age = Integer.parseInt(ageStr);
                person.setName(name);
                person.setAge(age);

                String result = coordinator.personUpdate(person);
                if ("ok".equalsIgnoreCase(result)) {
                    showMessage("Se actualizó exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showMessage("No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                showMessage("Edad inválida. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showMessage("No se encontró a la persona para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePerson() {
        String doc = txtDocument.getText().trim();

        if (doc.isEmpty()) {
            showMessage("Ingrese el documento para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta persona?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String result = coordinator.deletePerson(doc);
            if ("ok".equalsIgnoreCase(result)) {
                showMessage("Se eliminó exitosamente.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                showMessage("No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
}
