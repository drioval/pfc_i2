/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author insdrv00
 */
@Service
public class UserProfileDaoHibernate implements UserProfileDao {

    @Autowired
    GenericDao dao;

    @Override
    @Transactional
    public void guardarUserProfile(UserProfile userProfile) {
        dao.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfile obtenerUserProfile(String usuario) {
        Integer userId = null;
        try {
            userId = (Integer) dao.getSession().createQuery("SELECT a.userId FROM userProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            throw e;
        } finally {
            return (UserProfile) dao.find(userId);
        }
    }

    @Override
    @Transactional
    public boolean existeUserProfile(String usuario) {
        Integer userId = null;
        try {
            userId = (Integer) dao.getSession().createQuery("SELECT a.userId FROM userProfile a WHERE a.usuario = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            return false;
        } finally {
            return true;
        }
    }

    @Override
    @Transactional
    public void eliminarUserProfile(UserProfile usuario) {
        dao.remove(usuario.getUserId());
    }
}
