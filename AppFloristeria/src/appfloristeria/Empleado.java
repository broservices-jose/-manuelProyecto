/*
 * To change this license header, choose License Header    @Override
    public Object stringToValue(String string) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String valueToString(Object o) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
s in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appfloristeria;
import Controles.Conexion;
import Controles.Filtro;
import Controles.Imagen;
import static Controles.Imagen.ingresar;
import Controles.empleado;
import static Controles.empleado.*;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author JOSE
 */
public class Empleado extends javax.swing.JFrame{
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa la instanciacion de los objetos
    // de las clases que van a tener contato con el producto
    // ===========================================================================    
    empleado e = new empleado();
    Imagen i = new Imagen();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date fecha = null, fecha1 = null;
    DefaultTableModel modelo = new DefaultTableModel();
    int valida = 0;
    
    // ============================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen cargada de un jfilechooser
    // ===========================================================================
    private String nombrearchivo;
    private String ruta;
    private String nuevaruta;
    private File archivo;
    
    // ============================================================================
    // Esta linea de codigo representa el metodo cargarimagen el cual obtiene la ruta
    // de la imagen donde esta se encuentra localizada
    // ============================================================================
        private void cargarImagen(){
        String seleccionada;
        abril.addChoosableFileFilter(new Filtro());
        abril.setFileFilter(abril.getChoosableFileFilters()[1]);
        
        // Abre el cuadro de diaglo Abrir
        int retvalor = abril.showOpenDialog(Empleado.this);
        if (retvalor == JFileChooser.APPROVE_OPTION){
            File archivosele = abril.getSelectedFile();
            // ............Si el usuario selecciono un archivo, entonces usarlo
            seleccionada = archivosele.getAbsolutePath();
            // Mostrar la ruta al usuario en el area de texto correspondiente
            txtruta.setText(seleccionada);
            System.out.println(seleccionada);
            lblimagen.setIcon(new javax.swing.ImageIcon(seleccionada));            
        }
    }
    
    
    
    
    // Esta linea de codigo no funciona
//    private void cargarimagen(){
//       try{
//        
//        ImageIcon imagen; // permite almacenar la imagen que se abre con el jfilechooser
//        FileNameExtensionFilter filtro = new FileNameExtensionFilter("PNG, JPG", "png", "jpg");
//        abril.setFileFilter(filtro);
//        
//        int abrir = abril.showOpenDialog(this);
//        
//        if (abrir == JFileChooser.APPROVE_OPTION){
//            archivo = abril.getSelectedFile();
//            ruta = archivo.getAbsolutePath();
//            nombrearchivo = archivo.getName();
//            nuevaruta = System.getProperty("user.dir")+"\\src\\Icon\\"+nombrearchivo;
//            imagen = new ImageIcon(ruta);
//            
//            this.txtruta.setText(nombrearchivo);
//            this.lblimagen.setIcon(imagen);
//        }
//      }catch(NullPointerException ex){
//          JOptionPane.showMessageDialog(null, "Error en la copia :"+ex.getMessage());
//      }
//    }
    
// Esta linea de codigo no funciona        
        
    // =========================================================================================
    // Esta linea de codigo en adelante representa el metodo copiararchivo que nos va a permitir
    // copiar cualquier archivo desde la ruta origen hasta la ruta destino
    // ========================================================================================
    
//    private void copiararchivo(String origen, String destino){
//        try{
//            Path rutaorigen = Paths.get(origen);
//            Path rutadestino = Paths.get(destino);
//            CopyOption[] opciones = new CopyOption[]{
//               StandardCopyOption.REPLACE_EXISTING,
//               StandardCopyOption.COPY_ATTRIBUTES
//            };
//            Files.copy(rutadestino, rutadestino, opciones);
//        }catch(Exception ex){
//            JOptionPane.showMessageDialog(null, "Error  este lo mueve :"+ex.getMessage());
//        }
//    }
    
    // ===========================================================================================
    // Esta linea de codigo representa el metodo cambio el cual nos va a permitir realizar un accion
    // determinada cuando ocurra un evento de insert, update o delete en el jtextfield a utilizar
    // ============================================================================================
    
    private void textear(Object variable) throws SQLException, ClassNotFoundException{
        jt.setModel(modelo);
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Email");
        modelo.addColumn("Telefono");
        modelo.addColumn("Direccion");
        modelo.addColumn("F/Nacimiento");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha Contracto");
        modelo.addColumn("Sueldo");
        modelo.addColumn("Observaciones");
        modelo.addColumn("Ruta de imagen");
        
        filtrar(variable, jt, modelo);
    }
    
    @FunctionalInterface
    interface Metodo{
        void realizar();
    }
    
    private void cambio(JTextField texto, Metodo e){
        texto.getDocument().addDocumentListener(new DocumentListener() {
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
    
    // ==========================================================================================
    // Esta linea de codigo representa el metodo         que nos va a permitir traer los datos del 
    // JTable (jt) a los diferentes cuadros de texto del formulario empleado
    // ==========================================================================================
    
    private void tomardato(){
        try{
            int fila = jt.getSelectedRow();
            String id =  (jt.getModel().getValueAt(fila, 0).toString());
            String query = "select e.id_empleado, e.nombre, e.apellido, e.correo, e.telefono, e.direccion, e.fecha_nac, e.sexo, e.fecha_contracto, e.sueldo, e.observacion, i.ruta as ruta from empleado as e inner join imagen as i on e.id_imagen = i.id_imagen where e.id_empleado ='"+id+"'"; 
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
            if (cmd.next()){
                e.setid_empleado(cmd.getInt("id_empleado"));
                txtnombre.setText(cmd.getString("nombre"));
                txtapellido.setText(cmd.getString("apellido"));
                txtcorreo.setText((cmd.getString("correo")));
                txttelefono.setText(cmd.getString("telefono"));
                txtdireccion.setText(cmd.getString("direccion"));
                txtfecha_nac.setDate(cmd.getDate("fecha_nac"));
                cbsexo.setSelectedItem(cmd.getString("sexo"));
                txtfecha_contracto.setDate(cmd.getDate("fecha_contracto"));
                txtsueldo.setText(Double.toString(cmd.getDouble("sueldo")));
                txtobservacion.setText(cmd.getString("observacion"));
                txtruta.setText(cmd.getString("ruta"));
                lblnombre.setText(cmd.getString("nombre"));
                lblapellido.setText(cmd.getString("apellido"));
                lblcorreo.setText(cmd.getString("correo"));
                lbltelefono.setText(cmd.getString("telefono"));
                lblfecha_nac.setText(cmd.getString("fecha_nac"));
                lblsexo.setText(cmd.getString("sexo"));
                lblfecha_contrato.setText(cmd.getString("fecha_contracto"));
                lblsueldo.setText(Double.toString(cmd.getDouble("sueldo")));
                lblimagen.setIcon(new javax.swing.ImageIcon(cmd.getString("ruta")));
                lblimagen2.setIcon(new javax.swing.ImageIcon(cmd.getString("ruta")));

            }
            
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error es aqui:"+ex.getMessage());
        }
    }
    
     // ===============================================================================
    // Esta linea de codigo representa la creacion de los objetos que nos van a permitir
    // interactuar con la base de dato
    // ===============================================================================
    
    private PreparedStatement consulta;
    private Statement estado;
    private ResultSet cmd;
    private Connection cn;
   
    
    
    
    /**
     * Creates new form Empleado
     * @throws java.lang.ClassNotFoundException
     */
    
    
    // =============================================================================
    // Esta linea de codigo representa el constroctor del JFrame Producto
    // ===========================================================================
    
    public Empleado() throws ClassNotFoundException {
        initComponents();
        //consulta(tabla, modelo)
         consultar();
         jtpanel.setSelectedIndex(1);
        cbsexo.addItem("Masculino");
        cbsexo.addItem("Femenino");
        jsp.setViewportView(jt);
    }
    // ===========================================================================
    // Esta linea de codigo en adelante representa el metodo consultar el cual nos
    // va a traer los datos de la tabla empleado relacionada con la tabla imagen
    // los cuales seran y trucidos en la JTable(jt) la cual se crea por el metodo consulta
    // de la clase empleado
    // =============================================================================
    
    private void consultar() throws ClassNotFoundException{
        
        jt.setModel(modelo);
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Email");
        modelo.addColumn("Telefono");
        modelo.addColumn("Direccion");
        modelo.addColumn("F/Nacimiento");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha Contracto");
        modelo.addColumn("Sueldo");
        modelo.addColumn("Observaciones");
        modelo.addColumn("Ruta de imagen");
        
        consulta(jt, modelo);
    }
    
    // ==========================================================================
    // Esta linae de codigo representa el metodo obtener() el cual no vas a permitir
    // capturar los datos introducidos en los diferentes cuadro de texto
    // ==========================================================================
    private void obtener() throws ParseException{
       // fecha =  format.parse(txtfecha_contracto.getText());
        //fecha1 = format.parse(txtfecha_nac.getText());
        e.setnombre(txtnombre.getText());
        e.setapellido(txtapellido.getText());
        e.setcorreo(txtcorreo.getText());
        e.settelefono(txttelefono.getText());
        e.setdireccion(txtdireccion.getText());
        e.setfecha(txtfecha_nac.getDate());
        e.setsexo(cbsexo.getSelectedItem().toString());
        e.setfecha_contracto(txtfecha_contracto.getDate());
        e.setsueldo(Double.parseDouble(txtsueldo.getText()));
        e.setobservacion(txtobservacion.getText());
        i.setruta(txtruta.getText());
        
    }
    
    // ===========================================================================
    // Esta linea de codigo representa la declaracion de las variable para almacenar
    // la ruta de la imagen carga de un jfilechooser
    // ===========================================================================
    
    private void limpiar_t(){
        for (int c = 0; c < jt.getRowCount(); c++){
            modelo.removeRow(c);
        }
    }
    
    // ==================================================================================
    // Esta linea de codigo en adelante representa el metodo limpiar el cual nos va
    // permitir limpiar todos los cuadros de texto del formulario de registro de empleado
    // ===================================================================================
    
    private void limpiar(){
        txtnombre.setText("");
        txtapellido.setText("");
        txtcorreo.setText("");
        txttelefono.setText("");
        txtdireccion.setText("");
        txtfecha_nac.setDate(null);
        cbsexo.setSelectedItem("");
        txtfecha_contracto.setDate(null);
        txtruta.setText("");
        txtfecha_contracto.setDate(null);
        txtobservacion.setText("");
        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("icono.png")));
        lblimagen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("icono.png")));
        
    }
    
    // =======================================================================================================
    // Esta linea de codigo representa el metodo validar el cual nos va a permitir vefiricar si existe uno de
    // los controles vacio y detonarra un mensaje de alerta y no se pondran guardar los datos solicitados
    // =======================================================================================================
    
    private void validar(){
        if (txtapellido.getText().isEmpty() || txtnombre.getText().isEmpty()|| txtcorreo.getText().isEmpty()
        || txttelefono.getText().isEmpty() || txtdireccion.getText().isEmpty() || txtsueldo.getText().isEmpty()
        || txtobservacion.getText().isEmpty() || txtruta.getText().isEmpty() || txtfecha_nac.getDate() == null
        ||  txtfecha_contracto.getDate() == null){
            valida = 1;
            JOptionPane.showMessageDialog(null, "Por favor vefiricar los campos de registros hay algunos vacio");
            if (txtruta.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor seleccionar una imagen para este Empleado");
            }
             if (txtfecha_nac.getDate() == null || txtfecha_contracto.getDate() == null){
            JOptionPane.showMessageDialog(null, "Por favor introdusca la fechas solicitadas");
        }
            
        }
        else{
            valida = 0;
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
        abril = new javax.swing.JFileChooser();
        jtpanel = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lblapellido = new javax.swing.JLabel();
        lblcorreo = new javax.swing.JLabel();
        lbltelefono = new javax.swing.JLabel();
        lblfecha_nac = new javax.swing.JLabel();
        lblsexo = new javax.swing.JLabel();
        lblfecha_contrato = new javax.swing.JLabel();
        lblsueldo = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblimagen2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jsp = new javax.swing.JScrollPane();
        jt = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtapellido = new javax.swing.JTextField();
        txtcorreo = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        txtdireccion = new javax.swing.JTextField();
        cbsexo = new javax.swing.JComboBox<>();
        txtfecha_nac = new com.toedter.calendar.JDateChooser();
        txtfecha_contracto = new com.toedter.calendar.JDateChooser();
        txtsueldo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtruta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        lblimagen = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtobservacion = new javax.swing.JTextPane();
        btnguardar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jtpanel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel10.setLayout(null);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Nombre");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Correo");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Apellido");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Telefono");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("F/Nacimiento");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("F/Contracto");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Sueldo");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Sexo");

        lblnombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblnombre.setText("_____________________________");

        lblapellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblapellido.setText("_____________________________");

        lblcorreo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblcorreo.setText("_____________________________");

        lbltelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbltelefono.setText("_____________________________");

        lblfecha_nac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblfecha_nac.setText("_____________________________");

        lblsexo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblsexo.setText("_____________________________");

        lblfecha_contrato.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblfecha_contrato.setText("_____________________________");

        lblsueldo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblsueldo.setText("_____________________________");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbltelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblfecha_nac, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsexo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblfecha_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblfecha_nac, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsexo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblfecha_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbltelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel10.add(jPanel12);
        jPanel12.setBounds(0, 0, 720, 175);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lblimagen2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/icono.png"))); // NOI18N
        lblimagen2.setText("jLabel27");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblimagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(lblimagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel13);
        jPanel13.setBounds(726, 0, 284, 216);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Buscar Por :");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addContainerGap())
        );

        jPanel10.add(jPanel14);
        jPanel14.setBounds(0, 181, 720, 76);

        jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMouseClicked(evt);
            }
        });
        jsp.setViewportView(jt);

        jPanel10.add(jsp);
        jsp.setBounds(10, 270, 810, 220);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230618_Cancel.png"))); // NOI18N
        jButton6.setText("Eliminar");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton6);
        jButton6.setBounds(850, 263, 170, 70);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        jButton7.setText("Modificar");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton7);
        jButton7.setBounds(850, 340, 170, 70);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        jButton9.setText("Salir");
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel10.add(jButton9);
        jButton9.setBounds(850, 420, 170, 70);

        jtpanel.addTab("Consultar", jPanel10);

        jPanel2.setLayout(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Apellido");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Correo");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Direccion");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Telefono");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("F/Nacimiento");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Sexo");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("F/Contracto");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Sueldo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtdireccion))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbsexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfecha_contracto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfecha_nac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfecha_nac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbsexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfecha_contracto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(10, 27, 735, 250);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icono.png"))); // NOI18N
        lblimagen.setText("jLabel10");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addComponent(lblimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtruta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 194, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtruta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel4);
        jPanel4.setBounds(755, 27, 241, 250);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Observaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jScrollPane1.setViewportView(txtobservacion);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel5);
        jPanel5.setBounds(10, 283, 430, 220);

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230533_Save-as.png"))); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnguardar);
        btnguardar.setBounds(477, 292, 156, 57);

        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1475230403_edit-file.png"))); // NOI18N
        btnmodificar.setText("Modificar");
        btnmodificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        jPanel2.add(btnmodificar);
        btnmodificar.setBounds(477, 396, 156, 57);

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1476870877_Log Out.png"))); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(btnsalir);
        btnsalir.setBounds(765, 396, 156, 57);

        btnlimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appfloristeria/1475612418_edit-clear.png"))); // NOI18N
        btnlimpiar.setText("Limpiar");
        btnlimpiar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnlimpiar);
        btnlimpiar.setBounds(765, 292, 156, 57);

        jtpanel.addTab("Registrar", jPanel2);

        getContentPane().add(jtpanel);
        jtpanel.setBounds(0, 4, 1040, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == btnlimpiar){
            limpiar();
            JOptionPane.showMessageDialog(null, "aqui se limpiar");

        }
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        // TODO add your handling code here:
        validar();
        if(valida != 1){
        
        try{
            JOptionPane.showMessageDialog(null, "Entro");
            int dato = 0;
            obtener();
            dato = actualizar(getid_empleado());
            System.out.println(dato);
            if (dato > 0){
                JOptionPane.showMessageDialog(null, "Datos Actualizado correctamente");
                consulta(jt, modelo);
                limpiar();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al actualizar los datos solicitados");
            }
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    else{
         JOptionPane.showMessageDialog(null, "No se pudo registrar los datos");
          }
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:

        validar();
        if(valida != 1){
        try {
            
            int dato = 0, dato2 = 0;
            obtener();
            dato2 = ingresar();
            dato = registrar();

            if (dato2 > 0){
                ingresar();

                if (dato > 0 ){
                    registrar();
                    JOptionPane.showMessageDialog(null, "Datos Registrado correctamente");
                    limpiar();
                    limpiar_t();
                    consulta(jt, modelo);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error al registrar los datos");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al regsitrar los datos");
            }

        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            //Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
    } else{
         JOptionPane.showMessageDialog(null, "No se pudo registrar los datos");
          }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cargarImagen();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMouseClicked
        // TODO add your handling code here:
        tomardato();
    }//GEN-LAST:event_jtMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jtpanel.setSelectedIndex(1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
              try{
            int dato = 0;
            obtener();
            dato = eliminar(getid_empleado());
            System.out.println(dato);
            if (dato > 0){
                JOptionPane.showMessageDialog(null, "Datos Eliminado correctamente");
                limpiar();
                limpiar_t();
                consulta(jt, modelo);
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al eliminar los datos solicitados");
            }
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Empleado().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser abril;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JComboBox<String> cbsexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JTable jt;
    private javax.swing.JTabbedPane jtpanel;
    private javax.swing.JLabel lblapellido;
    private javax.swing.JLabel lblcorreo;
    private javax.swing.JLabel lblfecha_contrato;
    private javax.swing.JLabel lblfecha_nac;
    private javax.swing.JLabel lblimagen;
    private javax.swing.JLabel lblimagen2;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblsexo;
    private javax.swing.JLabel lblsueldo;
    private javax.swing.JLabel lbltelefono;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtdireccion;
    private com.toedter.calendar.JDateChooser txtfecha_contracto;
    private com.toedter.calendar.JDateChooser txtfecha_nac;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextPane txtobservacion;
    private javax.swing.JTextField txtruta;
    private javax.swing.JTextField txtsueldo;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables

    
    // =============================================================================================
    // Esta linea de codigo represena la clase Cargarimagen el cual nos va a permitir cargar la ruta
    // de una imagen y posteriormente almacenarla en la tabla imagen de la base de datos dbflores
    // ============================================================================================
}
