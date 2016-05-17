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
 * @author danielrios
 */
@Repository(value = "Revision")
public class RevisionDaoHibernate extends GenericDaoHibernate implements RevisionDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarRevision(Revision revision) {
        genericDao.save(revision);
    }
    
    @Override
    @Transactional
    public Revision obtenerRevision(Integer idRevision) {
        Integer idRevisionBBDD;
        try {
            idRevisionBBDD = (Integer)genericDao.getCurrentSession().createQuery("SELECT idRevision FROM Revision WHERE idRevision = :idRevision").setParameter("idRevision", idRevision).uniqueResult();
        } catch (HibernateException e) {
            throw e;
        }
        return (Revision )genericDao.find(Revision.class, idRevisionBBDD);
    }

    @Override
    @Transactional
    public List<Revision> obtenerRevisionesCongreso(Integer idCongreso) {

        List<Revision> revisiones = new ArrayList<Revision>();
        Iterator idRevision = null;
        try {
            idRevision = genericDao.getCurrentSession().createQuery("SELECT r.idRevision FROM Revision r, Congreso c, Traballo t"
                    + " WHERE r.congreso=c.idCongreso AND r.traballo=t.idTraballo AND c.idCongreso = :idCongreso ORDER BY t.idTraballo").setParameter("idCongreso", idCongreso).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idRevision.hasNext()) {
            Revision revision = (Revision) genericDao.find(Revision.class, (Integer) idRevision.next());
            revisiones.add(revision);
        }
        return revisiones;
    }
    
    @Override
    @Transactional
    public List<Revision> obtenerRevisionesCongresoTraballo(Integer idCongreso, Integer idTraballo) {

        List<Revision> revisiones = new ArrayList<Revision>();
        Iterator idRevision = null;
        try {
            idRevision = genericDao.getCurrentSession().createQuery("SELECT r.idRevision FROM Revision r, Congreso c, Traballo t"
                    + " WHERE r.congreso=c.idCongreso AND r.traballo=t.idTraballo AND c.idCongreso = :idCongreso AND t.idTraballo= :idTraballo").setParameter("idCongreso", idCongreso).setParameter("idTraballo", idTraballo).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idRevision.hasNext()) {
            Revision revision = (Revision) genericDao.find(Revision.class, (Integer) idRevision.next());
            revisiones.add(revision);
        }
        return revisiones;
    }
    
    @Override
    @Transactional
    public List<Revision> obtenerRevisionCongresoTraballoRevisor(Integer idCongreso, Integer idTraballo, Integer idRevisor) {
        List<Revision> revisiones = new ArrayList<Revision>();
        Iterator idRevision = null;
        try {
            idRevision = genericDao.getCurrentSession().createQuery("SELECT r.idRevision FROM Revision r, Congreso c, Traballo t, UserProfile u"
                    + " WHERE r.congreso=c.idCongreso AND r.traballo=t.idTraballo AND r.userProfileRevisor=u.userId"
                    + " AND c.idCongreso = :idCongreso AND t.idTraballo= :idTraballo"
                    + " AND u.userId = :idRevisor").setParameter("idCongreso", idCongreso).setParameter("idTraballo", idTraballo).setParameter("idRevisor", idRevisor).iterate();
        } catch (HibernateException e) {
            throw e;
        }
        while (idRevision.hasNext()) {
            Revision revision = (Revision) genericDao.find(Revision.class, (Integer) idRevision.next());
            revisiones.add(revision);
        }
        return revisiones;
    }
    
    
    
    @Override
    @Transactional
    public void eliminarRevision(Revision revision) {
        genericDao.remove(Traballo.class, revision.getIdRevision());
    }
}
