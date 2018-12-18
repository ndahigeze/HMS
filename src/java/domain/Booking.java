/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Ndahigeze
 */
@Entity
@Table(
   uniqueConstraints = {@UniqueConstraint(columnNames = {"room","checkingdate"})}
)
public class Booking implements Serializable {
    @Id
    private String bookingid = UUID.randomUUID().toString();
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookingdate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date checkingdate;
    private double stayindays;
    @ManyToOne
    private Users customer;
    private String type;
    @ManyToOne
    @JoinColumn(name = "room")
    private Room rooms;
    private double pricePerDay;
    private double totalamount;

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public Date getBookingdate() {
        return bookingdate;
    }

    public Date getCheckingdate() {
        return checkingdate;
    }

    public void setCheckingdate(Date checkingdate) {
        this.checkingdate = checkingdate;
    }

    public void setBookingdate(Date bookingdate) {
        this.bookingdate = bookingdate;
    }

    public double getStayindays() {
        return stayindays;
    }

    public void setStayindays(double stayindays) {
        this.stayindays = stayindays;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }
  
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Room getRooms() {
        return rooms;
    }

    public void setRooms(Room rooms) {
        this.rooms = rooms;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }
    
    
}
