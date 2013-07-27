/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author insdrv00
 */
@Service
public class UserProfileDaoHibernate extends GenericDaoHibernate implements UserProfileDao{

    private Session session;
    private GenericDaoHibernate generic;
    
    @Autowired
    public UserProfileDaoHibernate(SessionFactory session){
        this.generic = new GenericDaoHibernate(session);
    }
    

    @Override
    @Transactional
    public void guardarUserProfile(UserProfile userProfile) {
        this.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile obtenerUserProfile(String usuario) {
        Integer userId = null;
        try {
            userId = (Integer) this.getCurrentSession().createQuery("SELECT a.userId FROM userProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            throw e;
        } finally {
            return (UserProfile) this.find(userId);
        }
    }

    @Override
    @Transactional
    public boolean existeUserProfile(String usuario) {
        try {
            Integer userId = (Integer) this.getCurrentSession().createQuery("SELECT a.userId FROM userProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            return false;
        } finally {
            return true;
        }
    }

    @Override
    @Transactional
    public void eliminarUserProfile(UserProfile usuario) {
        this.remove(usuario.getUserId());
    }
}
