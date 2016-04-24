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
    public List<TraballoDetalleVersion> obtenerTraballoDetalleVersion(Integer idTraballo){
        
        List<TraballoDetalleVersion> traballosDetalleVersion = new ArrayList<TraballoDetalleVersion>();
        Iterator idTraballoDetalleVersion = null;
        try {
            idTraballoDetalleVersion = genericDao.getCurrentSession().createQuery("SELECT v.idTraballoDetalleVersion FROM TraballoDetalleVersion v, Traballo t WHERE v.idTraballo=t.idTraballo AND t.idTraballo = :idTraballo").setParameter("idTraballo", idTraballo).iterate();
        } catch (HibernateException e) {
            System.out.println(e);
            throw e;
        } finally {
            if (idTraballoDetalleVersion!=null){
                while (idTraballoDetalleVersion.hasNext()) {
                TraballoDetalleVersion traballoDetalleVersion=(TraballoDetalleVersion) genericDao.find(TraballoDetalleVersion.class, (Integer)idTraballoDetalleVersion.next());
                traballosDetalleVersion.add(traballoDetalleVersion);
                }
            }
            
        return traballosDetalleVersion;
        }
    }
    
    @Override
    @Transactional
    public void eliminarTraballoDetalleVersion(TraballoDetalleVersion traballoDetalleVersion) {
        genericDao.remove(TraballoDetalleVersion.class, traballoDetalleVersion.getIdTraballoDetalleVersion());
    }
    
}
