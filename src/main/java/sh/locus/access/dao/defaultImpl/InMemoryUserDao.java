package sh.locus.access.dao.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.RoleDao;
import sh.locus.access.dao.UserDao;
import sh.locus.access.entity.Role;
import sh.locus.access.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryUserDao implements UserDao {

    private Map<String, User> userMap;
    private RoleDao roleDao;

    public InMemoryUserDao(RoleDao roleDao) {
        Validate.notNull(roleDao);
        this.roleDao = roleDao;
        userMap = new LinkedHashMap<>();
    }

    @Override
    public synchronized void persist(User user) throws InvalidEntityException {

        Validate.notNull(user);

        // Checking for role validity
        for (Role role : user.roles()) {
            roleDao.get(role.id());
        }

        userMap.put(user.id(), new User(user));
    }

    @Override
    public synchronized void delete(String id) {
        userMap.remove(id);
    }

    @Override
    public synchronized User get(String id) throws InvalidEntityException {
        User user = userMap.get(id);
        if (user == null)
            throw new InvalidEntityException(id, User.class);
        return user;
    }
}
