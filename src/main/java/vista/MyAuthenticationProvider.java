/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author insdrv00
 */
public class MyAuthenticationProvider implements AuthenticationProvider {
 
    private UserDetailsService userDetailsService;
 
    public void setUserDetailsService(UserDetailsService userDetailsService) {
 
        this.userDetailsService = userDetailsService;
    }
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
 
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        /*if (userDetails != null && new BasicPasswordEncryptor().checkPassword(authentication.getCredentials().toString(),
                        userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        */
        throw new BadCredentialsException("Combinacion Usuario/Contrasinal INCORRECTA");
    }

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}