package controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Circulation;
import model.Member;
import model.dao.CirculationDAO;
import model.dao.MemberDAO;
import util.EmailService;
import util.Session;

public class CirculationController {
    private CirculationDAO dao = new CirculationDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public boolean borrow(String barcode, int memberId, LocalDateTime dueDate) {
        if (barcode == null || barcode.trim().isEmpty()) {
            System.out.println("Barcode is required.");
            return false;
        }
        if (memberId <= 0) {
            System.out.println("Valid Member ID is required.");
            return false;
        }
        if (dueDate == null) {
            System.out.println("Due date is required.");
            return false;
        }

        Circulation c = new Circulation();
        c.setBarcode(barcode.trim());
        c.setMemberId(memberId);
        c.setDueDate(dueDate);
        c.setProcessedBy(Session.getStaffID());

        boolean success = dao.borrow(c);
        if (success) {
            System.out.println("Borrow recorded successfully.");
            // Send borrow receipt email to member
            try {
                Member member = memberDAO.getById(memberId);
                if (member != null && member.getEmail() != null && !member.getEmail().isEmpty()) {
                    String dueDateStr = dueDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a"));
                    EmailService.sendBorrowReceipt(
                        member.getEmail(), member.getFullName(),
                        barcode.trim(), dueDateStr);
                }
            } catch (Exception e) {
                System.out.println("Email send failed: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to record borrow.");
        }
        return success;
    }

    public boolean returnCopy(int circulationId) {
        if (circulationId <= 0) {
            System.out.println("Valid Circulation ID is required.");
            return false;
        }
        boolean success = dao.returnCopy(circulationId);
        if (success) {
            System.out.println("Return recorded successfully.");
            // Send return notification email to member
            try {
                Circulation c = dao.getById(circulationId);
                if (c != null) {
                    Member member = memberDAO.getById(c.getMemberId());
                    if (member != null && member.getEmail() != null && !member.getEmail().isEmpty()) {
                        EmailService.sendReturnNotification(
                            member.getEmail(), member.getFullName(),
                            c.getBarcode());
                    }
                }
            } catch (Exception e) {
                System.out.println("Email send failed: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to record return.");
        }
        return success;
    }

    public boolean updateFine(int circulationId, BigDecimal amount, String reason, boolean paid) {
        try {
            Circulation c = dao.getById(circulationId);
            if (c == null) return false;
            c.setFineAmount(amount);
            if (reason != null && !reason.isEmpty()) {
                c.setReason(Circulation.FineReason.valueOf(reason.toUpperCase()));
            }
            c.setPaid(paid);
            return dao.updateFine(c);
        } catch (Exception e) {
            System.out.println("Update fine failed: " + e.getMessage());
            return false;
        }
    }

    public List<Circulation> getAllCirculations() {
        try { return dao.getAll(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }

    public List<Circulation> getActiveBorrows() {
        try { return dao.getActiveBorrows(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }

    public List<Circulation> getOverdue() {
        try { return dao.getOverdue(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }

    public Circulation getById(int id) {
        try { return dao.getById(id); }
        catch (Exception e) { return null; }
    }

    public List<Circulation> search(String keyword) {
        try { return dao.search(keyword); }
        catch (Exception e) { return List.of(); }
    }
}
