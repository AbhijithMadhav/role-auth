package sh.locus.access.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.locus.access.dao.ActionTypeDao;
import sh.locus.access.dao.ResourceDao;
import sh.locus.access.dao.RoleDao;
import sh.locus.access.dao.UserDao;
import sh.locus.access.dao.defaultImpl.InMemoryActionTypeDao;
import sh.locus.access.dao.defaultImpl.InMemoryResourceDao;
import sh.locus.access.dao.defaultImpl.InMemoryRoleDao;
import sh.locus.access.dao.defaultImpl.InMemoryUserDao;
import sh.locus.access.service.ActionTypeService;
import sh.locus.access.service.ResourceService;
import sh.locus.access.service.RoleService;
import sh.locus.access.service.UserService;
import sh.locus.access.service.defaultImpl.DefaultActionTypeService;
import sh.locus.access.service.defaultImpl.DefaultResourceService;
import sh.locus.access.service.defaultImpl.DefaultRoleService;
import sh.locus.access.service.defaultImpl.DefaultUserService;

@Configuration
public class DefaultConfiguration {

    @Bean
    public ActionTypeDao actionTypeDao() {
        return new InMemoryActionTypeDao();
    }

    @Bean
    public ResourceDao resourceDao() {
        return new InMemoryResourceDao(actionTypeDao());
    }

    @Bean
    public RoleDao roleDao() {
        return new InMemoryRoleDao(resourceDao(), actionTypeDao());
    }

    @Bean
    public UserDao userDao() {
        return new InMemoryUserDao(roleDao());
    }


    @Bean
    public ActionTypeService actionTypeService() {
        return new DefaultActionTypeService(actionTypeDao());
    }

    @Bean
    public ResourceService resourceService() {
        return new DefaultResourceService(resourceDao());
    }

    @Bean
    public RoleService roleService() {
        return new DefaultRoleService(roleDao(), resourceDao(), actionTypeDao());
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(userDao(), resourceDao(), actionTypeDao(), roleDao());
    }
}
