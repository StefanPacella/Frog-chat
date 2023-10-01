package it.project.chat.framework.sicurezza;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;



/**
 *
 * @author didattica
 */
@NameBinding
@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Logged {
	

}
