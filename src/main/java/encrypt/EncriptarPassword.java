/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author insdrv00
 */
public class EncriptarPassword extends GenerarPassword{
    
    public String encriptarContrasinal(String contrasinal) {
        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(contrasinal);
        return encodedPassword;
    }

    public boolean compararContrasinal(String contrasinalAp, String contrasinalBd) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(contrasinalAp, contrasinalBd);
        //return this.encriptarContrasinal(this.encriptarContrasinal(contrasinalAp)).equals(contrasinalBd);
    }
}