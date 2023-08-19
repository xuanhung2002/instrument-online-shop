package exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserIdNotFoundException extends UsernameNotFoundException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIdNotFoundException(String id) {
        super(id);
    }

    public UserIdNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
