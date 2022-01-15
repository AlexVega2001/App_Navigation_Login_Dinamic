package com.example.app_navigation_login_dinamic.Models;

public class Users {
    private int idUser;
    private String username, password, opc1, opc2;

    public Users() {
    }

    public Users(int idUser, String username, String password, String opc1, String opc2) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.opc1 = opc1;
        this.opc2 = opc2;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getOpc1() {
        return opc1;
    }

    public void setOpc1(String opc1) {
        this.opc1 = opc1;
    }

    public String getOpc2() {
        return opc2;
    }

    public void setOpc2(String opc2) {
        this.opc2 = opc2;
    }
}
