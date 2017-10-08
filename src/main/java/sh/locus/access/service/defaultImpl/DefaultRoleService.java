package sh.locus.access.service.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.EntityUnmappedException;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.dao.RoleDao;
import sh.locus.access.entity.Role;
import sh.locus.access.service.RoleService;

public class DefaultRoleService implements RoleService {
    private RoleDao roleDao;
    private ResourceDao resourceDao;
    private ActionTypeDao actionTypeDao;

    public DefaultRoleService(RoleDao roleDao, ResourceDao resourceDao, ActionTypeDao actionTypeDao) {
        Validate.notNull(roleDao);
        Validate.notNull(resourceDao);
        Validate.notNull(actionTypeDao);

        this.roleDao = roleDao;
        this.resourceDao = resourceDao;
        this.actionTypeDao = actionTypeDao;
    }

    @Override
    public Role get(String roleId) throws InvalidEntityException {
        return roleDao.get(roleId);
    }

    @Override
    public void persist(Role role) throws InvalidEntityException, EntityUnmappedException {
        roleDao.persist(role);
    }
}
