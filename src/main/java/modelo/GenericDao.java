/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author insdrv00
 */
public interface GenericDao{
    
    public void setSessionFactory(SessionFactory sessionFactory);

    public Session getCurrentSession();

    void save(Object obj);

    public Object find(Class claseEntidad,Integer PK);

    public void remove(Class claseEntidad,Integer PK);

    public boolean exists(Serializable PK);
}
