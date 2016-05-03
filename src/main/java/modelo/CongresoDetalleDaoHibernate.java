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
@Repository(value = "CongresoDetalle")
public class CongresoDetalleDaoHibernate extends GenericDaoHibernate implements CongresoDetalleDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional
    public void guardarCongresoDetalle(CongresoDetalle congresoDetalle){
        genericDao.save(congresoDetalle);
    }
    
    @Override
    @Transactional    
    public CongresoDetalle obtenerCongresoDetalle(Integer idCongreso){
        Integer idCongresoDetalle=null;
        try{
            idCongresoDetalle=(Integer) genericDao.getCurrentSession().createQuery("SELECT cd.idDetalleCongreso FROM CongresoDetalle cd, Congreso c WHERE"
                    + " cd.idDetalleCongreso=c.idCongreso AND c.idCongreso = :idCongreso").setParameter("idCongreso", idCongreso).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (CongresoDetalle) genericDao.find(CongresoDetalle.class, idCongresoDetalle);
        }
    }

}