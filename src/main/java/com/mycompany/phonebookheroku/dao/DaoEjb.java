/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebookheroku.dao;


import com.mycompany.phonebookheroku.entitys.Users;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.io.IOUtils;


@Stateless
public class DaoEjb {
     @PersistenceContext(unitName = "Phonebook")
     private EntityManager entityManager;
    
    public List<Users> getAllUsers(){
        return entityManager.createQuery("from Users u", Users.class).getResultList();
    }
     
    public boolean saveUser(String nameParam, String phoneParam, InputStream foto){
        Users user = new Users();
        user.setName(nameParam);
        user.setPhone(phoneParam);
        try {
            byte[] bytes = IOUtils.toByteArray(foto);
            user.setFoto(bytes);
            foto.close();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
        
        try{
            entityManager.persist(user);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Users> findUsers(String nameParam, String phoneParam){
        Query q = null;
        
        if("".equals(nameParam) == false && "".equals(phoneParam) == false){
             q = entityManager.createNamedQuery("Users.findByNameByPhone",Users.class);
             q.setParameter("name", nameParam);
             q.setParameter("phone", phoneParam);
        }
        else{
            if("".equals(nameParam) == false){
               q = entityManager.createNamedQuery("Users.findByName",Users.class); 
               q.setParameter("name", nameParam);
            }
            else{
               q = entityManager.createNamedQuery("Users.findByPhone",Users.class);
               q.setParameter("phone", phoneParam);
            }
        }
        
        return q.getResultList(); 
    }
    
}
