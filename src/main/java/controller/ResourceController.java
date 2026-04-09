package controller;

import java.util.List;
import model.Resource;
import model.Resource.ResourceStatus;
import model.dao.ResourceDAO;

public class ResourceController {

    private ResourceDAO resourceDAO = new ResourceDAO();

    public boolean addResource(String title, String resourceType,
                               String author, Integer yearPublished, String isbnIssn,
                               String degreeLevel, int totalCopies, int addedBy) {

        // 1. Validate required fields
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title is required.");
            return false;
        }
        if (resourceType == null || resourceType.trim().isEmpty()) {
            System.out.println("Resource type is required.");
            return false;
        }
        if (totalCopies < 1) {
            System.out.println("Total copies must be at least 1.");
            return false;
        }

        // 2. Build the model
        Resource r = new Resource();
        r.setTitle(title.trim());
        r.setResourceType(Resource.ResourceType.valueOf(resourceType.toUpperCase())); 
        r.setAuthor(author);
        r.setYearPublished(yearPublished);  // nullable Integer
        r.setIsbnIssn(isbnIssn);
        r.setDegreeLevel(degreeLevel);
        r.setStatus(ResourceStatus.ACTIVE);
        r.setTotalCopies(totalCopies);      // ← was setAvailableCopies()
        r.setAddedBy(addedBy);

        // 3. Call DAO
        boolean success = resourceDAO.Create(r);
        if (success) {
            System.out.println("Resource added successfully.");
        } else {
            System.out.println("Failed to add resource.");
        }
        return success;
    }
    
    public boolean updateResource(Resource r) { return resourceDAO.update(r); }
    public boolean deleteResource(int id)     { return resourceDAO.delete(id); }
 
    public List<Resource> getAllResources() {
        try { return resourceDAO.getAll(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }
    
}