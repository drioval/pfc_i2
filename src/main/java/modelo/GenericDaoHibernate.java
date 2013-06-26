/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author insdrv00
 */
@Repository
public class GenericDaoHibernate implements GenericDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Class claseEntidad;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Object obj) {
        getCurrentSession().save(obj);
    }

    @Override
    public boolean exists(Serializable PK) {
        return getCurrentSession().createCriteria(claseEntidad).add(
                Restrictions.idEq(PK)).setProjection(Projections.id())
                .uniqueResult() != null;
    }

    @Override
    public Object find(Serializable PK) {
        Object entidad = getCurrentSession().get(claseEntidad, PK);
        if (entidad == null) {
            throw null;//Lanzar excepción si no encontrado.
        }
        return entidad;
    }

    @Override
    public void remove(Serializable PK) {
        getCurrentSession().delete(find(PK));
    }
}