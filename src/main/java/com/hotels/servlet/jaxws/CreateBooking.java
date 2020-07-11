
package com.hotels.servlet.jaxws;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "createBooking", namespace = "http://jaxws.servlet.hotels.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createBooking", namespace = "http://jaxws.servlet.hotels.com/", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4"
})
public class CreateBooking {

    @XmlElement(name = "arg0", namespace = "")
    private Date arg0;
    @XmlElement(name = "arg1", namespace = "")
    private Date arg1;
    @XmlElement(name = "arg2", namespace = "")
    private int arg2;
    @XmlElement(name = "arg3", namespace = "")
    private double arg3;
    @XmlElement(name = "arg4", namespace = "")
    private List<Room> arg4;

    /**
     * 
     * @return
     *     returns Date
     */
    public Date getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(Date arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns Date
     */
    public Date getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(Date arg1) {
        this.arg1 = arg1;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getArg2() {
        return this.arg2;
    }

    /**
     * 
     * @param arg2
     *     the value for the arg2 property
     */
    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    /**
     * 
     * @return
     *     returns double
     */
    public double getArg3() {
        return this.arg3;
    }

    /**
     * 
     * @param arg3
     *     the value for the arg3 property
     */
    public void setArg3(double arg3) {
        this.arg3 = arg3;
    }

    /**
     * 
     * @return
     *     returns List<Room>
     */
    public List<Room> getArg4() {
        return this.arg4;
    }

    /**
     * 
     * @param arg4
     *     the value for the arg4 property
     */
    public void setArg4(List<Room> arg4) {
        this.arg4 = arg4;
    }

}
