/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/acceder.htm", params = "username")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, Object> modelo = new HashMap<String, Object>();

        modelo.put("usuario", request.getParameter("username").toString());
        modelo.put("contrasinal", request.getParameter("password").toString());

        ModelAndView vista = new ModelAndView("WEB-INF/jsp/acceder.jsp");
        vista.addAllObjects(modelo);

        return vista;
    }
}
