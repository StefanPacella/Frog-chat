package it.project.chat.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import it.project.chat.framework.sicurezza.AuthenticationMaster;
import it.project.chat.rest.jackson.ObjectMapperContextResolver;
import it.project.chat.rest.resources.ContactsResource;
import it.project.chat.rest.resources.MessagesResource;
import it.project.chat.rest.resources.UsersResource;
import it.project.chat.rest.security.AppExceptionMapper;
import it.project.chat.rest.security.CORSFilter;


@ApplicationPath("rest")
public class RESTApp extends Application {

	private final Set<Class<?>> classes;

	public RESTApp() {
		HashSet<Class<?>> c = new HashSet<>();
		
		c.add(UsersResource.class);
		c.add(ContactsResource.class);
		c.add(MessagesResource.class);
		
		c.add(JacksonJsonProvider.class);

		c.add(ObjectMapperContextResolver.class);

		c.add(CORSFilter.class);
		
		c.add(AuthenticationMaster.class);

		c.add(AppExceptionMapper.class);
		

		classes = Collections.unmodifiableSet(c);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}