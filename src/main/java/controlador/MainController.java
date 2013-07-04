/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vista.UserServiceImpl;

@Controller
public class MainController {

    private UserServiceImpl servicio;
    
    @RequestMapping(value = "/acceder.htm", params = "username")
    public ModelAndView acceder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        servicio = new UserServiceImpl();
        return servicio.comprobarUsuario(request.getParameter("username").toString(), 
                request.getParameter("password").toString());
    }
}
