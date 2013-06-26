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
public class UserProfileDetailsDaoHibernate implements UserProfileDetailsDao {
    
    @Autowired
    GenericDao dao;
    
    @Override
    @Transactional
    public void guardarUserProfileDetails(UserProfileDetails userProfileDetails) {
        dao.save(userProfileDetails);
    }
    
    public void eliminarUserProfileDetails(UserProfileDetails usuario) {
        dao.remove(usuario.getUserprofileid());
    }
    
    @Override
    @Transactional
    public boolean existeUserProfileDetails(Integer usuario) {
        Integer userId = null;
        try {
            userId = (Integer) dao.getSession().createQuery("SELECT a.userprofileid FROM userProfileDetails a WHERE a.userId = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            return false;
        } finally {
            return true;
        }
    }
    
    @Override
    @Transactional    
    public UserProfileDetails obtenerUserProfileDetails(Integer usuario) {
        Integer userId = null;
        try {
            userId = (Integer) dao.getSession().createQuery("SELECT a.userprofileid FROM userProfileDetails a WHERE a.userId = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            throw e;
        } finally {
            return (UserProfileDetails) dao.find(userId);
        }
    }
}
