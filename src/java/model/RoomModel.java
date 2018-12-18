/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.Message;
import dao.RoomDao;
import domain.Hotel;
import domain.Room;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ndahigeze
 */
@ManagedBean
@SessionScoped
public class RoomModel {
     private Room room=new Room();
 private Room update=new Room();
 private List<Room> rooms=new ArrayList();
 private String dist;
 private String search=new String();
 private List<Room> byp=new ArrayList();
 private String h;

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }
 
    public List<Room> getByp() {
        return byp;
    }

    public void setByp(List<Room> byp) {
        this.byp = byp;
    }
 
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
 
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getUpdate() {
        return update;
    }

    public void setUpdate(Room update) {
        this.update = update;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }
 
    public void recordRoom(){
        try{
            Hotel d=new Hotel();
            d.setId(dist);
            room.setHotel(d);
             String msg=new RoomDao().create(room);
            room=new Room();
            rooms=new RoomDao().findAll(Room.class);
             Message.succes(msg, "rrecord");
        }catch(Exception ex){
              Message.failure(ex.getLocalizedMessage(), "rrecord");
        }
        
    }
     
     public void updateRoom(){
        try{
             String msg=new RoomDao().update(update);
            update=new Room();
            rooms=new RoomDao().findAll(Room.class);
             Message.succes(msg, "rrecord");
        }catch(Exception ex){
             Message.failure(ex.getLocalizedMessage(), "rrecord");
        }
        
    }
     
     public void find(String s){
         search=s;
         view();
     }
     @PostConstruct
     public void view(){
         rooms=new RoomDao().findAll(Room.class);
     if(search.length()>0){
          rooms=new ArrayList<>();
         new RoomDao().findAll(Room.class).stream().filter((u) -> (
                         u.getHotel().getName().contains(search)||
                         search.toLowerCase().contains(u.getRoomno()+"")||
                         search.toLowerCase().contains(u.getPrice()+"")||
                         u.getDescription().toLowerCase().contains(search)   
                 )).forEachOrdered((u) -> {
                      rooms.add(u);
         });
     }else{
       rooms=new RoomDao().findAll(Room.class);
     }
     }
    
    public void setDetail(final Room room){
      this.update=room; 
    }
    
    public void deleteRoom(Room u){
      try{
         String msg=new RoomDao().delete(u);
         update=new Room();
         rooms=new RoomDao().findAll(Room.class);
          Message.succes(msg, "rrecord");
      }catch(Exception ex){
         Message.failure(ex.getLocalizedMessage(), "rrecord");
      }
    }
    public void setupdate(Room u){
      room=u;
    }
    public void byHotel(){
      byp=RoomDao.viewByHotel(h);
    }
   
}
