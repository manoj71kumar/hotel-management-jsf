package com.hotels.repository;

import com.hotels.domain.jaas.JAASAdmin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminRepository extends AbstractRepository<Long , JAASAdmin> {

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private static AdminRepository instance;

    private AdminRepository() {}


    @Override
    protected Class<JAASAdmin> getClazz() {
        return JAASAdmin.class;
    }
    public  static synchronized AdminRepository getInstance(){
        if (instance==null)
            instance= new AdminRepository();
        return instance;

    }


}
