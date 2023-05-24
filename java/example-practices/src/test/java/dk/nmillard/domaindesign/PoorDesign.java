package dk.nmillard.domaindesign;

import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.YEARS;

class BadUser {
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


public class PoorDesign {
    @Test
    public void invalidInvariants() {
        var user = new BadUser();
    }


    @Test
    public void allowingBadValues() {
        var user = new BadUser();
        user.setUsername("nmil");
        user.setBirthDate(LocalDate.of(1991, 11, 26));
    }


    @Test
    public void primitiveObsession() {
        String username = "2GB string...";
        var user = new BadUser();
        user.setUsername(username);
    }


    @Test
    public void operatingOnData() {
        var user = new BadUser();
        user.setUsername("nmil");
        user.setBirthDate(LocalDate.of(1991, 11, 26));

        var manager = new UserManager();

        long age = manager.getUserAge(user);
    }

    @Test
    public void hardCodedBranches() {
        var user = new BadUser();
        user.setUsername("nmil");

        var manager = new UserManager();

        boolean result = manager.hasValidUsername(user);

        System.out.println(result);
    }
}


class UserManager {
    public long getUserAge(BadUser user) {
        return YEARS.between(user.getBirthDate(), LocalDate.now());
    }

    public boolean hasValidUsername(BadUser user) {
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