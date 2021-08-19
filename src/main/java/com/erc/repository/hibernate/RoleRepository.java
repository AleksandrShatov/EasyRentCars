package com.erc.repository.hibernate;

import java.util.List;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import com.erc.repository.CrudOperations;

public interface RoleRepository extends CrudOperations<Integer, Role> {

    Role findByRoleName(String roleName);

    List<Role> getUserRoles(User user);

}
