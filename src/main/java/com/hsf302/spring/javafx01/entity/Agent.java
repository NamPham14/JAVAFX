package com.hsf302.spring.javafx01.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Agent_ID")
    private int agentId;

    @Column(name = "Agent_Name", length = 255)
    private String agentName;

    @Column(name = "Status", length = 50)
    private String status;

    @Column(name = "Email", length = 255)
    private String email;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "Register_Date")
    private Date registerDate;

    @Column(name = "Account_Balance")
    private double accountBalance;

    @Column(name = "Password")
    private String password;


    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
