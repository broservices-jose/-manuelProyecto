/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appfloristeria;

import Controles.Conexion;
import Controles.Filtro;
import Controles.Imagen;
import static Controles.Imagen.ingresar;
import Controles.producto;
import static Controles.producto.consuta;
import static Controles.producto.eliminar;
import static Controles.producto.getid_producto;
import static Controles.producto.guardar;
import static Controles.producto.guardar2;
import static Controles.producto.modificar;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;

/**
 *
 * @author FCC III
 */
public class Producto extends javax.swing.JFrame {

    /**
     * Creates new form Producto
     */
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa la instanciacion de los objetos
    // de las clases que van a tener contato con el producto
    // ===========================================================================
    
    producto pr = new producto();
    Imagen img = new Imagen();
    private int  fila , columna, valida = 0; 
    private String nombre;
    DefaultTableModel modelo = new DefaultTableModel();
    
    // ===========================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen carga de un jfilechooser
    // ===========================================================================
    private String nombrearchivo;
    private String ruta;
    private String nuevaruta;
    private File archivo;
    
    
    /*private void Clear_Table1(){
       for (int i = 0; i < tabla1.getRowCount(); i++) {
           modelo1.removeRow(i);
           i-=1;
       }
   }*/
    
    // ===========================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen carga de un jfilechooser
    // ===========================================================================
    
    private void limpiar_t(){
        for (int i = 0; i < jttabla.getRowCount(); i++){
            modelo.removeRow(i);
        }
    }
    
    // ============================================================================
    // Esta linea de codigo representa el metodo cargarimagen el cual obtiene la ruta
    // de la imagen donde esta se encuentra localizada
    // ============================================================================
    
    private void cargarImagen(){
        String seleccionada;
        abril.addChoosableFileFilter(new Filtro());
        abril.setFileFilter(abril.getChoosableFileFilters()[1]);
        
        // Abre el cuadro de diaglo Abrir
        int retvalor = abril.showOpenDialog(Producto.this);
        if (retvalor == JFileChooser.APPROVE_OPTION){
            File archivosele = abril.getSelectedFile();
            // ............Si el usuario selecciono un archivo, entonces usarlo
            seleccionada = archivosele.getAbsolutePath();
            // Mostrar la ruta al usuario en el area de texto correspondiente
            txtruta.setText(seleccionada);
            lblimagen.setIcon(new javax.swing.ImageIcon(seleccionada));            
        }
    }
    
    // Esta linea de codigo no funciona
//    private void cargarimagen(){
//        try{
//            ImageIcon imagen; // permite almacenar la imagen que se abre con el jfilechooser
//            FileNameExtensionFilter filtro = new FileNameExtensionFilter("PNG, JPG", "png", "jpg");
//            abril.setFileFilter(filtro);
//            
//            int abrir = abril.showOpenDialog(this);
//            
//            if (abrir == JFileChooser.APPROVE_OPTION){
//                archivo = abril.getSelectedFile();
//                ruta = archivo.getAbsolutePath();
//                nombrearchivo = archivo.getName();
//                nuevaruta = System.getProperty("user.dir")+"\\src\\Icon\\"+nombrearchivo;
//                imagen = new ImageIcon(ruta);
//                
//                this.txtruta.setText(nombrearchivo);
//                this.lblimagen.setIcon(imagen);
//            }
//        }   catch(NullPointerException ex){
//                    
//                    JOptionPane.showMessageDialog(null, "Error 8:"+ex.getMessage());
//                    }
//        
//    }
    
    // ===============================================================================
    // Esta linea de codigo representa la creacion de los objetos que nos van a permitir
    // interactuar con la base de dato
    // ===============================================================================
    
    private PreparedStatement consulta;
    private Statement estado;
    private ResultSet cmd;
    private Connection cn;
    
    // ================================================================================
    // Esta linea de codigo representa el metodo obtenerdato el cual nos va a permitir
    // obtener los datos de la fila seleccionada del JTable (jttabla) 
    // ===============================================================================
    
    private void obtenerdato(){
        try{
        int fila1 = jttabla.getSelectedRow();
        String id = (jttabla.getValueAt(fila1, 0).toString());
        
        
            String query = "select p.id_producto, p.producto, c.categoria, p.fecha_entrada, p.descripcion, "
                    + "p.stock, p.precio, i.ruta as imagen from producto as p inner join categoria as c on "
                    + "p.id_categoria = c.id_categoria inner join imagen i on i.id_imagen = p.id_imagen where p.id_producto ='"+id+"'";
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
            
            if(cmd.next()){
                pr.setid_producto(cmd.getInt("id_producto"));
                txtproducto.setText(cmd.getString("producto"));
                txtcategoria.setText(cmd.getString("categoria"));
                txtfecha.setDate(cmd.getDate("fecha_entrada"));
                txtdescripcion.setText(cmd.getString("descripcion"));
                txtcantidad.setText(Integer.toString(cmd.getInt("stock")));
                txtprecio.setText(Double.toString(cmd.getDouble("precio")));
                System.out.println(cmd.getString("imagen"));
                lblimagen.setIcon(new javax.swing.ImageIcon(cmd.getString("imagen")));
            }
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "error aqui"+ex.getMessage());
        }
        
    }
    
    // =============================================================================
    // Esta linea de codigo representa el constroctor del JFrame Producto
    // ===========================================================================
    
    public Producto() throws ClassNotFoundException, SQLException {
        initComponents();
        consultar();  
    }
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa el metdo consultar el cual nos
    // va a traer los datos de la tabla producto relacionada con la tabla categoria
    // los cuales seran y trucidos en la tabla la cual se crea por el metodo consulta
    // =============================================================================
    
    public void consultar() throws ClassNotFoundException, SQLException{
        jttabla.setModel(modelo);
        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Categoria");
        modelo.addColumn("Fecha Entrada");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stock");
        modelo.addColumn("Precio");
        
        consuta(jttabla, modelo);
    }
    
    // ================================================================================
    // Esta linea de codigo en adelante representa el metodo obtener de tipo void
    // el cual va a capturar los datos introducidos en las diferentes caja de texto
    // ================================================================================
    
    private void obtener(){
        pr.setcategoria(txtcategoria.getText());
        pr.setprecio(Double.parseDouble(txtprecio.getText()));
        pr.setproducto(txtproducto.getText());
        pr.setfecha(txtfecha.getDate());
        pr.setstock(Integer.parseInt(txtcantidad.getText()));
        pr.setdescripcion(txtdescripcion.getText());
        img.setruta(txtruta.getText());
    }
    
    
    
    // =================================================================================
    // Esta linea de codigo en adelante repesenta el metodo limpiar de tipo void
    // el cual va a limpiar las cajas de texto del JFrame prodcto
    // =================================================================================
    
    private void limpiar(){
        txtproducto.setText("");
        txtcategoria.setText("");
        txtfecha.setDate(null);
        txtprecio.setText("");
        txtcantidad.setText("");
        txtdescripcion.setText("");
        txtruta.setText("");
    }
    
    // =======================================================================================================
    // Esta linea de codigo representa el metodo validar el cual nos va a permitir vefiricar si existe uno de
    // los controles vacio y detonarra un mensaje de alerta y no se pondran guardar los datos solicitados
    // =======================================================================================================
    private void validar(){
        if (txtproducto.getText().isEmpty() || txtcategoria.getText().isEmpty() || txtfecha.getDate().equals(null)||
            txtcantidad.getText().isEmpty() || txtdescripcion.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor verificar los campos de registros se han encontrado campos vacios");
            valida = 1;
        }
        else {
            valida = 0;
        }
    }
    
    // =======================================================================================
    // ESta linea de codigo en adelante representa el metodo copiararchivo que nos va a permitir
    // copiar cualquier archiv desde la ruta origen hasta la ruta destino
    // =====================================================================================
    
//    private  void copiararchivo(String origen, String destino){
//        try{
//            Path rutaorigen = Paths.get(origen);
//            Path rutadestino = Paths.get(destino);
//            CopyOption[] opciones = new CopyOption[]{
//                StandardCopyOption.REPLACE_EXISTING,
//                StandardCopyOption.COPY_ATTRIBUTES
//            };
//            Files.copy(rutaorigen, rutadestino, opciones);
//        }catch(Exception ex){
//            JOptionPane.showMessageDialog(null, "Error 9:"+ ex.getMessage());
//        }
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abril = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtproducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtcantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtdescripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcategoria = new javax.swing.JTextField();
        txtfecha = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblimagen = new javax.swing.JLabel();
        txtruta = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jttabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel3.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Producto");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(16, 30, 54, 34);
        jPanel3.add(txtproducto);
        txtproducto.setBounds(99, 31, 192, 34);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Precio");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(351, 34, 41, 27);
        jPanel3.add(txtprecio);
        txtprecio.setBounds(450, 30, 210, 34);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("cantidad");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(16, 86, 54, 34);
        jPanel3.add(txtcantidad);
        txtcantidad.setBounds(99, 87, 192, 34);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Fecha Entrada");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(351, 86, 86, 27);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Descripcion");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(16, 139, 58, 34);
        jPanel3.add(txtdescripcion);
        txtdescripcion.setBounds(99, 140, 192, 34);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Categoria");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(351, 143, 75, 27);

        txtcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcategoriaActionPerformed(evt);
            }
        });
        jPanel3.add(txtcategoria);
        txtcategoria.setBounds(450, 140, 210, 34);
        jPanel3.add(txtfecha);
        txtfecha.setBounds(450, 90, 210, 30);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 10, 710, 192);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setLayout(null);

        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/WIN_20161021_202527.JPG"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 220, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 160, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);
        jPanel5.setBounds(16, 19, 240, 160);
        jPanel4.add(txtruta);
        txtruta.setBounds(70, 190, 200, 30);

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6);
        jButton6.setBounds(10, 190, 40, 30);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(9, 211, 300, 230);

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230533_Save-as.png"))); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnguardar);
        btnguardar.setBounds(380, 220, 121, 45);

        btnlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1475612418_edit-clear.png"))); // NOI18N
        btnlimpiar.setText("Limpiar");
        jPanel1.add(btnlimpiar);
        btnlimpiar.setBounds(380, 270, 121, 45);

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230618_Cancel.png"))); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btneliminar);
        btneliminar.setBounds(540, 220, 121, 45);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        jButton4.setText("Salir");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jButton4);
        jButton4.setBounds(380, 320, 121, 45);

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        btnsalir.setText("Modificar");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnsalir);
        btnsalir.setBounds(540, 270, 121, 45);

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jttablaMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jttabla);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 450, 700, 160);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Por :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(330, 370, 380, 70);

        jTabbedPane1.addTab("Agregar", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcategoriaActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtcategoriaActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        // TODO add your handling code here:
        validar();
        if (valida != 1){
        
        try{
            int dato = 0;
            obtener();
            dato = modificar(getid_producto());
            if(dato > 0){
                JOptionPane.showMessageDialog(null, "Datos Actualizados correctamente");
                limpiar_t();
                consuta(jttabla, modelo);
                limpiar();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar los datos solicitados");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
      }else{
            JOptionPane.showMessageDialog(null, "No se pudo actualizar los datos socilitados");
        }
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == btnguardar){
            validar();
            if(valida != 1){
            
            int dato = 0, dato1 = 0, dato3 = 0;
            
            try {
                
                obtener();
                dato1 = guardar2();
                dato = guardar();
                dato3 = ingresar();

                if (dato1 > 0){
                    limpiar();
                    if (dato3 > 0){
                        if (dato > 0){
                        JOptionPane.showMessageDialog(null, "Datos Registrado correctamente");
                        limpiar_t();
                        consuta(jttabla, modelo);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Error al insertar los datos solicitados");
                        }
                    }
                    else {
                    }
                }
                else{
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo registrar los datos socilitados");
            }
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        cargarImagen();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jttablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jttablaMouseClicked
        // TODO add your handling code here:
        if(evt.getSource() == jttabla){
            obtenerdato();
        }
    }//GEN-LAST:event_jttablaMouseClicked

    private void jttablaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jttablaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jttablaMouseEntered

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtener();
            dato = eliminar(getid_producto());
             System.out.println(dato);
             if(dato >  0){
                 limpiar_t();
                 consuta(jttabla, modelo);
                 JOptionPane.showMessageDialog(null, "Datos Eliminados Correctamente");
             }
             else{
                 JOptionPane.showMessageDialog(null, "Error al Eliminar los datos solicitados");
             }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btneliminarActionPerformed

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
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Producto().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser abril;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable jttabla;
    private javax.swing.JLabel lblimagen;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcategoria;
    private javax.swing.JTextField txtdescripcion;
    private com.toedter.calendar.JDateChooser txtfecha;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtproducto;
    private javax.swing.JTextField txtruta;
    // End of variables declaration//GEN-END:variables
}
