package sh.locus.access.service;

import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.User;

public interface UserService {

    Boolean hasAccess(String userId, String resourceId, String actionTypeId) throws InvalidEntityException;

    User get(String userId) throws InvalidEntityException;

    void persist(User user) throws InvalidEntityException;

}
