package com.hotels.repository;

import com.hotels.db.HibernateUtil;
import com.hotels.domain.AbstractEntity;
import com.hotels.domain.jaas.JAASAdmin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<ID extends Serializable,T extends AbstractEntity<ID>>{
    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    protected abstract Class<T> getClazz();

    class Connection implements Closeable {

        private Session session;

        public Connection() {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }

        public Session getSession() {
            return session;
        }

        @Override
        public void close() throws IOException {
            session.close();
        }

    }
    public void save(T entity) throws IOException {
        if (entity == null)
            return;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                c.session.save(entity);
            } catch (Exception e) {
                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }
    public void saveOrUpdate(T entity) throws IOException {
        if (entity == null)
            return;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                c.session.saveOrUpdate(entity);
            } catch (Exception e) {
                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }
    public void update(T entity) throws IOException {
        if (entity == null)
            return;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                c.session.update(entity);
            } catch (Exception e) {
                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }

    public void update(Long id) throws Exception {
        if (id == null)
            return;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                T obj = c.session.find(clazz, id);
                if (obj != null)
                    c.session.update(obj);
                else {
                    LOG.warn(String.format("Object with id  %d is not found for delete",id));
                    return;
                }

            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }

    public void delete(Long id) throws Exception {

        if (id == null)
            return;

        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                T obj = c.session.find(clazz, id);
                if (obj != null)
                    c.session.delete(obj);

            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }

    }
    public T findById(Long id) throws Exception {
        if (id == null)
            return null;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                return c.session.find(clazz, id);
            } catch (Exception e) {
                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }

    public long getCounts() throws Exception {

        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                CriteriaBuilder cb = c.session.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<T> root = query.from(clazz);
                query.select(cb.count(root.get(T.FLD_ID)));
                return c.session.createQuery(query).getSingleResult();
            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }

    }

    public List<T> getByName(String name) throws Exception {

        ArrayList<T> result = new ArrayList<>();
        if (name == null || name.isEmpty())
            return result;
        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();

            try {
                Class<T> clazz = getClazz();
                CriteriaBuilder cb = c.session.getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(clazz);
                Root<T> root = cq.from(clazz);
                cq.select(root).where(cb.equal(root.get(JAASAdmin.FLD_NAME), name));
                Query<T> query = c.session.createQuery(cq);
                return query.getResultList();
            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }

    public List<T> findAll() throws Exception {

        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                CriteriaBuilder cb = c.session.getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(clazz);
                Root<T> root = cq.from(clazz);
                cq.select(root);
                Query<T> query = c.session.createQuery(cq);
                return query.getResultList();
            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }

        }
    }
    public List<T> findAll(int currentPage, int recordsPerPage) throws Exception {

        try (Connection c = new Connection()) {
            Transaction tr = c.session.beginTransaction();
            try {
                Class<T> clazz = getClazz();
                CriteriaBuilder cb = c.session.getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(clazz);
                Root<T> root = cq.from(clazz);
                cq.select(root);
                Query<T> query = c.session.createQuery(cq);
                query.setFirstResult(currentPage*recordsPerPage);
                query.setMaxResults(recordsPerPage);
                return query.getResultList();
            }

            catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }

        }

    }

}
