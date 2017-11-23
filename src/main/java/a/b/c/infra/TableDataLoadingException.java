package a.b.c.infra;

public class TableDataLoadingException extends RuntimeException {

	public TableDataLoadingException() {
		super();
	}

	public TableDataLoadingException(String message) {
		super(message);
	}

	public TableDataLoadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TableDataLoadingException(Throwable cause) {
		super(cause);
	}

}
