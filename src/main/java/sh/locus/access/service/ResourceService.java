package sh.locus.access.service;

import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.Resource;

public interface ResourceService {
    Resource get(String resourceId) throws InvalidEntityException;

    void persist(Resource resource) throws InvalidEntityException;
}