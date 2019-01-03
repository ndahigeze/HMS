/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.Message;
import dao.UserDao;
import domain.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ndahigeze
 */
@ManagedBean
@SessionScoped
public class UserModel{
    private String username;
    private String password;
    private List<Users> users=new UserDao().findAll(Users.class);
    private Users user=new Users();
    private Users userDetails=new Users();
    private String search;
    private String userdetails;
    private String sid;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
    
    public String getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(String userdetails) {
        this.userdetails = userdetails;
    }
    
    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
    private String dic;
    private String ps; 
    
    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
  
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Users userDetails) {
        this.userDetails = userDetails;
    }

 
    public void viewUser(){
     users=new UserDao().findAll(Users.class);
     if(search.length()>0){
         users=new ArrayList<>();
         new UserDao().findAll(Users.class).stream().filter((u) -> (
                 search.contains(u.getId()+"")||
                         u.getFname().toLowerCase().contains(search)||
                         u.getLname().toLowerCase().contains(search)||
                         u.getId().toLowerCase().contains(search)||
                         u.getPhone().toLowerCase().contains(search)
                 )).forEachOrdered((u) -> {
                     users.add(u);
         });
     }else{
      users=new UserDao().findAll(Users.class);
     }
    }
    
    public void recordUser(){
        try{
            if(user.getPassword().equals(password)){
             user.setType("customer");
             String msg=new UserDao().create(user);
            user=new Users();
            users=new UserDao().findAll(Users.class);
             Message.succes(msg, "");
            }else{
               Message.failure("Confirm Password", "");
            }
        }catch(Exception ex){
              Message.failure(ex.getLocalizedMessage(), "");
        }
        
    }
    
     public void setDetails(Users u){
      user=u;
    }
    
     public void updateUser(){
        try{
             String msg=new UserDao().update(userDetails);
            userDetails=new Users();
            users=new UserDao().findAll(Users.class);
             Message.succes(msg, "");
        }catch(Exception ex){
             Message.failure(ex.getLocalizedMessage(), "");
        }
        
    }
    
    public void setDetail(final Users user){
      this.userDetails=user; 
    }
    
    public void deleteUser(){
      try{
         String msg=new UserDao().delete(userDetails);
         userDetails=new Users();
         users=new UserDao().findAll(Users.class);
          Message.succes(msg, "");
      }catch(Exception ex){
         Message.failure(ex.getLocalizedMessage(), "");
      }
    }
    
    
    //Login Action
      
    public String login() throws IOException{
        findUser();
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if(user!=null){
            userdetails=user.getType()+":  "+user.getFname()+" "+user.getLname()+" ";
            sid=user.getId();
            switch (user.getType()) {
                case "admin":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                     ec.redirect(ec.getRequestContextPath() + "/pages/adminPages/hotelPage.xhtml");
                    return "pages/adminPages/hotelPage.xhtml?faces-redirect=true";
                case "customer":
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/customerPage/customerPage.xhtml");
                    return "pages/customerPage/customerPage.xhtml?faces-redirect=true";
                default:
                    user=null;
                    
                    try {
                        ec.redirect(ec.getRequestContextPath() + "/pages/login.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return "/HMS/pages/login.xhtml";
            }
                  
        }else{
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Wrong Password Or Username"));
           return "/pages/login.xhtml";
        }
        
    }
    
    public void findUser(){
        List<Users> usersLogin=new UserDao().login(username, password);
        
        if(!usersLogin.isEmpty()){
            for(Users u: usersLogin){
               user=u;
            }
        }else{
          user=null;
        }
       
    }
    public void logout() throws IOException{
         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
         user = null;
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.redirect(ec.getRequestContextPath() + "/pages/login.xhtml");        
    }
    
      public void activate(Users u){
        try{
             String msg=new UserDao().update(u);
            users=new UserDao().findAll(Users.class);
             FacesContext.getCurrentInstance().addMessage("users", new FacesMessage(msg, ""));
        }catch(Exception ex){
              FacesContext.getCurrentInstance().addMessage("users", new FacesMessage("There is a problem", ""));
        }
        
    }
        public void block(Users u){
        try{
             String msg=new UserDao().update(u);
            users=new UserDao().findAll(Users.class);
             FacesContext.getCurrentInstance().addMessage("users", new FacesMessage(msg, ""));
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage("users", new FacesMessage("There is a problem", ""));
        }
        
    }
}
