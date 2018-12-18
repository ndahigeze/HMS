/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hotel;
import domain.Room;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilPac.HibernateUtil;

/**
 *
 * @author Ndahigeze
 */
public class RoomDao extends GenericDao<Room>{
   public static List<Room> viewByHotel(String pr){
      Session ses=HibernateUtil.getSessionFactory().openSession();
      Query que=ses.createQuery("FROM Room b WHERE b.hotel= :v ");
      que.setString("v",pr );
      List<Room> list=que.list();
      ses.close();
      return list;
    }   
}
