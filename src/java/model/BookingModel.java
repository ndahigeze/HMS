/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.Message;
import dao.BookingDao;
import dao.RoomDao;
import domain.Booking;
import domain.Hotel;
import domain.Room;
import domain.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ndahigeze
 */
@ManagedBean
@SessionScoped
public class BookingModel {

    private Booking booking = new Booking();
    private Booking update = new Booking();
    private List<Booking> bookings = new ArrayList();
    private String dist;
    private String search = new String();

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getUpdate() {
        return update;
    }

    public void setUpdate(Booking update) {
        this.update = update;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public void recordBooking() {
        Room d = new Room();
        d.setRoomno(dist);
        Users u = searchUser();
        try {
            booking.setCustomer(u);
            booking.setRooms(d);
            booking.setPricePerDay(calculatePrice(dist));
            booking.setBookingdate(new Date());
            booking.setType("taken");
            booking.setTotalamount(calculateAmoutn(dist,booking.getStayindays()));
            String msg = new BookingDao().create(booking);
            booking = new Booking();
            bookings = new BookingDao().findAll(Booking.class);
            Message.succes(msg, "recorded");
        } catch (Exception ex) {
            Message.failure(ex.getLocalizedMessage(), "recorded");
        }

    }

    public double calculateAmoutn(String id,double day){
        Room r=new RoomDao().findOne(Room.class, id);
        return r.getPrice()*day;
    }
      public double calculatePrice(String id){
        Room r=new RoomDao().findOne(Room.class, id);
        return r.getPrice();
    }
    public void updateBooking() {
        try {
            String msg = new BookingDao().update(update);
            update = new Booking();
            bookings = new BookingDao().findAll(Booking.class);
            Message.succes(msg, "rrecord");
        } catch (Exception ex) {
            Message.failure(ex.getLocalizedMessage(), "rrecord");
        }

    }

    public void find(String s) {
        search = s;
        view();
    }

    @PostConstruct
    public void view() {
        searchUser();
        bookings = new BookingDao().findAll(Booking.class);
        if (search.length() > 0) {
            bookings = new ArrayList<>();
            new BookingDao().findAll(Booking.class).stream().filter((u) -> (u.getCustomer().getUsername().contains(search)
                    || search.toLowerCase().contains(u.getPricePerDay() + "")
                    || u.getBookingdate().toString().contains(search)
                    || u.getCustomer().getUsername().contains(search)
                    || search.contains(u.getStayindays() + "")
                    || search.contains(u.getCheckingdate() + "")
                    || search.contains(u.getPricePerDay() + "")
                    || search.contains(u.getTotalamount() + ""))).forEachOrdered((u) -> {
                bookings.add(u);
            });
        } else {
            bookings = new BookingDao().findAll(Booking.class);
        }
    }

    public void setDetail(final Booking booking) {
        this.update = booking;
    }

    public void deleteBooking(Booking u) {
        try {
            String msg = new BookingDao().delete(u);
            update = new Booking();
            bookings = new BookingDao().findAll(Booking.class);
            Message.succes(msg, "rrecord");
        } catch (Exception ex) {
            Message.failure(ex.getLocalizedMessage(), "rrecord");
        }
    }

    public void setupdate(Booking u) {
        booking = u;
    }

    public Users searchUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Users user = (Users) session.getAttribute("session");
        return user;
    }
}
