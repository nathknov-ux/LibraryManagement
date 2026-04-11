package controller;

import java.util.List;
import model.Resource;
import model.ResourceCopy;
import model.dao.ResourceCopyDAO;

public class ResourceCopyController {
    ResourceCopyDAO rcd = new ResourceCopyDAO();
    
    public boolean addResourceCopy(Resource r, int copies) {

        // 1. Validate required fields
        
        if (copies < 0) {
            System.out.println("Copies must be at least 1.");
            return false;
        }

        ResourceCopy rc = new ResourceCopy();       
        rc.setResourceId(r.getResourceId());      
        

        boolean success = rcd.create(rc,r,copies);
        if (success) {
            System.out.println("Resource added successfully.");
        } else {
            System.out.println("Failed to add resource.");
        }
        return success;
    }
    
    public boolean updateResourceCopy(ResourceCopy r) { return rcd.update(r); }
    public boolean deleteResourceCopy(String barcode)     { return rcd.delete(barcode); }
 
    public List<ResourceCopy> getAllResources() {
        try { return rcd.getAll(); }
        catch (Exception e) { System.out.println(e.getMessage()); return null; }
    }

}
