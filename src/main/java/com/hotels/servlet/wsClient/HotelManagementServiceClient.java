package com.hotels.servlet.wsClient;

import com.hotels.servlet.jaxws.HotelBookingService;
import com.hotels.servlet.jaxws.HotelBookingServiceImpl;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class HotelManagementServiceClient {
    public static void main(String[] args) throws MalformedURLException {
        //Endpoint.publish("http://localhost:8080/hotel_management_1_0_SNAPSHOT_war/hotelBookingService?wsdl", new HotelBookingServiceImpl());
        URL wsdlUrl = new URL("http://localhost:8080/hotel_management_1_0_SNAPSHOT_war/hotelBookingService?wsdl");
        //qualifier name ...
        QName qname = new QName("", "HotelBookingServiceImpl");

        Service service = Service.create(wsdlUrl, qname);

        HotelBookingService hotelBookingServiceService = service.getPort(HotelBookingService.class);

       /* System.out.println(hotelBookingServiceService.createBooking(""));*/
    }
}
