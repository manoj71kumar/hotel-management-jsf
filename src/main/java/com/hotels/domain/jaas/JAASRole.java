package com.hotels.domain.jaas;

import com.hotels.domain.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.Objects;

@Entity
@Table(name = JAASRole.TBL_NAME)
public class JAASRole extends AbstractEntity<Long> implements Principal {

    public static  final String TBL_NAME= "role";
    public static final String FLD_NAME= "name";

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    @Column(name = FLD_NAME , nullable = false)
    @NotNull(message = "Name can not be null")
    @Size(min = 2, max = 50, message = "Length must be between the given length")
    private String name;

    public JAASRole(){}

    public JAASRole(String name){
        this.name=name;


    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JAASRole)) return false;
        if (!super.equals(o)) return false;
        JAASRole jaasRole = (JAASRole) o;
        return Objects.equals(name, jaasRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}

