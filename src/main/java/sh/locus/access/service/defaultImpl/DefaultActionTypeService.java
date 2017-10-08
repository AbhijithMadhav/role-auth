package sh.locus.access.service.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.ActionType;
import sh.locus.access.service.ActionTypeService;

public class DefaultActionTypeService implements ActionTypeService {

    private ActionTypeDao actionTypeDao;
    public DefaultActionTypeService(ActionTypeDao actionTypeDao) {
        Validate.notNull(actionTypeDao);

        this.actionTypeDao = actionTypeDao;
    }

    @Override
    public ActionType get(String id) throws InvalidEntityException {
        return actionTypeDao.get(id);
    }

    @Override
    public void persist(ActionType actionType) {
        actionTypeDao.persist(actionType);
    }
}
