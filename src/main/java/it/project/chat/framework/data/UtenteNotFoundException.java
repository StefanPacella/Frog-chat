package it.project.chat.framework.data;

public class UtenteNotFoundException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public UtenteNotFoundException() {
	}

	public UtenteNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteNotFoundException(String message) {
		super(message);
	}

	public UtenteNotFoundException(Throwable cause) {
		super(cause);
	}

}
