package ru.kai.crypt;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptography {
    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static final int workload = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(passwordPlaintext, salt);
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        if(null == storedHash || !storedHash.startsWith("$2a$"))
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        return BCrypt.checkpw(passwordPlaintext, storedHash);
    }

}
