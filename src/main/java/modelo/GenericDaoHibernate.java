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
@Repository(value = "GenericDao")
public class GenericDaoHibernate implements GenericDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Class claseEntidad;

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }
    
    @Override
    public Session getCurrentSession(){
        return this.sessionFactory.getCurrentSession();
    }
    
    @Override
    public void save(Object obj) {
        sessionFactory.getCurrentSession().save(obj);
    }

    @Override
    public boolean exists(Serializable PK) {
        return sessionFactory.getCurrentSession().createCriteria(claseEntidad).add(
                Restrictions.idEq(PK)).setProjection(Projections.id())
                .uniqueResult() != null;
    }

    @Override
    public Object find(Class claseEntidad, Serializable PK) {
        Object entidad = sessionFactory.getCurrentSession().get(claseEntidad, PK);
        if (entidad == null) {
            throw null;//Lanzar excepción si no encontrado.
        }
        return entidad;
    }

    @Override
    public void remove(Class claseEntidad,Serializable PK) {
        sessionFactory.getCurrentSession().delete(find(claseEntidad,PK));
    }
}