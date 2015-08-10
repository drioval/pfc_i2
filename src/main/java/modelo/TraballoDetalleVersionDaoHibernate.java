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
@Repository(value="TraballoDetalleVersion")
public class TraballoDetalleVersionDaoHibernate extends GenericDaoHibernate implements TraballoDetalleVersionDao{
    
    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }
    
    @Override
    @Transactional
    public void guardarTraballoDetalleVersion(TraballoDetalleVersion traballoDetalleVersion){
        genericDao.save(traballoDetalleVersion);
    }
    
    @Override
    @Transactional
    public TraballoDetalleVersion obtenerTraballoDetalleVersion(Integer idTraballoDetalleVersion){
        Integer idTraballoDetalleVersionBBDD=null;
        try {
            idTraballoDetalleVersionBBDD = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.idTraballoDetalleVersion FROM TraballoDetalleVersion a WHERE a.idTraballoDetalleVersion = :idTraballoDetalleVersion ").setParameter("idTraballoDetalleVersion", idTraballoDetalleVersion).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (TraballoDetalleVersion) genericDao.find(TraballoDetalleVersion.class, idTraballoDetalleVersionBBDD);
        }
    }
    
}
