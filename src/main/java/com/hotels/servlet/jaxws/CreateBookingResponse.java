
package com.hotels.servlet.jaxws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "createBookingResponse", namespace = "http://jaxws.servlet.hotels.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createBookingResponse", namespace = "http://jaxws.servlet.hotels.com/")
public class CreateBookingResponse {

    @XmlElement(name = "return", namespace = "")
    private boolean _return;

    /**
     * 
     * @return
     *     returns boolean
     */
    public boolean isReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(boolean _return) {
        this._return = _return;
    }

}
