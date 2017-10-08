package sh.locus.access.service.defaultImpl;

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
import sh.locus.access.entity.User;
import sh.locus.access.service.ActionTypeService;
import sh.locus.access.service.ResourceService;
import sh.locus.access.service.RoleService;
import sh.locus.access.service.UserService;

public class DefaultUserServiceTest {

    @Test
    public void hasAccess_test() throws InvalidEntityException, EntityUnmappedException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DefaultConfiguration.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        RoleService roleService = (RoleService) applicationContext.getBean("roleService");
        ActionTypeService actionTypeService = (ActionTypeService) applicationContext.getBean("actionTypeService");
        ResourceService resourceService = (ResourceService) applicationContext.getBean("resourceService");


        String actionTypeId1 = "A1";
        ActionType a1 = new ActionType(actionTypeId1);
        actionTypeService.persist(a1);

        String resourceId1 = "Re1";
        Resource re1 = new Resource(resourceId1);
        re1.addActionType(a1);
        resourceService.persist(re1);

        Role ro1 = new Role("Ro1");
        ro1.addAccess(re1, a1);
        roleService.persist(ro1);

        String userId1 = "U1";
        User u1 = new User(userId1);
        u1.addRole(ro1);
        userService.persist(u1);

        Assert.assertTrue(userService.hasAccess(userId1, resourceId1, actionTypeId1));

        String actionTypeId2 = "A2";
        ActionType a2 = new ActionType(actionTypeId2);
        actionTypeService.persist(a2);

        Assert.assertFalse(userService.hasAccess(userId1, resourceId1, actionTypeId2));

        String resourceId2 = "Re2";
        Resource re2 = new Resource(resourceId2);
        re2.addActionType(a2);
        resourceService.persist(re2);
        Assert.assertFalse(userService.hasAccess(userId1, resourceId2, actionTypeId1));

        u1.removeRole(ro1);
        userService.persist(u1);
        Assert.assertFalse(userService.hasAccess(userId1, resourceId1, actionTypeId1));

        u1.addRole(ro1);
        userService.persist(u1);
        Assert.assertTrue(userService.hasAccess(userId1, resourceId1, actionTypeId1));


        ro1.removeAccess(re1, a1);
        roleService.persist(ro1);
        Assert.assertFalse(userService.hasAccess(userId1, resourceId1, actionTypeId1));
    }
}
