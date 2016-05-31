/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    
    @Override
    @Transactional
    public List<UserProfileDetails> obtenerUserProfileDetailsRol(UserRol userRol) {
        List<UserProfileDetails> usuarios = new ArrayList<UserProfileDetails>();
        Iterator idUsuario = null;
        try {
            idUsuario = genericDao.getCurrentSession().createQuery("SELECT d.userprofileid FROM UserProfile a, UserProfileDetails d, UserRol r "
                    + "WHERE a.userId=d.userid AND a.userRol=r.rolId AND a.activo=1 AND r.rolId = :rolId ").setParameter("rolId", userRol.getRolId()).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idUsuario.hasNext()) {
            UserProfileDetails usuarioDetalle=(UserProfileDetails)genericDao.find(UserProfileDetails.class, (Integer) idUsuario.next());
            usuarios.add(usuarioDetalle);
        }
        return usuarios;
    }
}