package com.hotel;

import com.hotels.domain.Users;
import com.hotels.repository.UserRepository;
import com.hotels.servlet.jaxWSDL.UserWebServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class UsersTest {

    public static final String USERS_first_NAME = "Farhan";
    public static final String USERS_last_NAME = "Khalid";
    public static final String USERS_phone = "06202832737";
    public static final String USERS_address = "Ferencesek Utca 50";
    public static final String USERS_emailAddress = "khalid@gmail.com";
    public static final String USERS_PASSWORD = "12345";

    public static final String USER_first_NAME = "Fabiene ";
    public static final String USER_last_NAME = "Cantant";
    public static final String USER_phone = "06265412368";
    public static final String USER_address = "Kodaly Zoltan Utca";
    public static final String USER_emailAddress = "fabiene@gmail.com";
    public static final String USER_PASSWORD = "54852";

   @Test
   public void crudTest() throws Exception {

       Users users = new Users(null,USERS_first_NAME,USERS_last_NAME ,USERS_phone,USERS_address,USERS_emailAddress,USERS_PASSWORD );
       UserRepository userRepository = UserRepository.getInstance();
       userRepository.save(users);
       Assert.assertEquals(userRepository.getCounts() , 10);

       Assert.assertNotNull(users.getId());
       Assert.assertEquals(users.getFirstName(),USERS_first_NAME);
       Assert.assertEquals(users.getLastName(),USERS_last_NAME);
       Assert.assertEquals(users.getEmailAddress(),USERS_emailAddress);
       Assert.assertEquals(users.getPhoneNumber(),USERS_phone);
       Assert.assertEquals(users.getAddress(),USERS_address);
       Assert.assertEquals(users.getPassword(),USERS_PASSWORD);

       userRepository.delete(users.getId());
       Assert.assertEquals(userRepository.getCounts(),9);

       users.setFirstName(USER_first_NAME);
       users.setLastName(USER_last_NAME);
       users.setEmailAddress(USER_emailAddress);
       users.setPhoneNumber(USER_phone);
       users.setAddress(USER_address);
       users.setPassword(USER_PASSWORD);
       userRepository.save(users);

       Users users1= userRepository.findById(users.getId());
       compare(users, users1);

       Users byId= userRepository.findById(users.getId());
       compare(byId, users);

       userRepository.delete(users.getId());
       Assert.assertEquals(userRepository.getCounts(),9);

   }
    private void compare(Users users , Users users1){
        Assert.assertEquals(users.getFirstName(),users1.getFirstName());
        Assert.assertEquals(users.getLastName(),users1.getLastName());
        Assert.assertEquals(users.getEmailAddress(),users1.getEmailAddress());
        Assert.assertEquals(users.getPhoneNumber(),users1.getPhoneNumber());
        Assert.assertEquals(users.getAddress(),users1.getAddress());
        Assert.assertEquals(users.getPassword(),users1.getPassword());


    }

    @Test
    public void crudWebServiceTest() throws Exception {

        Users users = new Users(null,USERS_first_NAME,USERS_last_NAME ,USERS_phone,USERS_address,USERS_emailAddress,USERS_PASSWORD );
        UserWebServiceImpl userService = UserWebServiceImpl.getInstance();
        userService.save(users);
        Assert.assertEquals(userService.getCounts() , 10);

        Assert.assertNotNull(users.getId());
        Assert.assertEquals(users.getFirstName(),USERS_first_NAME);
        Assert.assertEquals(users.getLastName(),USERS_last_NAME);
        Assert.assertEquals(users.getEmailAddress(),USERS_emailAddress);
        Assert.assertEquals(users.getPhoneNumber(),USERS_phone);
        Assert.assertEquals(users.getAddress(),USERS_address);
        Assert.assertEquals(users.getPassword(),USERS_PASSWORD);

        userService.delete(users.getId());
        Assert.assertEquals(userService.getCounts(),9);

        users.setFirstName(USER_first_NAME);
        users.setLastName(USER_last_NAME);
        users.setEmailAddress(USER_emailAddress);
        users.setPhoneNumber(USER_phone);
        users.setAddress(USER_address);
        users.setPassword(USER_PASSWORD);
        userService.save(users);

        Users users1= userService.findById(users.getId());
        compare(users, users1);

        Users byId= userService.findById(users.getId());
        compare(byId, users);

        userService.delete(users.getId());
        Assert.assertEquals(userService.getCounts(),9);

    }

}
