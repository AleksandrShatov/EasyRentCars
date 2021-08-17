package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

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
    public Role findByRoleName(String roleName) {
        try(Session session = sessionFactory.openSession()) {

            Query<Role> query = session.createQuery("select r from Role r where r.roleName = :roleName", Role.class);
            query.setParameter("roleName", roleName);

            return query.getSingleResult();
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
        try(Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Role> entities) {
        for (Role role : entities) {
            addOne(role);
        }
    }

    @Override
    public Role update(Role entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Role> query = session.createQuery("update Role r set " +
                    "r.roleName = :roleName where r.id = :id");
            query.setParameter("roleName", entity.getRoleName());
            query.setParameter("id", entity.getId());
            query.executeUpdate();
            transaction.commit();

            session.update(entity);

            return findOne(entity.getId());
        }
    }

    @Override
    public void delete(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Role> query = session.createQuery("delete from Role r where r.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<Role> getUserRoles(User user) {
        return null;
    }

}
