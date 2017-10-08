package sh.locus.access.dao;

import sh.locus.access.entity.Resource;

public interface ResourceDao {

    // TODO : exceptions
    void persist(Resource resource) throws InvalidEntityException;

    void delete(String id);

    Resource get(String id) throws InvalidEntityException;
}
