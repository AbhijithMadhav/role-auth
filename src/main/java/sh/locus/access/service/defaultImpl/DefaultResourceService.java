package sh.locus.access.service.defaultImpl;

import org.apache.commons.lang3.Validate;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.entity.Resource;
import sh.locus.access.service.ResourceService;

public class DefaultResourceService implements ResourceService {

    private ResourceDao resourceDao;

    public DefaultResourceService(ResourceDao resourceDao) {
        Validate.notNull(resourceDao);

        this.resourceDao = resourceDao;
    }

    @Override
    public Resource get(String resourceId) throws InvalidEntityException {
        return resourceDao.get(resourceId);
    }

    @Override
    public void persist(Resource resource) throws InvalidEntityException {
        resourceDao.persist(resource);
    }
}
