/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    public List<Traballo> obtenerTraballoUsuarioCongreso(Integer idUsuario, Integer idCongreso) {

        List<Traballo> traballos = new ArrayList<Traballo>();
        Iterator idTraballo = null;
        try {
            idTraballo = genericDao.getCurrentSession().createQuery("SELECT t.idTraballo FROM Traballo t, Congreso c, UserProfile u WHERE t.userProfile=u.userId AND c.idCongreso=t.congreso AND c.idCongreso = :idCongreso AND u.userId = :idUsuario").setParameter("idCongreso", idCongreso).setParameter("idUsuario", idUsuario).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idTraballo.hasNext()) {
            Traballo traballo=(Traballo)genericDao.find(Traballo.class, (Integer) idTraballo.next());
            traballos.add(traballo);
        }
        return traballos;
    }
    
    @Override
    @Transactional
    public List<Traballo> obtenerTraballosCongreso(Integer idCongreso) {

        List<Traballo> traballos = new ArrayList<Traballo>();
        Iterator idTraballo = null;
        try {
            idTraballo = genericDao.getCurrentSession().createQuery("SELECT t.idTraballo FROM Traballo t, TraballoDetalle td, Congreso c, UserProfile u, EstadoTraballo e"
                    + " WHERE c.idCongreso=t.congreso AND t.idTraballo=td.idTraballo AND t.userProfile=u.userId AND td.estadoTraballo=e.idEstadoTraballo AND e.idEstadoTraballo<>1 "
                    + "AND c.idCongreso = :idCongreso ORDER BY u.userId").setParameter("idCongreso", idCongreso).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idTraballo.hasNext()) {
            Traballo traballo=(Traballo)genericDao.find(Traballo.class, (Integer) idTraballo.next());
            traballos.add(traballo);
        }
        return traballos;
    }
    
    @Override
    @Transactional
    public void eliminarTraballo(Traballo traballo) {
        genericDao.remove(Traballo.class, traballo.getIdTraballo());
    }
}
