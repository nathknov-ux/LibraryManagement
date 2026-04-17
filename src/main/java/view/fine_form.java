package view;

import java.awt.Color;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import controller.CirculationController;
import model.Circulation;

public class fine_form extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(fine_form.class.getName());
    private CirculationPage parent;
    private Circulation circulation;
    private CirculationController cc = new CirculationController();

    public fine_form() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public fine_form(CirculationPage parent, Circulation circulation) {
        this.parent = parent;
        this.circulation = circulation;
        initComponents();
        setLocationRelativeTo(null);
        loadData();
    }

    private void loadData() {
        if (circulation == null) return;
        circulationIdLabel.setText(String.valueOf(circulation.getCirculationId()));
        barcodeLabel.setText(circulation.getBarcode());
        memberIdLabel.setText(String.valueOf(circulation.getMemberId()));
        statusLabel.setText(circulation.getStatus() != null ? circulation.getStatus().name() : "");
        dueDateLabel.setText(circulation.getDueDate() != null ? circulation.getDueDate().toString() : "");
        fineAmountField.setText(circulation.getFineAmount() != null ? circulation.getFineAmount().toPlainString() : "0.00");
        if (circulation.getReason() != null) {
            reasonCombo.setSelectedItem(circulation.getReason().name());
        }
        paidCheckbox.setSelected(circulation.isPaid());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelCID = new javax.swing.JLabel();
        circulationIdLabel = new javax.swing.JLabel();
        jLabelBarcode = new javax.swing.JLabel();
        barcodeLabel = new javax.swing.JLabel();
        jLabelMember = new javax.swing.JLabel();
        memberIdLabel = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        jLabelDue = new javax.swing.JLabel();
        dueDateLabel = new javax.swing.JLabel();
        jLabelFine = new javax.swing.JLabel();
        fineAmountField = new javax.swing.JTextField();
        jLabelReason = new javax.swing.JLabel();
        reasonCombo = new javax.swing.JComboBox<>();
        paidCheckbox = new javax.swing.JCheckBox();
        saveBtn = (javax.swing.JButton) new rounded_buttons(20, Color.BLACK); saveBtn.setOpaque(false);
        cancelBtn = (javax.swing.JButton) new rounded_buttons(20, Color.WHITE); cancelBtn.setOpaque(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Fine");

        jPanel1.setBackground(new Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 12));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo white.png")));
        jLabel2.setText("OOKEEPR ™");

        jLabel8.setFont(new java.awt.Font("Georgia", 1, 24));
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setText("FINE MANAGEMENT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(80, 80, 80)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18));
        jLabel5.setText("CIRCULATION INFO");

        jLabelCID.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelCID.setText("Circulation ID:");
        circulationIdLabel.setFont(new java.awt.Font("Georgia", 1, 14));
        circulationIdLabel.setText("-");

        jLabelBarcode.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelBarcode.setText("Barcode:");
        barcodeLabel.setFont(new java.awt.Font("Georgia", 1, 14));
        barcodeLabel.setText("-");

        jLabelMember.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelMember.setText("Member ID:");
        memberIdLabel.setFont(new java.awt.Font("Georgia", 1, 14));
        memberIdLabel.setText("-");

        jLabelStatus.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelStatus.setText("Status:");
        statusLabel.setFont(new java.awt.Font("Georgia", 1, 14));
        statusLabel.setText("-");

        jLabelDue.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelDue.setText("Due Date:");
        dueDateLabel.setFont(new java.awt.Font("Georgia", 1, 14));
        dueDateLabel.setText("-");

        jLabelFine.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelFine.setText("Fine Amount:");

        jLabelReason.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabelReason.setText("Reason:");

        reasonCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"", "OVERDUE", "LOST", "DAMAGED"}));

        paidCheckbox.setFont(new java.awt.Font("Georgia", 0, 14));
        paidCheckbox.setText("Paid");
        paidCheckbox.setBackground(new Color(255, 255, 255));

        saveBtn.setBackground(new Color(0, 0, 0));
        saveBtn.setFont(new java.awt.Font("Georgia", 0, 12));
        saveBtn.setForeground(new Color(255, 255, 255));
        saveBtn.setText("SAVE");
        saveBtn.addActionListener(this::saveBtnActionPerformed);

        cancelBtn.setFont(new java.awt.Font("Georgia", 0, 12));
        cancelBtn.setText("CANCEL");
        cancelBtn.addActionListener(e -> this.dispose());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelCID)
                        .addGap(10, 10, 10)
                        .addComponent(circulationIdLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelBarcode)
                        .addGap(10, 10, 10)
                        .addComponent(barcodeLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelMember)
                        .addGap(10, 10, 10)
                        .addComponent(memberIdLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelStatus)
                        .addGap(10, 10, 10)
                        .addComponent(statusLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelDue)
                        .addGap(10, 10, 10)
                        .addComponent(dueDateLabel))
                    .addComponent(jLabelFine)
                    .addComponent(fineAmountField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jLabelReason)
                    .addComponent(reasonCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paidCheckbox)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCID).addComponent(circulationIdLabel))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBarcode).addComponent(barcodeLabel))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMember).addComponent(memberIdLabel))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus).addComponent(statusLabel))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDue).addComponent(dueDateLabel))
                .addGap(20, 20, 20)
                .addComponent(jLabelFine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fineAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabelReason)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reasonCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(paidCheckbox)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (circulation == null) return;

        String fineText = fineAmountField.getText().trim();

        // Validate fine amount is not empty
        if (fineText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Fine amount is required.",
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate fine amount is a valid number (not letters or symbols)
        BigDecimal amount;
        try {
            amount = new BigDecimal(fineText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Fine amount must be a valid number. Letters and special characters are not allowed.",
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate fine amount is not negative
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this,
                "Fine amount cannot be negative. Please enter a value of 0 or greater.",
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String reason = (String) reasonCombo.getSelectedItem();
        boolean paid = paidCheckbox.isSelected();

        boolean success = cc.updateFine(circulation.getCirculationId(), amount, reason, paid);
        if (success) {
            updated_notification a = new updated_notification();
            a.setVisible(true);
            if (parent != null) parent.allCirculationsTable();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Save failed.");
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new fine_form().setVisible(true));
    }

    private javax.swing.JLabel barcodeLabel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel circulationIdLabel;
    private javax.swing.JLabel dueDateLabel;
    private javax.swing.JTextField fineAmountField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelBarcode;
    private javax.swing.JLabel jLabelCID;
    private javax.swing.JLabel jLabelDue;
    private javax.swing.JLabel jLabelFine;
    private javax.swing.JLabel jLabelMember;
    private javax.swing.JLabel jLabelReason;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel memberIdLabel;
    private javax.swing.JCheckBox paidCheckbox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> reasonCombo;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel statusLabel;
}
