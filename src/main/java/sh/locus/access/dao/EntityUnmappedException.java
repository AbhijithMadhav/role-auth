package sh.locus.access.dao;

public class EntityUnmappedException extends Throwable {
    public EntityUnmappedException(String unmappedEntityId, Class unmappedEntityType, String toBeMappedEntityId, Class toBeMappedEntityType) {
        super(unmappedEntityId + "(" + unmappedEntityType.getSimpleName() + ") not mapped to " + toBeMappedEntityId + "(" + toBeMappedEntityType.getSimpleName() + ")");
        this.unmappedEntityId = unmappedEntityId;
        this.unmappedEntityTypeName = unmappedEntityType.getSimpleName();
        this.toBeMappedEntityId = toBeMappedEntityId;
        this.toBeMappedEntityTypeName = toBeMappedEntityType.getSimpleName();
    }

    private String unmappedEntityId;
    private String unmappedEntityTypeName;
    private String toBeMappedEntityId;

    public String getUnmappedEntityId() {
        return unmappedEntityId;
    }

    public String getUnmappedEntityTypeName() {
        return unmappedEntityTypeName;
    }

    public String getToBeMappedEntityId() {
        return toBeMappedEntityId;
    }

    public String getToBeMappedEntityTypeName() {
        return toBeMappedEntityTypeName;
    }

    private String toBeMappedEntityTypeName;
}
