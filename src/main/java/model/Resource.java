package model;

import java.time.LocalDateTime;

public class Resource {

    // ============================================================
    // Enums
    // ============================================================

    public enum ResourceType {
        BOOK, JOURNAL, THESIS, DISSERTATION,
        MAGAZINE, NEWSPAPER, RESEARCH_PAPER,
        GOVERNMENT_DOCUMENT, AUDIOVISUAL, MAP, OTHER;

        public static ResourceType fromString(String value) {
            return ResourceType.valueOf(value.toUpperCase());
        }
    }

    public enum ResourceStatus {
        ACTIVE, INACTIVE, REFERENCE_ONLY,
        ON_ORDER, ARCHIVED, WITHDRAWN;

        public static ResourceStatus fromString(String value) {
            return ResourceStatus.valueOf(value.toUpperCase());
        }
    }

    // ============================================================
    // Fields
    // ============================================================

    private int resourceId;
    private String title;               // NOT NULL  
    private ResourceType resourceType;  // NOT NULL  
    private String author;              // NULL
    private int yearPublished;
    private String isbnIssn;            // NULL      
    private String degreeLevel;         // NULL      
    private ResourceStatus status;      // NOT NULL 
    private int totalCopies;
    private int availableCopies;
    private Integer addedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ============================================================
    // Constructor
    // ============================================================

    public Resource() {}

    public Resource(int resourceId, String title, ResourceType resourceType,
                    String author,int yearPublished, String isbnIssn, String degreeLevel,
                    ResourceStatus status, int totalCopies, int availableCopies,
                    int addedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.resourceId   = resourceId;
        this.title        = title;
        this.resourceType = resourceType;
        this.author       = author;
        this.yearPublished = yearPublished;
        this.isbnIssn     = isbnIssn;
        this.degreeLevel  = degreeLevel;
        this.status       = status;
        this.totalCopies      = totalCopies;
        this.availableCopies = availableCopies;
        this.addedBy      = addedBy;
        this.createdAt    = createdAt;
        this.updatedAt    = updatedAt;
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

    public int getResourceId()               { return resourceId; }
    public void setResourceId(int id)        { this.resourceId = id; }

    public String getTitle()                 { return title; }
    public void setTitle(String title)       { this.title = title; }

    public ResourceType getResourceType()             { return resourceType; }
    public void setResourceType(ResourceType type)    { this.resourceType = type; }

    public String getAuthor()                { return author; }
    public void setAuthor(String author)     { this.author = author; }
    
    public int getYearPublished()            { return yearPublished; }
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished; }

    public String getIsbnIssn()              { return isbnIssn; }
    public void setIsbnIssn(String isbnIssn) { this.isbnIssn = isbnIssn; }

    public String getDegreeLevel()                   { return degreeLevel; }
    public void setDegreeLevel(String degreeLevel)   { this.degreeLevel = degreeLevel; }

    public ResourceStatus getStatus()                { return status; }
    public void setStatus(ResourceStatus status)     { this.status = status; }

    public int getTotalCopies()              { return totalCopies; }
    public void setTotalCopies(int total)    { this.totalCopies = total; }
    
    public int getAvailableCopies()              { return availableCopies; }
    public void setAvailableCopies(int total)    { this.availableCopies = total; }

    public Integer getAddedBy()                  { return addedBy; }
    public void setAddedBy(Integer addedBy)      { this.addedBy = addedBy; }

    public LocalDateTime getCreatedAt()              { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()              { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
