/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lee
 */
import java.time.LocalDateTime;
public class Member {
    private int           memberId;
    private String        fullName;
    private String        email;
    private LocalDateTime dateRegistered;
 
    public Member() {}
    public int getMemberId()               { return memberId; }
    public void setMemberId(int id)        { this.memberId = id; }
 
    public String getFullName()            { return fullName; }
    public void setFullName(String n)      { this.fullName = n; }
 
    public String getEmail()               { return email; }
    public void setEmail(String e)         { this.email = e; }
 
    public LocalDateTime getDateRegistered()       { return dateRegistered; }
    public void setDateRegistered(LocalDateTime d) { this.dateRegistered = d; }
}


