package kompilatory.exception;

public class SyntaxError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SyntaxError(String input) {
		this.message = input;
	}

}
