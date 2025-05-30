package org.calculator.views;

import org.calculator.controllers.Coordinator;
import org.calculator.models.data.dto.PersonDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ShowListWindow extends JDialog {

    private Coordinator coordinator;
    private JPanel panelPersonList;

    public ShowListWindow(MainWindow parent, boolean modal) {
        super(parent, modal);
        setTitle("Lista de Personas Registradas");
        setSize(400, 450);
        setLocationRelativeTo(null);

        // Contenedor principal
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Título
        JLabel lblTitle = new JLabel("CONSULTA LISTA PERSONAS");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Panel de lista
        panelPersonList = new JPanel();
        panelPersonList.setLayout(new BoxLayout(panelPersonList, BoxLayout.Y_AXIS));
        panelPersonList.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(panelPersonList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    private void loadPersonList() {
        panelPersonList.removeAll();

        ArrayList<PersonDTO> people = (ArrayList<PersonDTO>) coordinator.consultListPersons();

        if (people == null || people.isEmpty()) {
            JLabel lblEmpty = new JLabel("No hay personas registradas.");
            lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblEmpty.setFont(new Font("Arial", Font.ITALIC, 14));
            panelPersonList.add(lblEmpty);
        } else {
            for (PersonDTO person : people) {
                String text = person.getName() + " (" + person.getDocument() + ")";
                JButton btnPerson = new JButton(text);
                btnPerson.setAlignmentX(Component.CENTER_ALIGNMENT);

                btnPerson.addActionListener(e -> {
                        coordinator.showOperationsWindow();
                        dispose(); // Cierra esta ventana
                });

                panelPersonList.add(btnPerson);
                panelPersonList.add(Box.createVerticalStrut(8));
            }
        }

        // Espaciador al final
        panelPersonList.add(Box.createVerticalGlue());

        panelPersonList.revalidate();
        panelPersonList.repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            loadPersonList();
        }
        super.setVisible(visible);
    }
}
