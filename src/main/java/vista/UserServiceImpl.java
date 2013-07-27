/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.HashMap;
import java.util.Map;
import modelo.GenericDaoHibernate;
import modelo.UserProfile;
import org.springframework.web.servlet.ModelAndView;

import modelo.UserProfileDaoHibernate;
import modelo.UserRol;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author insdrv00
 */
public class UserServiceImpl implements UserService {
  
    private static final SessionFactory sessionFactory;

    static {
        try {
            //Aqui creamos una session de la fabrica de session usando
            //el archivo de configuracion hibernate.cfg.xml
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public ModelAndView comprobarUsuario(String usuario, String contrasinal) {
        
        UserProfileDaoHibernate user = new UserProfileDaoHibernate(sessionFactory);

        Map<String, Object> modelo = new HashMap<String, Object>();

        UserRol rol = new UserRol(3, "autor", null);
        UserProfile usuario2 = new UserProfile(rol, usuario, contrasinal);
        System.out.println("Antes de guardar usuario");
        System.out.println("Session: " + user.getCurrentSession());
        user.guardarUserProfile(usuario2);
        System.out.println("Despois de guardar usuario");
        if (user.existeUserProfile(usuario) == false) {
            System.out.println("Usuario false");
            modelo.put("usuario", usuario);
            modelo.put("contrasinal", "Este usuario non existe");
            ModelAndView vista = new ModelAndView("WEB-INF/jsp/acceder.jsp");
            return vista;
        } else {
            System.out.println("usuario true");
            modelo.put("usuario", user.obtenerUserProfile(usuario).getUsuario().toString());
            modelo.put("contrasinal", user.obtenerUserProfile(usuario).getContrasinal().toString());
            ModelAndView vista = new ModelAndView("WEB-INF/jsp/acceder.jsp");
            return vista;
        }
    }
}
