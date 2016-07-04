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
import modelo.EstadoRevision;
import modelo.EstadoRevisionDaoHibernate;
import modelo.EstadoTraballo;
import modelo.EstadoTraballoDaoHibernate;
import modelo.Revision;
import modelo.RevisionDaoHibernate;
import modelo.Traballo;
import modelo.TraballoDaoHibernate;
import modelo.TraballoDetalle;
import modelo.TraballoDetalleDaoHibernate;
import modelo.TraballoDetalleVersion;
import modelo.TraballoDetalleVersionDaoHibernate;
import modelo.UserProfile;
import modelo.UserProfileDaoHibernate;
import modelo.UserProfileDetails;
import modelo.UserProfileDetailsDaoHibernate;
import modelo.UserRol;
import modelo.UserRolDaoHibernate;
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
                    String asunto = "Congreso 2016: Solicitude de reenvio do contrasinal";
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

                CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
                congresoDaoHibernate.setSessionFactory(sessionFactory);
                Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();

                String asunto = congreso.getNomeCongreso() + ": Solicitude de reenvio do contrasinal";
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

                    CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
                    congresoDaoHibernate.setSessionFactory(sessionFactory);
                    Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();

                    String asunto = congreso.getNomeCongreso() + ": Alta de usuario " + usuario;
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
                    vista.setViewName("/WEB-INF/jsp/access/admin_traballos_congreso.jsp");
                    TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
                    traballoDao.setSessionFactory(sessionFactory);
                    List<Traballo> traballos = traballoDao.obtenerTraballosCongreso(congreso.getIdCongreso());
                    if (traballos.isEmpty()) {
                        vista.setViewName("/WEB-INF/jsp/access/admin_traballos_congreso.jsp");
                        vista.addObject("textoAccion", "admin_traballo03");
                        vista.addObject("existenTraballos", "0");
                    } else {
                        List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
                        List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
                        TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
                        traballoDetalleDao.setSessionFactory(sessionFactory);
                        for (int i = 0; i < traballos.size(); i++) {
                            TraballoDetalle traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
                            listaDetalleTraballos.add(traballoDetalle);
                            EstadoTraballo estadoTraballo = traballoDetalle.getEstadoTraballo();
                            listaEstadoTraballos.add(traballoDetalle.getEstadoTraballo());
                            System.out.println("nomeEstadoTraballo: " + estadoTraballo.getNomeEstado());
                        }
                        vista.addObject("existenTraballos", "1");
                        vista.addObject("listaTraballos", listaDetalleTraballos);
                        vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
                        vista.addObject("fFinEnvio", congreso.getCongresoDetalle().getfFinEnvio());
                        vista.addObject("textoAccion", "admin_traballo02");
                    }
                    break;
                }
                break;
            case 2://rollid: 2 - Revisor
                vista.setViewName("WEB-INF/jsp/access/revisar_traballos.jsp");

                UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
                userProfileDaoHibernate.setSessionFactory(sessionFactory);

                RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
                revisionDaoHibernate.setSessionFactory(sessionFactory);

                List<Revision> listaRevisiones = revisionDaoHibernate.obtenerRevisionCongresoRevisor(congreso.getIdCongreso(), user.getUserId());

                if (listaRevisiones.isEmpty()) {
                    vista.addObject("textoAccion", "revisor_sin_revisiones");
                    vista.addObject("existenRevisiones", "0");
                } else {
                    TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
                    traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
                    TraballoDetalle traballoDetalle = new TraballoDetalle();
                    List<TraballoDetalle> listaTraballoDetalle = new ArrayList<>();
                    Revision traballoRevision = new Revision();
                    Iterator iterador = listaRevisiones.iterator();
                    while (iterador.hasNext()) {
                        traballoRevision = (Revision) iterador.next();
                        traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(traballoRevision.getTraballo().getIdTraballo());
                        listaTraballoDetalle.add(traballoDetalle);
                    }
                    System.out.println("Congreso: " + congreso.getNomeCongreso());
                    System.out.println("Usuario: " + user.getUsuario());
                    System.out.println("Revision: " + listaRevisiones.get(0).getUserProfileRevisor().getUsuario());
                    vista.addObject("existenRevisiones", "1");
                    vista.addObject("listaRevisiones", listaRevisiones);
                    vista.addObject("listaTraballoDetalle", listaTraballoDetalle);
                    vista.addObject("textoAccion", "revisor_con_revisiones");
                }

                break;
            case 3://rollid: 3 - Autor
                TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
                traballoDao.setSessionFactory(sessionFactory);
                List<Traballo> traballos = traballoDao.obtenerTraballoUsuarioCongreso(user.getUserId(), congreso.getIdCongreso());
                if (traballos.isEmpty()) {
                    CongresoDetalleDaoHibernate congresoDetalleDaoHibernate = new CongresoDetalleDaoHibernate();
                    congresoDetalleDaoHibernate.setSessionFactory(sessionFactory);
                    CongresoDetalle congresoDetalle = congresoDetalleDaoHibernate.obtenerCongresoDetalle(congreso.getIdCongreso());
                    vista.setViewName("WEB-INF/jsp/access/alta_traballo.jsp");
                    vista.addObject("fFinEnvio", congresoDetalle.getfInicioRevision());
                    vista.addObject("textoAccion", "alta_traballo02");
                } else {
                    List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
                    List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
                    TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
                    traballoDetalleDao.setSessionFactory(sessionFactory);
                    for (int i = 0; i < traballos.size(); i++) {
                        TraballoDetalle traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
                        listaDetalleTraballos.add(traballoDetalle);
                        EstadoTraballo estadoTraballo = traballoDetalle.getEstadoTraballo();
                        listaEstadoTraballos.add(traballoDetalle.getEstadoTraballo());
                        System.out.println("nomeEstadoTraballo: " + estadoTraballo.getNomeEstado());
                    }

                    vista.setViewName("WEB-INF/jsp/access/admin_traballo.jsp");
                    vista.addObject("listaTraballos", listaDetalleTraballos);
                    vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
                    vista.addObject("fFinEnvio", congreso.getCongresoDetalle().getfFinEnvio());
                    vista.addObject("textoAccion", "admin_traballo02");
                }
                break;
        }
        vista.addObject("usuario", usuario);
        return vista;
    }

    @Override
    public ModelAndView confirmarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/confirmar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());

        return vista;
    }

    @Override
    public ModelAndView accionConfirmarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        TraballoDetalleVersionDaoHibernate traballoDetalleVersionDao = new TraballoDetalleVersionDaoHibernate();
        traballoDetalleVersionDao.setSessionFactory(sessionFactory);
        TraballoDetalleVersion traballoDetalleVersion = new TraballoDetalleVersion(traballoDetalle);
        traballoDetalleVersionDao.guardarTraballoDetalleVersion(traballoDetalleVersion);

        traballoDetalleDaoHibernate.eliminarTraballoDetalle(traballoDetalle);

        EstadoTraballoDaoHibernate estadoTraballoDao = new EstadoTraballoDaoHibernate();
        estadoTraballoDao.setSessionFactory(sessionFactory);

        EstadoTraballo estadoTraballo = estadoTraballoDao.obtenerEstadoTraballo(2);
        traballoDetalle.setEstadoTraballo(estadoTraballo);

        traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);

        Integer idUsuario = traballo.getUserProfile().getUserId();
        Integer idCongreso = traballo.getCongreso().getIdCongreso();

        TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
        traballoDao.setSessionFactory(sessionFactory);

        List<Traballo> traballos;
        traballos = traballoDao.obtenerTraballoUsuarioCongreso(idUsuario, idCongreso);
        if (traballos.isEmpty()) {
            vista.setViewName("WEB-INF/jsp/access/alta_traballo.jsp");
        } else {
            List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
            List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
            TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
            traballoDetalleDao.setSessionFactory(sessionFactory);
            for (int i = 0; i < traballos.size(); i++) {
                traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
                listaDetalleTraballos.add(traballoDetalle);
                estadoTraballo = traballoDetalle.getEstadoTraballo();
                listaEstadoTraballos.add(estadoTraballo);
                System.out.println("nomeEstadoTraballo: " + estadoTraballo.getNomeEstado());
            }

            vista.addObject("listaTraballos", listaDetalleTraballos);
            vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
        }

        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("fFinEnvio", traballo.getCongreso().getCongresoDetalle().getfFinEnvio());
        vista.addObject("textoAccion", "confirm_traballo02");

        return vista;
    }

    ;

    @Override
    public ModelAndView anadirtrabajos(String usuario) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/alta_traballo.jsp");
        vista.addObject("usuario", usuario);

        CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
        congresoDaoHibernate.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();
        vista.addObject("fFinEnvio", congreso.getCongresoDetalle().getfFinEnvio());
        vista.addObject("textoAccion", "alta_traballo_nuevo");
        return vista;
    }

    @Override
    @Transactional
    public ModelAndView altaTrabajo(String usuario, String nomeTraballo, Integer categoria, String autores, byte[] traballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_traballo.jsp");
        vista.addObject("usuario", usuario);

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

        EstadoTraballo estadoTraballo = estadoTraballoDao.obtenerEstadoTraballo(1);

        TraballoDetalle traballoDetalle = new TraballoDetalle(trabajo.getIdTraballo(),
                nomeTraballo, categoria, autores, traballo, estadoTraballo, congreso.getCongresoDetalle().getfInicioEnvio(),
                congreso.getCongresoDetalle().getfFinEnvio(), congreso.getCongresoDetalle().getfInicioRevision(),
                congreso.getCongresoDetalle().getfFinRevision());

        traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);

        TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
        traballoDao.setSessionFactory(sessionFactory);

        List<Traballo> traballos;
        traballos = traballoDao.obtenerTraballoUsuarioCongreso(user.getUserId(), congreso.getIdCongreso());

        List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
        List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
        TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
        traballoDetalleDao.setSessionFactory(sessionFactory);
        for (int i = 0; i < traballos.size(); i++) {
            traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
            listaDetalleTraballos.add(traballoDetalle);
            estadoTraballo = traballoDetalle.getEstadoTraballo();
            listaEstadoTraballos.add(estadoTraballo);
            System.out.println("nomeEstadoTraballo: " + estadoTraballo.getNomeEstado());
        }

        vista.addObject("listaTraballos", listaDetalleTraballos);
        vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
        vista.addObject("fFinEnvio", congreso.getCongresoDetalle().getfFinEnvio());

        vista.addObject("textoAccion", "accion_alta_traballo01");

        return vista;
    }

    @Override
    public ModelAndView accioneditarTrabajo(String usuario, Integer idTraballoDetalle) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/editar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballoDetalle);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView modificacionTraballo(String usuario, Integer idTraballoDetalle, String nomeTraballo, Integer categoria, String autores, byte[] ficheroTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballoDetalle);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        if (ficheroTraballo.length == 0) {
            ficheroTraballo = traballoDetalle.getTraballo();
        }

        if ((!traballoDetalle.getNomeTraballo().equals(nomeTraballo))
                || (traballoDetalle.getCategoria().compareTo(categoria) != 0)
                || (!traballoDetalle.getAutores().equals(autores))
                || (!traballoDetalle.getTraballo().equals(ficheroTraballo))) {

            TraballoDetalleVersionDaoHibernate traballoDetalleVersionDao = new TraballoDetalleVersionDaoHibernate();
            traballoDetalleVersionDao.setSessionFactory(sessionFactory);
            TraballoDetalleVersion traballoDetalleVersion = new TraballoDetalleVersion(traballoDetalle);
            traballoDetalleVersionDao.guardarTraballoDetalleVersion(traballoDetalleVersion);

            traballoDetalleDaoHibernate.eliminarTraballoDetalle(traballoDetalle);

            CongresoDaoHibernate congresoDao = new CongresoDaoHibernate();
            congresoDao.setSessionFactory(sessionFactory);
            Congreso congreso = congresoDao.obtenerCongreso(traballo.getCongreso().getIdCongreso());

            CongresoDetalleDaoHibernate congresoDetalleDao = new CongresoDetalleDaoHibernate();
            congresoDetalleDao.setSessionFactory(sessionFactory);
            CongresoDetalle congresoDetalle = congresoDetalleDao.obtenerCongresoDetalle(congreso.getIdCongreso());

            traballoDetalle = new TraballoDetalle(traballo.getIdTraballo(), nomeTraballo, categoria, autores, ficheroTraballo, traballoDetalleVersion.getEstadoTraballo(),
                    congresoDetalle.getfInicioEnvio(), congresoDetalle.getfFinEnvio(), congresoDetalle.getfInicioRevision(), congresoDetalle.getfFinRevision());
            traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);
        }

        Integer idUsuario = traballo.getUserProfile().getUserId();
        Integer idCongreso = traballo.getCongreso().getIdCongreso();

        TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
        traballoDao.setSessionFactory(sessionFactory);

        List<Traballo> traballos;

        traballos = traballoDao.obtenerTraballoUsuarioCongreso(idUsuario, idCongreso);
        if (traballos.isEmpty()) {
            vista.setViewName("WEB-INF/jsp/access/alta_traballo.jsp");
        } else {
            List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
            List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
            TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
            traballoDetalleDao.setSessionFactory(sessionFactory);
            for (int i = 0; i < traballos.size(); i++) {
                traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
                listaDetalleTraballos.add(traballoDetalle);
                listaEstadoTraballos.add(traballoDetalle.getEstadoTraballo());
                System.out.println("nomeEstadoTraballo: " + traballoDetalle.getEstadoTraballo().getNomeEstado());
            }

            vista.addObject("listaTraballos", listaDetalleTraballos);
            vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
        }

        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("textoAccion", "modif_traballo02");
        vista.addObject("fFinEnvio", traballo.getCongreso().getCongresoDetalle().getfFinEnvio());
        return vista;
    }

    @Override
    public ModelAndView accioneliminarTrabajo(String usuario, Integer idTraballoDetalle) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/eliminar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballoDetalle);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);

        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("categoria", traballoDetalle.getCategoria());
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView borrarTraballo(String usuario, Integer idTraballoDetalle) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/admin_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballoDetalle);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);

        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        TraballoDetalleVersionDaoHibernate traballoDetalleVersionDaoHibernate = new TraballoDetalleVersionDaoHibernate();
        traballoDetalleVersionDaoHibernate.setSessionFactory(sessionFactory);

        List<TraballoDetalleVersion> listatraballoDetalleVersion = traballoDetalleVersionDaoHibernate.obtenerTraballoDetalleVersion(traballo.getIdTraballo());
        if (!listatraballoDetalleVersion.isEmpty()) {
            for (int i = 0; i < listatraballoDetalleVersion.size(); i++) {
                TraballoDetalleVersion traballoDetalleVersion = listatraballoDetalleVersion.get(i);
                traballoDetalleVersionDaoHibernate.eliminarTraballoDetalleVersion(traballoDetalleVersion);
            }
        };

        traballoDetalleDaoHibernate.eliminarTraballoDetalle(traballoDetalle);
        traballoDaoHibernate.eliminarTraballo(traballo);

        Integer idUsuario = traballo.getUserProfile().getUserId();
        Integer idCongreso = traballo.getCongreso().getIdCongreso();

        TraballoDaoHibernate traballoDao = new TraballoDaoHibernate();
        traballoDao.setSessionFactory(sessionFactory);

        List<Traballo> traballos;

        traballos = traballoDao.obtenerTraballoUsuarioCongreso(idUsuario, idCongreso);
        if (traballos.isEmpty()) {
            vista.setViewName("WEB-INF/jsp/access/alta_traballo.jsp");
        } else {
            List<TraballoDetalle> listaDetalleTraballos = new ArrayList<TraballoDetalle>();
            List<EstadoTraballo> listaEstadoTraballos = new ArrayList<EstadoTraballo>();
            TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
            traballoDetalleDao.setSessionFactory(sessionFactory);
            for (int i = 0; i < traballos.size(); i++) {
                traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(traballos.get(i).getIdTraballo());
                listaDetalleTraballos.add(traballoDetalle);
                listaEstadoTraballos.add(traballoDetalle.getEstadoTraballo());
                System.out.println("nomeEstadoTraballo: " + traballoDetalle.getEstadoTraballo().getNomeEstado());
            }
            vista.addObject("listaTraballos", listaDetalleTraballos);
            vista.addObject("listaEstadoTraballos", listaEstadoTraballos);
        }

        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("textoAccion", "accion_eliminar_traballo01");
        vista.addObject("fFinEnvio", traballo.getCongreso().getCongresoDetalle().getfFinEnvio());

        return vista;
    }

    @Override
    public ModelAndView accionVerDatosTrabajo(String usuario, Integer idTraballo) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/ver_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());

        return vista;
    }

    @Override
    public ModelAndView accionRevisarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/revisar_traballo.jsp");
        vista.addObject("usuario", usuario);

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile usuarioRevisor = userProfileDaoHibernate.obtenerUserProfile(usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());

        vista.addObject("fInicioEnvio", traballoDetalle.getfInicioEnvio());
        vista.addObject("fFinEnvio", traballoDetalle.getfFinEnvio());
        vista.addObject("fIncioRevision", traballoDetalle.getfIncioRevision());
        vista.addObject("fFinRevision", traballoDetalle.getfFinRevision());

        vista.addObject("nomeEstado", traballoDetalle.getEstadoTraballo().getNomeEstado());

        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("usuarioRol", usuarioRevisor.getUserRol().getRolId());
        vista.addObject("textoAccion", "revision_traballo02");

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView enviaRevision(Integer idOperacion, String usuario, Integer idTraballo, String informePublico, String informePrivado, Integer puntuacion, Integer recomendacion) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/revision_traballo.jsp");
        vista.addObject("usuario", usuario);
        vista.addObject("textoAccion", "revision_enviada01");

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);

        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(traballo.getIdTraballo());

        CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
        congresoDaoHibernate.setSessionFactory(sessionFactory);

        Congreso congreso = congresoDaoHibernate.obtenerCongreso(traballo.getCongreso().getIdCongreso());

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);

        UserProfile usuarioAutor = userProfileDaoHibernate.obtenerUserProfile(traballo.getUserProfile().getUserId());
        UserProfile usuarioRevisor = userProfileDaoHibernate.obtenerUserProfile(usuario);

        EstadoRevisionDaoHibernate estadoRevisionDaoHibernate = new EstadoRevisionDaoHibernate();
        estadoRevisionDaoHibernate.setSessionFactory(sessionFactory);
        EstadoRevision estadoRevision = estadoRevisionDaoHibernate.obtenerEstadoRevision(3);//Provisional
        if (idOperacion == 2) {
            estadoRevision = estadoRevisionDaoHibernate.obtenerEstadoRevision(4);//Confirmada
        }
        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);

        List<Revision> listaRevision = revisionDaoHibernate.obtenerRevisionCongresoTraballoRevisor(congreso.getIdCongreso(), idTraballo,
                usuarioRevisor.getUserId());
        Revision revision = new Revision();
        if (listaRevision.isEmpty()) {
            revision = new Revision(congreso, traballo, usuarioAutor, usuarioRevisor, informePublico, informePrivado,
                    puntuacion, recomendacion, estadoRevision);

        } else {
            revision = listaRevision.get(0);
            revision.setRevisionPrivada(informePrivado);
            revision.setRevisionPublica(informePublico);
            revision.setPuntuacion(puntuacion);
            revision.setRecomendacion(recomendacion);
            revision.setEstadoRevision(estadoRevision);
        }

        revisionDaoHibernate.guardarRevision(revision);
        if (idOperacion == 2) {//Enviar revision
            EstadoTraballoDaoHibernate estadoTraballoDao = new EstadoTraballoDaoHibernate();
            estadoTraballoDao.setSessionFactory(sessionFactory);

            EstadoTraballo estadoTraballo = estadoTraballoDao.obtenerEstadoTraballo(7);//Revisado
            traballoDetalle.setEstadoTraballo(estadoTraballo);
            traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);
        }

        vista.addObject("revision", revision);
        vista.addObject("rolUsuario", usuarioRevisor.getUserRol().getRolId());
        vista.addObject("traballoDetalle", traballoDetalle);

        return vista;
    }

    @Override
    public ModelAndView ver_lista_revisiones(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/lista_revisiones.jsp");
        vista.addObject("usuario", usuario);
        vista.addObject("textoAccion", "lista_revisiones01");

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);

        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(traballo.getIdTraballo());

        CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
        congresoDaoHibernate.setSessionFactory(sessionFactory);

        Congreso congreso = congresoDaoHibernate.obtenerCongreso(traballo.getCongreso().getIdCongreso());

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);

        List<Revision> revisiones = revisionDaoHibernate.obtenerRevisionesCongresoTraballo(congreso.getIdCongreso(), idTraballo);

        for (int i = 0; i < revisiones.size(); i++) {
            String nomeRevisor = revisiones.get(i).getUserProfileRevisor().getUsuario();
        }

        vista.addObject("listaRevisiones", revisiones);
        vista.addObject("idTraballo", traballo.getIdTraballo());
        vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());

        return vista;
    }

    @Override
    public ModelAndView ver_revision_traballo(String usuario, Integer idRevision) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/revision_traballo.jsp");
        vista.addObject("usuario", usuario);
        vista.addObject("textoAccion", "ver_revision_enviada01");

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);

        Revision revision = revisionDaoHibernate.obtenerRevision(idRevision);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(revision.getTraballo().getIdTraballo());

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);

        UserProfile usuarioRol = userProfileDaoHibernate.obtenerUserProfile(usuario);
        UserRol rol = usuarioRol.getUserRol();

        vista.addObject("revision", revision);
        vista.addObject("traballoDetalle", traballoDetalle);
        vista.addObject("rolUsuario", rol.getRolId());

        return vista;
    }

    @Override
    public ModelAndView accionAceptarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/aceptar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("traballo", traballoDetalle);
        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("textoAccion", "datos_traballo02");

        return vista;
    }

    @Override
    public ModelAndView editarRevision(String usuario, Integer idRevision) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/editar_revision.jsp");
        vista.addObject("usuario", usuario);
        vista.addObject("textoAccion", "revision_enviada01");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile usuarioRevisor = userProfileDaoHibernate.obtenerUserProfile(usuario);

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        Revision revision = revisionDaoHibernate.obtenerRevision(idRevision);

        Traballo traballo = revision.getTraballo();

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(traballo.getIdTraballo());

        vista.addObject("traballoDetalle", traballoDetalle);
        vista.addObject("revision", revision);
        vista.addObject("rolUsuario", usuarioRevisor.getUserRol().getRolId());
        vista.addObject("idTraballo", revision.getTraballo().getIdTraballo());

        return vista;
    }

    @Override
    public ModelAndView accionCorrixirTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/corrixir_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("textoAccion", "datos_traballo02");

        return vista;

    }

    @Override
    public ModelAndView accionRexeitarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/rexeitar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("textoAccion", "datos_traballo02");

        return vista;
    }

    @Override
    public ModelAndView aceptarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/aceptar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        EstadoTraballoDaoHibernate estadoTraballoDaoHibernate = new EstadoTraballoDaoHibernate();
        estadoTraballoDaoHibernate.setSessionFactory(sessionFactory);

        EstadoTraballo estadoTraballo = estadoTraballoDaoHibernate.obtenerEstadoTraballo(3);

        traballoDetalle.setEstadoTraballo(estadoTraballo);
        traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        Congreso congreso = traballo.getCongreso();

        EnviarEmail enviarEmail = new EnviarEmail();
        String asunto = "Congreso 2016: Resultadio evaluación traballo";
        String cuerpo = "Una vez finalizada la revisión del trabajo " + traballoDetalle.getNomeTraballo() + " presentado al " + congreso.getNomeCongreso()
                + " le comunicamos que su trabajo ha sido ACEPTADO para ser presentado durante el congreso. "
                + "Puede consultar el resultado de las evaluaciones de su trabajo accediendo al área de trabajos de la Web del Congreso: <br>"
                + "<href>http://localhost:8084/congreso_1.4</href><br>"
                + "Muchas gracias por participar.";

        UserProfile usuarioAutor = traballo.getUserProfile();
        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);
        UserProfileDetails usuarioAutorDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(usuarioAutor.getUserId());

        enviarEmail.enviarEmail(usuarioAutorDetalle.getEmail(), asunto, cuerpo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("textoAccion", "traballo_aceptado01");

        return vista;
    }

    @Override
    public ModelAndView rexeitarTraballo(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/rexeitar_traballo.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        EstadoTraballoDaoHibernate estadoTraballoDaoHibernate = new EstadoTraballoDaoHibernate();
        estadoTraballoDaoHibernate.setSessionFactory(sessionFactory);

        EstadoTraballo estadoTraballo = estadoTraballoDaoHibernate.obtenerEstadoTraballo(4);

        traballoDetalle.setEstadoTraballo(estadoTraballo);
        traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(traballoDetalle.getIdTraballo());

        Congreso congreso = traballo.getCongreso();

        EnviarEmail enviarEmail = new EnviarEmail();
        String asunto = "Congreso 2016: Resultadio evaluación traballo";
        String cuerpo = "Una vez finalizada la revisión del trabajo " + traballoDetalle.getNomeTraballo() + " presentado al " + congreso.getNomeCongreso()
                + " le comunicamos que su trabajo ha sido RECHAZADO para ser presentado durante el congreso. "
                + "Puede consultar el resultado de las evaluaciones de su trabajo accediendo al área de trabajos de la Web del Congreso: <br>"
                + "<href>http://localhost:8084/congreso_1.4</href><br>"
                + "Muchas gracias por participar.";

        UserProfile usuarioAutor = traballo.getUserProfile();
        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);
        UserProfileDetails usuarioAutorDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(usuarioAutor.getUserId());

        enviarEmail.enviarEmail(usuarioAutorDetalle.getEmail(), asunto, cuerpo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("textoAccion", "traballo_rexeitado01");

        return vista;
    }

    @Override
    public ModelAndView asignarRevisores(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/asignar_revisores.jsp");
        vista.addObject("usuario", usuario);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        UserRolDaoHibernate userRolDaoHibernate = new UserRolDaoHibernate();
        userRolDaoHibernate.setSessionFactory(sessionFactory);

        UserRol userRol = userRolDaoHibernate.obtenerUserRol(2);

        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);

        List<UserProfileDetails> usuariosDetalleRol = userProfileDetailsDaoHibernate.obtenerUserProfileDetailsRol(userRol);

        CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
        congresoDaoHibernate.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();

        String textoRevisores = "Estimad@ colega,\n\n \tDesde la organización del " + congreso.getNomeCongreso() + " creemos que es usted la persona indicada para efectuar la revision del siguiente trabajo:\n"
                + traballoDetalle.getNomeTraballo() + ", presentado al congreso por los autores: " + traballoDetalle.getAutores() + ".\n\n"
                + "\tPor favor, si desea participar como revisor de este trabajo, haga click en el enlace que se muestra a continacion, complete los datos de registro y realize la revision antes de"
                + " la fecha limite: " + traballoDetalle.getfFinRevision() + "\n\n"
                + "En caso contrario, haga click en el siguiente enlace para rechazar la revision.\n\n"
                + "Un saludo, el comite organizador del " + congreso.getNomeCongreso();

        vista.addObject("textoRevisores", textoRevisores);
        vista.addObject("listaRevisores", usuariosDetalleRol);
        vista.addObject("idTraballo", traballoDetalle.getIdTraballo());
        vista.addObject("idTraballoDetalle", traballoDetalle.getIdTraballoDetalle());
        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("fFinRevision", traballoDetalle.getfFinRevision());
        vista.addObject("nomeEstado", traballoDetalle.getEstadoTraballo().getNomeEstado());

        vista.addObject("textoAccion", "datos_traballo02");

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView accionasignarRevisores(String usuario, Integer idTraballo, String[] revisores,
            String email, String textoRevisores) {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/lista_revisores.jsp");
        vista.addObject("usuario", usuario);

        if (revisores == null && email == "") {
            vista = this.asignarRevisores(usuario, idTraballo);
            vista.addObject("textoAccion", "error_asignacion01");
        } else {

            TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
            traballoDaoHibernate.setSessionFactory(sessionFactory);

            Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

            TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
            traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);

            TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);

            CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
            congresoDaoHibernate.setSessionFactory(sessionFactory);

            Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();

            RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
            revisionDaoHibernate.setSessionFactory(sessionFactory);

            UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
            userProfileDaoHibernate.setSessionFactory(sessionFactory);

            UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
            userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);

            EstadoTraballoDaoHibernate estadoTraballoDaoHibernate = new EstadoTraballoDaoHibernate();
            estadoTraballoDaoHibernate.setSessionFactory(sessionFactory);
            EstadoTraballo estadoTraballo = estadoTraballoDaoHibernate.obtenerEstadoTraballo(6);//En revision

            ArrayList<String> listaRevisores = new ArrayList<String>();
            ArrayList<UserProfileDetails> listaFinalRevisores = new ArrayList<UserProfileDetails>();
            ArrayList<Revision> listaRevisiones = new ArrayList<Revision>();

            if (!email.equals("")) {
                String separador = "[;]";
                String[] listaRevisoresInvitados = email.split(separador);
                UserRolDaoHibernate userRolDaoHibernate = new UserRolDaoHibernate();
                userRolDaoHibernate.setSessionFactory(sessionFactory);
                UserRol rolRevisor = userRolDaoHibernate.obtenerUserRol(2);//Revisor
                EstadoRevisionDaoHibernate estadoRevisionDaoHibernate = new EstadoRevisionDaoHibernate();
                estadoRevisionDaoHibernate.setSessionFactory(sessionFactory);
                EstadoRevision estadoRevision = estadoRevisionDaoHibernate.obtenerEstadoRevision(2);//Aceptada
                String separadorCorreo = "[@]";
                for (int i = 0; i < listaRevisoresInvitados.length; i++) {
                    UserProfileDetails usuarioRevisorDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetailsEmail(listaRevisoresInvitados[i].trim());
                    if (usuarioRevisorDetalle == null) {
                        String[] usuarioInvitado=listaRevisoresInvitados[i].trim().split(separadorCorreo);
                        UserProfile usuarioRevisor = new UserProfile(rolRevisor, usuarioInvitado[0], listaRevisoresInvitados[i].trim());
                        userProfileDaoHibernate.guardarUserProfile(usuarioRevisor);
                        UserProfileDetails usuarioDetalle = new UserProfileDetails(null, null, null, listaRevisoresInvitados[i].trim(), null, usuarioRevisor.getUserId());
                        userProfileDetailsDaoHibernate.guardarUserProfileDetails(usuarioDetalle);
                        listaFinalRevisores.add(usuarioDetalle);
                        Revision revision = new Revision(traballo.getCongreso(), traballo, traballo.getUserProfile(),
                                usuarioRevisor, estadoRevision);
                        revisionDaoHibernate.guardarRevision(revision);
                        listaRevisiones.add(revision);

                        EnviarEmail enviarEmail = new EnviarEmail();
                        String asunto = congreso.getNomeCongreso() + ": Invitacion a revision trabajo";
                        String cuerpo = "Estimad@ colega,<br> Desde la organización del " + congreso.getNomeCongreso() + " creemos que es usted la persona indicada para efectuar la revision del siguiente trabajo:\n"
                                + traballoDetalle.getNomeTraballo() + ", presentado al congreso por los autores: " + traballoDetalle.getAutores() + ".\n\n"
                                + "\tPor favor, si desea participar como revisor de este trabajo, haga click en el enlace que se muestra a continacion, evalúe el trabajo y realize la revision antes de"
                                + " la fecha limite: " + traballoDetalle.getfFinRevision() + "<br>"
                                + "<href>http://localhost:8084/congreso_1.4/aceptoRevision.htm?idu=" + usuarioRevisor.getUserId() + "&idt=" + traballo.getIdTraballo() + "</href><br>"
                                + "En caso contrario, haga click en el siguiente enlace para rechazar la revision.<br>"
                                + "<href>http://localhost:8084/congreso_1.4/rechazoRevision.htm?idu=" + usuarioRevisor.getUserId() + "&idt=" + traballo.getIdTraballo() + "</href><br><br>"
                                + "Un saludo, el comite organizador del " + congreso.getNomeCongreso();
                        enviarEmail.enviarEmail(usuarioDetalle.getEmail(), asunto, cuerpo);
                    } else {
                        listaRevisores.add(usuarioRevisorDetalle.getUserid().toString());
                    }
                }
            }

            if (revisores != null) {
                for (int i = 0; i < revisores.length; i++) {
                    listaRevisores.add(revisores[i]);
                }
            }

            EstadoRevisionDaoHibernate estadoRevisionDaoHibernate = new EstadoRevisionDaoHibernate();
            estadoRevisionDaoHibernate.setSessionFactory(sessionFactory);
            EstadoRevision estadoRevision = estadoRevisionDaoHibernate.obtenerEstadoRevision(2);//Aceptada
            System.out.println("Estado revision: " + estadoRevision.getIdEstadoRevision() + " - " + estadoRevision.getEstadoRevision());
            for (int i = 0; i < listaRevisores.size(); i++) {
                UserProfile userProfileRevisor = userProfileDaoHibernate.obtenerUserProfile(Integer.parseInt(listaRevisores.get(i)));
                UserProfileDetails usuarioRevisorDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(userProfileRevisor.getUserId());
                Revision revision = new Revision(traballo.getCongreso(), traballo, traballo.getUserProfile(),
                        userProfileRevisor, estadoRevision);
                listaFinalRevisores.add(usuarioRevisorDetalle);
                revisionDaoHibernate.guardarRevision(revision);
                listaRevisiones.add(revision);

                EnviarEmail enviarEmail = new EnviarEmail();
                String asunto = congreso.getNomeCongreso() + ": Invitacion a revision trabajo";
                String cuerpo = "Estimad@ colega,<br> Desde la organizacion del " + congreso.getNomeCongreso() + " creemos que es usted la persona indicada para efectuar la revision del siguiente trabajo:\n"
                        + traballoDetalle.getNomeTraballo() + ", presentado al congreso por los autores: " + traballoDetalle.getAutores() + ".<br>"
                        + "\tPor favor, si desea participar como revisor de este trabajo, acceda a la aplicacion y en la opcion de Trabajos Cientificos accedera a la revision del trabajo."
                        + " La fecha limite para la revision del mismo es: " + traballoDetalle.getfFinRevision() + "<br>"
                        + "En el caso de que desee rechazar la revision, pinche en el siguiente enlace:<br><br>"
                        + "<href>http://localhost:8084/congreso_1.4/rechazoRevision.htm?idu=" + userProfileRevisor.getUserId() + "&idt=" + traballo.getIdTraballo() + "</href><br><br>"
                        + "Un saludo, el comite organizador del " + congreso.getNomeCongreso();
                enviarEmail.enviarEmail(usuarioRevisorDetalle.getEmail(), asunto, cuerpo);
            }

            traballoDetalle.setEstadoTraballo(estadoTraballo);
            traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);

            vista.addObject("listaRevisores", listaFinalRevisores);
            vista.addObject("idTraballo", idTraballo);
            vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());
            vista.addObject("listaRevisiones", listaRevisiones);
            vista.addObject("textoAccion", "asignacion_revisores01");
        }

        return this.accionVerRevisores(usuario, idTraballo);
    }

    @Override
    public ModelAndView accionVerRevisores(String usuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/lista_revisores.jsp");
        vista.addObject("usuario", usuario);

        CongresoDaoHibernate congresoDaoHibernate = new CongresoDaoHibernate();
        congresoDaoHibernate.setSessionFactory(sessionFactory);
        Congreso congreso = congresoDaoHibernate.obtenerCongresoActivo();

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);

        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
        traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
        TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(traballo.getIdTraballo());

        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        List<Revision> listaRevisiones = revisionDaoHibernate.obtenerRevisionesCongresoTraballo(traballo.getCongreso().getIdCongreso(), traballo.getIdTraballo());

        UserRolDaoHibernate userRolDaoHibernate = new UserRolDaoHibernate();
        userRolDaoHibernate.setSessionFactory(sessionFactory);

        UserRol userRol = userRolDaoHibernate.obtenerUserRol(2);

        List<UserProfileDetails> usuariosDetalleRol = userProfileDetailsDaoHibernate.obtenerUserProfileDetailsRol(userRol);

        Iterator iterador = listaRevisiones.iterator();
        ArrayList<UserProfileDetails> listaFinalRevisores = new ArrayList<UserProfileDetails>();
        while (iterador.hasNext()) {
            Revision revision = (Revision) iterador.next();
            listaFinalRevisores.add(userProfileDetailsDaoHibernate.obtenerUserProfileDetails(revision.getUserProfileRevisor().getUserId()));
            if (usuariosDetalleRol.contains(userProfileDetailsDaoHibernate.obtenerUserProfileDetails(revision.getUserProfileRevisor().getUserId()))) {
                usuariosDetalleRol.remove(userProfileDetailsDaoHibernate.obtenerUserProfileDetails(revision.getUserProfileRevisor().getUserId()));
            }
        }

        String textoRevisores = "Estimad@ colega,\n\n \tDesde la organización del " + congreso.getNomeCongreso() + " creemos que es usted la persona indicada para efectuar la revision del siguiente trabajo:\n"
                + traballoDetalle.getNomeTraballo() + ", presentado al congreso por los autores: " + traballoDetalle.getAutores() + ".\n\n"
                + "\tPor favor, si desea participar como revisor de este trabajo, haga click en el enlace que se muestra a continacion, complete los datos de registro y realize la revision antes de"
                + " la fecha limite: " + traballoDetalle.getfFinRevision() + "\n\n"
                + "En caso contrario, haga click en el siguiente enlace para rechazar la revision.\n\n"
                + "Un saludo, el comite organizador del " + congreso.getNomeCongreso();

        Integer idCategoria = traballoDetalle.getCategoria();
        String categoria = null;
        if (idCategoria == 1) {
            categoria = "Publicación";
        } else if (idCategoria == 2) {
            categoria = "Artículo";
        } else if (idCategoria == 3) {
            categoria = "Tesis";
        } else if (idCategoria == 4) {
            categoria = "Investigación";
        } else if (idCategoria == 5) {
            categoria = "Disertación";
        }

        vista.addObject("textoRevisores", textoRevisores);

        vista.addObject("listaRevisores", listaFinalRevisores);
        vista.addObject("listaRevisoresDisponibles", usuariosDetalleRol);
        vista.addObject("idTraballo", idTraballo);
        vista.addObject("idEstadoTraballo", traballoDetalle.getEstadoTraballo().getIdEstadoTraballo());
        vista.addObject("listaRevisiones", listaRevisiones);
        vista.addObject("textoAccion", "asignacion_revisores01");

        vista.addObject("nomeTraballo", traballoDetalle.getNomeTraballo());
        vista.addObject("idCategoria", traballoDetalle.getCategoria());
        vista.addObject("categoria", categoria);
        vista.addObject("autores", traballoDetalle.getAutores());
        vista.addObject("traballo", traballoDetalle.getTraballo());
        vista.addObject("fFinRevision", traballoDetalle.getfFinRevision());
        vista.addObject("nomeEstado", traballoDetalle.getEstadoTraballo().getNomeEstado());

        return vista;
    }

    @Override
    public ModelAndView aceptoRevision(Integer idUsuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/aceptoRevision.jsp");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);

        UserProfile usuario = userProfileDaoHibernate.obtenerUserProfile(idUsuario);

        vista.addObject("idUsuario", usuario.getUserId());
        vista.addObject("idTraballo", idTraballo);
        return vista;
    }

    @Override
    public ModelAndView rechazoRevision(Integer idUsuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/rechazoRevision.jsp");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile usuario = userProfileDaoHibernate.obtenerUserProfile(idUsuario);

        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);
        UserProfileDetails usuarioDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(usuario.getUserId());

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        Congreso congreso = traballo.getCongreso();

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        List<Revision> listaRevision = revisionDaoHibernate.obtenerRevisionCongresoTraballoRevisor(congreso.getIdCongreso(), idTraballo, idUsuario);

        if (listaRevision.isEmpty()) {
            vista.addObject("textoAccion", "revision_xa_rexeitada01");
            vista.addObject("existeRevision", "0");

        } else {
            vista.addObject("existeRevision", "1");
            vista.addObject("textoAccion", "rexeitar_revision_02");
            vista.addObject("idUsuario", idUsuario);
            vista.addObject("idTraballo", idTraballo);
        }

        return vista;
    }

    @Override
    @Transactional
    public ModelAndView accionRechazoRevision(Integer idUsuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/rechazoRevision.jsp");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile usuario = userProfileDaoHibernate.obtenerUserProfile(idUsuario);

        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);
        UserProfileDetails usuarioDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(usuario.getUserId());

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        Congreso congreso = traballo.getCongreso();

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        List<Revision> listaRevision = revisionDaoHibernate.obtenerRevisionCongresoTraballoRevisor(congreso.getIdCongreso(), idTraballo, idUsuario);

        Iterator iterador = listaRevision.iterator();
        Revision revisionBorrar = listaRevision.get(0);

        revisionDaoHibernate.eliminarRevision(revisionBorrar);

        EnviarEmail enviarEmail = new EnviarEmail();
        String asunto = null;
        String cuerpo = null;
        if (usuario.getActivo() == 0) {
            asunto = congreso.getNomeCongreso() + ": RECHAZADA invitacion revisor invitado " + usuarioDetalle.getEmail();
            cuerpo = "Estimad@ colega,<br>La invitación enviada a la dirección de correo ellectrónico " + usuarioDetalle.getEmail() + " ha sido RECHAZADA<br>";

        } else {
            asunto = congreso.getNomeCongreso() + ": RECHAZADA invitacion revisor " + usuarioDetalle.getNome() + " " + usuarioDetalle.getApelido1() + " " + usuarioDetalle.getApelido2();
            cuerpo = "Estimad@ colega,<br>La invitación enviada al usuario " + usuarioDetalle.getNome() + " " + usuarioDetalle.getApelido1() + " " + usuarioDetalle.getApelido2() + " ha sido RECHAZADA<br>";
        }

        enviarEmail.enviarEmail(usuarioDetalle.getEmail(), asunto, cuerpo);

        if (usuario.getActivo() == 0) {//Si es invitado eliminamos su cuenta
            userProfileDetailsDaoHibernate.eliminarUserProfileDetails(usuarioDetalle);
            userProfileDaoHibernate.eliminarUserProfile(usuario);
        }

        List<Revision> cuentaRevisiones = revisionDaoHibernate.obtenerRevisionesCongresoTraballo(idUsuario, idTraballo);
        if (cuentaRevisiones.isEmpty()) {//Si se rechazan todas la revisiones pasamos el trabajo a pendiente_revision
            TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
            traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
            TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);
            EstadoTraballoDaoHibernate estadoTraballoDaoHibernate = new EstadoTraballoDaoHibernate();
            estadoTraballoDaoHibernate.setSessionFactory(sessionFactory);
            EstadoTraballo estadoTraballo = estadoTraballoDaoHibernate.obtenerEstadoTraballo(5);
            traballoDetalle.setEstadoTraballo(estadoTraballo);
            traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);
        }
        vista.addObject("textoAccion", "rechazar_revision_02");
        return vista;
    }

    @Override
    public ModelAndView rechazoRevisionRevisor(String usuario, Integer idRevision) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/rechazoRevision.jsp");
        vista.addObject("usuario", usuario);

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        Revision revision = revisionDaoHibernate.obtenerRevision(idRevision);

        Traballo traballo = revision.getTraballo();

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);

        UserProfile usuarioRevisor = userProfileDaoHibernate.obtenerUserProfile(usuario);

        vista.addObject("existeRevision", "1");
        vista.addObject("textoAccion", "rexeitar_revision_02");
        vista.addObject("idUsuario", usuarioRevisor.getUserId());
        vista.addObject("idTraballo", traballo.getIdTraballo());

        return vista;
    }

    @Override
    public ModelAndView accionRechazoRevisionRevisor(Integer idUsuario, Integer idTraballo) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/rechazoRevision.jsp");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile usuario = userProfileDaoHibernate.obtenerUserProfile(idUsuario);

        UserProfileDetailsDaoHibernate userProfileDetailsDaoHibernate = new UserProfileDetailsDaoHibernate();
        userProfileDetailsDaoHibernate.setSessionFactory(sessionFactory);
        UserProfileDetails usuarioDetalle = userProfileDetailsDaoHibernate.obtenerUserProfileDetails(usuario.getUserId());

        TraballoDaoHibernate traballoDaoHibernate = new TraballoDaoHibernate();
        traballoDaoHibernate.setSessionFactory(sessionFactory);
        Traballo traballo = traballoDaoHibernate.obtenerTraballo(idTraballo);

        Congreso congreso = traballo.getCongreso();

        RevisionDaoHibernate revisionDaoHibernate = new RevisionDaoHibernate();
        revisionDaoHibernate.setSessionFactory(sessionFactory);
        List<Revision> listaRevision = revisionDaoHibernate.obtenerRevisionCongresoTraballoRevisor(congreso.getIdCongreso(), idTraballo, idUsuario);

        Iterator iterador = listaRevision.iterator();
        Revision revisionBorrar = listaRevision.get(0);

        revisionDaoHibernate.eliminarRevision(revisionBorrar);

        EnviarEmail enviarEmail = new EnviarEmail();
        String asunto = null;
        String cuerpo = null;
        if (usuario.getActivo() == 0) {
            asunto = congreso.getNomeCongreso() + ": RECHAZADA invitacion revisor invitado " + usuarioDetalle.getEmail();
            cuerpo = "Estimad@ colega,<br>La invitación enviada a la dirección de correo ellectrónico " + usuarioDetalle.getEmail() + " ha sido RECHAZADA<br>";

        } else {
            asunto = congreso.getNomeCongreso() + ": RECHAZADA invitacion revisor " + usuarioDetalle.getNome() + " " + usuarioDetalle.getApelido1() + " " + usuarioDetalle.getApelido2();
            cuerpo = "Estimad@ colega,<br>La invitación enviada al usuario " + usuarioDetalle.getNome() + " " + usuarioDetalle.getApelido1() + " " + usuarioDetalle.getApelido2() + " ha sido RECHAZADA<br>";
        }

        enviarEmail.enviarEmail(usuarioDetalle.getEmail(), asunto, cuerpo);

        if (usuario.getActivo() == 0) {//Si es invitado eliminamos su cuenta
            userProfileDetailsDaoHibernate.eliminarUserProfileDetails(usuarioDetalle);
            userProfileDaoHibernate.eliminarUserProfile(usuario);
        }

        List<Revision> cuentaRevisiones = revisionDaoHibernate.obtenerRevisionesCongresoTraballo(idUsuario, idTraballo);
        if (cuentaRevisiones.isEmpty()) {//Si se rechazan todas la revisiones pasamos el trabajo a pendiente_revision
            TraballoDetalleDaoHibernate traballoDetalleDaoHibernate = new TraballoDetalleDaoHibernate();
            traballoDetalleDaoHibernate.setSessionFactory(sessionFactory);
            TraballoDetalle traballoDetalle = traballoDetalleDaoHibernate.obtenerTraballoDetalle(idTraballo);
            EstadoTraballoDaoHibernate estadoTraballoDaoHibernate = new EstadoTraballoDaoHibernate();
            estadoTraballoDaoHibernate.setSessionFactory(sessionFactory);
            EstadoTraballo estadoTraballo = estadoTraballoDaoHibernate.obtenerEstadoTraballo(5);
            traballoDetalle.setEstadoTraballo(estadoTraballo);
            traballoDetalleDaoHibernate.guardarTraballoDetalle(traballoDetalle);
        }
        vista.addObject("textoAccion", "rechazar_revision_02");
        vista.addObject("idUsuario", usuario.getUserId());
        return vista;
    }

    @Override
    public ModelAndView accionAceptarRevisionInvitado(Integer idUsuario, Integer idTraballo, String email) {
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/aceptoRevisionInvitado.jsp");

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);

        UserProfile usuario = userProfileDaoHibernate.obtenerUserProfile(idUsuario);

        System.out.println("Parametros dentro do metodo: " + idUsuario + " " + idTraballo + " " + email);

        if (usuario == null) {
            vista.addObject("mensaxe", "recordar_error_01");
            vista.setViewName("WEB-INF/jsp/recordar_error.jsp");
        } else {
            System.out.println("Vou a enviar correo ao revisor.");
            Set<UserProfileDetails> userProfileDetails = usuario.getUserProfileDetailses();
            UserProfileDetails detalleUsuario = (UserProfileDetails) userProfileDetails.toArray()[0];

            GenerarPassword pass = new GenerarPassword();
            String newPassword = pass.generarContrasinal();
            EncriptarPassword passEncripted = new EncriptarPassword();
            usuario.setContrasinal(passEncripted.encriptarContrasinal(newPassword).toString());

            EnviarEmail enviarEmail = new EnviarEmail();
            String asunto = "Congreso 2016: Solicitude alta como revisor";
            String cuerpo = "Datos de alta revisor. "
                    + "A continuación le proporcionamos los datos de acceso a su cuenta de revisor: ";
            cuerpo = cuerpo + "<br><br> Usuario: " + usuario.getUsuario()
                    + "<br><br> Correo electronico: " + email
                    + "<br><br> Contrasinal: " + newPassword
                    + "<br><br> Por favor, una vez dentro de la aplicación, acceda a su perfíl y complete los datos que falten."
                    + "<br><br> Un saludo.";
            enviarEmail.enviarEmail(detalleUsuario.getEmail(), asunto, cuerpo);
            
            usuario.setActivo(1);
            userProfileDaoHibernate.guardarUserProfile(usuario);

            vista.addObject("mensaxe", "alta_revisor_invitado");
        }
        return vista;
    }

}
