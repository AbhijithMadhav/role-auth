package sh.locus.access.dao.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.entity.ActionType;
import sh.locus.access.entity.Resource;

import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryResourceDao implements ResourceDao {

    private Map<String, Resource> resourceMap;

    private ActionTypeDao actionTypeDao;

    public InMemoryResourceDao(ActionTypeDao actionTypeDao) {
        Validate.notNull(actionTypeDao);

        this.actionTypeDao = actionTypeDao;
        resourceMap = new LinkedHashMap<>();
    }

    @Override
    public synchronized void persist(Resource resource) throws InvalidEntityException {
        Validate.notNull(resource);
        for (ActionType actionType : resource.actionTypes()){
            actionTypeDao.get(actionType.id()); // checking for validity
        }
        resourceMap.put(resource.id(), new Resource(resource));
    }

    @Override
    public synchronized void delete(String id) {
        resourceMap.remove(id);
    }

    @Override
    public synchronized Resource get(String id) throws InvalidEntityException {
        Resource resource = resourceMap.get(id);
        if (resource == null)
            throw new InvalidEntityException(id, Resource.class);
        return resourceMap.get(id);
    }
}
