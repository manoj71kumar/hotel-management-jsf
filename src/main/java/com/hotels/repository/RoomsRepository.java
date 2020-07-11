package com.hotels.repository;


import com.hotels.domain.Rooms;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RoomsRepository extends AbstractRepository<Long, Rooms> {

    public RoomsRepository() {
    }
    private static RoomsRepository instance;


    public static synchronized RoomsRepository getInstance(){

        if (instance==null)
            instance= new RoomsRepository();
        return instance;

    }

    @Override
    protected Class<Rooms> getClazz() {
        return Rooms.class;
    }
    public Rooms getByRoomType(String roomType) throws Exception {
        if (roomType == null || roomType.isEmpty())
            return null;
        try (Connection c = new Connection()) {
            Transaction tr = c.getSession().beginTransaction();
            try {

                CriteriaBuilder cb = c.getSession().getCriteriaBuilder();
                CriteriaQuery<Rooms> cq = cb.createQuery(Rooms.class);
                Root<Rooms> root = cq.from(Rooms.class);
                cq.select(root).where(cb.equal(root.get(Rooms.FLD_ROOM_TYPE), roomType));
                Query<Rooms> query = c.getSession().createQuery(cq);
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
