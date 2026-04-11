package model;

import java.time.LocalDateTime;

public class ResourceCopy {
    public enum ResourceStatus {
        ACTIVE, INACTIVE, REFERENCE_ONLY,
        ON_ORDER, ARCHIVED, WITHDRAWN;

        public static ResourceStatus fromString(String value) {
            return ResourceStatus.valueOf(value.toUpperCase());
        }
    }
    
    private String barcode;
    private int resourceId;
    private ResourceStatus status;
    private LocalDateTime acquiredAt;
    private LocalDateTime updatedAt;

    public ResourceCopy() {}
    
    public ResourceCopy(String barcode, int resource_id, ResourceStatus status, LocalDateTime acquireAt, LocalDateTime updatedAt) {
        this.barcode = barcode;
        this.resourceId = resource_id;
        this.status = status;
        this.acquiredAt = acquireAt;
        this.updatedAt = updatedAt;
    }
    
    public String getBarcode()               { return barcode; }
    public void setBarcode(String barcode)        { this.barcode = barcode; }

    public int getResourceId()               { return resourceId; }
    public void setResourceId(int id)        { this.resourceId = resourceId; }

    public ResourceStatus getStatus()               { return status; }
    public void setStatus(ResourceStatus rs)        { this.barcode = barcode; }

    public LocalDateTime getAcquiredAt()  { return acquiredAt; }
    public void setAcquiredAt (LocalDateTime ldt)        { this.acquiredAt = acquiredAt; }

    public LocalDateTime getUpdatedAt()  { return updatedAt; }
    public void setUpdatedAt (LocalDateTime ldt)        { this.updatedAt = updatedAt; }
}
