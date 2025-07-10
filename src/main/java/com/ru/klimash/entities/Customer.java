package com.ru.klimash.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private int id;

    @Column
    private String fio;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Timestamp authenticated_at;

    @Column
    private int balance;

    public Customer() {
    }

    public Customer(int id, String fio, String email,
                    String password, int balance, Timestamp authenticated_at) {
        this.id = id;
        this.fio = fio;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.authenticated_at = authenticated_at;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Timestamp getAuthenticated_at() {
        return authenticated_at;
    }

    public void setAuthenticated_at(Timestamp authenticated_at) {
        this.authenticated_at = authenticated_at;
    }
}
