package dk.nmillard.domaindesign;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.YEARS;

public class BetterDesign {

    @Test
    public void createValidUser() {
        LocalDate date = LocalDate.of(1991, 11, 26);
        Username username = new Username("nmil");

        var user = new User(username, date);

        System.out.println(user.getUsername());
        System.out.println(user.getAge());
    }
}

class User {
    public User(Username username, LocalDate birthDate) {
        this.username = username;
        this.birthDate = birthDate;
    }

    private Username username;
    private final LocalDate birthDate;

    public String getUsername() {
        return username.value();
    }

    public int getAge() {
        return (int) YEARS.between(this.birthDate, LocalDate.now());
    }

    public void changeUsername(Username username) {
        if (username == null) return;
        this.username = username;
    }
}

record Username(String value) {
    public Username {
        boolean missingUsername = value == null || value.isEmpty();
        if (missingUsername) throw new InvalidUsernameException("Cannot be null or empty");

        boolean hasRuleViolation = rules.stream().anyMatch(r -> r.hasViolation(value));
        if (hasRuleViolation) throw new InvalidUsernameException("Some rule was violated.");
    }

    private static final List<UsernameRule> rules = new ArrayList<>() {{
            add(new TooLongUsername(20));
            add(new OnlyAToZCharacters());
        }};
}


abstract class UsernameRule {
    abstract boolean hasViolation(String value);
}


class TooLongUsername extends UsernameRule {
    public TooLongUsername(int maxLength) {
        this.maxLength = maxLength;
    }

    private final int maxLength;

    @Override
    boolean hasViolation(String value) {
        return value.length() <= maxLength;
    }
}

class OnlyAToZCharacters extends UsernameRule {

    @Override
    boolean hasViolation(String value) {
        return !Pattern.matches("[a-zA-Z]+", value);
    }
}

class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}