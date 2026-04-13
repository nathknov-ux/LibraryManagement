package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Password utility for hashing and verifying passwords using BCrypt.
 */
public class PasswordUtil {

    /**
     * Hash a plain-text password using BCrypt.
     * @param plainPassword The raw password to hash.
     * @return The hashed password string.
     */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    /**
     * Verify a plain-text password against a BCrypt hash.
     * @param plainPassword The raw password to check.
     * @param hashedPassword The stored BCrypt hash.
     * @return true if the password matches the hash.
     */
    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
