package sh.locus.access.dao;

public class InvalidEntityException extends Throwable {

    private String id;
    private String entityTypeName;

    public InvalidEntityException(String id, Class entityType) {
        super("No such " + entityType.getSimpleName() + " : " + id);
        this.id = id;
        this.entityTypeName = entityType.getSimpleName();
    }

    public String getId() {
        return id;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }
}
