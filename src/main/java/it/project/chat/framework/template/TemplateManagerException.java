package it.project.chat.framework.template;

/**
 *
 * @author Giuseppe Della Penna
 */
public class TemplateManagerException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TemplateManagerException(String message) {
        super(message);
    }

    public TemplateManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateManagerException(Throwable cause) {
        super(cause);
    }

}
