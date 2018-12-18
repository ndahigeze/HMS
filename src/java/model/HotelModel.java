/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.Message;
import dao.HotelDao;
import domain.District;
import domain.Hotel;
import domain.Users;
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
public class HotelModel {
 private Hotel hotel=new Hotel();
 private Hotel update=new Hotel();
 private List<Hotel> hotels=new ArrayList();
 private String dist;
 private String search=new String();

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
 
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Hotel getUpdate() {
        return update;
    }

    public void setUpdate(Hotel update) {
        this.update = update;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }
 
    public void recordhotel(){
        try{
            District d=new District();
            d.setDistrictcode(dist);
            hotel.setAddress(d);
             String msg=new HotelDao().create(hotel);
            hotel=new Hotel();
            hotels=new HotelDao().findAll(Hotel.class);
             Message.succes(msg, "crecod");
        }catch(Exception ex){
              Message.failure(ex.getLocalizedMessage(), "crecod");
        }
        
    }
    
     public void updatehotel(){
        try{
             String msg=new HotelDao().update(update);
            update=new Hotel();
            hotels=new HotelDao().findAll(Hotel.class);
             Message.succes(msg, "");
        }catch(Exception ex){
             Message.failure(ex.getLocalizedMessage(), "");
        }
        
    }
      @PostConstruct
     public void viewHotle(){
       hotels=new HotelDao().findAll(Hotel.class);
     if(search.length()>0){
         hotels=new ArrayList<>();
         new HotelDao().findAll(Hotel.class).stream().filter((u) -> (
                 search.contains(u.getId()+"")||
                         u.getAddress().getNamedistrict().toLowerCase().contains(search)||
                         u.getEmail().toLowerCase().contains(search)||
                         u.getId().toLowerCase().contains(search)||
                         u.getPhone().toLowerCase().contains(search)||
                         u.getName().toLowerCase().contains(search)
                 )).forEachOrdered((u) -> {
                      hotels.add(u);
         });
     }else{
      hotels=new HotelDao().findAll(Hotel.class);
     }
     }
    
    public void setDetail(final Hotel hotel){
      this.update=hotel; 
    }
    
    public void deletehotel(Hotel u){
      try{
         String msg=new HotelDao().delete(u);
         update=new Hotel();
         hotels=new HotelDao().findAll(Hotel.class);
          Message.succes(msg, "");
      }catch(Exception ex){
         Message.failure(ex.getLocalizedMessage(), "");
      }
    }
    public void setupdate(Hotel u){
      hotel=u;
    }
    
}
