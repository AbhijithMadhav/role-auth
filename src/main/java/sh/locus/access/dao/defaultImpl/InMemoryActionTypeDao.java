package sh.locus.access.dao.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.ActionType;

import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryActionTypeDao implements ActionTypeDao {

    private Map<String, ActionType> actionTypeMap;

    public InMemoryActionTypeDao() {
        this.actionTypeMap = new LinkedHashMap<>();
    }

    @Override
    public synchronized void persist(ActionType actionType) {
        Validate.notNull(actionType);
        actionTypeMap.put(actionType.id(), new ActionType(actionType));
    }

    @Override
    public synchronized void delete(String id) throws InvalidEntityException {
        ActionType actionType = actionTypeMap.get(id);
        if (actionType == null)
            throw new InvalidEntityException(id, ActionType.class);
        actionTypeMap.remove(id);
    }

    @Override
    public synchronized ActionType get(String id) throws InvalidEntityException {
        ActionType actionType = actionTypeMap.get(id);
        if (actionType == null)
            throw new InvalidEntityException(id, ActionType.class);
        return actionTypeMap.get(id);
    }
}
