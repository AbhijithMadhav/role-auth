package sh.locus.access.entity;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {
    private final String id;
    private Set<Role> roles;

    public User(String id) {
        Validate.notNull(id);

        this.id = id;
        roles = new LinkedHashSet<>();
    }

    public User(User user) {
        Validate.notNull(user);
        id = user.id();

        roles = new LinkedHashSet<>();
        for (Role role : user.roles()) {
            roles.add(new Role(role.id()));
        }
    }
    public String id() {
        return id;
    }

    public Set<Role> roles() {
        return new LinkedHashSet(roles);
    }

    public void addRole(Role role) {
        Validate.notNull(role);
        roles.add(role);
    }

    public void removeRole(Role role) {
        Validate.notNull(role);
        roles.remove(role);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("roles", roles)
                .toString();
    }
}
