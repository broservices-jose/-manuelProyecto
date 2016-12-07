/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author JOSE
 */
public class Filtro extends FileFilter {


    @Override
    public boolean accept(File file) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if( file.isDirectory() )      
         return true;
       
      String nombreArchivo = file.getName().toLowerCase();
      if( nombreArchivo != null )
         if( nombreArchivo.endsWith(".jpeg") ||
            nombreArchivo.endsWith(".jpg")  ||
            nombreArchivo.endsWith(".png")  ||
            nombreArchivo.endsWith(".gif")
           )
            return true;
       
      return false;    
    }

    @Override
    public String getDescription() {
        return "Archivos de  imagenes (*.jpg, *.png, *.gif)";

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
