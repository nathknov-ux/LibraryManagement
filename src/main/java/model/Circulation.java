/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lee
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;
public class Circulation {
    public enum CirculationStatus { BORROWED, RETURNED, OVERDUE, LOST }
    public enum FineReason        { OVERDUE, LOST, DAMAGED }
 
    private int               circulationId;
    private String            barcode;
    private int               memberId;
    private LocalDateTime     borrowedAt;
    private LocalDateTime     dueDate;
    private LocalDateTime     returned;    // null if not yet returned
    private CirculationStatus status;
    private BigDecimal        fineAmount;
    private FineReason        reason;      // nullable
    private boolean           paid;
    private int               processedBy;
 
    public Circulation() {}

    public int getCirculationId()            { return circulationId; }
    public void setCirculationId(int id)     { this.circulationId = id; }
    public String getBarcode()               { return barcode; }
    public void setBarcode(String b)         { this.barcode = b; }
    public int getMemberId()                 { return memberId; }
    public void setMemberId(int id)          { this.memberId = id; }
    public LocalDateTime getBorrowedAt()     { return borrowedAt; }
    public void setBorrowedAt(LocalDateTime d){ this.borrowedAt = d; }
    public LocalDateTime getDueDate()        { return dueDate; }
    public void setDueDate(LocalDateTime d)  { this.dueDate = d; }
    public LocalDateTime getReturned()       { return returned; }
    public void setReturned(LocalDateTime d) { this.returned = d; }
    public CirculationStatus getStatus()     { return status; }
    public void setStatus(CirculationStatus s){ this.status = s; }
    public BigDecimal getFineAmount()        { return fineAmount; }
    public void setFineAmount(BigDecimal f)  { this.fineAmount = f; }
    public FineReason getReason()            { return reason; }
    public void setReason(FineReason r)      { this.reason = r; }
    public boolean isPaid()                  { return paid; }
    public void setPaid(boolean p)           { this.paid = p; }
    public int getProcessedBy()              { return processedBy; }
    public void setProcessedBy(int id)       { this.processedBy = id; }
}


