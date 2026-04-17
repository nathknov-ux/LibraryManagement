package controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Circulation;
import model.Member;
import model.ResourceCopy;
import model.dao.CirculationDAO;
import model.dao.MemberDAO;
import model.dao.ResourceCopyDAO;
import util.EmailService;
import util.Session;

public class CirculationController {
    private CirculationDAO dao = new CirculationDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private ResourceCopyDAO resourceCopyDAO = new ResourceCopyDAO();

    /**
     * Checks if a resource copy is currently borrowed.
     * @param barcode the barcode of the resource copy
     * @return true if the copy exists and has BORROWED status, false otherwise
     */
    public boolean isBorrowed(String barcode) {
        try {
            ResourceCopy copy = resourceCopyDAO.getByBarcode(barcode);
            return copy != null && copy.getStatus() == ResourceCopy.ResourceStatus.BORROWED;
        } catch (Exception e) {
            System.out.println("Status check failed: " + e.getMessage());
            return false;
        }
    }

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

        // Check if the resource copy is already borrowed
        try {
            ResourceCopy copy = resourceCopyDAO.getByBarcode(barcode.trim());
            if (copy == null) {
                System.out.println("No resource copy found with barcode: " + barcode);
                return false;
            }
            if (copy.getStatus() == ResourceCopy.ResourceStatus.BORROWED) {
                System.out.println("Resource copy is already borrowed.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Failed to check resource status: " + e.getMessage());
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
            try {
                Circulation c = dao.getById(circulationId);
                if (c != null) {
                    // Explicitly update resource_copy status to AVAILABLE
                    try {
                        ResourceCopy copy = resourceCopyDAO.getByBarcode(c.getBarcode());
                        if (copy != null && copy.getStatus() == ResourceCopy.ResourceStatus.BORROWED) {
                            copy.setStatus(ResourceCopy.ResourceStatus.AVAILABLE);
                            resourceCopyDAO.update(copy);
                        }
                    } catch (Exception ex) {
                        System.out.println("Failed to update resource copy status: " + ex.getMessage());
                    }

                    // Send return notification email to member
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
        if (amount == null) {
            System.out.println("Fine amount is required.");
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Fine amount cannot be negative.");
            return false;
        }
        try {
            Circulation c = dao.getById(circulationId);
            if (c == null) return false;
            c.setFineAmount(amount);
            if (reason != null && !reason.isEmpty()) {
                Circulation.FineReason fineReason = Circulation.FineReason.valueOf(reason.toUpperCase());
                c.setReason(fineReason);

                // Map reason to status:
                // LOST → always LOST (record returned timestamp when paid)
                // OVERDUE / DAMAGED → RETURNED when paid, otherwise OVERDUE
                switch (fineReason) {
                    case LOST:
                        c.setStatus(Circulation.CirculationStatus.LOST);
                        if (paid && c.getReturned() == null) {
                            c.setReturned(LocalDateTime.now());
                        }
                        break;
                    case OVERDUE:
                        c.setStatus(paid
                            ? Circulation.CirculationStatus.RETURNED
                            : Circulation.CirculationStatus.OVERDUE);
                        if (paid && c.getReturned() == null) {
                            c.setReturned(LocalDateTime.now());
                        }
                        break;
                    case DAMAGED:
                        c.setStatus(paid
                            ? Circulation.CirculationStatus.RETURNED
                            : c.getStatus()); // keep current status until paid
                        if (paid && c.getReturned() == null) {
                            c.setReturned(LocalDateTime.now());
                        }
                        break;
                }
            }
            c.setPaid(paid);
            boolean success = dao.updateFine(c);

            // Update resource_copy status to AVAILABLE when circulation is resolved
            if (success && paid) {
                Circulation.CirculationStatus newStatus = c.getStatus();
                if (newStatus == Circulation.CirculationStatus.RETURNED
                        || newStatus == Circulation.CirculationStatus.LOST) {
                    try {
                        ResourceCopy copy = resourceCopyDAO.getByBarcode(c.getBarcode());
                        if (copy != null && copy.getStatus() == ResourceCopy.ResourceStatus.BORROWED) {
                            copy.setStatus(ResourceCopy.ResourceStatus.AVAILABLE);
                            resourceCopyDAO.update(copy);
                        }
                    } catch (Exception ex) {
                        System.out.println("Failed to update resource copy status: " + ex.getMessage());
                    }
                }
            }

            return success;
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
