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
@Repository(value = "UserProfileDetailsDao")
public class UserProfileDetailsDaoHibernate extends GenericDaoHibernate implements UserProfileDetailsDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarUserProfileDetails(UserProfileDetails userProfileDetails) {
        genericDao.save(userProfileDetails);
    }

    public void eliminarUserProfileDetails(UserProfileDetails usuario) {
        genericDao.remove(UserProfileDetails.class, usuario.getUserprofileid());
    }

    @Override
    @Transactional
    public boolean existeUserProfileDetails(Integer usuario) {
        Integer userId = null;
        try {
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userprofileid FROM userProfileDetails a WHERE a.userid = :usuario ").setParameter("usuario", usuario).uniqueResult();
        } catch (HibernateException e) {
            return false;
        } finally {
            return true;
        }
    }

    @Override
    @Transactional
    public UserProfileDetails obtenerUserProfileDetails(Integer idUsuario) {
        Integer userId = null;
        try {
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userprofileid FROM UserProfileDetails a WHERE a.userid = :usuario ").setParameter("usuario", idUsuario).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (UserProfileDetails) genericDao.find(UserProfileDetails.class, userId);
        }
    }

    @Override
    @Transactional
    public UserProfileDetails obtenerUserProfileDetailsEmail(String email) {
        Integer userId = null;
        try {
            userId = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.userprofileid FROM UserProfileDetails a WHERE a.email = :email ").setParameter("email", email).uniqueResult();
        } catch (HibernateException e) {
            throw e;
        } finally {
            if (userId == null) {
                return null;
            } else {
                return (UserProfileDetails) genericDao.find(UserProfileDetails.class, userId);
            }
        }
    }
}