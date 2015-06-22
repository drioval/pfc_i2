/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

/**
 *
 * @author drioval
 */
@Controller
public class AppConfiguration {

    @Value("${config.appurl}")
    private String valor;

    @Autowired
    private Environment environment;
      
    
    @Autowired
    ApplicationContext applicationcontex;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @return the env
     */
    @Bean
    public String getEnv(String parametro) {
        return environment.getProperty(parametro);
    }
}
