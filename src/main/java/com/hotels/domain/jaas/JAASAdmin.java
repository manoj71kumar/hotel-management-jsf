package com.hotels.domain.jaas;

import com.hotels.domain.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.List;


@Entity
@Table(name = JAASAdmin.TBL_NAME)
public class JAASAdmin extends AbstractEntity<Long> implements Principal {

    public static  final String TBL_NAME= "admin";
    public static final String FLD_NAME= "name";

    public static final String FLD_PASSWORD= "password";

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    @Column(name = FLD_NAME , nullable = false)
    @NotNull(message = "Name can not be null")
    @Size(min = 2, max = 50, message = "Length must be betwwen the given length")
    private String name;

    @Column(name = FLD_PASSWORD , nullable = false)
    @NotNull(message = "password can not be null")
    @Size(min = 1, max = 255, message = "Length must be between the given length")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role",
            joinColumns = { @JoinColumn(name = "admin_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<JAASRole> jaasRole;

    public JAASAdmin() {}

    public JAASAdmin (String username, String password) {

        this.name = username;
        this.password = encryptPassword(password);

    }

    private String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    public List<JAASRole> getJaasRole() {
        return jaasRole;
    }

    public void setJaasRole(List<JAASRole> jaasRole) {
        this.jaasRole = jaasRole;
    }
}