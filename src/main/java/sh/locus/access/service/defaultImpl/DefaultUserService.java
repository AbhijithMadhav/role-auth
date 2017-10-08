package sh.locus.access.service.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.dao.RoleDao;
import sh.locus.access.dao.UserDao;
import sh.locus.access.entity.ActionType;
import sh.locus.access.entity.Resource;
import sh.locus.access.entity.Role;
import sh.locus.access.entity.User;
import sh.locus.access.service.UserService;

import java.util.Map;
import java.util.Set;

public class DefaultUserService implements UserService {


    private UserDao userDao;
    private ResourceDao resourceDao;
    private ActionTypeDao actionTypeDao;
    private RoleDao roleDao;

    public DefaultUserService(UserDao userDao, ResourceDao resourceDao, ActionTypeDao actionTypeDao, RoleDao roleDao) {
        Validate.notNull(userDao);
        Validate.notNull(resourceDao);
        Validate.notNull(actionTypeDao);
        Validate.notNull(roleDao);

        this.userDao = userDao;
        this.resourceDao = resourceDao;
        this.actionTypeDao = actionTypeDao;
        this.roleDao = roleDao;
    }

    @Override
    public Boolean hasAccess(String userId, String resourceId, String actionTypeId) throws InvalidEntityException {
        Validate.notNull(userId);
        Validate.notNull(resourceId);
        Validate.notNull(actionTypeId);

        User user = userDao.get(userId);
        Resource resource = resourceDao.get(resourceId);
        ActionType actionType = actionTypeDao.get(actionTypeId);

        for (Role role : user.roles()){
            role = roleDao.get(role.id());
            Map<Resource, Set<ActionType>> accessMap = role.accessMap();
            Set<ActionType> actionTypes = accessMap.get(resource);
            if (actionTypes == null)
                continue;

            if (actionTypes.contains(actionType))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public User get(String userId) throws InvalidEntityException {
        return userDao.get(userId);
    }

    @Override
    public void persist(User user) throws InvalidEntityException {
        userDao.persist(user);
    }
}
