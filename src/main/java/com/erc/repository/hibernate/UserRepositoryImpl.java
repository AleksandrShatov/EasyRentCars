package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<User> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from User u", User.class).getResultList();
        }
    }

    @Override
    public User findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }

    @Override
    public User findByLogin(String login) {
        try(Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("select u from User u where u.login = :login", User.class);
            query.setParameter("login", login);

            return query.getSingleResult();
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        try(Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("select u from User u where u.login = :login and u.password = :password", User.class);
            query.setParameter("login", login);
            query.setParameter("password", password);

            return query.getSingleResult();
        }
    }

    @Override
    public User save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long userId = (Long) session.save(entity);
            transaction.commit();
            return findOne(userId);
        }
    }

    @Override
    public void addOne(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<User> entities) {
        for (User user : entities) {
            addOne(user);
        }
    }

    // TODO change to Criteria API
    @Override
    public User update(User entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<User> query = session.createQuery("update User u set " +
                    "u.name = :name, " +
                    "u.surname = :surname, " +
                    "u.patronymic = :patronymic, " +
                    "u.passportNumber = :passportNumber, " +
                    "u.passportSeries = :passportSeries, " +
                    "u.birthDate = :birthDate, " +
                    "u.phone = :phone, " +
                    "u.email = :email, " +
                    "u.login = :login, " +
                    "u.password = :password " +
                    "where u.id = :id");
            query.setParameter("name", entity.getName());
            query.setParameter("surname", entity.getSurname());
            query.setParameter("patronymic", entity.getPatronymic());
            query.setParameter("passportNumber", entity.getPassportNumber());
            query.setParameter("passportSeries", entity.getPassportSeries());
            query.setParameter("birthDate", entity.getBirthDate());
            query.setParameter("phone", entity.getPhone());
            query.setParameter("email", entity.getEmail());
            query.setParameter("login", entity.getLogin());
            query.setParameter("password", entity.getPassword());
            query.setParameter("id", entity.getId());
            query.executeUpdate();
            transaction.commit();

            session.update(entity);

            return findOne(entity.getId());

        }
    }

    @Override
    public void delete(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<User> query = session.createQuery("delete from User u where u.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    //TODO Fix native SQL
    @Override
    public void saveUserRoles(Long userId, List<Role> userRoles) {
        try(Session session = sessionFactory.openSession()) {

            for (Role role : userRoles) {
                Integer roleId = role.getId();

                Transaction transaction = session.getTransaction();
                transaction.begin();

                NativeQuery<?> query = session.createSQLQuery("insert into user_roles(role_id, user_id) values (:roleId, :userId)");
                query.setParameter("userId", userId);
                query.setParameter("roleId", roleId);
                query.executeUpdate();

                transaction.commit();
            }
        }
    }

    @Override
    public void saveUserRoles(User user, List<Role> userRoles) {

        Long userId = user.getId();

        saveUserRoles(userId, userRoles);
    }

}
