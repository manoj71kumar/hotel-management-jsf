package com.hotels.servlet.jaxWSDL;

import com.hotels.domain.Booking;
import com.hotels.repository.BookingRepository;

import javax.jws.WebService;
import java.io.IOException;
import java.util.List;

@WebService
public class BookingWebServiceImpl implements JaxWebService {
	private BookingRepository bookingRepository;

	public BookingWebServiceImpl() {
		this.bookingRepository = BookingRepository.getInstance();
	}

	private static BookingWebServiceImpl instance;

	public static synchronized BookingWebServiceImpl getInstance() {

		if (instance == null)
			instance = new BookingWebServiceImpl();
		return instance;

	}

	@Override
	public <T> List<T> findAll() throws Exception {
		List<Booking> booking = bookingRepository.findAll();
		return (List<T>) booking;
	}

	@Override
	public <T> void update(T entity) throws IOException {
		bookingRepository.update((Booking) entity);
	}

	@Override
	public void update(Long id) throws Exception {
		bookingRepository.update(id);

	}

	@Override
	public void delete(Long id) throws Exception {
		bookingRepository.delete(id);

	}

	@Override
	public <T> void save(T entity) throws Exception {
		bookingRepository.save((Booking) entity);

	}

	@Override
	public long getCounts() throws Exception {
		return bookingRepository.getCounts();
	}

	@Override
	public <T> T findById(Long id) throws Exception {
		return (T) bookingRepository.findById(id);
	}

}
