package com.hotels.servlet.jsf;

import com.hotels.domain.Users;
import com.hotels.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@ManagedBean
@ViewScoped
public class AuthController extends AbstractController<Users> {

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;
    @Override
    protected List<Users> findAll(int currentPage, int rowsPerPage) throws Exception {
        return this.userRepository.findAll(currentPage, rowsPerPage);
    }

    @Override
    protected long getCounts() throws Exception {
        return 0;
    }

    private String username;
    private String password;
    private String originalURL;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    public AuthController() {
        this.userRepository = UserRepository.getInstance();
    }


    private UserRepository userRepository;

    public void login(AuthController users) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            boolean isValid = validateLogin(users.username, users.password);
            if(isValid) {
                Users user = find(users.username);
                user.isLogged= true;
                externalContext.getSessionMap().put("user", user);
                request.setAttribute("user",user);
                externalContext.redirect(originalURL);
            }

        } catch (Exception e) {
            // Handle unknown username/password in request.login().
            LOG.error(e.getMessage());
//            context.addMessage(null, new FacesMessage("Unknown login"));
        }
    }

    private Boolean validateLogin(String username, String password) {
        Users users = find(username);
        Boolean flag = false;
        if(users!=null){
            //hash check in login
            if(users.getEmailAddress().equals(username) && BCrypt.checkpw(password, users.getPassword())){
                flag= true;
            }
        }
        return flag;
    }

    private Users find(String username) {
        try {
            Users user=  userRepository.findAll().stream().filter(users -> users.getEmailAddress().equals(username)).findFirst().orElse(null);
            return user;
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        request.getSession().removeAttribute("user");
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

    public boolean isLogoutRendered() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Users pr = (Users)request.getSession().getAttribute("user");
        return pr != null;
    }

    /**
     * Verifies if the currently logged in user, if exists, is in the given ROLE.
     *
     * @return {@code true} if the user is logged in and has the given ROLE. {@code false} otherwise.
     */
    public boolean isUserLoggedIn() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Users pr = (Users)request.getSession().getAttribute("user");
        return pr!=null?true:false;
    }

}
