package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Car;
import com.erc.domain.hibernate.Rent;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentRepositoryImpl implements RentRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private final SessionFactory sessionFactory;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Rent> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select r from Rent r", Rent.class).getResultList();
        }
    }

    @Override
    public Rent findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Rent.class, id);
        }
    }

    @Override
    public List<Rent> findByUserId(Long userId) {
        try(Session session = sessionFactory.openSession()) {

            Query<Rent> query = session.createQuery("select r from Rent r where r.user.id = :userId", Rent.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        }
    }

    @Override
    public List<Rent> findByCarId(Long carId) {
        try(Session session = sessionFactory.openSession()) {

            Query<Rent> query = session.createQuery("select r from Rent r where r.car.id = :carId", Rent.class);
            query.setParameter("carId", carId);

            return query.getResultList();
        }
    }

    @Override
    public Rent save(Rent entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long rentId = (Long) session.save(entity);
            transaction.commit();
            return findOne(rentId);
        }
    }

    @Override
    public void addOne(Rent entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Rent> entities) {
        for (Rent rent : entities) {
            addOne(rent);
        }
    }

    @Override
    public Rent update(Rent entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Rent> query = session.createQuery("update Rent r set " +
                    "r.startRentDate = :startRentDate, " +
                    "r.numberOfDays = :numberOfDays, " +
                    "r.endRentDate = :endRentDate, " +
                    "r.actualReturnDate = :actualReturnDate, " +
                    "r.rentStatus = :rentStatus, " +
                    "r.user = :user, " +
                    "r.car = :car " +
                    "where r.id = :id");
            query.setParameter("startRentDate", entity.getStartRentDate());
            query.setParameter("numberOfDays", entity.getNumberOfDays());
            query.setParameter("endRentDate", entity.getEndRentDate());
            query.setParameter("actualReturnDate", entity.getActualReturnDate());
            query.setParameter("rentStatus", entity.getRentStatus());
            query.setParameter("user", entity.getUser());
            query.setParameter("car", entity.getCar());
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
            Query<Rent> query = session.createQuery("delete from Rent r where r.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }
}
