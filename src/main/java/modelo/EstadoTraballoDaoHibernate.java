/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author drioval
 */
@Repository(value="EstadoTraballo")
public class EstadoTraballoDaoHibernate extends GenericDaoHibernate implements EstadoTraballoDao{
    
    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional
    public void guardarEstadoTraballo(EstadoTraballo estadoTraballo){
        genericDao.save(estadoTraballo);
    }
    
    public EstadoTraballo obtenerEstadoTraballo(Integer idEstadoTraballo){
        Integer idEstadoTraballoBBDD=null;
        try{
            idEstadoTraballoBBDD=(Integer) genericDao.getCurrentSession().createQuery("SELECT a.idEstadoTraballo FROM EstadoTraballo a WHERE a.idEstadoTraballo = :idEstadoTraballo").setParameter("idEstadoTraballo", idEstadoTraballo).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (EstadoTraballo) genericDao.find(EstadoTraballo.class, idEstadoTraballoBBDD);
        }
    }
}
