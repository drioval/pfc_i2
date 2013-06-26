/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author insdrv00
 */
@Service
public class UserRolDaoHibernate implements UserRolDao{
    
    @Autowired
    GenericDao dao;
    
    @Override
    @Transactional
    public void guardarUserRol(UserRol userRol){
        dao.save(userRol);
    }
    
    @Override
    @Transactional
    public void eliminarUserRol(UserRol usuario){
        dao.remove(usuario.getRolId());
    }
    
    @Override
    @Transactional
    public boolean existeUserRol(Integer userRolId){
        return dao.exists(userRolId);
    }
    
    public UserRol obtenerUserRol(Integer userRolId){
        return (UserRol)dao.find(userRolId);
    }
}
