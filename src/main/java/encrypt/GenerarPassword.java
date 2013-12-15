/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt;

import java.util.Random;

/**
 *
 * @author insdrv00
 */
public class GenerarPassword {
    
    public String generarContrasinal(){
        String letras = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

       String password = new String();
            for( int i = 0; i < 8; i++ ) 
                password= password + letras.charAt(rnd.nextInt(letras.length()));
            return password.toString();
    }
}