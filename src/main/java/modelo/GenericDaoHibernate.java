/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import org.hibernate.HibernateException;
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
        Traballo traballo=(Traballo) obj;
        System.out.println("IdUsuario: "+traballo.getUserId());
        System.out.println("IdCongreso: "+traballo.getIdCongreso());
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
        Object entidad=null;
        try{
            entidad = sessionFactory.getCurrentSession().get(claseEntidad, PK);
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        }finally{
            return entidad;
        }
    }

    @Override
    public void remove(Class claseEntidad,Serializable PK) {
        sessionFactory.getCurrentSession().delete(find(claseEntidad,PK));
    }
}