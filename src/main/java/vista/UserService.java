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
     public ModelAndView reenviarContrasinal(String email, String usuario);
     public ModelAndView contactar(String nome, String email, String asunto, String texto);
     public ModelAndView enviarRegistro(String email,String usuario, String password, String password2);
}
