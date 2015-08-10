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
@Repository(value = "EstadoCongreso")
public class EstadoCongresoDaoHibernate extends GenericDaoHibernate implements EstadoCongresoDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional    
    public void guardarEstadoCongreso(EstadoCongreso estadoCongreso){
        genericDao.save(estadoCongreso);
    }
    
    @Override
    @Transactional
    public EstadoCongreso obtenerEstadoCongreso(Integer idEstadoCongreso){
        Integer idEstadoCongresoBBDD=null;
        try{
            idEstadoCongresoBBDD=(Integer) genericDao.getCurrentSession().createQuery("SELECT a.idEstadoCongreso FROM EstadoCongreso a WHERE a.idEstadoCongreso = :idEstadoCongreso ").setParameter("idEstadoCongreso", idEstadoCongreso).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (EstadoCongreso) genericDao.find(EstadoCongreso.class, idEstadoCongresoBBDD);
        }
    }
}