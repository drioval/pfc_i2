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
     public ModelAndView confirmarTraballo(String usuario, Integer idTraballo);
     public ModelAndView accionConfirmarTraballo(String usuario, Integer idTraballo);
     public ModelAndView anadirtrabajos(String usuario);
     public ModelAndView modificacionTraballo(String usuario, Integer idTraballoDetalle, String nomeTraballo, Integer categoria, String autores, byte[] traballo);
     public ModelAndView accionVerDatosTrabajo(String usuario, Integer idTraballo);
     public ModelAndView accionRevisarTraballo(String usuario, Integer idTraballo);
     public ModelAndView enviaRevision(String usuario, Integer idTraballo,String informePublico, String informePrivado, Integer puntuacion, Integer recomendacion);
     public ModelAndView ver_lista_revisiones(String usuario, Integer idTraballo);
     public ModelAndView ver_revision_traballo(String usuario, Integer idTraballo);
     public ModelAndView accionAceptarTraballo(String usuario,Integer idTraballo);
     public ModelAndView accionRexeitarTraballo(String usuario,Integer idTraballo);
     public ModelAndView aceptarTraballo(String usuario,Integer idTraballo);
     public ModelAndView rexeitarTraballo(String usuario,Integer idTraballo);
}