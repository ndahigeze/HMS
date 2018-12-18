/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Province;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Lievre
 */
@Entity
public class District implements Serializable {

    @OneToMany(mappedBy = "address")
    private List<Hotel> hotels;
    @Id
    private String districtcode;
    private String namedistrict;
    @ManyToOne
    private Province province;
    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getNamedistrict() {
        return namedistrict;
    }

    public void setNamedistrict(String namedistrict) {
        this.namedistrict = namedistrict;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public String toString(){
     return districtcode;
    }
   
}
