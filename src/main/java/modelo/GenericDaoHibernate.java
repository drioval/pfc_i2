/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

/**
 *
 * @author insdrv00
 */
@Repository
public class GenericDaoHibernate implements GenericDao {

    private Class claseEntidad;
    private SessionFactory sessionFactory;
    
    public GenericDaoHibernate(SessionFactory session){
        this.sessionFactory=session;
    }


    @Override
    public Session getCurrentSession() {
        return this.getCurrentSession();
    }

    public void setSession(SessionFactory session) {
        this.sessionFactory=session;
    }

    
    @Override
    public void save(Object obj) {
        this.getCurrentSession().save(obj);
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