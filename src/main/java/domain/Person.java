package domain;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private String userid;
    private String email;
    /**
     * This is a sha 512 version of the password.
     */
    private String password;
    private String firstName;
    private String lastName;

    public Person(String userid, String email, String password, String firstName, String lastName) {
        setUserid(userid);
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Person() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        if (userid.isEmpty()) {
            throw new IllegalArgumentException("No userid given");
        }
        this.userid = userid;
    }

    public void setEmail(String email) {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("No email given");
        }
        String USERID_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(USERID_PATTERN);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new IllegalArgumentException("Email not valid");
        }
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    /**
     * @return sha512 version of the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Check if it's the same password
     * @param password plaintext password (it get's hashed in the function)
     * @return Do the passwords match
     */
    public boolean isCorrectPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("No password given");
        }

        // Hash the password
        password = hash(password);

        // Compare them
        return getPassword().equals(password);
    }

    /**
     * Set a password
     * @param password The password
     * @param hashed Is the password hashed
     */
    public void setPassword(String password, boolean hashed) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("No password given");
        }

        if(!hashed) {
            password = hash(password);
        }

        this.password = password;
    }
    /**
     * Set a plaintext password
     * @param password a plaintext version of the password
     */
    public void setPassword(String password) {
        setPassword(password, false);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty()) {
            throw new IllegalArgumentException("No firstname given");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("No last name given");
        }
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
    }

    private String hash(String value) {
        try {
            // Get the hashing algorithm
            MessageDigest crypt = MessageDigest.getInstance("SHA-512");

            // Hash it
            crypt.reset();
            byte[] valueBytes = value.getBytes("UTF-8");
            crypt.update(valueBytes);
            byte[] digest = crypt.digest();

            // Return it as a hex-string
            return new BigInteger(1, digest).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new DomainException("SHA-512 not supported on server", e);
        } catch (UnsupportedEncodingException e) {
            throw new DomainException("UTF-8 not supported on server", e);
        }
    }
}
