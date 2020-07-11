package com.hotels.jaas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.callback.*;
import java.io.IOException;

public class JaasCallbackHandler implements CallbackHandler {

    private static final Logger LOG = LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY = false;

    private String userName;
    private String password;

    public JaasCallbackHandler(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter JAASCallbackHandler.handle ");
        for (int i = 0; i < callbacks.length; ++i) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nc = (NameCallback) callbacks[i];
                nc.setName(userName);
                continue;
            }
            if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callbacks[i];
                pc.setPassword(password.toCharArray());
                continue;

            }
            throw new UnsupportedCallbackException(callbacks[i],"The submitted callback is not supported");


        }
    }
}
