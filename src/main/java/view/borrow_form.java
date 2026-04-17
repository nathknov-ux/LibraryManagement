package view;

import java.awt.Color;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import controller.CirculationController;
import util.Session;

public class borrow_form extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(borrow_form.class.getName());
    private CirculationPage parent;
    private CirculationController cc = new CirculationController();

    public borrow_form() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public borrow_form(CirculationPage parent) {
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        barcodeField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        memberIdField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dueDaysField = new javax.swing.JTextField();
        borrowBtn = (javax.swing.JButton) new rounded_buttons(20, Color.BLACK); borrowBtn.setOpaque(false);
        cancelBtn = (javax.swing.JButton) new rounded_buttons(20, Color.WHITE); cancelBtn.setOpaque(false);
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Borrow Resource");

        jPanel1.setBackground(new Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 12));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo white.png")));
        jLabel2.setText("OOKEEPR ™");

        jLabel8.setFont(new java.awt.Font("Georgia", 1, 24));
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setText("BORROW RESOURCE");

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

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabel1.setText("Barcode:");

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabel3.setText("Member ID:");

        jLabel4.setFont(new java.awt.Font("Georgia", 0, 14));
        jLabel4.setText("Due in (days):");

        dueDaysField.setText("7");

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18));
        jLabel5.setText("DETAILS");

        borrowBtn.setBackground(new Color(0, 0, 0));
        borrowBtn.setFont(new java.awt.Font("Georgia", 0, 12));
        borrowBtn.setForeground(new Color(255, 255, 255));
        borrowBtn.setText("BORROW");
        borrowBtn.addActionListener(this::borrowBtnActionPerformed);

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
                    .addComponent(jLabel1)
                    .addComponent(barcodeField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(memberIdField)
                    .addComponent(jLabel4)
                    .addComponent(dueDaysField)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(borrowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barcodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memberIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dueDaysField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void borrowBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String barcode = barcodeField.getText().trim();
        String memberIdStr = memberIdField.getText().trim();
        String dueDaysStr = dueDaysField.getText().trim();

        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Barcode is required.");
            return;
        }
        if (memberIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID is required.");
            return;
        }

        // Check if the resource copy is already borrowed
        if (cc.isBorrowed(barcode)) {
            JOptionPane.showMessageDialog(this,
                "This resource is already borrowed. Please return it first before borrowing again.",
                "Already Borrowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int memberId = Integer.parseInt(memberIdStr);
            int dueDays = Integer.parseInt(dueDaysStr);
            LocalDateTime dueDate = LocalDateTime.now().plusDays(dueDays);

            boolean success = cc.borrow(barcode, memberId, dueDate);
            if (success) {
                added_notification a = new added_notification();
                a.setVisible(true);
                if (parent != null) parent.allCirculationsTable();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Borrow failed. Check barcode/member ID.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID and Due Days must be numbers.");
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
        java.awt.EventQueue.invokeLater(() -> new borrow_form().setVisible(true));
    }

    private javax.swing.JTextField barcodeField;
    private javax.swing.JButton borrowBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField dueDaysField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField memberIdField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
}
