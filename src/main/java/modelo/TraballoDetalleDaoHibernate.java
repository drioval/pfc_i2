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
@Repository(value = "TraballoDetalle")
public class TraballoDetalleDaoHibernate extends GenericDaoHibernate implements TraballoDetalleDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarTraballoDetalle(TraballoDetalle traballoDetalle) {
        genericDao.save(traballoDetalle);
    }
    
    @Override
    @Transactional
    public TraballoDetalle obtenerTraballoDetalle(Integer idTraballoDetalle) {
        Integer idTraballoDetalleBBDD = null;
        try {
            idTraballoDetalleBBDD = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.idTraballoDetalle FROM TraballoDetalle a WHERE a.idTraballoDetalle = :idTraballoDetalle ").setParameter("idTraballoDetalle", idTraballoDetalle).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (TraballoDetalle) genericDao.find(TraballoDetalle.class, idTraballoDetalleBBDD);
        }
    }
}
