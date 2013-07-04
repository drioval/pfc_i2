/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import org.hibernate.Session;

/**
 *
 * @author insdrv00
 */
public interface GenericDao{

    public Session getSession();

    void save(Object obj);

    public Object find(Serializable PK);

    public void remove(Serializable PK);

    public boolean exists(Serializable PK);
}
