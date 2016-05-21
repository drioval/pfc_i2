/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.TraballoDetalle;
import modelo.TraballoDetalleDaoHibernate;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import vista.UserServiceImpl;

@Controller
@Transactional
@MultipartConfig
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

    @RequestMapping(value = "/trabajos.htm")
    public ModelAndView trabajos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        if (request.getUserPrincipal() == null) {
            return servicio.congreso(null);
        }
        return servicio.trabajos(request.getUserPrincipal().getName());
    }

    @RequestMapping(value = "/alta_traballo.htm")
    public ModelAndView alta_trabajos(DefaultMultipartHttpServletRequest req,
            @RequestParam("nome_traballo") String nomeTraballo, HttpServletRequest request)
            throws ServletException, IOException {

        byte[] trabajo = req.getFile("trabajo").getBytes();

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.altaTrabajo(request.getUserPrincipal().getName(), request.getParameter("nome_traballo"),
                Integer.parseInt(request.getParameter("categoria")), request.getParameter("autores"),
                trabajo);
    }

    @RequestMapping(value = "/ver_datos_traballo.htm")
    public ModelAndView ver_datos_traballo(@RequestParam("id") Integer idTraballoDetalle, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accionVerDatosTrabajo(request.getUserPrincipal().getName(), idTraballoDetalle);
    }
    
    @RequestMapping(value = "/editar_traballo.htm")
    public ModelAndView editar_trabajo(@RequestParam("id") Integer idTraballoDetalle, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accioneditarTrabajo(request.getUserPrincipal().getName(), idTraballoDetalle);
    }

    @RequestMapping(value = "/modificacion_traballo.htm")
    public ModelAndView modificacion_trabajo(DefaultMultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        byte[] trabajo = req.getFile("trabajoModificado").getBytes();

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.modificacionTraballo(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idTraballo")), request.getParameter("nome_traballo"),
                Integer.parseInt(request.getParameter("categoria")), request.getParameter("autores"),
                trabajo);
    }

    @RequestMapping(value = "/eliminar_traballo.htm")
    public ModelAndView eliminar_trabajo(@RequestParam("id") Integer idTraballoDetalle, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accioneliminarTrabajo(request.getUserPrincipal().getName(), idTraballoDetalle);
    }

    @RequestMapping(value = "/borra_traballo.htm")
    public ModelAndView borrar_traballo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.borrarTraballo(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idTraballo")));
    }

    @RequestMapping(value = "/confirmar_traballo.htm")
    public ModelAndView confirmar_traballo(@RequestParam("id") Integer idTraballo, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.confirmarTraballo(request.getUserPrincipal().getName(), idTraballo);
    }

    @RequestMapping(value = "/accion_confirmar_traballo.htm")
    public ModelAndView accion_confirmar_trabajo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accionConfirmarTraballo(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idTraballo")));
    }

    @RequestMapping(value = "/anadir_trabajo.htm")
    public ModelAndView anadir_traballo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        if (request.getUserPrincipal() == null) {
            return servicio.congreso(null);
        }
        return servicio.anadirtrabajos(request.getUserPrincipal().getName());
    }

    @ResponseBody
    @RequestMapping(value = "/abrir_traballo.htm", produces = "application/pdf", method = RequestMethod.GET)
    public byte[] abrir_traballo(@RequestParam("id") Integer idTraballoDetalle,
            HttpServletRequest request, HttpServletResponse response) {

        TraballoDetalleDaoHibernate traballoDetalleDao = new TraballoDetalleDaoHibernate();
        traballoDetalleDao.setSessionFactory(sessionFactory);
        TraballoDetalle traballoDetalle = traballoDetalleDao.obtenerTraballoDetalle(idTraballoDetalle);

        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");

        response.setHeader("Content-Disposition", "inline; filename=" + traballoDetalle.getNomeTraballo());
        response.setContentType("application/pdf");
        response.setContentLength(traballoDetalle.getTraballo().length);
        try {
            response.getOutputStream().write(traballoDetalle.getTraballo());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            response.getOutputStream().flush();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return traballoDetalle.getTraballo();
    }
    
    @RequestMapping(value = "/revisar_traballo.htm")
    public ModelAndView revisar_trabajo(@RequestParam("id") Integer idTraballo, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accionRevisarTraballo(request.getUserPrincipal().getName(), idTraballo);
    }
    
    @RequestMapping(value = "/envia_revision.htm")
    public ModelAndView envia_revision(HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        return servicio.enviaRevision(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idTraballo")),
                request.getParameter("informe_publico"),request.getParameter("informe_privado"), Integer.parseInt(request.getParameter("puntuacion")),
                Integer.parseInt(request.getParameter("sugerencia")));
    }
    
    @RequestMapping(value = "/ver_lista_revisiones.htm")
    public ModelAndView ver_lista_revsiones(@RequestParam("id") Integer idTraballo,
            HttpServletRequest request, HttpServletResponse response) {
    
        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        return servicio.ver_lista_revisiones(request.getUserPrincipal().getName(), idTraballo);
    }
    
    @RequestMapping(value = "/ver_revision_traballo.htm")
    public ModelAndView ver_revision_traballo(@RequestParam("id") Integer idRevision,
            HttpServletRequest request, HttpServletResponse response) {
    
        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        return servicio.ver_revision_traballo(request.getUserPrincipal().getName(), idRevision);
    }
    
    @RequestMapping(value = "/accion_aceptar_traballo.htm")
    public ModelAndView accion_aceptar_traballo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);

        return servicio.accionAceptarTraballo(request.getUserPrincipal().getName(), Integer.parseInt(request.getParameter("idTraballo")));
    }
    
    @RequestMapping(value = "/accion_rexeitar_traballo.htm")
    public ModelAndView accion_rexeitar_traballo(@RequestParam("id") Integer idTraballo, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        if (idTraballo==null){
            idTraballo=Integer.parseInt(request.getParameter("idTraballo"));
        }

        return servicio.accionRexeitarTraballo(request.getUserPrincipal().getName(), idTraballo);
    }
    
    @RequestMapping(value = "/aceptar_traballo.htm")
    public ModelAndView aceptar_traballo(@RequestParam("id") Integer idTraballo, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        if (idTraballo==null){
            idTraballo=Integer.parseInt(request.getParameter("idTraballo"));
        }

        return servicio.aceptarTraballo(request.getUserPrincipal().getName(), idTraballo);
    }
    
    @RequestMapping(value = "/rexeitar_traballo.htm")
    public ModelAndView rexeitar_traballo(@RequestParam("id") Integer idTraballo, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        servicio = new UserServiceImpl();
        servicio.setSessionFactory(sessionFactory);
        
        if (idTraballo==null){
            idTraballo=Integer.parseInt(request.getParameter("idTraballo"));
        }

        return servicio.rexeitarTraballo(request.getUserPrincipal().getName(), idTraballo);
    }
}