/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author insdrv00
 */
public class MainControllerTest {

    @Test
    public void testHandleRequestView() throws Exception {
        MainController controller = new MainController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello.jsp", modelAndView.getViewName());
    }
}