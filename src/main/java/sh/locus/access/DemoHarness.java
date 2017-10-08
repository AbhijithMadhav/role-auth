package sh.locus.access;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DemoHarness {

    // TODO : logger
    // TODO : return types in dao
    // TODO : remove wrappers in service layers
    // TODO : Documentation
    public static void main(String args[]) throws IOException {


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DefaultConfiguration.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        RoleService roleService = (RoleService) applicationContext.getBean("roleService");
        ActionTypeService actionTypeService = (ActionTypeService) applicationContext.getBean("actionTypeService");
        ResourceService resourceService = (ResourceService) applicationContext.getBean("resourceService");

        BufferedReader br = null;
        try {
            try {
                br = new BufferedReader(new FileReader(args[0]));
            } catch (ArrayIndexOutOfBoundsException e) {
                if (br != null)
                    br.close();
                br =   new BufferedReader(new InputStreamReader(DemoHarness.class.getClassLoader().getResourceAsStream("input.txt")));
            }

            String line;
            while((line = br.readLine()) != null) {
                System.out.println("Processing -> " + line);
                StringTokenizer tokenizer = new StringTokenizer(line, ":");
                String key = tokenizer.nextToken().trim();
                String value = tokenizer.nextToken().trim();

                if (key.startsWith("Re")) { // resource to action mapping
                    processResourceActionAssociation(resourceService, actionTypeService, key, value);
                } else if (key.startsWith("Ro")) { // role definition
                    String[] values = value.split("\\s");
                    values[0] = values[0].trim();
                    values[1] = values[1].trim();
                    processRoleDefinition(resourceService, actionTypeService, roleService, key, values[0], values[1]);
                } else if (key.startsWith("U")) {
                    String[] values = value.split("\\s");
                    values[0] = values[0].trim();
                    values[1] = values[1].trim();

                    switch (values[0]) {
                        case "+":
                            processUserRoleModification(key, values[1], userService, roleService, Boolean.TRUE);
                            break;
                        case "-":
                            processUserRoleModification(key, values[1], userService, roleService, Boolean.FALSE);
                            break;
                        default:  // User accessMap query
                            answerUserAccessQuery(userService, key, values[0], values[1]);
                            break;
                    }
                } else
                    System.out.print("Invalid line. Ignoring...");
            }
        } catch (InvalidEntityException e){
            System.out.println(e.getId() + "(" + e.getEntityTypeName() + " ) not found");
        } catch (EntityUnmappedException e){
            System.out.println(e.getUnmappedEntityId() + "(" + e.getUnmappedEntityTypeName() + ") not mapped to " + e.getToBeMappedEntityId() + "(" + e.getToBeMappedEntityTypeName() + ")");
        }finally {
            if (br != null)
                br.close();
        }
    }

    private static void processUserRoleModification(String userId, String roleId, UserService userService, RoleService roleService, Boolean add) throws InvalidEntityException {
        User user;
        try {
            user = userService.get(userId);
        } catch (InvalidEntityException e) {
            user = new User(userId);
        }

        if (add)
            user.addRole(roleService.get(roleId));
        else
            user.removeRole(roleService.get(roleId));
        userService.persist(user);

    }
    private static void answerUserAccessQuery(UserService userService, String userId, String resourceId, String actionTypeId) throws InvalidEntityException {
        Boolean hasAccess = userService.hasAccess(userId, resourceId, actionTypeId);
        System.out.println(userId + " has " + actionTypeId + " type access on " + resourceId + "? " + (hasAccess ? "Yes" : "No"));
    }

    private static void processRoleDefinition(ResourceService resourceService, ActionTypeService actionTypeService, RoleService roleService, String roleId, String resourceId, String actionId) throws InvalidEntityException, EntityUnmappedException {
        Role role;
        try {
            role = roleService.get(roleId);
        } catch (InvalidEntityException e) {
            role = new Role(roleId);
        }

        role.addAccess(resourceService.get(resourceId), actionTypeService.get(actionId));
        roleService.persist(role);
    }

    private static void processResourceActionAssociation(ResourceService resourceService, ActionTypeService actionTypeService, String key, String value) throws InvalidEntityException {
        Resource resource;
        try {
            resource = resourceService.get(key);
        } catch (InvalidEntityException e) {
            resource = new Resource(key);
        }

        StringTokenizer valueTokenizer = new StringTokenizer(value);
        while (valueTokenizer.hasMoreTokens()) {
            String actionTypename = valueTokenizer.nextToken().trim();
            ActionType actionType;
            try {
                actionType = actionTypeService.get(actionTypename);
            } catch (InvalidEntityException e) {
                actionType = new ActionType(actionTypename);
                actionTypeService.persist(actionType);
            }
            resource.addActionType(actionType);
            resourceService.persist(resource);
        }
    }
}
