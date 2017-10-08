package sh.locus.access.entity;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

public class Resource {

    private final String id;

    private Set<ActionType> actionTypes;

    public Set<ActionType> actionTypes() {
        return new LinkedHashSet<>(actionTypes);
    }

    public Resource(String id) {
        Validate.notNull(id);
        this.id = id;
        actionTypes = new LinkedHashSet<>();
    }

    public Resource(Resource resource) {
        Validate.notNull(resource);
        this.id = resource.id;
        actionTypes = new LinkedHashSet<>();
        for (ActionType actionType : resource.actionTypes()) {
            actionTypes.add(new ActionType(actionType.id()));
        }
    }
    public String id() {
        return id;
    }

    public Boolean addActionType(ActionType actionType) {
        Validate.notNull(actionType);
        if (!actionTypes.contains(actionType)) {
            actionTypes.add(actionType);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean removeActionType(ActionType actionType) {
        Validate.notNull(actionType);
        if (!actionTypes.contains(actionType)) {
            actionTypes.remove(actionType);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return new EqualsBuilder()
                .append(id, resource.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}
