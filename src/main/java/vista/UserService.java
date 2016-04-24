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
     public ModelAndView contactar(String usuario, String nome, String email, String asunto, String texto);
     public ModelAndView enviarRegistro(String email,String usuario, String password, String password2);
     public ModelAndView rematarRexistro(String usuario, String key);
     public ModelAndView completarPerfil(String usuario, String key, String nome, String apelido1, String apelido2,
                String telefono);
     public ModelAndView obtenerPerfil(String usuario);
     public ModelAndView actualizarPrefil(String usuario,String email, String nome, String apelido1, String apelido2,
             String telefono, String password, String re_password, String re_password_2);
     public ModelAndView contacto(String usuario);
     public ModelAndView congreso(String usuario);
     public ModelAndView altaCongreso(String usuario, String nombreCongreso, Integer IdEstadoCongreso, String fechaInicioEnvio, String fechaFinEnvio,
             String fechaInicioRevision, String fechaFinRevision);
     public ModelAndView modificaCongreso(String usuario, Integer idCongreso, String nombreCongreso, Integer IdEstadoCongreso, 
             String fechaInicioEnvio, String fechaFinEnvio, String fechaInicioRevision, String fechaFinRevision);
     public ModelAndView trabajos(String usuario);
     public ModelAndView altaTrabajo(String usuario, String nomeTraballo, Integer categoria, String autores, byte[] traballo);
     public ModelAndView accioneditarTrabajo(String usuario, Integer idTraballoDetalle);
     public ModelAndView accioneliminarTrabajo(String usuario, Integer idTraballoDetalle);
     public ModelAndView borrarTraballo(String usuario, Integer idTraballoDetalle);
     public ModelAndView anadirtrabajos();
     public ModelAndView modificacionTraballo(String usuario, Integer idTraballoDetalle, String nomeTraballo, Integer categoria, String autores, byte[] traballo);
}