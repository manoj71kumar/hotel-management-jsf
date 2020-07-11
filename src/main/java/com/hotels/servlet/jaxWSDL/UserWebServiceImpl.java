package com.hotels.servlet.jaxWSDL;

import com.hotels.domain.Users;
import com.hotels.repository.UserRepository;

import javax.jws.WebService;
import java.util.List;

@WebService
public class UserWebServiceImpl implements JaxWebService {

	private UserRepository userRepository;

	private static UserWebServiceImpl instance;

	public UserWebServiceImpl() {
		this.userRepository = UserRepository.getInstance();
	}

	public static synchronized UserWebServiceImpl getInstance() {

		if (instance == null)
			instance = new UserWebServiceImpl();
		return instance;

	}

	@Override
	public <T> List<T> findAll() throws Exception {

		List<Users> userList = userRepository.findAll();
		return (List<T>) userList;

	}

	@Override
	public <T> void update(T entity) throws Exception {
		userRepository.update((Users) entity);

	}

	@Override
	public void update(Long id) throws Exception {
		userRepository.update(id);
	}

	@Override
	public void delete(Long id) throws Exception {
		userRepository.delete(id);
	}

	@Override
	public <T> void save(T entity) throws Exception {
		userRepository.save((Users) entity);
	}

	@Override
	public long getCounts() throws Exception {
		return userRepository.getCounts();
	}

	@Override
	public <T> T findById(Long id) throws Exception {
		return (T) userRepository.findById(id);
	}

}
