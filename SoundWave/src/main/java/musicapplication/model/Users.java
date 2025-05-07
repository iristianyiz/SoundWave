package musicapplication.model;

/**
 * Represents a user in the music application.
 * 
 * @author Krushna Sanjay Sharma
 */
public class Users {
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	/**
	 * Constructs a new Users object.
	 *
	 * @param userName  The unique username for the user.
	 * @param password  The user's password.
	 * @param firstName The user's first name.
	 * @param lastName  The user's last name.
	 * @param email     The user's email address.
	 * @param phone     The user's phone number.
	 */
	public Users(String userName, String password, String firstName, String lastName, String email, String phone) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}

	public Users(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the username of the user.
	 *
	 * @return The username.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the username of the user.
	 *
	 * @param userName The username to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password of the user.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the first name of the user.
	 *
	 * @return The first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName The first name to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the user.
	 *
	 * @return The last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName The last name to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email address of the user.
	 *
	 * @return The email address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the user.
	 *
	 * @param email The email address to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the phone number of the user.
	 *
	 * @return The phone number.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of the user.
	 *
	 * @param phone The phone number to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
