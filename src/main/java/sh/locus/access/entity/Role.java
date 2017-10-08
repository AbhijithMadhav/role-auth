package sh.locus.access.entity;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Role {

    private final String id;

    private Map<Resource, Set<ActionType>> resourceActionTypeMap;

    public Role(String id) {
        Validate.notNull(id);
        this.id = id;
        resourceActionTypeMap = new LinkedHashMap<>();
    }

    public Role(Role role) {
        Validate.notNull(role);
        this.id = role.id;
        resourceActionTypeMap = new HashMap<>();
        for (Resource resource : role.accessMap().keySet()) {
            Resource re = new Resource(resource);
            Set<ActionType> actionTypes = new LinkedHashSet<>();
            for (ActionType actionType : resource.actionTypes()) {
                ActionType at = new ActionType(actionType);
                actionTypes.add(at);
            }
            resourceActionTypeMap.put(re, actionTypes);
        }
    }

    public String id() {
        return id;
    }

    public Map<Resource, Set<ActionType>> accessMap() {
        return new LinkedHashMap<>(resourceActionTypeMap);
    }

    public Set<ActionType> accessList(Resource resource) {
        return resourceActionTypeMap.get(resource);
    }

    public void addAccess(Resource resource, ActionType actionType) {
        Validate.notNull(resource);
        Validate.notNull(actionType);

        Set<ActionType> actionTypes = resourceActionTypeMap.get(resource);
        if (actionTypes == null)
            actionTypes = new LinkedHashSet<>();
        actionTypes.add(actionType);
        resourceActionTypeMap.put(resource, actionTypes);
    }

    public void removeAccess(Resource resource, ActionType actionType) {
        Validate.notNull(resource);
        Validate.notNull(actionType);

        Set<ActionType> actionTypes = resourceActionTypeMap.get(resource);
        if (actionTypes == null)
            return;
        actionTypes.remove(actionType);
        if (actionTypes.isEmpty())
            resourceActionTypeMap.remove(resource);
        else
            resourceActionTypeMap.put(resource, actionTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return new EqualsBuilder()
                .append(id, role.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}
