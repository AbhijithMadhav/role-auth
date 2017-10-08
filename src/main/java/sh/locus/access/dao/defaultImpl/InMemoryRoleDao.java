package sh.locus.access.dao.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.EntityUnmappedException;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.dao.RoleDao;
import sh.locus.access.entity.ActionType;
import sh.locus.access.entity.Resource;
import sh.locus.access.entity.Role;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryRoleDao implements RoleDao {

    private Map<String, Role> roleMap;
    private ResourceDao resourceDao;
    private ActionTypeDao actionTypeDao;

    public InMemoryRoleDao(ResourceDao resourceDao, ActionTypeDao actionTypeDao) {
        Validate.notNull(resourceDao);
        Validate.notNull(actionTypeDao);

        this.resourceDao = resourceDao;
        this.actionTypeDao = actionTypeDao;
        roleMap = new LinkedHashMap<>();
    }

    @Override
    public synchronized void persist(Role role) throws InvalidEntityException, EntityUnmappedException {
        Validate.notNull(role);

        Map<Resource, Set<ActionType>> map = role.accessMap();
        for (Resource resource : role.accessMap().keySet()) {
            Resource persistedResource = resourceDao.get(resource.id()); // check for resource validity
            for (ActionType actionType : role.accessList(resource)){
                actionTypeDao.get(actionType.id()); // check for actionType validity
                if (!persistedResource.actionTypes().contains(actionType)) { // check for resource-actiontype mapping
                    throw new EntityUnmappedException(actionType.id(), ActionType.class, persistedResource.id(), Resource.class);
                }
            }
        }
        roleMap.put(role.id(), new Role(role));
    }

    @Override
    public synchronized void delete(String id) {
        roleMap.remove(id);
    }

    @Override
    public synchronized Role get(String id) throws InvalidEntityException {
        Role role = roleMap.get(id);
        if (role == null)
            throw new InvalidEntityException(id, Role.class);
        return new Role(role);
    }
}
