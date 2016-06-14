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
 * @author danielrios
 */
@Repository(value="EstadoRevision")
public class EstadoRevisionDaoHibernate extends GenericDaoHibernate implements EstadoRevisionDao{
    
    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional
    public void guardarEstadoRevision(EstadoRevision estadoRevision){
        genericDao.save(estadoRevision);
    }
    
    @Override
    public EstadoRevision obtenerEstadoRevision(Integer idEstadoRevision){
        Integer idEstadoRevisionBBDD=null;
        try{
            idEstadoRevisionBBDD=(Integer) genericDao.getCurrentSession().createQuery("SELECT a.idEstadoRevision FROM EstadoRevision a WHERE a.idEstadoRevision = :idEstadoRevision").setParameter("idEstadoRevision", idEstadoRevision).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (EstadoRevision) genericDao.find(EstadoRevision.class, idEstadoRevisionBBDD);
        }
    }    
}
