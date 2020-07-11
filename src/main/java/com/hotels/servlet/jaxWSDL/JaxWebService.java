package com.hotels.servlet.jaxWSDL;

import com.sun.xml.ws.runtime.config.ObjectFactory;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@WebService(name = "JaxWebService")
@XmlSeeAlso({ ObjectFactory.class })
public interface JaxWebService {

	@WebMethod
	public <T> List<T> findAll() throws Exception;

	@WebMethod
	public <T> void update(T entity) throws Exception;

	@WebMethod
	public void update(Long id) throws Exception;

	@WebMethod
	public void delete(Long id) throws Exception;

	@WebMethod
	public <T> void save(T entity) throws Exception;

	@WebMethod
	public long getCounts() throws Exception;

	@WebMethod
	public <T> T findById(Long id) throws Exception;

}
