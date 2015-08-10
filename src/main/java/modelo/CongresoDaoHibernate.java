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
@Repository(value = "CongresoDao")
public class CongresoDaoHibernate extends GenericDaoHibernate implements CongresoDao {

    private GenericDaoHibernate genericDao = new GenericDaoHibernate();

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.genericDao.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional
    public void guardarCongreso(Congreso congreso) {
        genericDao.save(congreso);
    }

    @Override
    @Transactional
    public Congreso obtenerCongreso(Integer idCongreso) {
        Integer identificadorCongreso = null;
        try {
            identificadorCongreso = (Integer) genericDao.getCurrentSession().createQuery("SELECT a.idCongreso FROM Congreso a WHERE a.idCongreso = :idCongreso ").setParameter("idCongreso", idCongreso).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (Congreso) genericDao.find(Congreso.class, identificadorCongreso);
        }
    }

    @Override
    public Congreso obtenerCongresoActivo() {
        Integer idCongreso = null;
        try {
            idCongreso = (Integer) genericDao.getCurrentSession().createQuery("SELECT c.idCongreso FROM Congreso c"
                    + " WHERE c.estadoCongreso.idEstadoCongreso <> :estadoCongreso ").setParameter("estadoCongreso", 7).uniqueResult();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            return (Congreso) genericDao.find(Congreso.class, idCongreso);
        }
    }
}