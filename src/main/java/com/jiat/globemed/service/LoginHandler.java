package com.jiat.globemed.service;
import com.jiat.globemed.model.Staff;

abstract public class LoginHandler {
    protected LoginHandler next;

    public void setNext(LoginHandler next) {
        this.next = next;
    }

    public abstract boolean handle(Staff staff, String inputPassword);
}
