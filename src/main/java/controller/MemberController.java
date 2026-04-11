package controller;

import java.util.List;
import model.Member;
import model.Resource;
import model.Staff;
import model.dao.MemberDAO;

public class MemberController {
    MemberDAO memberDAO = new MemberDAO();
    
    public boolean addMember(String fullName, String email) {
       if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            return false;
        }
       
        Member m = new Member();
        m.setFullName(fullName.trim());
        m.setEmail(email);               
          
        boolean success = memberDAO.create(m);

        if (success) {
            System.out.println("Resource added successfully.");
        } else {
            System.out.println("Failed to add resource.");
        }

        return success;
    }
    
    public boolean updateMember(Member r) { return memberDAO.update(r); }
    public boolean deleteResource(int id)     { return memberDAO.delete(id); }
 
    public List<Member> getAllResources() {
        try { return memberDAO.getAll(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }
}
