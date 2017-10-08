package sh.locus.access.dao;

import sh.locus.access.entity.Role;

public interface RoleDao {
    // TODO : exceptions
    void persist(Role role) throws InvalidEntityException, EntityUnmappedException;
    void delete(String id);
    Role get(String id) throws InvalidEntityException;
}
