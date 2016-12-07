/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appfloristeria;

import Controles.Conexion;
import static Controles.factura.consultaf;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author FCC III
 */
public class Facturaa extends javax.swing.JFrame {

    /**
     * Creates new form Desvoluciones
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public Facturaa() throws ClassNotFoundException, SQLException {
        initComponents();
        consultar();
        llenar();
    }
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa la instanciacion de los objetos
    // de las clases que van a tener contato con el producto
    // ===========================================================================
    DefaultTableModel modelo = new DefaultTableModel();
    
    
    // ===============================================================================
    // Esta linea de codigo representa la creacion de los objetos que nos van a permitir
    // interactuar con la base de dato
    // ===============================================================================
    
    private PreparedStatement consulta;
    private Statement estado;
    private ResultSet cmd;
    private Connection cn;
    private int resultado = 0;
    private DefaultComboBoxModel modelocb = new DefaultComboBoxModel();
        
    
     // ===========================================================================
    // Esta linea de codigo en adelante representa el metdo consultar el cual nos
    // va a traer los datos de la tabla producto relacionada con la tabla categoria
    // los cuales seran y trucidos en la tabla la cual se crea por el metodo consulta
    // =============================================================================

    private void consultar() throws ClassNotFoundException, SQLException{
        jtabla.setModel(modelo);
        modelo.addColumn("No Factura");
        modelo.addColumn("Sumatoria Descuento");
        modelo.addColumn("Total venta");
        modelo.addColumn("Fecha");
        
        consultaf(jtabla, modelo);
    }
    
    // ===========================================================================
    // Esta linea de codigo representa el metodo llenar() el cual nos va a permitir 
    // llenar el combobox prodcto con el id del producto registrado registrados
    // en la base de datos dbflores
    // ============================================================================

    private void llenar() throws SQLException, ClassNotFoundException{
        int valor =0;
        try{
            String sql = "select distinct f.id_factura from factura f inner join detalle d on d.id_factura = f.id_factura";
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(sql);
            modelocb.addElement("Seleccionar un codigo");
            cbcodigo.setModel(modelocb);
            
            while(cmd.next()){
                valor = cmd.getInt("id_factura");
                cbcodigo.addItem(Integer.toString(valor));
            }
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error aqui en la imprision "+ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        cbcodigo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Factura a Imprimir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setLayout(null);

        jPanel1.add(cbcodigo);
        cbcodigo.setBounds(130, 30, 190, 30);

        jLabel1.setText("Buscar por :");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(370, 30, 60, 30);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(450, 30, 240, 30);

        jLabel2.setText("Codigo de Factura");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 30, 88, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 20, 700, 80);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setLayout(null);

        jtabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtabla);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 660, 140);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(20, 110, 690, 190);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230618_Cancel.png"))); // NOI18N
        jButton1.setText("Salir");
        getContentPane().add(jButton1);
        jButton1.setBounds(390, 320, 150, 70);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475289689_Print.png"))); // NOI18N
        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(550, 320, 150, 70);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        FileInputStream fichero = null;
        try {
            
            Map parametro = new HashMap();
            parametro.put("id", cbcodigo.getSelectedItem());
            cn = Conexion.conectar();
//            JasperReport jpr  = null;
            String archivo = "C:/Users/JOSE/Documents/NetBeansProjects/AppFloristeria/src/Reporte.jasper";
//            jpr = (JasperReport) JRLoader.loadObjectFromLocation(archivo);
//            JasperPrint jp = JasperFillManager.fillReport(jpr, parametro,  cn);
//            JOptionPane.showMessageDialog(null, "Este es el que imprime :"+jp+"los parametros :"+parametro+"el reporte :"+jpr);
//            JasperViewer jv = new JasperViewer(jp);
//            jv.setVisible(true);
//            jv.setTitle("Esta es su factura");
              //  URL in = this.getClass().getResource("Reporte.jrxml");
                JasperReport reporte = (JasperReport) JRLoader.loadObjectFromLocation(archivo);
                JasperPrint imprimir = JasperFillManager.fillReport(reporte, parametro, cn);
                JRExporter exportar = new JRPdfExporter();
                exportar.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
                exportar.exportReport();
                exportar.setParameter(JRExporterParameter.OUTPUT_FILE, new File(archivo));        
                exportar.exportReport();
                JasperViewer jv = new JasperViewer(imprimir);
        } catch (JRException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturaa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Facturaa().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Facturaa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbcodigo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jtabla;
    // End of variables declaration//GEN-END:variables
}
