package sh.locus.access.service;

import sh.locus.access.dao.EntityUnmappedException;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.ActionType;
import sh.locus.access.entity.Resource;
import sh.locus.access.entity.Role;

public interface RoleService {
    Role get(String roleId) throws InvalidEntityException;

    void persist(Role role) throws InvalidEntityException, EntityUnmappedException;

}