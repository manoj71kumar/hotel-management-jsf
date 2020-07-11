package com.hotels.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


/** Represents a Users.
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */
@Entity
@Table(name = Users.TBL_NAME)
@Inheritance(strategy =InheritanceType.TABLE_PER_CLASS)
public class  Users extends AbstractEntity<Long> {

    public  static final String TBL_NAME = "Users";
    public static final String FLD_first_NAME = "fname";
    public static final String FLD_last_NAME = "lname";
    public static final String FLD_phone = "phone";
    public static final String FLD_address = "address";
    public static final String FLD_emailAddress = "email";
    public static final String FLD_PASSWORD = "password";


    /**
     * Represents the Users First Name
     */
    @Column(name = FLD_first_NAME , nullable = false)
    @NotNull(message = "Name can not be Empty")
    @Size(min = 2,max = 20, message = "First Name must be between the given length")
    private String firstName;

    /**
     * Represents the Users Last Name
     */
    @Column(name = FLD_last_NAME , nullable = false)
    @NotNull(message = "Name can not be Empty")
    @Size(min = 2,max = 20, message = "Last Name must be between the given length")
    private String lastName;

    /**
     * Represents the Users Contact Number
     */
    @Column(name = FLD_phone , nullable = false)
    @NotNull(message = "phone Number can not be Empty")
    @Size(min = 8,max = 15, message = "Phone Number must be between the given length")
    private String phoneNumber;

    /**
     * Represents the Users Address
     */
    @Column(name = FLD_address , nullable = false)
    private String address;

    /**
     * Represents the Users Email
     */
    @Column(name = FLD_emailAddress ,unique = true , nullable = false)
    @NotNull(message = "Email can not be Empty")
    @Size(min = 5,max = 50, message = "Email must be between the given length")
    private String emailAddress;

    /**
     * Represents the Users Password for Login
     */
    @Column(name = FLD_PASSWORD , nullable = false , length = 250)
    @NotNull(message = "Password can not be Empty")
//    @Size(min = 1,max = 255, message = "Password must be between the given length")
    private String password;

    /**
     * Empty Constructor for Java Beans
     */
    public Users() {
    }

    /**
     * Constructor and initialization
     * @param firstName   firstName of Person
     * @param lastName    lastName of Person
     * @param phoneNumber phoneNumber of Person
     * @param address      address of Person
     * @param emailAddress emailAddress of Person
     * @param password     password of Person
     */
    public Users(String firstName, String lastName, String phoneNumber, String address, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.password = (password);
    }

    /**
     * Constructor and initialization of Variables with Super class variable
     * @param id          Unqiue id of a Person
     * @param firstName   firstName of Person
     * @param lastName    lastName of Person
     * @param phoneNumber phoneNumber of Person
     * @param address      address of Person
     * @param emailAddress emailAddress of Person
     * @param password     password of Person
     */

    public Users(Long id, String firstName, String lastName, String phoneNumber, String address, String emailAddress, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.password = (password);

    }

    /**
     * Gets the user’s first name.
     * @return A string representing the user’s first
     *  name.
     */
    public String getFirstName() {
        return firstName;
    }

    /** Sets the user’s last name.
     * @param firstName A String containing the users’s
     *     first name.
     */
    public void setFirstName(String firstName) {
        if (firstName==null  || firstName.isEmpty() || StringUtils.isBlank(firstName))
            return;
        this.firstName = firstName;
    }

    /** Gets the user’s last name.
     * @return A string representing the user’s last
     *     name.
     */

    public String getLastName() {
        return lastName;
    }

    /** Sets the employee’s last name.
     * @param lastName A String containing the users’s
     *     last name.
     */
    public void setLastName(String lastName) {
        if (lastName==null  || lastName.isEmpty() || StringUtils.isBlank(lastName))
            return;
        this.lastName = lastName;
    }

    /**
     * Gets the user’s Contact Number.
     * @return A string representing the user’s Contact
     * Number.
     */
    public String getPhoneNumber() {

        return phoneNumber;
    }

    /** Sets the user’s Phone Number.
     * @param phoneNumber A String containing the user’s
     *     Phone Number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Gets the user’s address.
     * @return A string representing the user’s address.
     */
    public String getAddress() {
        return address;
    }

    /** Sets the user’s address.
     * @param address A String containing the user’s
     *     address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Gets the user’s email.
     * @return A string representing the user’s email.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /** Sets the user’s email.
     * @param emailAddress A String containing the user’s
     *     email.
     */
    public void setEmailAddress(String emailAddress) {
        if (lastName==null || lastName.isEmpty())
            return;
        this.emailAddress = emailAddress;
    }

    /** Gets the users password.
     * @return A string representing the employee’s password.
     */
    public String getPassword() {
        return password;
    }

    /** Sets the user’s password.
     * @param password A String containing the user’s
     *     password.
     */
    public void setPassword(String password) {
        this.password = (password);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o o - the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */

    public Boolean isLogged = false;

    public void action(){
        isLogged = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return Objects.equals(firstName, users.firstName) &&
                Objects.equals(lastName, users.lastName) &&
                Objects.equals(phoneNumber, users.phoneNumber) &&
                Objects.equals(address, users.address) &&
                Objects.equals(emailAddress, users.emailAddress) &&
                Objects.equals(password, users.password);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, address, emailAddress, password);
    }

}
