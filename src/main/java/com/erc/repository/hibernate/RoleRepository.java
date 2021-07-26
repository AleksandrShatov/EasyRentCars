package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import com.erc.repository.CrudOperations;

import java.util.List;

public interface RoleRepository extends CrudOperations<Integer, Role> {

    Role findByRoleName(String roleName);

    List<Role> getUserRoles(User user);

}
