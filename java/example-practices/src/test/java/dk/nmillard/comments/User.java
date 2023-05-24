package dk.nmillard.comments;

import java.time.LocalDate;

/**
 * The User class.
 */
public class User {

    /**
     * The username.
     */
    private String username;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;
}


class UserManager {

//    public int getAge(User user) {
//        // Do some work
//
//        throw new UnsupportedOperationException();
//    }

    public int getAge(LocalDate birthDate) {
        // Implementation
        throw new UnsupportedOperationException();
    }
}


class PremiumUser {
    /* -----------------
    Fields
    -------------------- */
    private String username;
    private LocalDate birthDate;

    /* -----------------
    Constructors
    -------------------- */
    public PremiumUser() {
    }

    public PremiumUser(String username, LocalDate birthDate) {
        this.username = username;
        this.birthDate = birthDate;
    }

    /* -----------------
        Getters and Setters
        -------------------- */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        throw new UnsupportedOperationException();
    }
}
