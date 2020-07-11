package com.hotels.repository;

import com.hotels.domain.jaas.JAASRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RoleRepository extends AbstractRepository <Long , JAASRole>{

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private static RoleRepository instance;

    private RoleRepository() {}


    @Override
    protected Class<JAASRole> getClazz() {
        return JAASRole.class;
    }
    public  static synchronized RoleRepository getInstance(){
        if (instance==null)
            instance= new RoleRepository();
        return instance;

    }

}
