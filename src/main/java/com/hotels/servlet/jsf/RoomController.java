package com.hotels.servlet.jsf;

import com.hotels.domain.Rooms;
import com.hotels.repository.RoomsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

@ViewScoped
@ManagedBean
public class RoomController extends AbstractController<Rooms>{

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private String roomType;
    private double pricePerNight;
    private String description;
    private RoomsRepository roomsRepository;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomController() {
        this.roomsRepository = RoomsRepository.getInstance();
    }

    public String delete(long id) {
        try {
            this.roomsRepository.delete(id);
            return toUrl("admin/rooms.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String update(long id) {
        try {
            Rooms r=this.roomsRepository.findById(id);
            if (r==null)
                return null;
            Map<String, Object> sessionMapObject= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMapObject.put("editRecord", r);
            return toUrl("admin/editroom.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }


    public String updateRoom(Rooms rooms) {
        try {
            if (rooms==null)
                return null;
            this.roomsRepository.update(rooms);
            return toUrl("admin/rooms.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String save(Rooms rooms) {
        try {
            if (rooms==null)
                return null;
            this.roomsRepository.save(rooms);
            return toUrl("admin/rooms.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String save(RoomController roomController) {
        try {
            Rooms rooms = new Rooms(roomController.roomType, roomController.pricePerNight, roomController.description);
            this.roomsRepository.save(rooms);
            return toUrl("admin/rooms.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }


    @Override
    protected List<Rooms> findAll(int currentPage, int rowsPerPage) throws Exception {
        return this.roomsRepository.findAll(currentPage, rowsPerPage);
    }

    @Override
    protected long getCounts() throws Exception {
        return this.roomsRepository.getCounts();
    }
}


