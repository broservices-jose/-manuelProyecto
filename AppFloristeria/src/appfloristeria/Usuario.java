/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appfloristeria;

import Controles.Conexion;
import Controles.prueba;
import Controles.usuario;
import static Controles.usuario.actualizar;
import static Controles.usuario.consultau;
import static Controles.usuario.eliminar;
import static Controles.usuario.getid_usuario;
import static Controles.usuario.ingresar;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JOSE
 */
public class Usuario extends javax.swing.JFrame {

    /**
     * Creates new form Usuario
     */
    
    // ==========================================================================
    // Esta linea de codigo representa la creacion de los objetos de la clases
    // que van interactuar con el formulario usuario y la declariacion de las
    // variable de que se van utilizar en dicho formulario
    // =========================================================================
    
    JTable jt = new JTable();
    
    private int id_empleado;
    
    private int getid(){
        return id_empleado;
    }
    private void setid(int id){
        this.id_empleado = id;
    }
    
    private int id;
    
    // ===========================================================
    // aqui va todos los objetos, metodos y propiedades  que van
    // a permitir llenar desde la base de datos el combobox cbcodigo
    // =============================================================
    
    private Statement cmd;
    private ResultSet consulta;
    private DefaultTableModel modelo;
    private Connection cn;
    private DefaultComboBoxModel modelocb = new DefaultComboBoxModel();
//    private DefaultComboBoxModel modelocb1 = new DefaultComboBoxModel();
    
    
    // ===========================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen carga de un jfilechooser
    // ===========================================================================
    
    private void limpiar_t(){
        for (int i = 0; i < jttabla.getRowCount(); i++){
            modelo.removeRow(i);
        }
    }
    
    
    // ================================================================================
    // Esta linea de codigo representa el metodo obtenerdato el cual nos va a permitir
    // obtener los datos de la fila seleccionada del JTable (jttabla) 
    // ===============================================================================
    
    private void damedato() throws ClassNotFoundException, SQLException{
        try{
            int fila = jttabla.getSelectedRow();
            String id = (jttabla.getValueAt(fila, 0).toString());
            
            String sql = "select u.id_usuario, u.nombre, u.clave, concat(e.nombre,'', e.apellido) as Empleado, i.ruta as Imagen from usuario as u inner join empleado as e on u.id_empleado = e.id_empleado  inner join imagen as i on e.id_imagen = i.id_imagen where u.id_usuario='"+id+"'";
            cn = Conexion.conectar();
            cmd = cn.createStatement();
            consulta = cmd.executeQuery(sql);
            
            if(consulta.next()){
                u.setid_usuario(consulta.getInt("id_usuario"));
                txtcuenta.setText(consulta.getString("nombre"));
                txtclave.setText(consulta.getString("clave"));
                lblhorainici.setText("8 : 00 AM");
                lblhoracierre.setText("6 : 00 PM");
                cbempleado.setSelectedItem(consulta.getString("Empleado"));
                lblimagen.setIcon(new javax.swing.ImageIcon(consulta.getString("Imagen")));
                lblimagen2.setIcon(new javax.swing.ImageIcon(consulta.getString("Imagen")));
                lblempleado.setText(consulta.getString("Empleado"));
                lblcuenta.setText(consulta.getString("nombre"));
                lblclave.setText(consulta.getString("clave"));
               
                
            }
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error"+ex.getMessage());
        }
    }
    
    // ===========================================================================
    // Esta linea de codigo representa el metodo llenar() el cual nos va a permitir 
    // llenar el combobox empleado con el nombre y apellido de los empleados registrados
    // en la base de datos dbflores
    // ============================================================================
    
    private void llenar() throws ClassNotFoundException, SQLException{
        try{
            String query = "select id_empleado, concat(e.nombre,' ', e.apellido) as Empleado"
                    + " from empleado as e inner join imagen as i on e.id_imagen = i.id_imagen";
            cn = Conexion.conectar();
            cmd = cn.createStatement();
            consulta = cmd.executeQuery(query);
            modelocb.addElement("Seleccionar un Empleado");
            cbempleado.setModel(modelocb);
            
            while(consulta.next()){
            prueba pr = new prueba(consulta.getInt(1),consulta.getString(2));
             cbempleado.addItem(pr.toString());
            }

            
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    usuario u = new usuario();
    
    public Usuario() throws ClassNotFoundException, SQLException {
        initComponents();
        consultar();
        llenar();
        jtpanel.setSelectedIndex(1);
    }

    
    // ==================================================================================
    // Esta linea de codigo en adelante representa el metodo consultar() el cual nos va
    // a permitir traer los datos del la base de datos y llenar la tabla jttbla con dichos datos
    // ==================================================================================
    
    private void consultar() throws ClassNotFoundException{
        DefaultTableModel modelo = new DefaultTableModel();
        jttabla.setModel(modelo);
        modelo.addColumn("ID");
        modelo.addColumn("Cuenta de Usuario");
        modelo.addColumn("Clave");
        modelo.addColumn("Empleado");
        modelo.addColumn("Ruta Imagen");
        consultau(jttabla, modelo);
    }
    
    
    // ======================================================================================
    // Esta linea de codigo representa el metodo tomaemp() el cual nos va a permiter tomar los 
    // datos y la imagen del empleado al cual se le va a crear una cuenta de usuario
    // =======================================================================================
    private void tomaemp() throws ClassNotFoundException, SQLException{
        try{
            int id = cbempleado.getSelectedIndex();
            System.out.println(id);
            String sql = "select distinct e.nombre, e.id_empleado, e.nombre, e.apellido, e.correo, e.telefono, e.direccion, e.fecha_nac, e.sexo, e.fecha_contracto, i.ruta as ruta from empleado as e inner join imagen as i on e.id_imagen = i.id_imagen where e.estado = 'habilitado' and e.id_empleado ='"+id+"'";
            cn = Conexion.conectar();
            cmd = cn.createStatement();
            consulta = cmd.executeQuery(sql);
            if (consulta.next()){
                lblimagen.setIcon(new javax.swing.ImageIcon(consulta.getString("ruta")));
                lblimagen2.setIcon(new javax.swing.ImageIcon(consulta.getString("ruta")));
            }
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
    }
    
    //  =======================================================================================
    // Esta linea de codigo representa el metodo obtener el cual nos va a permitir obtener los
    // datos introducidos en los diferentes cuadros de texto del formulario usuario
    // ==================================================================================
    
    private void obtener(){
      u.setnombre(txtcuenta.getText());
      u.setclave(txtclave.getText());
      u.setempleado(cbempleado.getSelectedIndex());
      
    }
    // ==================================================================================
    // Esta linea de codigo en adelante representa el metodo limpiar el cual nos va
    // permitir limpiar todos los cuadros de texto del formulario de registro de empleado
    // ===================================================================================
    
    private void limpiar(){
        cbempleado.setSelectedItem("");
        txtcuenta.setText("");
        txtclave.setText("");
        txthorainicio.setText("");
        txthoraciere.setText("");
    }
    
      //System.out.println(obtenerid(String) cbempleado.getSelectedItem());    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpanel = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblempleado = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblcuenta = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblclave = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblhorainici = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblhoracierre = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblimagen2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jttabla = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbempleado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtcuenta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txthorainicio = new javax.swing.JFormattedTextField();
        txthoraciere = new javax.swing.JFormattedTextField();
        txtclave = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblimagen = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Empleado");

        lblempleado.setText("____________________");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Cuenta");

        lblcuenta.setText("____________________");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Clave");

        lblclave.setText("____________________");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Hora Inicio");

        lblhorainici.setText("____________________");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Hora cierre");

        lblhoracierre.setText("____________________");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblclave, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lblempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lblcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblhoracierre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblhorainici, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(lblhorainici, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(lblhoracierre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblclave, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14)))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblimagen2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(0, 81, Short.MAX_VALUE)
                    .addComponent(lblimagen2)
                    .addGap(0, 82, Short.MAX_VALUE)))
        );

        jttabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jttabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jttablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jttabla);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230618_Cancel.png"))); // NOI18N
        jButton5.setText("Eliminar");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        jButton6.setText("Salir");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.setInheritsPopupMenu(true);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        jButton7.setText("Modificar");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel17.setText("Buscar por :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(514, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 47, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(293, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(164, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(171, Short.MAX_VALUE)))
        );

        jtpanel.addTab("Consultar", jPanel2);

        jPanel1.setLayout(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Seleccionar Empleado");

        cbempleado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbempleadoItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Cuenta");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Clave");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Hora Inicio");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Hora Cierre");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtclave))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcuenta))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthorainicio))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthoraciere, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txthorainicio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cbempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txthoraciere, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtclave, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 11, 615, 190);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/icono.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblimagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblimagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);
        jPanel4.setBounds(646, 11, 241, 190);

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230533_Save-as.png"))); // NOI18N
        btnguardar.setText("Registrar");
        btnguardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnguardar);
        btnguardar.setBounds(59, 277, 135, 54);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1475612418_edit-clear.png"))); // NOI18N
        jButton2.setText("Limpiar");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jButton2);
        jButton2.setBounds(271, 277, 135, 54);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        jButton3.setText("Modificar");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(473, 277, 135, 54);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        jButton4.setText("Salir");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setInheritsPopupMenu(true);
        jPanel1.add(jButton4);
        jButton4.setBounds(702, 277, 135, 54);

        jtpanel.addTab("Registrar", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpanel))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jtpanel))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == btnguardar){
            try{
                int dato = 0;
                obtener();
                dato = ingresar();
                if(dato > 0){
                    JOptionPane.showMessageDialog(null, "Datos Registrado correctamente");
                    limpiar();
                    limpiar_t();
                    consultau(jttabla, modelo);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error al registrar los datos solicitados");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void jttablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jttablaMouseClicked
        try {
            // TODO add your handling code here:
            damedato();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jttablaMouseClicked

    private void cbempleadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbempleadoItemStateChanged
        try {
            // TODO add your handling code here:
            tomaemp();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbempleadoItemStateChanged

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jtpanel.setSelectedIndex(1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtener();
            dato = actualizar(getid_usuario());
            if (dato > 0){
                JOptionPane.showMessageDialog(null, "Datos Actualizado corretamente");
                limpiar_t();
                consultau(jt, modelo);
            }
            else{
                JOptionPane.showMessageDialog(null, "Eror al acutalizar los datos solicitados");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtener();
            dato = eliminar(getid_usuario());
            if (dato > 0){
                JOptionPane.showMessageDialog(null, "Datos Eliminados correctamente");
                limpiar_t();
                consultau(jt, modelo);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Usuario().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnguardar;
    private javax.swing.JComboBox<String> cbempleado;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jtpanel;
    private javax.swing.JTable jttabla;
    private javax.swing.JLabel lblclave;
    private javax.swing.JLabel lblcuenta;
    private javax.swing.JLabel lblempleado;
    private javax.swing.JLabel lblhoracierre;
    private javax.swing.JLabel lblhorainici;
    private javax.swing.JLabel lblimagen;
    private javax.swing.JLabel lblimagen2;
    private javax.swing.JPasswordField txtclave;
    private javax.swing.JTextField txtcuenta;
    private javax.swing.JFormattedTextField txthoraciere;
    private javax.swing.JFormattedTextField txthorainicio;
    // End of variables declaration//GEN-END:variables
}
