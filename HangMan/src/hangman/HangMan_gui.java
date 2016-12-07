/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import javax.swing.JOptionPane;

/**
 *
 * @author JOSE
 */
public class HangMan_gui extends javax.swing.JFrame {

    /**
     * Creates new form HangMan_gui
     */
    public HangMan_gui() {
        
        initComponents();
        //lblimagen.setText("dfjkalsjfl");
        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/img0.jpg")));
    }

        
    private boolean iniciar;
/*    private String [] contenedor = { "ESCRIBIR", "APUNTAR", "ACEPTAR", "ACTUAR", "PERMITIR", "PREGUNTAR", 
                                         "EVITAR", "CAMBIAR", "CONSTRUIR", "Argentina","Australia","Brasil", 
                                         "Chile","China","Colombia", "Costa Rica", "Cuba", "Ecuador", "Egipto",
                                         "El Salvador", "España", "Estados Unidos", "Francia", "Haití", "Honduras",
                                         "Hong Kong", "Holanda", "India", "Indonesia", "Irak", "Irán","Irlanda",
                                         "Islas Caimán","Israel","Italia","Jamaica","Japón","Líbano","México",
                                         "Nicaragua","Noruega","Nueva Zelanda","Pakistán","Palestina","Palau",
                                         "Panamá","Paraguay","Perú","Portugal","Puerto Rico","Reino Unido",
                                         "República Dominicana","Ruanda","Rumania","Rusia","Sudáfrica","Suecia",
                                         "Suiza","Tailandia","Ucrania","Uruguay","Vanuatu","Vaticano","Venezuela",
                                         "Vietnam","Yemen","Yibuti","Zimbabue"};
    */
    private char [] palabra_oculta;
    private char [] palabra;
    int vidas = 6, img = 0;
    private boolean cambio = false;
    
    public void principal(String palabra){
        this.palabra_oculta = palabra.toCharArray();
        String objeto = "";
        System.out.println(this.palabra_oculta);
        
        for (int i = 0; i < this.palabra_oculta.length; i++){
            objeto += " _";
        }
        
        this.palabra = objeto.toCharArray();
        txtpalabraoculta.setText(objeto);
        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/img0.jpg")));
    }
    
    private void evaluar (char letra){
        iniciar = true;
        int a = 0, n=0;
        if (iniciar){
            String p = "";
            
            if (this.vidas == 0){
                JOptionPane.showMessageDialog(null, "ha perdido capullo \n"
                        + " dar clic en reiniciar si quiere jugar otra vez");
                iniciar = false;
            }
            else{
                for (int c = 0; c < this.palabra_oculta.length; c++){
                    
                   // JOptionPane.showMessageDialog(null, letra);
                    if (this.palabra_oculta[c] == letra){
                        //JOptionPane.showMessageDialog(null, "Es correcta");
                        this.palabra[c] = letra;
                        
                        a++;
                    }
                    else{
                        //JOptionPane.showMessageDialog(null, "No es correcto");
                        
                        n++;
                    }
                    p += this.palabra[c];
                }
                if ( a > 0){
                    JOptionPane.showMessageDialog(null, "Esta letra es correcta");
                    this.cambio = true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Esta letra no es correcta");
                    this.cambio = false;
                }
                txtpalabraoculta.setText(p);
                gano();

                if (this.cambio == false){
                    this.vidas -= 1;
                    this.img += 1;
                    if (this.vidas > 0){
                        JOptionPane.showMessageDialog(null, "Fulano te quedan"+this.vidas+" vidas");
                        lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/img"+img+".jpg")));
                    }
                    else {
                        this.cambio = false;
                    }
                    
                    
                }
            }
            
        }
        else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error \n"
                    + "Se ha quedado si vinda o no ha iniciado sesion Por favor inicie la sesion y por si acaso esto no es PHP");
        }
    }
    
    private void gano(){
        boolean ganar = false;
        
        for (int i = 0; i < this.palabra_oculta.length; i++){
            if (this.palabra[i] == this.palabra_oculta[i]){
                ganar = true;
            }
            else {
                ganar = false;
                break;
            }
        }
        if (ganar){
            JOptionPane.showMessageDialog(null, "Ganaste, Felicidades te libraste de la soga para ahocarte");
            
        }
    }
    
    private String adivinar(String palabra){
        return palabra;
    }
/*    private String aleatorio(){
    int num = (int)(Math.random()*(contenedor.length));
    return contenedor[num];
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtpalabraoculta = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btninicial = new javax.swing.JButton();
        btnreiniciar = new javax.swing.JButton();
        txtpalabraadiviniar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtpalabra = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblimagen = new javax.swing.JLabel();
        btningresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("HangMan");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Palabra oculta", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        txtpalabraoculta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpalabraocultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpalabraoculta, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpalabraoculta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Menu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        btninicial.setText("Inicial");
        btninicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninicialActionPerformed(evt);
            }
        });

        btnreiniciar.setText("Reiniciar");
        btnreiniciar.setToolTipText("");
        btnreiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreiniciarActionPerformed(evt);
            }
        });

        txtpalabraadiviniar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpalabraadiviniarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btninicial, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btnreiniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpalabraadiviniar, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btninicial, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnreiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtpalabraadiviniar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Introdusca una letra", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        txtpalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpalabraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpalabra, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtpalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Nivel de Ahorcado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(lblimagen)
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(lblimagen)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        btningresar.setText("Ingresar");
        btningresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btningresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btningresar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(334, 334, 334))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(527, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btningresar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(280, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtpalabraocultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpalabraocultaActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtpalabraocultaActionPerformed

    private void txtpalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpalabraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpalabraActionPerformed

    private void btningresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btningresarActionPerformed
        // TODO add your handling code here:

        
         if (evt.getSource() == btningresar){
           // JOptionPane.showMessageDialog(null, "todo anda bien");
            String var = txtpalabra.getText();
            evaluar(var.charAt(0));
            txtpalabra.setText("");
        }
    }//GEN-LAST:event_btningresarActionPerformed

    private void btninicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninicialActionPerformed
        // TODO add your handling code here:
            if (evt.getSource() == btninicial){
                if (txtpalabraadiviniar.getText() != null){
                    if (this.iniciar == false){
                       this.iniciar = true;
                        JOptionPane.showMessageDialog(null, "Has iniciado sesion correctamente");
                         principal(txtpalabraadiviniar.getText()); 
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Debe de Reiniciar sesion");

                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Por favor introdusca la palabra a adivinar antes de iniciar");
                }
                    
                

        }
    }//GEN-LAST:event_btninicialActionPerformed

    private void btnreiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreiniciarActionPerformed
        // TODO add your handling code here:
         if (evt.getSource() == btnreiniciar){
                if (this.iniciar == true){
                   this.iniciar = true;
                    JOptionPane.showMessageDialog(null, "Has Reiniciado sesion correctamente");
                     principal(txtpalabraadiviniar.getText()); 
                }
         }   
    }//GEN-LAST:event_btnreiniciarActionPerformed

    private void txtpalabraadiviniarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpalabraadiviniarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpalabraadiviniarActionPerformed

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
            java.util.logging.Logger.getLogger(HangMan_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HangMan_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HangMan_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HangMan_gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HangMan_gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btningresar;
    private javax.swing.JButton btninicial;
    private javax.swing.JButton btnreiniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblimagen;
    private javax.swing.JTextField txtpalabra;
    private javax.swing.JTextField txtpalabraadiviniar;
    private javax.swing.JTextField txtpalabraoculta;
    // End of variables declaration//GEN-END:variables
}
