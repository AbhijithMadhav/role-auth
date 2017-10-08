package sh.locus.access.dao.defaultImpl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sh.locus.access.config.DefaultConfiguration;
import sh.locus.access.dao.EntityUnmappedException;
import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.ActionType;
import sh.locus.access.entity.Resource;
import sh.locus.access.entity.Role;
import sh.locus.access.service.ActionTypeService;
import sh.locus.access.service.ResourceService;
import sh.locus.access.service.RoleService;
import sh.locus.access.service.UserService;

public class InMemoryRoleDaoTest {

    @Test
    public void persist_test() throws InvalidEntityException, EntityUnmappedException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DefaultConfiguration.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        RoleService roleService = (RoleService) applicationContext.getBean("roleService");
        ActionTypeService actionTypeService = (ActionTypeService) applicationContext.getBean("actionTypeService");
        ResourceService resourceService = (ResourceService) applicationContext.getBean("resourceService");

        ActionType a1 = new ActionType("a1");
        actionTypeService.persist(a1);

        Resource resource = new Resource("Re1");
        resource.addActionType(a1);
        resourceService.persist(resource);

        Role role = new Role("Ro1");
        role.addAccess(resource, a1);
        roleService.persist(role);

        ActionType a2 = new ActionType("a2");
        actionTypeService.persist(a2);
        role.addAccess(resource, a2);

        try {
            roleService.persist(role);
            Assert.fail();
        } catch (EntityUnmappedException e) {

        }


    }
}
