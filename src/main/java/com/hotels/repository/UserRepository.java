package com.hotels.repository;


import com.hotels.domain.Users;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserRepository extends AbstractRepository<Long, Users> {

    public UserRepository() {
    }


    private static UserRepository instance;


    public static UserRepository getInstance(){

        if (instance==null)
            instance= new UserRepository();
        return instance;

    }

    @Override
    protected Class<Users> getClazz() {
        return Users.class;
    }

    public Users getByEmail(String email) throws Exception {
        if (email == null || email.isEmpty())
            return null;
        try (Connection c = new Connection()) {
            Transaction tr = c.getSession().beginTransaction();
            try {

                CriteriaBuilder cb = c.getSession().getCriteriaBuilder();
                CriteriaQuery<Users> cq = cb.createQuery(Users.class);
                Root<Users> root = cq.from(Users.class);
                cq.select(root).where(cb.equal(root.get(Users.FLD_emailAddress), email));
                Query<Users> query = c.getSession().createQuery(cq);
                return query.getSingleResult();
            } catch (Exception e) {

                tr.rollback();
                throw e;
            } finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }

        }

    }

}
