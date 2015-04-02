/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author insdrv00
 */
@Repository(value = "UserProfileDao")
public class UserProfileDaoHibernate extends GenericDaoHibernate implements UserProfileDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarUserProfile(UserProfile userProfile) {
        genericDao.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile obtenerUserProfile(String usuario) {
        Integer userId = null;
        try {
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userId FROM UserProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            System.out.println("userId en obtenerUserProfile: "+userId);
            return (UserProfile) genericDao.find(UserProfile.class, userId);
        }
    }
    
    @Override
    @Transactional
    public UserProfile obtenerUserProfile(Integer idUsuario) {
        Integer userId = null;
        try {
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userId FROM UserProfile a WHERE a.userId = :idUsuario ").setParameter("idUsuario", idUsuario).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (UserProfile) genericDao.find(UserProfile.class, userId);
        }
    }

    @Override
    @Transactional
    public boolean existeUserProfile(String usuario) {
        try {
            Integer userId = null;
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userId FROM UserProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
            if (userId == null) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean existeUserProfileActivo(String usuario) {
        try {
            Integer userId = null;
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userId FROM UserProfile a WHERE a.usuario = :usuario and activo=1").setParameter("usuario", usuario).uniqueResult();
            if (userId == null) {
                return false;
            }
        } catch (HibernateException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    @Transactional
    public void eliminarUserProfile(UserProfile usuario) {
        genericDao.remove(UserProfile.class, usuario);
    }
}