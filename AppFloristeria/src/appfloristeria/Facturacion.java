/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appfloristeria;

import Controles.Conexion;
import Controles.detalle;
import static Controles.detalle.consultad;
import static Controles.detalle.eliminar;
import static Controles.detalle.getnum_detalle;
import static Controles.detalle.modificar;
import static Controles.detalle.registrar;
import Controles.factura;
import static Controles.factura.guardarf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class Facturacion extends javax.swing.JFrame {

    /**
     * Creates new form Facturacion
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    
    public Facturacion() throws ClassNotFoundException, SQLException {
        initComponents();
        consultar();
        llenar();
        comparar();
        desabilitar();
//        tomauser();
        
        this.cambio(txtcantidad,()->{
            numero();
        });
        
        this.cambio(txtdescuento, ()->{
            calcular();
        });

    }
    
    // ========================================================================================
    // Esta linea de codigo representa el metodo desabilita() el cual nos permitira desabilitar
    // los controles del formulario facturacion que no se podran utilizar ni modificar
    // ========================================================================================
    
    private void desabilitar(){
    txtproducto.setEnabled(false);
    txtstock.setEnabled(false);
    txtcategoria.setEnabled(false);
    txtprecio.setEnabled(false);
    txtsubtotal.setEnabled(false);
    btneliminar.setEnabled(false);
    btnmodificar.setEnabled(false);
    }
    
    // ========================================================================================
    // Esta linea de codigo representa el metodo desabilita() el cual nos permitira habilitar
    // los controles del formulario facturacion que se podran utilizar despues de insertar los datos
    // ========================================================================================
    private void habilitar(){
        txtcantidad.setEnabled(true);
        txtdescuento.setEnabled(true);
        txtcantidad.setText("");
        txtdescuento.setText("");
    }
    
    
    // ===========================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen carga de un jfilechooser
    // ===========================================================================
    
    private void limpiar_t(){
        for (int i = 0; i < jttabla.getRowCount(); i++){
            modelo.removeRow(i);
        }
    }
    
    
    // ==============================================================================================
        // Esta linea de codigo en adelante representa el metodo llenarfac el cual nos va a permitir llenar
        // los controles txttotaldesc y txttotal desde la base de datos con su respectiva detalle de factura
        // ===============================================================================================
        public void llenarfac() throws ClassNotFoundException, SQLException{
            try{
                resultado = 0;
                String sql = "select sum(totaldesc)as Totaldesc, sum(subtotal) as total from detalle where id_factura = (select max(id_factura) from detalle)";
               cn = Conexion.conectar();
             consulta = cn.prepareStatement(sql);
            cmd = consulta.executeQuery();
            
            while(cmd.next()){
                Object [] obj = new Object[2];
                txttotaldescuento.setText(Double.toString(cmd.getDouble("Totaldesc")));
                txttotalfactura.setText(Double.toString(cmd.getDouble("total")));
            }
           
            }catch(ClassNotFoundException | SQLException ex){
                JOptionPane.showMessageDialog(null, "Error aqui en el metodo llenarfac:"+ex.getMessage());
            }
            
        }
    
    // ========================================================================================
    // Esta linea de codigo representa el metodo desabilita() el cual nos permitira desabilitar
    // los controles del formulario facturacion que no se podran utilizar ni modificar
    // ========================================================================================
    private void numero(){
        int stock = Integer.parseInt(txtstock.getText());
        int cantidad, resultado;
        if (txtcantidad.getText().isEmpty()){
            
        }
        else{
            txtcantidad.enable(false);
            cantidad = Integer.parseInt(txtcantidad.getText());
            if (cantidad < stock){
            resultado = stock - cantidad;
            txtstock.setText(Integer.toString(resultado));
            }
            else {
                JOptionPane.showMessageDialog(null, "Error la cantidad a vender no puede ser mayor que el stock almacenado en el almacen");
            }
        }
    }
    
    @FunctionalInterface
    interface Metodo{
        void realizar();
    }
    private void cambio(JTextField campo,Metodo e){
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                e.realizar();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
               e.realizar();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
              e.realizar();
            }
        });
    }
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa la instanciacion de los objetos
    // de las clases que van a tener contato con el producto
    // ===========================================================================

    detalle d = new detalle();
    factura f = new factura();
    DefaultTableModel modelo = new DefaultTableModel();
    int compa = 0, otro = 0;
    
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
    
    // ================================================================================
    // Esta linea de codigo representa el metodo obtenerdato el cual nos va a permitir
    // obtener los datos de la fila seleccionada del JTable (jttabla) 
    // ===============================================================================

    private void obtenerdato() throws ClassNotFoundException, SQLException{
        try{
           int fila = jttabla.getSelectedRow(), valor = 0;
           String id = (jttabla.getValueAt(fila, 0).toString());
           
           String query = "select p.id_producto, d.cantidad, d.descuento, d.subtotal  from "
                   + "detalle d inner join producto p on d.id_producto = p.id_producto inner join "
                   + "categoria c on c.id_categoria = p.id_categoria where d.num_detalle ="+id;
           cn = Conexion.conectar();
           estado = cn.createStatement();
           cmd = estado.executeQuery(query);
           cbproducto.setModel(modelocb);
           
           if (cmd.next()){
               valor = cmd.getInt("id_producto");
                System.out.println(valor);
                cbproducto.setSelectedItem(Integer.toString(valor));

               
               
               
           }
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error es aqui "+ex.getMessage());
        }
    }
    
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa el metdo consultar el cual nos
    // va a traer los datos de la tabla producto relacionada con la tabla categoria
    // los cuales seran y trucidos en la tabla la cual se crea por el metodo consulta
    // =============================================================================

    public void consultar() throws ClassNotFoundException, SQLException{
        jttabla.setModel(modelo);
        modelo.addColumn("No Factura");
        modelo.addColumn("No Detalle");
        modelo.addColumn("Producto");
        modelo.addColumn("Categoria");
        modelo.addColumn("Precio Und");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Descuento");
        modelo.addColumn("Total Descuento");
        modelo.addColumn("Subtotal");

        consultad(jttabla, modelo);
    }
    
    // ================================================================================
    // Esta linea de codigo en adelante representa el metodo obtener de tipo void
    // el cual va a capturar los datos introducidos en las diferentes caja de texto
    // ================================================================================

    private void obtener(){
        double precio = Double.parseDouble(txtprecio.getText());
        double cantidad = Double.parseDouble(txtcantidad.getText());
        double resul = precio * cantidad;
        double desc = ((Double.parseDouble(txtdescuento.getText()) / 100) * resul);
         d.settotaldesc(desc);
        d.setid_producto(Integer.parseInt((String) cbproducto.getSelectedItem()));
        JOptionPane.showMessageDialog(null, desc);
        d.setcantidad(Integer.parseInt( txtcantidad.getText()));
       // desc = Double.parseDouble(txtdescuento.getText());
        d.setdescuento(Double.parseDouble(txtdescuento.getText()) / 100);
        d.setprecio(Double.parseDouble(txtprecio.getText()));
        d.setsubtotal(Double.parseDouble(txtsubtotal.getText()));
    }
    // =======================================================================================
    // Esta linea de codigo representa el metodo obtenerfac() el cual nos va a permitir captura
    // los datos que se van a introducir en la tabla factura
    // ====================================================================================
    public void obtenerfac(){
        f.settotaldesc(Double.parseDouble(txttotaldescuento.getText()));
        f.settotal(Double.parseDouble(txttotalfactura.getText()));
    }
    
    // =======================================================================================
    // Esta linea de codigo representa el metodo calcular el cual nos va a permitir calcular el
    // descuento y el subtotal al mismo tiempo
    // ====================================================================================
    private void calcular(){
        
        if (txtcantidad.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Por Favor Escriba una cantidad antes de aplicarle un porciento");
        }
        else{
        txtdescuento.enable(false);
        int cantidad = Integer.parseInt( txtcantidad.getText());
        double precio = Double.parseDouble(txtprecio.getText());
        double resultado = cantidad * precio;
        double desc = (Double.parseDouble(txtdescuento.getText()) / 100) * resultado;
        double total = resultado - desc;
        
        txtsubtotal.setText(Double.toString(total));
        }
    }
    
    // =================================================================================
    // Esta linea de codigo en adelante repesenta el metodo limpiar de tipo void
    // el cual va a limpiar las cajas de texto del JFrame prodcto
    // =================================================================================

    private void limpiar(){
        txtdescuento.setText("");
        txtcantidad.setText("");
        txtproducto.setText("");
        txtstock.setText("");
        txtprecio.setText("");
        txtcategoria.setText("");
        cbproducto.setSelectedItem("");
        txtsubtotal.setText("");
    }
    
    // ===========================================================================
    // Esta linea de codigo representa el metodo llenar() el cual nos va a permitir 
    // llenar el combobox prodcto con el id del producto registrado registrados
    // en la base de datos dbflores
    // ============================================================================

    private void llenar() throws ClassNotFoundException, SQLException{
          int valor;
        try{
            String query = "select p.id_producto from producto p inner join categoria c on "
                    + "c.id_categoria = p.id_categoria inner join imagen i on i.id_imagen = p.id_imagen";
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
            modelocb.addElement("Seleccionar un codigo");
            cbproducto.setModel(modelocb);
        
            while(cmd.next()){
                valor = cmd.getInt("id_producto");
                System.out.println(valor);
                cbproducto.addItem(Integer.toString(valor));
            }
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        
        // =============================
        // Esto es solamente para llenar
        // los cuadro de texto :
        
        txttotaldescuento.setText("0.0");
        txttotalfactura.setText("0.0");
    }
    
    // ==============================================================================================
    // Esta linea de codigo repu comprueba si se termino de registrar una fictura 
    // =============================================================================================
    private void primero(){
        try{
            String query = "select max(id_factura) as codigo from factura";
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
        
            while(cmd.next()){
                compa = cmd.getInt("codigo");
            }
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    // =============================================================================================
    // Esta linea de codigo repu comprueba si se termino de registrar una fictura 
    // =============================================================================================

    private void otro(){
        try{
            String query = "select max(id_factura) as numero from detalle";
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
        
            while(cmd.next()){
                otro = cmd.getInt("numero");
            }
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }    
    
    // ==============================================================================================
    // Esta linea de codigo repu comprueba si se termino de registrar una fictura 
    // =============================================================================================
    
    private void comparar() throws ClassNotFoundException, SQLException{
        primero();
        otro();
            if (compa == otro){
            JOptionPane.showMessageDialog(null, "no se");
            }
            else{
                JOptionPane.showMessageDialog(null, "Todavia no se a terminado de registrar las ventas realizada para \n terminar de "
                                                +   "registrar las ventas dar un clic en el boton registrar factura");
                obtenerfac();
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblusuario = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblempleado = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbproducto = new javax.swing.JComboBox<>();
        txtcategoria = new javax.swing.JTextField();
        txtproducto = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        txtdescuento = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        txtstock = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jttabla = new javax.swing.JTable();
        btnlimpiar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnguardarfac = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txttotaldescuento = new javax.swing.JTextField();
        txttotalfactura = new javax.swing.JTextField();
        btnimprimir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Usuario");

        lblusuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblusuario.setText("_______________");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Empleado");

        lblempleado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblempleado.setText("________________");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Codigo Producto");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Cantidad");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Producto");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Stock");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Categoria");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Precio Unidad");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Descuentos");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Total Producto");

        cbproducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbproductoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cbproducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(143, 143, 143))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(cbproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(txtcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
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

        btnlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1475612418_edit-clear.png"))); // NOI18N
        btnlimpiar.setText("Limpiar");
        btnlimpiar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });

        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        btnmodificar.setText("Modificar");
        btnmodificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230533_Save-as.png"))); // NOI18N
        btnguardar.setText("Registrar Detalle");
        btnguardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230618_Cancel.png"))); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Totales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setLayout(null);

        btnguardarfac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230533_Save-as.png"))); // NOI18N
        btnguardarfac.setText("Registrar Factura");
        btnguardarfac.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnguardarfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarfacActionPerformed(evt);
            }
        });
        jPanel4.add(btnguardarfac);
        btnguardarfac.setBounds(260, 40, 145, 70);

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel4.add(btnsalir);
        btnsalir.setBounds(10, 40, 110, 70);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Total Desc");
        jPanel4.add(jLabel14);
        jLabel14.setBounds(506, 39, 74, 29);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Total Factura");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(506, 88, 74, 20);
        jPanel4.add(txttotaldescuento);
        txttotaldescuento.setBounds(610, 39, 145, 31);
        jPanel4.add(txttotalfactura);
        txttotalfactura.setBounds(610, 85, 145, 29);

        btnimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475289689_Print.png"))); // NOI18N
        btnimprimir.setText("Imprimir");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });
        jPanel4.add(btnimprimir);
        btnimprimir.setBounds(130, 40, 120, 70);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setLayout(null);
        jPanel2.add(jTextField2);
        jTextField2.setBounds(19, 20, 160, 30);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 804, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addComponent(btnmodificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnlimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Venta", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            llenarfac();
            obtener();
            dato = registrar();
            
            if (dato > 0){
                habilitar();
                JOptionPane.showMessageDialog(null, "Informacion Registrada correctamente");
                txtdescuento.setText("");
                limpiar_t();
                consultad(jttabla, modelo);
                limpiar();
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al Registrar los datos solicitados");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void jttablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jttablaMouseClicked
        try {
            // TODO add your handling code here:
            obtenerdato();
            txtdescuento.setEnabled(true);
            txtcantidad.setEnabled(true);
             btneliminar.setEnabled(true);
             btnmodificar.setEnabled(true);
             btnlimpiar.setEnabled(true);
             btnguardar.setEnabled(false);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jttablaMouseClicked

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtener();
            dato = eliminar(getnum_detalle());
            
            if (dato > 0){
                JOptionPane.showMessageDialog(null, "Informacion eliminada correctamente");
                limpiar_t();
                consultad(jttabla, modelo);
            }
            else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente los datos");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtener();
            dato = modificar(getnum_detalle());
            if (dato > 0){
                consultad(jttabla, modelo);
                limpiar();
                JOptionPane.showMessageDialog(null, "Datos Actualizados correctamente");
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al actualizar los datos solictados");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void cbproductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbproductoItemStateChanged
        // TODO add your handling code here:
        String valor = (String) cbproducto.getSelectedItem();
        
        // ==================================================================================================
        // Eta linea de codigo representa las siguientes instrucciones las cuales nos va a permitir llenar los
        // diferentes controles osea cuadro de texto cuando se seleccione uno de los items del combobox (cbpro)
        // ===============================================================================================
        try{
            String sql = "select p.id_producto, p.producto, c.categoria, p.fecha_entrada, p.descripcion, p.stock, "
                    + "p.precio from producto as p inner join categoria as c on p.id_categoria = c.id_categoria where "
                    + "p.estado = 'habilitado' and p.id_producto ="+valor;
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(sql);
            
            if(cmd.next()){
                txtproducto.setText(cmd.getString("producto"));
                txtstock.setText(Integer.toString(cmd.getInt("stock")));
                txtprecio.setText(Double.toString(cmd.getDouble("precio")));
                txtcategoria.setText(cmd.getString("categoria"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }//GEN-LAST:event_cbproductoItemStateChanged

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btnguardarfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarfacActionPerformed
        // TODO add your handling code here:
        try{
            int dato = 0;
            obtenerfac();
            dato = guardarf();
            if (dato > 0){
                limpiar();
                txttotaldescuento.setText("");
                txttotalfactura.setText("");
                llenarfac();
                JOptionPane.showMessageDialog(null, "Venta registrada correctamente");
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al registrada esta venta");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnguardarfacActionPerformed

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        try {
            // TODO add your handling code here:
            Facturaa fa = new Facturaa();
            fa.setVisible(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnimprimirActionPerformed

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
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Facturacion().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnguardarfac;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbproducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable jttabla;
    private javax.swing.JLabel lblempleado;
    private javax.swing.JLabel lblusuario;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcategoria;
    private javax.swing.JTextField txtdescuento;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtproducto;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txttotaldescuento;
    private javax.swing.JTextField txttotalfactura;
    // End of variables declaration//GEN-END:variables
}
