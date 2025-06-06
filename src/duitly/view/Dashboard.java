/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package duitly.view;

import duitly.controller.MainController;
import duitly.dto.DashboardSummary;
import duitly.model.Transaction;
import duitly.model.TransactionType;
import duitly.model.User;
import duitly.util.ErrorDialogSwing;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author arkankhalifamusta
 */
public class Dashboard extends javax.swing.JFrame {
    private final MainController mainController;
    /**
     * Creates new form Dashboard
     * 
     * @param mainController
     */
    public Dashboard(MainController mainController) {
        this.mainController = mainController;
        initComponents();
        sayHello();
        showDashboardSummary();
        showTableTodayTransaction();
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        showPieChart(mainController);
        setVisible(true);
        setResizable(false);
    }
    
    public void showPieChart(MainController mainController) {
    // 1. Get all transactions
    List<Transaction> transactions = mainController.getAllTransactions();

    // 2. Group total amount by transaction type
    Map<TransactionType, BigDecimal> typeTotals = new HashMap<>();

    for (Transaction transaction : transactions) {
        TransactionType type = transaction.getType();
        BigDecimal amount = transaction.getAmount();

        typeTotals.put(type,
            typeTotals.getOrDefault(type, BigDecimal.ZERO).add(amount));
    }

    // 3. Populate the dataset using TransactionType (e.g., INCOME, EXPENSE)
    DefaultPieDataset dataset = new DefaultPieDataset();
    for (Map.Entry<TransactionType, BigDecimal> entry : typeTotals.entrySet()) {
        dataset.setValue(entry.getKey().toString(), entry.getValue());
    }

    // 4. Create the chart
    JFreeChart pieChart = ChartFactory.createPieChart("Cashflow", dataset,true,true,        false);
    pieChart.setBackgroundPaint(new Color(244,246,248));
    pieChart.getTitle().setFont(new Font("Helvetica", Font.BOLD, 18));

    // 5. Customize the plot
    PiePlot plot = (PiePlot) pieChart.getPlot();
    plot.setBackgroundPaint(Color.WHITE);

    // Show percentage with category
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
        "{0}: {1} ({2})", new DecimalFormat("0.00"), new DecimalFormat("0%")));

    plot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
    plot.setLabelLinkMargin(0.05);
    plot.setLabelGap(0.01);
    plot.setLabelBackgroundPaint(new Color(244,246,248));
    plot.setLabelOutlinePaint(null);
    plot.setLabelShadowPaint(null);
    plot.setStartAngle(360);

    // 6. Display chart in panel
    ChartPanel chartPanel = new ChartPanel(pieChart);
    jPanel1.removeAll();
    jPanel1.setPreferredSize(new Dimension(500, 400));
    jPanel1.setLayout(new BorderLayout());
    jPanel1.add(chartPanel, BorderLayout.CENTER);
    jPanel1.validate();
    jPanel1.repaint();
}

    // untuk ucapan halo ke user
    private void sayHello() {
        User user = mainController.getCurrentUser();
       jLabel1.setText("Hello " + user.getUsername());
    }

    // untuk menampilkan data summay transaksi milik user    
    private void showDashboardSummary() {
        try {
            DashboardSummary dashboardSummary = mainController.getDashboardSummary();
            jLabel6.setText("Rp. " + dashboardSummary.getIncome());
            jLabel7.setText("Rp. " + dashboardSummary.getExpense());
            jLabel8.setText("Rp. " + dashboardSummary.getBalance());
        } catch (Exception e) {
            ErrorDialogSwing.showError("Show Dashboard Error", e.getLocalizedMessage());
        }
    }
    
    // untuk menampilkan table transaksi pada hari ini
    
    private void showTableTodayTransaction() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // semua kolom tidak bisa diedit
            }
        };
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Amount", "Type", "Time"});
        
        
        try {
              List<Transaction> todayTransactions = mainController.getTodayTransactions();
        for (Transaction transaction : todayTransactions) {
        model.addRow(new Object[]{
            transaction.getId(),
            transaction.getCategoryName(),
            transaction.getAmount().toString(),
            transaction.getType().toString(),
            transaction.getTime()
        });
        
        }
        
        jTable1.setModel(model);
        
         jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = jTable1.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        
                        int id = (int) jTable1.getValueAt(row, 0);
                        System.out.println("Double-clicked row, ID: " + id);
                        Transaction transaction = mainController.getDetailTransactionById(id);
                        TransactionDetail transactionDetail = new TransactionDetail(Dashboard.this, mainController, transaction);
                        
                        transactionDetail.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                showTableTodayTransaction();
                                showDashboardSummary();
                            }
                        });
                        transactionDetail.setLocationRelativeTo(null);
                        transactionDetail.setVisible(true);

                        // Bisa buka dialog edit, detail, dll.
                        // new TransactionDetailDialog(id).setVisible(true);
                    }
                }
            }
        });
        } catch (Exception e) {
            ErrorDialogSwing.showError("Can not show transactions today", e.getLocalizedMessage());
        }
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        BackgroundImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(233, 238, 238));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 330, 380));

        jButton4.setBackground(new java.awt.Color(255, 171, 46));
        jButton4.setFont(new java.awt.Font("Helvetica", 1, 13)); // NOI18N
        jButton4.setText("Logout");
        jButton4.setBorderPainted(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 180, 50));
        jButton4.setOpaque(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);

        jButton1.setBackground(new java.awt.Color(252, 185, 83));
        jButton1.setFont(new java.awt.Font("Helvetica", 1, 13)); // NOI18N
        jButton1.setText("Dashboard");
        jButton1.setBorderPainted(false);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 180, 50));
        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);

        jButton2.setBackground(new java.awt.Color(255, 171, 46));
        jButton2.setFont(new java.awt.Font("Helvetica", 1, 13)); // NOI18N
        jButton2.setText("Transactions");
        jButton2.setBorderPainted(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 180, 40));
        jButton2.setOpaque(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setBorderPainted(false);

        jLabel4.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel4.setText("Balance");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 80, -1, 20));

        jButton3.setBackground(new java.awt.Color(255, 171, 46));
        jButton3.setFont(new java.awt.Font("Helvetica", 1, 13)); // NOI18N
        jButton3.setText("Profile");
        jButton3.setBorderPainted(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 180, 50));
        jButton3.setOpaque(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setBorderPainted(false);

        jPanel1.setBackground(new java.awt.Color(244, 246, 248));
        jPanel1.setForeground(new java.awt.Color(233, 238, 238));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 330, 400));

        jLabel1.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        jLabel1.setText("Hello");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 220, -1));

        jLabel2.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel2.setText("This Month's Income");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel3.setText("This Month's Expenses");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, -1, -1));

        jLabel5.setFont(new java.awt.Font("Helvetica", 1, 18)); // NOI18N
        jLabel5.setText("Today's Transactions");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        jLabel6.setText("Rp");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 179, -1));

        jLabel7.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        jLabel7.setText("Rp");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 189, 20));

        jLabel8.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        jLabel8.setText("Rp");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 189, -1));

        jLabel10.setFont(new java.awt.Font("Helvetica Rounded", 1, 20)); // NOI18N
        jLabel10.setText("Duitly");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 70, -1));

        BackgroundImg.setIcon(new javax.swing.ImageIcon("/home/rahadityaputra/NetBeansProjects/duitly/JAR/Dashboard.png")); // NOI18N
        getContentPane().add(BackgroundImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        new Profile(mainController);
        this.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        new TransactionPage(mainController);
        this.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        new Login();
        this.dispose();
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundImg;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
