/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import encrypt.EncriptarPassword;
import encrypt.GenerarPassword;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import modelo.Congreso;
import modelo.CongresoDaoHibernate;
import modelo.CongresoDetalle;
import modelo.CongresoDetalleDaoHibernate;
import modelo.EstadoCongreso;
import modelo.EstadoCongresoDaoHibernate;
import modelo.EstadoTraballo;
import modelo.EstadoTraballoDaoHibernate;
import modelo.Traballo;
import modelo.TraballoDaoHibernate;
import modelo.TraballoDetalle;
import modelo.TraballoDetalleDaoHibernate;
import modelo.UserProfile;
import modelo.UserProfileDaoHibernate;
import modelo.UserProfileDetails;
import modelo.UserProfileDetailsDaoHibernate;
import modelo.UserRol;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import recursos.EnviarEmail;
import recursos.ToTimeStamp;

/**
 *
 * @author insdrv00
 */
@Service(value = "UserService")
@PropertySource("classpath:/config.properties")
public class UserServiceImpl implements UserService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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
                if (UserProfileDao.existeUserProfileActivo(usuario) == false) {
                    vista.setViewName("WEB-INF/jsp/completar_perfil.jsp");
                } else {
                    vista.addObject("userRol", user.getUserRol());
                }
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
                vista.addObject("mensaxe", "recordar_error_01");
                vista.setViewName("WEB-INF/jsp/recordar_error.jsp");
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
                    String asunto = "Congreso 2015: Solicitude de reenvio do contrasinal";
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

                String asunto = "Congreso 2015: Solicitude de reenvio do contrasinal";
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
    public ModelAndView contactar(String usuario, String nome, String email, String asunto, String texto) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/contactar.jsp");
        if (usuario != null) {
            vista.setViewName("WEB-INF/jsp/access/contactar.jsp");
            vista.addObject("usuario", usuario);
        }

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
    @Bean
    public ModelAndView enviarRegistro(String email, String usuario, String password, String password2) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/registro_error.jsp");
        if (password.equals(password2)) {
            UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
            userProfileDao.setSessionFactory(sessionFactory);
            UserProfileDetailsDaoHibernate userDetails = new UserProfileDetailsDaoHibernate();
            userDetails.setSessionFactory(sessionFactory);
            if (userProfileDao.existeUserProfile(usuario) == false) {
                if (userDetails.obtenerUserProfileDetailsEmail(email) == null) {
                    EncriptarPassword passEncripted = new EncriptarPassword();

                    UserRol rol = new UserRol(3);

                    UserProfile usuarioNovo = new UserProfile(rol, usuario, passEncripted.encriptarContrasinal(password));
                    userProfileDao.guardarUserProfile(usuarioNovo);

                    UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
                    userProfileDetailsDao.setSessionFactory(sessionFactory);

                    UserProfileDetails usuarioDetalle = new UserProfileDetails(null, null, null, email, null, usuarioNovo.getUserId());

                    userProfileDetailsDao.guardarUserProfileDetails(usuarioDetalle);

                    String asunto = "Congreso 2015: Alta de usuario " + usuario;
                    String cuerpo = "Solicitouse voste a alta como autor/a para o Congreso Cientfífico 2015. "
                            + "<br><br> Usuario: " + usuario.toString() + "<br><br>"
                            + "Para confirmar a alta como usuario, por favor, prema no seguinte enlace e complete "
                            + "os datos do seu perfil: <br><br>";
                    cuerpo = cuerpo + "<href>http://localhost:8084/congreso_1.4/rematar_rexistro.htm?user=" + usuarioNovo.getUsuario().toString()
                            + "&key=" + usuarioNovo.getContrasinal().toString() + "</href>"
                            + "<br><br> Un saudo";

                    EnviarEmail enviarEmail = new EnviarEmail();
                    enviarEmail.enviarEmail(email, asunto, cuerpo);

                    vista = new ModelAndView("WEB-INF/jsp/registro_correcto.jsp");

                    vista.addObject("usuario", usuario);
                    vista.addObject("email", email);

                } else {
                    vista.addObject("email", email);
                    vista.addObject("usuario", userProfileDao.obtenerUserProfile(userDetails.obtenerUserProfileDetailsEmail(email).getUserid()).getUsuario());
                }
            } else {
                vista.addObject("email", userDetails.obtenerUserProfileDetails(userProfileDao.obtenerUserProfile(usuario).getUserId()).getEmail());
                vista.addObject("usuario", usuario);
            }
        } else {
            vista.setViewName("WEB-INF/jsp/registar_error.jsp");
            vista.addObject("mensaxePassword", "reg_msg_error_01");
            vista.addObject("email", email);
            vista.addObject("usuario", usuario);
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

                    vista.addObject("usuario", usuario);
                    vista.addObject("email", detalleUsuario.getEmail());
                    vista.addObject("nome", nome);
                    vista.addObject("apelido1", apelido1);
                    vista.addObject("apelido2", apelido2);
                    vista.addObject("telefono", telefono);
                }
            }
        }

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView obtenerPerfil(String usuario) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/perfil_usuario.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDao.setSessionFactory(sessionFactory);

        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        UserProfileDetails userDetails = userProfileDetailsDao.obtenerUserProfileDetails(user.getUserId());

        vista.addObject("usuario", user.getUsuario());
        vista.addObject("email", userDetails.getEmail());
        vista.addObject("nome", userDetails.getNome());
        vista.addObject("apelido1", userDetails.getApelido1());
        vista.addObject("apelido2", userDetails.getApelido2());
        vista.addObject("telefono", userDetails.getTelefono());

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView actualizarPrefil(String usuario, String email, String nome, String apelido1, String apelido2,
            String telefono, String password, String re_password, String re_password_2) {
        ModelAndView vista = new ModelAndView("/WEB-INF/jsp/access/perfil_actualizado.jsp");
        vista.addObject("usuario", usuario);
        vista.addObject("email", email);
        vista.addObject("nome", nome);
        vista.addObject("apelido1", apelido1);
        vista.addObject("apelido2", apelido2);
        vista.addObject("telefono", telefono);

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        Set<UserProfileDetails> userProfileDetails = user.getUserProfileDetailses();
        UserProfileDetails userDetails = (UserProfileDetails) userProfileDetails.toArray()[0];

        UserProfileDetailsDaoHibernate userProfileDetailsDao = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDao.setSessionFactory(sessionFactory);

        UserProfileDetails usuarioDetalleEmail = userProfileDetailsDao.obtenerUserProfileDetailsEmail(email);

        if (usuarioDetalleEmail != null) {
            if (usuarioDetalleEmail.getUserid().compareTo(user.getUserId()) != 0) {
                vista.setViewName("/WEB-INF/jsp/access/error_act_prefil.jsp");
                vista.addObject("errorActualizarPerfil", "msg_act_perfil04");
                return vista;
            }
        }

        String contrasinalBD = userProfileDao.obtenerUserProfile(usuario).getContrasinal();
        EncriptarPassword comparaPassword = new EncriptarPassword();

        if (comparaPassword.compararContrasinal(password, contrasinalBD)) {
            boolean haiCambioPassword = true;
            boolean cambiarPassword = false;
            if (re_password.equals("")) {
                haiCambioPassword = false;
            }
            if (haiCambioPassword) {
                if (re_password.equals(re_password_2)) {
                    cambiarPassword = true;
                } else {
                    vista.setViewName("/WEB-INF/jsp/access/error_act_prefil.jsp");
                    vista.addObject("errorActualizarPerfil", "msg_act_perfil01");
                    return vista;
                }
            }

            if (cambiarPassword) {
                user.setContrasinal(comparaPassword.encriptarContrasinal(re_password));
                userProfileDao.guardarUserProfile(user);
            }

            userDetails.setNome(nome);
            userDetails.setApelido1(apelido1);
            userDetails.setApelido2(apelido2);
            userDetails.setEmail(email);
            userDetails.setTelefono(telefono);

            userProfileDetailsDao.guardarUserProfileDetails(userDetails);

        } else {
            vista.setViewName("/WEB-INF/jsp/access/error_act_prefil.jsp");
            vista.addObject("errorActualizarPerfil", "msg_act_perfil02");
            return vista;
        }
        vista.addObject("perfilActualizado", "msg_act_perfil03");
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView contacto(String usuario) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/contacto.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        Set<UserProfileDetails> userProfileDetails = user.getUserProfileDetailses();
        UserProfileDetails userDetails = (UserProfileDetails) userProfileDetails.toArray()[0];

        vista.addObject("usuario", usuario);
        vista.addObject("nome", userDetails.getNome() + " " + userDetails.getApelido1() + " " + userDetails.getApelido2());
        vista.addObject("email", userDetails.getEmail());

        return vista;
    }

    @Override
    public ModelAndView congreso(String usuario) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/congreso.jsp");
        if (usuario == null) {
            return vista;
        }
        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
        congresoDao.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDao.obtenerCongresoActivo();

        Integer rol = user.getUserRol().getRolId();
        switch (rol) {
            case 1://rollid: 1 - Organizador

                if (congreso == null) {
                    vista.setViewName("/WEB-INF/jsp/access/alta_congreso.jsp");
                    vista.addObject("usuario", usuario);
                } else {
                    vista.setViewName("/WEB-INF/jsp/access/admin_congreso.jsp");
                    System.out.println(congreso.getIdCongreso());
                    vista.addObject("idCongreso", congreso.getIdCongreso());
                    vista.addObject("nombreCongreso", congreso.getNomeCongreso());
                    vista.addObject("idEstadoCongreso", congreso.getEstadoCongreso().getIdEstadoCongreso());
                    vista.addObject("estadoCongreso", congreso.getEstadoCongreso().getNomeEstadoCongreso());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY");
                    vista.addObject("fechaInicioEnvio", dateFormat.format(congreso.getCongresoDetalle().getfInicioEnvio()));
                    vista.addObject("fechaFinEnvio", dateFormat.format(congreso.getCongresoDetalle().getfFinEnvio()));
                    vista.addObject("fechaInicioRevision", dateFormat.format(congreso.getCongresoDetalle().getfInicioRevision()));
                    vista.addObject("fechaFinRevision", dateFormat.format(congreso.getCongresoDetalle().getfFinRevision()));
                }
                break;
            case 2://rollid: 2 - Revisor
                vista.setViewName("WEB-INF/jsp/access/congreso.jsp");
                break;
            case 3://rollid: 3 - Autor
                vista.setViewName("WEB-INF/jsp/access/congreso.jsp");
        }
        vista.addObject("usuario", usuario);
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView altaCongreso(String usuario, String nombreCongreso, Integer idEstadoCongreso, String fechaInicioEnvio, String fechaFinEnvio,
            String fechaInicioRevision, String fechaFinRevision) {

        CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
        congresoDao.setSessionFactory(sessionFactory);

        CongresoDetalleDaoHibernate congresoDetalleDaoHibernate = new CongresoDetalleDaoHibernate();
        congresoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        ToTimeStamp fecha = new ToTimeStamp();
        Timestamp fechaIEnvio = fecha.convertToTimeStamp(fechaInicioEnvio);
        Timestamp fechaFEnvio = fecha.convertToTimeStamp(fechaFinEnvio);
        Timestamp fechaIRevision = fecha.convertToTimeStamp(fechaInicioRevision);
        Timestamp fechaFRevision = fecha.convertToTimeStamp(fechaFinRevision);

        EstadoCongresoDaoHibernate estadoCongresoDao = new EstadoCongresoDaoHibernate();
        estadoCongresoDao.setSessionFactory(sessionFactory);
        EstadoCongreso estadoCongreso = estadoCongresoDao.obtenerEstadoCongreso(idEstadoCongreso);

        Congreso congreso = new Congreso(nombreCongreso, null, estadoCongreso);
        congresoDao.guardarCongreso(congreso);

        CongresoDetalle congresoDetalle = new CongresoDetalle(fechaIEnvio, fechaFEnvio, fechaIRevision, fechaFRevision, congreso);
        congresoDetalleDaoHibernate.guardarCongresoDetalle(congresoDetalle);

        congreso.setCongresoDetalle(congresoDetalle);
        congresoDao.guardarCongreso(congreso);

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_congreso.jsp");
        vista.addObject("idCongreso", congreso.getIdCongreso());
        vista.addObject("usuario", usuario);
        vista.addObject("nombreCongreso", nombreCongreso);
        vista.addObject("idEstadoCongreso", idEstadoCongreso);
        vista.addObject("estadoCongreso", estadoCongreso.getNomeEstadoCongreso());
        vista.addObject("fechaInicioEnvio", fechaInicioEnvio);
        vista.addObject("fechaFinEnvio", fechaFinEnvio);
        vista.addObject("fechaInicioRevision", fechaInicioRevision);
        vista.addObject("fechaFinRevision", fechaFinRevision);
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView modificaCongreso(String usuario, Integer idCongreso, String nombreCongreso, Integer idEstadoCongreso,
            String fechaInicioEnvio, String fechaFinEnvio, String fechaInicioRevision, String fechaFinRevision) {

        CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
        congresoDao.setSessionFactory(sessionFactory);

        CongresoDetalleDaoHibernate congresoDetalleDaoHibernate = new CongresoDetalleDaoHibernate();
        congresoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        EstadoCongresoDaoHibernate estadoCongresoDao = new EstadoCongresoDaoHibernate();
        estadoCongresoDao.setSessionFactory(sessionFactory);

        Congreso congreso = congresoDao.obtenerCongreso(idCongreso);
        congreso.setNomeCongreso(nombreCongreso);
        congreso.setEstadoCongreso(estadoCongresoDao.obtenerEstadoCongreso(idEstadoCongreso));

        CongresoDetalle congresoDetalle = congresoDao.obtenerCongreso(idCongreso).getCongresoDetalle();

        ToTimeStamp fecha = new ToTimeStamp();
        Timestamp fechaIEnvio = fecha.convertToTimeStamp(fechaInicioEnvio);
        Timestamp fechaFEnvio = fecha.convertToTimeStamp(fechaFinEnvio);
        Timestamp fechaIRevision = fecha.convertToTimeStamp(fechaInicioRevision);
        Timestamp fechaFRevision = fecha.convertToTimeStamp(fechaFinRevision);
        System.out.println(fechaInicioEnvio);
        System.out.println(fecha.convertToTimeStamp(fechaInicioEnvio));
        congresoDetalle.setfInicioEnvio(fechaIEnvio);
        congresoDetalle.setfFinEnvio(fechaFEnvio);
        congresoDetalle.setfInicioRevision(fechaIRevision);
        congresoDetalle.setfFinRevision(fechaFRevision);

        congresoDetalleDaoHibernate.guardarCongresoDetalle(congresoDetalle);
        congresoDao.guardarCongreso(congreso);

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_congreso.jsp");
        vista.addObject("idCongreso", congreso.getIdCongreso());
        vista.addObject("usuario", usuario);
        vista.addObject("nombreCongreso", nombreCongreso);
        vista.addObject("idEstadoCongreso", idEstadoCongreso);
        vista.addObject("estadoCongreso", estadoCongresoDao.obtenerEstadoCongreso(idEstadoCongreso).getNomeEstadoCongreso());
        vista.addObject("fechaInicioEnvio", fechaInicioEnvio);
        vista.addObject("fechaFinEnvio", fechaFinEnvio);
        vista.addObject("fechaInicioRevision", fechaInicioRevision);
        vista.addObject("fechaFinRevision", fechaFinRevision);
        return vista;
    }

    @Override
    public ModelAndView trabajos(String usuario) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/registar.jsp");
        if (usuario == null) {
            return vista;
        }
        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);

        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
        congresoDao.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDao.obtenerCongresoActivo();

        Integer rol = user.getUserRol().getRolId();
        switch (rol) {
            case 1://rollid: 1 - Organizador

                if (congreso == null) {
                    vista.setViewName("/WEB-INF/jsp/access/alta_congreso.jsp");
                    vista.addObject("usuario", usuario);
                } else {
                    vista.setViewName("/WEB-INF/jsp/access/admin_traballos.jsp");
                    System.out.println(congreso.getIdCongreso());
                    vista.addObject("idCongreso", congreso.getIdCongreso());
                    vista.addObject("nombreCongreso", congreso.getNomeCongreso());
                    vista.addObject("idEstadoCongreso", congreso.getEstadoCongreso().getIdEstadoCongreso());
                    vista.addObject("estadoCongreso", congreso.getEstadoCongreso().getNomeEstadoCongreso());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY");
                    vista.addObject("fechaInicioEnvio", dateFormat.format(congreso.getCongresoDetalle().getfInicioEnvio()));
                    vista.addObject("fechaFinEnvio", dateFormat.format(congreso.getCongresoDetalle().getfFinEnvio()));
                    vista.addObject("fechaInicioRevision", dateFormat.format(congreso.getCongresoDetalle().getfInicioRevision()));
                    vista.addObject("fechaFinRevision", dateFormat.format(congreso.getCongresoDetalle().getfFinRevision()));
                }
                break;
            case 2://rollid: 2 - Revisor
                vista.setViewName("WEB-INF/jsp/access/revisar_traballos.jsp");
                break;
            case 3://rollid: 3 - Autor
                TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
                traballoDao.setSessionFactory(sessionFactory);
                List<Traballo> traballos;
                traballos = traballoDao.obtenerTraballoUsuarioCongreso(user.getUserId(), congreso.getIdCongreso());
                if (traballos.isEmpty()) {
                    vista.setViewName("WEB-INF/jsp/access/alta_traballo.jsp");
                } else {
                    Iterator traballo=traballos.iterator();
                    List<TraballoDetalle> listaTraballos=new ArrayList<TraballoDetalle>();;
                    TraballoDetalleDaoHibernate traballoDetalle=new TraballoDetalleDaoHibernate();
                    traballoDetalle.setSessionFactory(sessionFactory);
                    for(int i=0;i<traballos.size();i++){
                        listaTraballos.add(traballoDetalle.obtenerTraballoDetalle(traballos.get(i).getIdTraballo()));
                    }
                    vista.setViewName("WEB-INF/jsp/access/admin_traballo.jsp");
                    vista.addObject("listaTraballos", listaTraballos);
                }
                break;
        }
        vista.addObject("usuario", usuario);
        return vista;
    }

    @Override
    public ModelAndView altaTrabajo(String usuario, String nomeTraballo, Integer categoria, String autores, byte[] traballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_traballo.jsp");

        UserProfileDaoHibernate userProfileDao = new UserProfileDaoHibernate();
        userProfileDao.setSessionFactory(sessionFactory);
        UserProfile user = userProfileDao.obtenerUserProfile(usuario);

        CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
        congresoDao.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDao.obtenerCongresoActivo();

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo trabajo = new Traballo(user, congreso);
        traballoDaoHibernate.guardarTraballo(trabajo);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        EstadoTraballoDaoHibernate estadoTraballoDao = new EstadoTraballoDaoHibernate();
        estadoTraballoDao.setSessionFactory(sessionFactory);

        EstadoTraballo estado = estadoTraballoDao.obtenerEstadoTraballo(1);

        EstadoTraballo estadoTraballo = new EstadoTraballo();
        estadoTraballo.setIdEstadoTraballo(1);

        TraballoDetalle traballoDetalle = new TraballoDetalle(trabajo.getIdTraballo(),
                nomeTraballo, categoria, autores, traballo, estadoTraballo, congreso.getCongresoDetalle().getfInicioEnvio(),
                congreso.getCongresoDetalle().getfFinEnvio(), congreso.getCongresoDetalle().getfInicioRevision(), congreso.getCongresoDetalle().getfFinRevision());

        traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);
        
        vista.addObject("nomeTraballo", nomeTraballo);
        vista.addObject("idCategoria", categoria);        
        vista.addObject("traballo", traballo);
        
        return vista;
    }
}
