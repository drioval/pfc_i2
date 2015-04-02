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
        return new ModelAndView("WEB-INF/jsp/index.jsp");
    }

    /*@RequestMapping(value = "/acceder.htm", params = "username")
     public ModelAndView acceder(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {

     servicio = new UserServiceImpl();
     servicio.setSessionFactory(sessionFactory);

     return servicio.comprobarUsuario(request.getParameter("username").toString(),
     request.getParameter("password").toString());
     }*/
    @RequestMapping("/acceder.htm")
    public ModelAndView acceder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Estou dentro de acceder");
        return new ModelAndView("WEB-INF/jsp/access/acceder.jsp");
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

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/contacto.jsp");
        return vista;

    }

    @RequestMapping(value = "/contactar.htm")
    public ModelAndView contactar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();

        return servicio.contactar(request.getParameter("nome").toString(),
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
}