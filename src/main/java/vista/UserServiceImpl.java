/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import encrypt.EncriptarPassword;
import encrypt.GenerarPassword;
import java.util.Set;
import modelo.UserProfile;
import modelo.UserProfileDaoHibernate;
import modelo.UserProfileDetails;
import modelo.UserProfileDetailsDaoHibernate;
import modelo.UserRol;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import recursos.EnviarEmail;

/**
 *
 * @author insdrv00
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService {

    private SessionFactory sessionFactory;
    
    private @Value(value="${config.url}") String url;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public ModelAndView comprobarUsuario(String usuario, String contrasinal) {
        UserProfileDaoHibernate UserProfileDao = new UserProfileDaoHibernate();
        UserProfileDao.setSessionFactory(sessionFactory);
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/acceder.jsp");
        if (UserProfileDao.existeUserProfile(usuario) == true) {
            UserProfile user = UserProfileDao.obtenerUserProfile(usuario);
            vista.addObject("usuario", user.getUsuario());
            if (user.getContrasinal().equals(contrasinal)) {
                vista.addObject("userRol", user.getUserRol());
            } else {
                vista.addObject("contrasinal", false);
            }
        } else {
            vista.addObject("usuario", false);
        }
        return vista;
    }

    public ModelAndView obternerUsuario(String usuario) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/perfil.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);
        UserProfile userProfile = userProfileDao.obtenerUserProfile(usuario);

        Set<UserProfileDetails> userProfileDetails = userProfile.getUserProfileDetailses();

        vista.addObject("userProfile", userProfile);
        vista.addObject("userProfileDetails", userProfileDetails);

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView reenviarContrasinal(String email, String usuario) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/reenviar.jsp");

        if (email.equals("")) {
            if (usuario.equals("")) {
                vista.addObject("mensaxe", "Parece que introduciches información non válida. Por favor, revise os campos e ténteo de novo.");
            } else {
                UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
                userProfileDao.setSessionFactory(sessionFactory);

                if (userProfileDao.existeUserProfile(usuario) == true) {
                    UserProfile user = userProfileDao.obtenerUserProfile(usuario);
                    Set<UserProfileDetails> userProfileDetails = user.getUserProfileDetailses();
                    UserProfileDetails detalleUsuario = (UserProfileDetails) userProfileDetails.toArray()[0];

                    GenerarPassword pass = new GenerarPassword();
                    String newPassword = pass.generarContrasinal();
                    EncriptarPassword passEncripted = new EncriptarPassword();
                    user.setContrasinal(passEncripted.encriptarContrasinal(newPassword).toString());

                    EnviarEmail enviarEmail = new EnviarEmail();
                    String asunto = "Congreso 2014: Solicitude de reenvio do contrasinal";
                    String cuerpo = "Solicitouse o reenvio do contrasinal para o seu usuario. "
                            + "A continuación indicamoslle os seus datos de acceso: ";
                    cuerpo = cuerpo + "<br><br> Usuario: " + usuario + "<br><br> Contrasinal: " + newPassword
                            + "<br><br> Un saludo.";
                    enviarEmail.enviarEmail(detalleUsuario.getEmail(), asunto, cuerpo);

                    vista.addObject("mensaxe", "Enviouselle a súa dirección de correo electrónico as instruccións para reestablecer o seu contrasinal.");
                    vista.addObject("usuario", user.getUsuario());
                    vista.addObject("email", detalleUsuario.getEmail());
                } else {
                    vista.addObject("mensaxe", "O usuario introducido non existe. Por favor, verifique que o usuario e correcto.");
                    vista.addObject("usuario", usuario);
                }
            }
        } else {
            UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
            userProfileDetailsDao.setSessionFactory(sessionFactory);

            UserProfileDetails detalleUsuario = userProfileDetailsDao.obtenerUserProfileDetailsEmail(email);
            if (detalleUsuario == null) {
                vista.addObject("mensaxe", "O email introducido non existe. Por favor, verifique que o email e correcto.");
                vista.addObject("email", email);
            } else {
                GenerarPassword pass = new GenerarPassword();
                String newPassword = pass.generarContrasinal();
                EncriptarPassword passEncripted = new EncriptarPassword();

                UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
                userProfileDao.setSessionFactory(sessionFactory);

                UserProfile user = userProfileDao.obtenerUserProfile(detalleUsuario.getUserid());
                user.setContrasinal(passEncripted.encriptarContrasinal(newPassword).toString());
                userProfileDao.guardarUserProfile(user);

                String asunto = "Congreso 2014: Solicitude de reenvio do contrasinal";
                String cuerpo = "Solicitouse o reenvio de contrasinal para o seu usuario. "
                        + "A continuación indicamoslle os seus datos de acceso: ";
                cuerpo = cuerpo + "<br><br> Usuario: " + user.getUsuario().toString() + "<br><br> Contrasinal: " + newPassword
                        + "<br><br> Un saludo.";

                EnviarEmail enviarEmail = new EnviarEmail();
                enviarEmail.enviarEmail(detalleUsuario.getEmail(), asunto, cuerpo);

                vista.addObject("mensaxe", "Enviouselle a súa dirección de correo electrónico as instruccións para reestablecer o seu contrasinal.");
                vista.addObject("usuario", user.getUsuario().toString());
                vista.addObject("email", detalleUsuario.getEmail());
            }
        }
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView contactar(String nome, String email, String asunto, String texto) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/contactar.jsp");

        if (nome.equals("")) {
            vista.addObject("mensaxe", "Parece que introduciches información non válida. Por favor, revise os campos e ténteo de novo.");
        } else if (email.equals("")) {
            vista.addObject("mensaxe", "Parece que introduciches información non válida. Por favor, revise os campos e ténteo de novo.");
        } else if (texto.equals("")) {
            vista.addObject("mensaxe", "Parece que introduciches información non válida. Por favor, revise os campos e ténteo de novo.");
        } else {
            EnviarEmail enviarEmail = new EnviarEmail();
            String asuntoMail = asunto;
            String cuerpo = "Contacto:" + nome + "<br><br> Email: " + email + "<br><br>" + texto;
            enviarEmail.enviarEmail("congresocientifico2014@gmail.com", asuntoMail, cuerpo);

            vista.addObject("mensaxe", "Enviouselle a súa dirección de correo electrónico as instruccións para reestablecer o seu contrasinal.");
        }
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView enviarRegistro(String email, String usuario, String password, String password2) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/registro_error.jsp");
        System.out.println("Ando por aqui: enviando registro");
        if (password.equals(password2)) {

            UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
            userProfileDao.setSessionFactory(sessionFactory);
            if (userProfileDao.existeUserProfile(usuario) == false) {
                UserProfileDetailsDaoHibernate userDetails = new UserProfileDetailsDaoHibernate();
                userDetails.setSessionFactory(sessionFactory);
                if (userDetails.obtenerUserProfileDetailsEmail(email) == null) {
                    EncriptarPassword passEncripted = new EncriptarPassword();

                    UserRol rol = new UserRol(3);

                    UserProfile usuarioNovo = new UserProfile(rol, usuario, passEncripted.encriptarContrasinal(password));
                    userProfileDao.guardarUserProfile(usuarioNovo);

                    UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
                    userProfileDetailsDao.setSessionFactory(sessionFactory);

                    UserProfileDetails usuarioDetalle = new UserProfileDetails(null, null, null, email, null, usuarioNovo.getUserId());

                    userProfileDetailsDao.guardarUserProfileDetails(usuarioDetalle);
                    
                    System.out.println("Url:"+url);
                    
                    String asunto = "Congreso 2014: Alta de usuario " + usuario;
                    String cuerpo = "Solicitouse voste a alta como autor/a para o Congreso Cientfífico 2014. "
                            + "<br><br> Usuario: " + usuario.toString() + "<br><br>"
                            + "Para confirmar a alta como usuario, por favor, prema no seguinte enlace e complete "
                            + "os datos do seu perfil: <br><br>";
                    cuerpo = cuerpo + "<href>" + url + "/rematar_rexistro.htm?user=" + usuarioNovo.getUsuario().toString()
                            + "&key=" + usuarioNovo.getContrasinal().toString() + "<href>"
                            + "<br><br> Un saudo.</href>";

                    EnviarEmail enviarEmail = new EnviarEmail();
                    enviarEmail.enviarEmail(email, asunto, cuerpo);

                    vista = new ModelAndView("WEB-INF/jsp/registro_correcto.jsp");

                    vista.addObject("usuario", usuario);
                    vista.addObject("email", email);

                } else {
                    vista.addObject("mensaxeEmail", "Email xa rexistrado.");
                }
            } else {
                vista.addObject("mensaxeUsuario", "Usuari@ xa rexistrado.");
            }
        } else {
            vista.addObject("mensaxePassword", "Os contrasinais non coinciden.");
        }

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView rematarRexistro(String usuario, String key) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/completar_perfil.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        if (userProfileDao.existeUserProfile(usuario) == true) {
            if (userProfileDao.obtenerUserProfile(usuario).getContrasinal().equals(key) == true) {
                if (userProfileDao.existeUserProfileActivo(usuario) == true) {
                    vista.addObject("userId", userProfileDao.obtenerUserProfile(usuario).getUserId());
                    vista = new ModelAndView("WEB-INF/jsp/error_rexistro.jsp");
                }
            }
        }
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView completarPerfil(String usuario, String key, String nome, String apelido1, String apelido2,
            String telefono) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/registro_completo.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        if (userProfileDao.existeUserProfile(usuario) == true) {
            if (userProfileDao.obtenerUserProfile(usuario).getContrasinal().equals(key) == true) {
                if (userProfileDao.existeUserProfileActivo(usuario) == true) {
                    vista.addObject("userId", userProfileDao.obtenerUserProfile(usuario).getUserId());
                    vista = new ModelAndView("WEB-INF/jsp/error_completar.jsp");
                } else {

                    UserProfile usuarioRexistrado = userProfileDao.obtenerUserProfile(usuario);
                    usuarioRexistrado.setActivo(1);
                    userProfileDao.guardarUserProfile(usuarioRexistrado);

                    Integer userId = usuarioRexistrado.getUserId();

                    Set<UserProfileDetails> userProfileDetails = usuarioRexistrado.getUserProfileDetailses();
                    UserProfileDetails detalleUsuario = (UserProfileDetails) userProfileDetails.toArray()[0];

                    detalleUsuario.setNome(nome);
                    detalleUsuario.setApelido1(apelido1);
                    detalleUsuario.setApelido2(apelido2);
                    detalleUsuario.setTelefono(telefono);

                    UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
                    userProfileDetailsDao.setSessionFactory(sessionFactory);

                    userProfileDetailsDao.guardarUserProfileDetails(detalleUsuario);
                }
            }
        }

        return vista;
    }
}