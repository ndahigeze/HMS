/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilPac.HibernateUtil;

/**
 *
 * @author Ndahigeze
 */
public class GenericDao<X> {
      public String create(X x){
        try{
            Session s=HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.saveOrUpdate(x);
            s.getTransaction().commit();
            s.close();
            return "Saved";
        }catch(Exception ex){
            return ""+ex.getMessage();
        }
    }
    
      public String update(X x){
        try{
            Session s=HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(x);
            s.getTransaction().commit();
            s.close();
            return "Done Successfully";
        }catch(Exception ex){
            return "not Updated"+ex.getMessage();
        }
    }
        public String delete(X x){
        try{
            Session s=HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(x);
            s.getTransaction().commit();
            s.close();
            return "Saved";
        }catch(Exception ex){
            return "not Saved"+ex.getMessage();
        }
    }
        public X findOne(Class x,Serializable id){
            
             Session s=HibernateUtil.getSessionFactory().openSession();
             X o=(X)s.get(x.getName(), id);
             s.close();
             return o;
            
        }
        public List<X> findAll(Class c){
            Session s=HibernateUtil.getSessionFactory().openSession();
            Query q=s.createQuery("from "+c.getName()+" s");
            List<X> l=q.list();
            s.close();
            return l;
        }
   
}
