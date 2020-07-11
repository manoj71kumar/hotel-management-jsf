package com.hotels.db;

import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.domain.Users;
import com.hotels.domain.jaas.JAASAdmin;
import com.hotels.domain.jaas.JAASRole;
import com.hotels.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HibernateUtil {
    private static final Logger LOG = LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY = true;

    private static SessionFactory sessionFactory;

    public synchronized static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration config = new Configuration();
            Properties props = new Properties();
            props.put(Environment.DRIVER, "org.hsqldb.jdbc.JDBCDriver");
            props.put(Environment.URL, "jdbc:hsqldb:mem:db/mp;hsqldb.log_data=false");
            props.put(Environment.USER, "sa");
            props.put(Environment.PASS, "");
            props.put(Environment.DIALECT, "");
            props.put(Environment.SHOW_SQL, "true");
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            props.put(Environment.ALLOW_UPDATE_OUTSIDE_TRANSACTION, "true");
            props.put(Environment.HBM2DDL_AUTO, "create-drop");
            props.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
            config.setProperties(props);
            config.addAnnotatedClass(Users.class);
            config.addAnnotatedClass(Booking.class);
            config.addAnnotatedClass(Rooms.class);
            config.addAnnotatedClass(JAASRole.class);
            config.addAnnotatedClass(JAASAdmin.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties())
                    .build();
            sessionFactory = config.buildSessionFactory(serviceRegistry);
            initDb();

        }
        return sessionFactory;
    }
    private static void initDb(){
        try {
            initSecurity();
            initUsers();
            initRooms();
            initBooking();
        }
        catch (Exception e){
            LOG.error(e);
        }
    }

    private static void createRole(String name) throws Exception {

        RoleRepository roleRepository= RoleRepository.getInstance();
        JAASRole r =new JAASRole(name);
        roleRepository.save(r);


    }

    private static void createAdmin(String name, String password, String ...roleNames) throws Exception {
        AdminRepository adminRepository = AdminRepository.getInstance();
        RoleRepository roleRepository = RoleRepository.getInstance();
        List<JAASRole> roles= new ArrayList<>(roleNames.length);
        for (String r:roleNames)
            roles.addAll(roleRepository.getByName(r));

        JAASAdmin adm = new JAASAdmin(name, password);
        adm.setJaasRole(roles);
        adminRepository.save(adm);


    }
    private static void initSecurity() throws Exception {
        createRole("admin");
        createRole("api");
        createAdmin("Mehdi" , "Corona" ,  "admin");
        createAdmin("Farhan" , "COVID19" , "api");


    }

    private static void initUsers() throws Exception{


        createUser("Farhan", "khalid", "0620232737", "Ferencesek utca " , "fkhalid1991@gmail.com" , "12345" );
        createUser("Ahmad", "Irfan", "0685214785", "Kodaly utca " , "ahmad@gmail.com" , "54752" );
        createUser("Hashaam", "Irfan", "0798521475", "Zoltan utca " , "hashaam@gmail.com" , "85236" );
        createUser("Urwa", "Irfan", "0896321475", "Maria utca " , "urwa@gmail.com" , "75214" );
        createUser("Axele", "Vincent", "0258456321", "Xavier utca " , "axele@gmail.com" , "32145" );
        createUser("Zamek", "Ziadrics", "0954752365", "Rokus utca 7" , "zamek42@gmail.com" , "95147" );
        createUser("Morris", "Muo", "0475214563", "Petofi utca 5" , "muo@gmail.com" , "74521" );
        createUser("Maryam", "Rizwan", "0142365478", "Boszorkany utca 50" , "maryam@gmail.com" , "25896" );
        createUser("Abdullah", "Irfan", "0852369874", "Qammar utca 50" , "abdullah@gmail.com" , "02586" );


    }
    private static void createUser(String firstName, String lastName, String phoneNumber, String address, String emailAddress, String password) throws Exception{

        UserRepository ur = UserRepository.getInstance();
        Users u = new Users(null,firstName, lastName,phoneNumber,address , emailAddress,password );
        ur.save(u);

    }
    private static void initRooms() throws Exception{
        createRoom("Single", 1500, "Single Person only");
        createRoom("Double", 3000, "Couple Person only");
        createRoom("Standard", 4500, "Double Bed with Balcony");
        createRoom("Superior", 6000, "Double Bed and bath and balcony");
        createRoom("King", 8500, "A room with a king-sized bed.");
        createRoom("Studio", 9000, "A room with a Studio bed.");

    }
    private static void createRoom(String roomType, double pricePerNight, String description) throws Exception {
        RoomsRepository roomsRepository = RoomsRepository.getInstance();
        Rooms rm = new Rooms(null,roomType, pricePerNight,description);
        roomsRepository.save(rm);

    }
    private static void createBooking(String checkInDate,String checkOutDate,String roomType) throws Exception {
        BookingRepository bookingRepository = BookingRepository.getInstance();
        Rooms rooms = RoomsRepository.getInstance().getByRoomType(roomType);
        Booking booking = new Booking(null, rooms,checkInDate, checkOutDate);
        bookingRepository.save(booking);
    }
    private static void initBooking() throws Exception{


//        createBooking("05/26/2020", "05/29/2020" ,"Superior");
//        createBooking("05/27/2020", "05/29/2020", "Double");
//        createBooking("05/28/2020", "06/01/2020" ,"Single");
//        createBooking("05/29/2020", "05/30/2020",  "Studio " );
//        createBooking("05/30/2020", "06/10/2020",  "King " );
//        createBooking("05/31/2020", "06/15/2020",  "Standard" );

    }
}
