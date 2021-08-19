package com.erc.repository.hibernate;

import java.util.List;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import com.erc.repository.CrudOperations;

public interface UserRepository extends CrudOperations<Long, User> {

    User findByLogin(String login);

    void saveUserRoles(Long userId, List<Role> userRoles);

    void saveUserRoles(User user, List<Role> userRoles);

    User findByLoginAndPassword(String login, String password);

}
