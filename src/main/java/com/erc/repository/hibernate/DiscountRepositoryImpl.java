package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Discount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class DiscountRepositoryImpl implements DiscountRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Discount> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select d from Discount d", Discount.class).getResultList();
        }
    }

    @Override
    public Discount findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Discount.class, id);
        }
    }

    @Override
    public List<Discount> findByCarId(Long carId) {
        try(Session session = sessionFactory.openSession()) {

            Query<Discount> query = session.createQuery("select d from Discount d where d.car.id = :carId", Discount.class);
            query.setParameter("carId", carId);

            return query.getResultList();
        }
    }

    @Override
    public List<Discount> findByPercent(Integer percent) {
        try(Session session = sessionFactory.openSession()) {

            Query<Discount> query = session.createQuery("select d from Discount d where d.percent = :percent", Discount.class);
            query.setParameter("percent", percent);

            return query.getResultList();
        }
    }

    @Override
    public List<Discount> findFromStartDate(LocalDateTime startDate) {
        try(Session session = sessionFactory.openSession()) {

            Query<Discount> query = session.createQuery("select d from Discount d where d.startDate >= :startDate", Discount.class);
            query.setParameter("startDate", startDate);

            return query.getResultList();
        }
    }

    @Override
    public Discount save(Discount entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long discountId = (Long) session.save(entity);
            transaction.commit();
            return findOne(discountId);
        }
    }

    @Override
    public void addOne(Discount entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Discount> entities) {
        for (Discount discount : entities) {
            addOne(discount);
        }
    }

    @Override
    public Discount update(Discount entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Discount> query = session.createQuery("update Discount d set " +
                    "d.percent = :percent, " +
                    "d.startDate = :startDate, " +
                    "d.endDate = :endDate, " +
                    "d.car = :car " +
                    "where d.id = :id");
            query.setParameter("percent", entity.getPercent());
            query.setParameter("startDate", entity.getStartDate());
            query.setParameter("endDate", entity.getEndDate());
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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Discount> query = session.createQuery("delete from Discount d where d.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }
}
