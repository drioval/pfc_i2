/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vista.UserServiceImpl;

@Controller
@Transactional
public class MainController {

    @Autowired
    private SessionFactory sessionFactory;
    private UserServiceImpl servicio;

    @RequestMapping(value = "/index.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getUserPrincipal() == null) {
            return new ModelAndView("WEB-INF/jsp/index.jsp");
        }
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/index.jsp");
        vista.addObject("usuario", request.getUserPrincipal().getName());
        return vista;
    }

    @RequestMapping(value = "/aindex.htm")
    public ModelAndView inicioAutor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getUserPrincipal() == null) {
            return new ModelAndView("WEB-INF/jsp/index.jsp");
        }
        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/index.jsp");
        vista.addObject("usuario", request.getUserPrincipal().getName());
        return vista;
    }

    @RequestMapping(value = "/recordar.htm")
    public ModelAndView recordar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/recordar.jsp");
        return vista;
    }

    @RequestMapping(value = "/reenviar_contrasinal.htm")
    public ModelAndView reenviar_contrasinal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.reenviarContrasinal(request.getParameter("email").toString(),
                request.getParameter("usuario").toString());
    }

    @RequestMapping(value = "/contacto.htm")
    public ModelAndView contacto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getUserPrincipal() == null) {
            ModelAndView vista = new ModelAndView("WEB-INF/jsp/contacto.jsp");
            return vista;
        }
        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        return servicio.contacto(request.getUserPrincipal().getName());
    }

    @RequestMapping(value = "/contactar.htm")
    public ModelAndView contactar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();

        if (request.getUserPrincipal() == null) {
            return servicio.contactar(null, request.getParameter("nome").toString(),
                    request.getParameter("email").toString(), request.getParameter("asunto").toString(), request.getParameter("texto").toString());
        }
        return servicio.contactar(request.getUserPrincipal().getName(), request.getParameter("nome").toString(),
                request.getParameter("email").toString(), request.getParameter("asunto").toString(), request.getParameter("texto").toString());

    }

    @RequestMapping(value = "/registrar.htm")
    public ModelAndView registar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/registar.jsp");
        return vista;
    }

    @RequestMapping(value = "/enviar_registro.htm")
    public ModelAndView enviar_registro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.enviarRegistro(request.getParameter("email").toString(),
                request.getParameter("usuario").toString(),
                request.getParameter("password").toString(),
                request.getParameter("re_password").toString());
    }

    @RequestMapping(value = "/rematar_rexistro.htm")
    public ModelAndView rematar_rexistro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.rematarRexistro(request.getParameter("user").toString(),
                request.getParameter("key"));
    }

    @RequestMapping(value = "/completar_perfil.htm")
    public ModelAndView completar_perfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.completarPerfil(request.getParameter("usuario").toString(),
                request.getParameter("key"), request.getParameter("nome").toString(),
                request.getParameter("apelido1").toString(), request.getParameter("apelido2"),
                request.getParameter("telefono").toString());
    }

    @RequestMapping(value = "/congreso.htm")
    public ModelAndView congreso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/congreso.jsp");

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        if (request.getUserPrincipal() == null) {
            return servicio.congreso(null);
        }
        return servicio.congreso(request.getUserPrincipal().getName());
    }

    @RequestMapping(value = "/acceder.htm")
    public ModelAndView acceder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/perfil_usuario.jsp");
        vista.addObject("usuario", request.getUserPrincipal().getName());
        return vista;
    }

    @RequestMapping(value = "/trabajos.htm")
    public ModelAndView trabajos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/access/trabajos_usuario.jsp");
        vista.addObject("usuario", request.getUserPrincipal().getName());
        return vista;
    }

    @RequestMapping(value = "/prefil_usuario.htm")
    public ModelAndView prefil_usuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.obtenerPerfil(request.getUserPrincipal().getName());
    }

    @RequestMapping(value = "/actualizar_prefil.htm")
    public ModelAndView actualizar_usuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.actualizarPrefil(request.getUserPrincipal().getName(),
                request.getParameter("email"), request.getParameter("nome"), request.getParameter("apelido1"),
                request.getParameter("apelido2"), request.getParameter("telefono"),
                request.getParameter("password"), request.getParameter("re_password"),
                request.getParameter("re_password_2"));
    }

    @RequestMapping(value = "/alta_congreso.htm")
    public ModelAndView alta_congreso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.altaCongreso(request.getUserPrincipal().getName(), request.getParameter("nome_congreso"),
                Integer.parseInt(request.getParameter("estado_congreso")), request.getParameter("fecha_inicio_envio"),
                request.getParameter("fecha_fin_envio"), request.getParameter("fecha_inicio_revision"),
                request.getParameter("fecha_fin_revision"));
    }
    
    @RequestMapping(value = "/modifica_congreso.htm")
    public ModelAndView admin_congreso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        return servicio.modificaCongreso(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idCongreso")),
                request.getParameter("nome_congreso"),
                Integer.parseInt(request.getParameter("estado_congreso")), request.getParameter("fecha_inicio_envio"),
                request.getParameter("fecha_fin_envio"), request.getParameter("fecha_inicio_revision"),
                request.getParameter("fecha_fin_revision"));
    }
}