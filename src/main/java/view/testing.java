
package view;


import controller.ResourceController;
import javax.swing.*;
import java.awt.*;

public class testing extends JDialog {

    private ResourceController resourceController = new ResourceController();

    private JTextField titleField       = new JTextField(20);
    private JComboBox<String> typeCombo = new JComboBox<>(new String[]{
        "book", "journal", "thesis", "dissertation",
        "magazine", "newspaper", "research_paper",
        "government_document", "audiovisual", "map", "other"
    });
    private JTextField authorField      = new JTextField(20);
    private JTextField yearPublishedField    = new JTextField(20);
    private JTextField isbnIssnField    = new JTextField(20);
    private JTextField degreeLevelField = new JTextField(20);
    private JTextField totalCopiesField = new JTextField(20);
    private JButton saveButton          = new JButton("Save");
    private JButton cancelButton        = new JButton("Cancel");

    public testing(JFrame parent) {
        super(parent, "Add Resource", true);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setSize(400, 350);
        setLocationRelativeTo(getParent());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        formPanel.add(new JLabel("Title *"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Resource Type *"));
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Author"));
        formPanel.add(authorField);
        
        formPanel.add(new JLabel("Year Published"));
        formPanel.add(yearPublishedField);

        formPanel.add(new JLabel("ISBN / ISSN"));
        formPanel.add(isbnIssnField);

        formPanel.add(new JLabel("Degree Level"));
        formPanel.add(degreeLevelField);
        
        formPanel.add(new JLabel("Total Copies"));
        formPanel.add(totalCopiesField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Cancel button
        cancelButton.addActionListener(e -> dispose());

        // Save button — calls controller
        saveButton.addActionListener(e -> handleSave());
    }

    private void handleSave() {
        try {
            String title       = titleField.getText().trim();
            String type        = (String) typeCombo.getSelectedItem();
            String author      = authorField.getText().trim().isEmpty()
                                    ? null : authorField.getText().trim();

            String yearText    = yearPublishedField.getText().trim();
            Integer yearPublished = yearText.isEmpty() ? null : Integer.parseInt(yearText);

            String isbnIssn    = isbnIssnField.getText().trim().isEmpty()
                                    ? null : isbnIssnField.getText().trim();
            String degreeLevel = degreeLevelField.getText().trim().isEmpty()
                                    ? null : degreeLevelField.getText().trim();

            String copiesText  = totalCopiesField.getText().trim();
            int totalCopies    = copiesText.isEmpty() ? 1 : Integer.parseInt(copiesText);

            int addedBy = 1; // replace with session staff ID

            boolean success = resourceController.addResource(
                title, type, author, yearPublished, isbnIssn, degreeLevel, totalCopies, addedBy
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Resource added successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed. Please check your inputs.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year and Total Copies must be valid numbers.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
