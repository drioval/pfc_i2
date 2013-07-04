/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author insdrv00
 */
public interface UserService {
     public ModelAndView comprobarUsuario(String usuario, String contrasinal);
    
}
