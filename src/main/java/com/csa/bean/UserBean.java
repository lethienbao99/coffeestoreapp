/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csa.bean;

import com.csa.pojo.User;
import com.csa.services.UserSvc;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;

/**
 *
 * @author LTBao
 */
@ManagedBean
@Named(value = "userBean")
@RequestScoped
public class UserBean {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Transient //để cho biết thuộc tính để validate chứ ko phải thuộc tính chính trong bean
    private String confirmPassword;

    private static UserSvc userSvc = new UserSvc();

    public String register() {
        if (!this.password.isEmpty() && this.password.equals(this.confirmPassword)) {
            User u = new User(); //transient: chưa link tới 1 record nào dưới db
            u.setFirstname(firstname);
            u.setLastname(lastname);
            u.setEmail(email);
            u.setPassword(password);
            u.setUsername(username);

            if (getUserSvc().addUser(u) == true) {
                return "login?faces-redirect=true";
            }
        }
        return "signup";    
    }

    public String login() {
        User u = userSvc.login(username, password);
        if (u != null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("user", u);
            return "index?faces-redirect=true";
        }
        return "login";
    }

    public String checkLogin() {
        if (FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("user") != null) {
            return "index?faces-redirect=true";
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .remove("user");
        return "login?faces-redirect=true";
    }

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the userSvc
     */
    public static UserSvc getUserSvc() {
        return userSvc;
    }

    /**
     * @param aUserSvc the userSvc to set
     */
    public static void setUserSvc(UserSvc aUserSvc) {
        userSvc = aUserSvc;
    }

}
