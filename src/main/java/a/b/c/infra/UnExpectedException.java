package a.b.c.infra;

public class UnExpectedException extends RuntimeException {

	public UnExpectedException() {
		super();
	}

	public UnExpectedException(String message) {
		super(message);
	}

	public UnExpectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnExpectedException(Throwable cause) {
		super(cause);
	}

}
