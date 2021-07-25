package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select r from Role r", Role.class).getResultList();
        }
    }

    @Override
    public Role findOne(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Role.class, id);
        }
    }

    @Override
    public Role save(Role entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Integer roleId = (Integer) session.save(entity);
            transaction.commit();
            return findOne(roleId);
        }
    }

    @Override
    public void addOne(Role entity) {

    }

    @Override
    public void save(List<Role> entities) {

    }

    @Override
    public Role update(Role entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Role> getUserRoles(User user) {
        return null;
    }
}
