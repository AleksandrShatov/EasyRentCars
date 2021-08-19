package com.erc.repository.hibernate;

import java.util.List;

import com.erc.domain.hibernate.Model;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ModelRepositoryImpl implements ModelRepository{

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    @Override
    public List<Model> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select m from Model m", Model.class).getResultList();
        }
    }

    @Override
    public Model findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Model.class, id);
        }
    }

    @Override
    public List<Model> findByModelName(String modelName) {
        try(Session session = sessionFactory.openSession()) {

            Query<Model> query = session.createQuery("select m from Model m where m.modelName = :modelName", Model.class);
            query.setParameter("modelName", modelName);

            return query.getResultList();
        }
    }

    @Override
    public List<Model> findByManufacturer(String manufacturer) {
        try(Session session = sessionFactory.openSession()) {

            Query<Model> query = session.createQuery("select m from Model m where m.manufacturer = :manufacturer", Model.class);
            query.setParameter("manufacturer", manufacturer);

            return query.getResultList();
        }
    }

    @Override
    public List<Model> findByFuel(String fuel) {
        try(Session session = sessionFactory.openSession()) {

            Query<Model> query = session.createQuery("select m from Model m where m.fuel = :fuel", Model.class);
            query.setParameter("fuel", fuel);

            return query.getResultList();
        }
    }

    @Override
    public Model save(Model entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long modelId = (Long) session.save(entity);
            transaction.commit();
            return findOne(modelId);
        }
    }

    @Override
    public void addOne(Model entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Model> entities) {
        for (Model model : entities) {
            addOne(model);
        }
    }

    @Override
    public Model update(Model entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Model> query = session.createQuery("update Model m set " +
                    "m.manufacturer = :manufacturer, " +
                    "m.modelName = :modelName, " +
                    "m.fuel = :fuel, " +
                    "m.engineVolume = :engineVolume " +
                    "where m.id = :id");
            query.setParameter("manufacturer", entity.getManufacturer());
            query.setParameter("modelName", entity.getModelName());
            query.setParameter("fuel", entity.getFuel());
            query.setParameter("engineVolume", entity.getEngineVolume());
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
            Query<Model> query = session.createQuery("delete from Model m where m.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }
}
