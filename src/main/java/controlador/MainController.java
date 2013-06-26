/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author insdrv00
 */

@Controller
public class MainController {

    @RequestMapping("/registrarse")
    public String resgistarse(Map<String, Object> map){
        return "registarse";
    }
}
