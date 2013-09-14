/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.Set;
import modelo.UserProfile;
import modelo.UserProfileDaoHibernate;
import modelo.UserProfileDetails;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author insdrv00
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public ModelAndView comprobarUsuario(String usuario, String contrasinal) {
        UserProfileDaoHibernate UserProfileDao = new UserProfileDaoHibernate();
        UserProfileDao.setSessionFactory(sessionFactory);
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/acceder.jsp");
        if (UserProfileDao.existeUserProfile(usuario) == true){
            UserProfile user = UserProfileDao.obtenerUserProfile(usuario);
            vista.addObject("usuario", user.getUsuario());
            if (user.getContrasinal().equals(contrasinal)){
                vista.addObject("userRol", user.getUserRol().toString());
            }else{
                vista.addObject("contrasinal", false);
            }
        }else{
            vista.addObject("usuario", false);
        }
        return vista;
    }
    
    public ModelAndView obternerUsuario(String usuario){
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/perfil.jsp");
        
        UserProfileDaoHibernate userProfileDao =  new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);
        UserProfile userProfile = userProfileDao.obtenerUserProfile(usuario);

        Set<UserProfileDetails> userProfileDetails = userProfile.getUserProfileDetailses();
        
        vista.addObject("userProfile", userProfile);
        vista.addObject("userProfileDetails", userProfileDetails);
        
        return vista;
    }
}