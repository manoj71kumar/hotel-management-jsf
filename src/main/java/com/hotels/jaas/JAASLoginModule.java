package com.hotels.jaas;


import com.hotels.domain.jaas.JAASAdmin;
import com.hotels.repository.AdminRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class JAASLoginModule  implements LoginModule {

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private CallbackHandler callbackHandler;
    private Subject subject;
    private JAASAdmin admin;
    private Map shareState;
    private Map options;
    private boolean succeeded;
    private boolean commitSucceed;
    private String userName;
    private String password;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
        this.subject= subject;
        this.shareState=sharedState;
        this.options=options;


    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks =new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);
        AdminRepository adminRepository = AdminRepository.getInstance();
        try {
            callbackHandler.handle(callbacks);
            this.userName= ((NameCallback)callbacks[0]).getName();
            this.password= String.valueOf(((PasswordCallback) callbacks[1]).getPassword());
            List<JAASAdmin> adminList = adminRepository.getByName(userName);
            if (adminList==null || adminList.isEmpty()){
                LOG.warn("Not Existing User:" +userName);
                return false;

            }
            Optional<JAASAdmin> adm = adminList.stream().filter(u->BCrypt.checkpw(password,u.getPassword()))
                    .findFirst();

//            Optional<JAASAdmin> adm = adminRepository.findAll().stream().filter(ad -> ad.getPassword().equals(password)).findFirst();

            if (!adm.isPresent()){
                LOG.warn("Wrong Password:" +userName);
                return false;
            }
            this.admin=adm.get();
            this.succeeded=true;
            return true;

        } catch (Exception e) {
            LOG.warn(e);
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (!(succeeded && (this.admin!=null)))
        return false;
        if (!this.subject.getPrincipals().contains(this.admin)){
            this.subject.getPrincipals().add(this.admin);
            LOG.info("User principal added" + this.admin.getName());

        }
        this.admin.getJaasRole().forEach(this.subject.getPrincipals()::add);
        commitSucceed= true;
        LOG.info("Login subject were successfully populated with principals ans roles");
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        if (!succeeded)
        return false;

        if (!commitSucceed){
            this.succeeded=false;
            admin=null;
            userName=null;
            password=null;

        }
        else
            logout();
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        this.subject.getPrincipals().clear();
        this.succeeded=false;
        admin=null;
        this.userName=null;
        StringUtils.leftPad(password, password.length(), ' ');
        return true;
    }
}
