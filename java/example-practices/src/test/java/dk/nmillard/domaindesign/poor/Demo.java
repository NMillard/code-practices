package dk.nmillard.domaindesign.poor;

import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.YEARS;


class User {

    private String username;
    private LocalDate birthDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}


class UserManager {
    public long getUserAge(User user) {
        return YEARS.between(user.getBirthDate(), LocalDate.now());
    }

    public boolean hasValidUsername(User user) {
        if (user == null) return false;

        String username = user.getUsername();

        if (username == null || username.isEmpty()) {
            return false;
        } else if (username.length() > 20) {
            return false;
        } else if (!Pattern.matches("[a-zA-Z]+", username)) {
            return false;
        } else {
            return true;
        }
    }
}


public class Demo {
    @Test
    public void invalidInvariants() {
        var user = new User();
    }


    @Test
    public void allowingBadValues() {
        var user = new User();
        user.setUsername("2 GB String");
        user.setBirthDate(LocalDate.of(1991, 11, 26));
    }


    @Test
    public void primitiveObsession() {
        String username = "2GB string...";
        var user = new User();
        user.setUsername(username);
    }


    @Test
    public void operatingOnData() {
        var user = new User();
        user.setUsername("nmil");
        user.setBirthDate(LocalDate.of(1991, 11, 26));

        var manager = new UserManager();

        long age = manager.getUserAge(user);
    }

    @Test
    public void hardCodedBranches() {
        var user = new User();
        user.setUsername("nmil");

        var manager = new UserManager();

        boolean result = manager.hasValidUsername(user);

        System.out.println(result);
    }
}
