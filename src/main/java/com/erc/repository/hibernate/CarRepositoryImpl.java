package com.erc.repository.hibernate;

import com.erc.domain.CarStatus;
import com.erc.domain.hibernate.Car;
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
public class CarRepositoryImpl implements CarRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Car> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select c from Car c", Car.class).getResultList();
        }
    }

    @Override
    public Car findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Car.class, id);
        }
    }

    @Override
    public Car findByRegNumber(String regNumber) {
        try(Session session = sessionFactory.openSession()) {

            Query<Car> query = session.createQuery("select c from Car c where c.regNumber = :regNumber", Car.class);
            query.setParameter("regNumber", regNumber);

            return query.getSingleResult();
        }
    }

    @Override
    public List<Car> findByTariff(Integer tariff) {
        try(Session session = sessionFactory.openSession()) {

            Query<Car> query = session.createQuery("select c from Car c where c.tariff = :tariff", Car.class);
            query.setParameter("tariff", tariff);

            return query.getResultList();
        }
    }

    @Override
    public List<Car> findByCarStatus(CarStatus carStatus) {
        try(Session session = sessionFactory.openSession()) {

            Query<Car> query = session.createQuery("select c from Car c where c.carStatus = :carStatus", Car.class);
            query.setParameter("carStatus", carStatus);

            return query.getResultList();
        }
    }

    @Override
    public Car save(Car entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long carId = (Long) session.save(entity);
            transaction.commit();
            return findOne(carId);
        }
    }

    @Override
    public void addOne(Car entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Car> entities) {
        for (Car car : entities) {
            addOne(car);
        }
    }

    @Override
    public Car update(Car entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Car> query = session.createQuery("update Car c set " +
                    "c.regNumber = :regNumber, " +
                    "c.productionDate = :productionDate, " +
                    "c.tariff = :tariff, " +
                    "c.color = :color, " +
                    "c.carStatus = :carStatus " +
                    "where c.id = :id");
            query.setParameter("regNumber", entity.getRegNumber());
            query.setParameter("productionDate", entity.getProductionDate());
            query.setParameter("tariff", entity.getTariff());
            query.setParameter("color", entity.getColor());
            query.setParameter("carStatus", entity.getCarStatus());
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
            Query<Car> query = session.createQuery("delete from Car c where c.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void changeCarStatus(Long carId, CarStatus carStatus) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Car> query = session.createQuery("update Car c set c.carStatus = :carStatus where c.id = :carId");
            query.setParameter("carStatus", carStatus);
            query.setParameter("carId", carId);
            query.executeUpdate();
            transaction.commit();
        }
    }
}
