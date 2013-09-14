/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author insdrv00
 */
@Repository(value = "UserRolDao")
public class UserRolDaoHibernate extends GenericDaoHibernate implements UserRolDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarUserRol(UserRol userRol) {
        genericDao.save(userRol);
    }

    @Override
    @Transactional
    public void eliminarUserRol(UserRol usuario) {
        genericDao.remove(UserRol.class, usuario.getRolId());
    }

    @Override
    @Transactional
    public boolean existeUserRol(Integer userRolId) {
        return genericDao.exists(userRolId);
    }

    public UserRol obtenerUserRol(Integer userRolId) {
        return (UserRol) genericDao.find(UserRol.class, userRolId);
    }
}
