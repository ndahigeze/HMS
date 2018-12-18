/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Users;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilPac.HibernateUtil;

/**
 *
 * @author Ndahigeze
 */
public class UserDao extends GenericDao<Users>{
    public List<Users>login(String u,String password){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("from Users u where u.username= :v and u.password=:p");
        q.setParameter("v", u);
        q.setParameter("p", password);
        List<Users> l=q.list();
        return l;
    } 
}
