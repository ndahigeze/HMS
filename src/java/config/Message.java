/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ndahigeze
 */
public class Message {
   
    public static void succes(String summary,String details){
     FacesContext fc=FacesContext.getCurrentInstance();
     fc.addMessage(details, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, details));
    }
    
    public static void failure(String summary,String details){
     FacesContext fc=FacesContext.getCurrentInstance();
     fc.addMessage(details, new FacesMessage(FacesMessage.SEVERITY_INFO,summary,details));
    }
   
}
