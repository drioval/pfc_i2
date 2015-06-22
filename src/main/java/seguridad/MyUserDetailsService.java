/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.UserProfile;
import modelo.UserProfileDaoHibernate;
import modelo.UserProfileDetailsDaoHibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author drioval
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(sessionFactory);
        UserProfile user = userProfileDaoHibernate.obtenerUserProfile(username);

        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRol().getRolId());

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(UserProfile user, List<GrantedAuthority> authorities) {
        UserProfileDetailsDaoHibernate userDetails = new UserProfileDetailsDaoHibernate();
        userDetails.setSessionFactory(sessionFactory);
        Boolean activo = user.getActivo() == 1;
        return new User(user.getUsuario(), user.getContrasinal(), activo, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Integer rolId) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        switch (rolId) {
            case 1:
                setAuths.add(new SimpleGrantedAuthority("ROLE_ORGANIZADOR"));
                break;
            case 2:
                setAuths.add(new SimpleGrantedAuthority("ROLE_REVISOR"));
                break;
            case 3:
                setAuths.add(new SimpleGrantedAuthority("ROLE_AUTOR"));
                break;
        }
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
        return Result;
    }
}
