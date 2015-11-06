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
@Repository(value = "Traballo")
public class TraballoDaoHibernate extends GenericDaoHibernate implements TraballoDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarTraballo(Traballo traballo) {
        genericDao.save(traballo);
    }

    @Override
    @Transactional
    public Traballo obtenerTraballo(Integer idTraballo) {
        Integer idTraballoBBDD = null;
        try {
            idTraballoBBDD = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.idTraballo FROM Traballo a WHERE a.idTraballo = :idTraballo ").setParameter("idTraballo", idTraballo).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (Traballo) genericDao.find(Traballo.class, idTraballoBBDD);
        }
    }
    
    @Override
    @Transactional
    public Traballo obtenerTraballoUsuarioCongreso(Integer idUsuario, Integer idCongreso) {
        Integer idTraballo = null;
        try {
            idTraballo = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.idTraballo FROM Traballo a WHERE a.idUsuario = :idUsuario"
                    + "AND a.idCongreso = :idCongreso ").setParameter("idUsuario", idUsuario).setParameter("idCongreso", idCongreso).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (Traballo) genericDao.find(Traballo.class, idTraballo);
        }
    }
}
