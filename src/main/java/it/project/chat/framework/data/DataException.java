package it.project.chat.framework.data;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataException(String message) {
		super(message);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage() + (getCause() != null ? " (" + getCause().getMessage() + ")" : "");
	}
}