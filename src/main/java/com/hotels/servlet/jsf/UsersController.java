package com.hotels.servlet.jsf;


import com.hotels.domain.Users;
import com.hotels.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;


@ViewScoped
@ManagedBean
public class UsersController extends AbstractController<Users>{

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private String password;
    private UserRepository userRepository;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = (password);
    }

    public UsersController() {
        this.userRepository = UserRepository.getInstance();
    }

    public String delete(long id) {
        try {
            this.userRepository.delete(id);
            return toUrl("admin/users.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String update(long id) {
        try {
            Users user=this.userRepository.findById(id);
            if (user==null)
                return null;
            Map<String, Object> sessionMapObject= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMapObject.put("editRecord", user);
            return toUrl("admin/edituser.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String save(Users users) {
        try {
            if (users==null)
                return null;
            this.userRepository.save(users);
            return toUrl("login.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }
    public String save(UsersController usersController) {
        try {


            //generate hash for password
            String passwordHash= BCrypt.hashpw(usersController.getPassword(), BCrypt.gensalt());

            usersController.setPassword(passwordHash);


            Users users= new Users(usersController.firstName, usersController.lastName,usersController.phoneNumber,
                    usersController.address,usersController.emailAddress,usersController.password);
            this.userRepository.save(users);
            return toUrl("login.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String updateUser(Users users) {
        try {
            if (users==null)
                return null;
            this.userRepository.update(users);
            return toUrl("admin/users.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    protected List<Users> findAll(int currentPage, int rowsPerPage) throws Exception {
        return this.userRepository.findAll(currentPage, rowsPerPage);
    }

    @Override
    protected long getCounts() throws Exception {
        return this.userRepository.getCounts();
    }
}

