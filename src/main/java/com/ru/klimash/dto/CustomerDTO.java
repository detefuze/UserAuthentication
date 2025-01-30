package com.ru.klimash.dto;

public class CustomerDTO {

    private int id;
    private String fio;
    private String email;
    private int balance;

    public CustomerDTO() {
    }

    public CustomerDTO(int id, String fio, String email, int balance) {
        this.id = id;
        this.fio = fio;
        this.email = email;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}