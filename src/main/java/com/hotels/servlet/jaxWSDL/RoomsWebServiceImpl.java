package com.hotels.servlet.jaxWSDL;

import com.hotels.domain.Rooms;
import com.hotels.repository.RoomsRepository;

import javax.jws.WebService;
import java.util.List;

@WebService
public class RoomsWebServiceImpl implements JaxWebService {

	private RoomsRepository roomsRepository;

	public RoomsWebServiceImpl() {
		this.roomsRepository = RoomsRepository.getInstance();
	}

	private static RoomsWebServiceImpl instance;

	public static synchronized RoomsWebServiceImpl getInstance() {

		if (instance == null)
			instance = new RoomsWebServiceImpl();
		return instance;

	}

	@Override
	public <T> List<T> findAll() throws Exception {

		List<Rooms> rooms = roomsRepository.findAll();
		return (List<T>) rooms;
	}

	@Override
	public <T> void update(T entity) throws Exception {
		roomsRepository.update((Rooms) entity);

	}

	@Override
	public void update(Long id) throws Exception {
		roomsRepository.update(id);
	}

	@Override
	public void delete(Long id) throws Exception {
		roomsRepository.delete(id);
	}

	@Override
	public <T> void save(T entity) throws Exception {
		roomsRepository.save((Rooms) entity);
	}
	
	@Override
	public long getCounts() throws Exception {
		return roomsRepository.getCounts();
	}
	
	@Override
	public <T> T findById(Long id) throws Exception {
		return (T) roomsRepository.findById(id);
	}


}
