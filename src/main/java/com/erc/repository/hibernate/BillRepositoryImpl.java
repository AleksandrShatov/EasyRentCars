package com.erc.repository.hibernate;

import com.erc.domain.BillStatus;
import com.erc.domain.hibernate.Bill;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BillRepositoryImpl implements BillRepository {

    @Autowired
    @Qualifier("sessionFactory")
    private final SessionFactory sessionFactory;

    @Override
    public List<Bill> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("select b from Bill b", Bill.class).getResultList();
        }
    }

    @Override
    public Bill findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Bill.class, id);
        }
    }

    @Override
    public Bill findByRentId(Long rentId) {
        try(Session session = sessionFactory.openSession()) {

            Query<Bill> query = session.createQuery("select b from Bill b where b.rent.id = :rentId", Bill.class);
            query.setParameter("rentId", rentId);

            return query.getSingleResult();
        }
    }

    @Override
    public List<Bill> findByUserId(Long userId) {
        try(Session session = sessionFactory.openSession()) {
            Query<Bill> query = session.createQuery("select b from Bill b where b.rent.user.id = :userId", Bill.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        }
    }

    @Override
    public List<Bill> findByCarId(Long carId) {
        try(Session session = sessionFactory.openSession()) {

            Query<Bill> query = session.createQuery("select b from Bill b where b.rent.car.id = :carId", Bill.class);
            query.setParameter("carId", carId);

            return query.getResultList();
        }
    }

    @Override
    public List<Bill> findByPaymentStatus(BillStatus paymentStatus) {
        try(Session session = sessionFactory.openSession()) {

            Query<Bill> query = session.createQuery("select b from Bill b where b.paymentStatus = :paymentStatus", Bill.class);
            query.setParameter("paymentStatus", paymentStatus);

            return query.getResultList();
        }
    }

    public List<Bill> findWithoutPaymentAndExpiredDates() {
        try(Session session = sessionFactory.openSession()) {

            LocalDateTime currentDate = LocalDateTime.now();

            Query<Bill> query = session.createQuery("select b from Bill b where b.dateForPayment < :currentDate and b.paymentStatus = :paymentStatus", Bill.class);
            query.setParameter("currentDate", currentDate);
            query.setParameter("paymentStatus", BillStatus.AWAITING_PAYMENT);

            return query.getResultList();
        }
    }

    @Override
    public Bill save(Bill entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long billId = (Long) session.save(entity);
            transaction.commit();
            return findOne(billId);
        }
    }

    @Override
    public void addOne(Bill entity) {
        try(Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }

    @Override
    public void save(List<Bill> entities) {
        for(Bill bill : entities) {
            addOne(bill);
        }
    }

    @Override
    public Bill update(Bill entity) {
        try(Session session = sessionFactory.openSession()) {

            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Bill> query = session.createQuery("update Bill b set " +
                    "b.dateForPayment = :dateForPayment, " +
                    "b.totalPrice = :totalPrice, " +
                    "b.paymentDate = :paymentDate, " +
                    "b.payment = :payment, " +
                    "b.paymentStatus = :paymentStatus, " +
                    "b.rent = :rent " +
                    "where b.id = :id");
            query.setParameter("dateForPayment", entity.getDateForPayment());
            query.setParameter("totalPrice", entity.getTotalPrice());
            query.setParameter("paymentDate", entity.getPaymentDate());
            query.setParameter("payment", entity.getPayment());
            query.setParameter("paymentStatus", entity.getPaymentStatus());
            query.setParameter("rent", entity.getRent());
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
            Query<Bill> query = session.createQuery("delete from Bill b where b.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void changeBillPaymentStatus(Long billId, BillStatus paymentStatus) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Bill> query = session.createQuery("update Bill b set b.paymentStatus = :paymentStatus where b.id = :id");
            query.setParameter("paymentStatus", paymentStatus);
            query.setParameter("id", billId);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public Bill paymentByBill(Long billId, Integer payment, LocalDateTime paymentDate) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Query<Bill> query = session.createQuery("update Bill b set " +
                    "b.payment = :payment, " +
                    "b.paymentDate = :paymentDate " +
                    "where b.id = :id");
            query.setParameter("payment", payment);
            query.setParameter("paymentDate", paymentDate);
            query.setParameter("id", billId);
            query.executeUpdate();
            transaction.commit();

            session.update(findOne(billId));

            return findOne(billId);
        }
    }
}
