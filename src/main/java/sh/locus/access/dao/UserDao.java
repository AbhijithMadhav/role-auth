package sh.locus.access.dao;

import sh.locus.access.entity.User;

public interface UserDao {
    void persist(User user) throws InvalidEntityException;
    void delete(String id);
    User get(String id) throws InvalidEntityException;
}
