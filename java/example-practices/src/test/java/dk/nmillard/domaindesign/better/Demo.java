package dk.nmillard.domaindesign.better;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.YEARS;


class User {
    public User(Username username, LocalDate birthDate) {
        checkNull(username);
        checkNull(birthDate);

        this.username = username;
        this.birthDate = birthDate;
    }

    private void checkNull(Object object) {
        if (object == null) throw new IllegalArgumentException();
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
        return value.length() > maxLength;
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


class UserManager {
    public UserManager(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    private final PasswordGenerator passwordGenerator;

    public String generateTotp(User user) {
        if (user == null) throw new IllegalArgumentException();
        return passwordGenerator.generate(user.getUsername());
    }
}


interface PasswordGenerator {
    String generate(String seed);
}


class TotpPasswordGenerator implements PasswordGenerator {
    @Override
    public String generate(String seed) {
        var random = new Random();
        random.setSeed(seed.length());

        byte[] buffer = new byte[3];
        random.nextBytes(buffer);

        StringBuilder sb = new StringBuilder();
        for (var element : buffer) sb.append(String.format("%02x", element));

        return sb.toString();
    }
}


public class Demo {


    @Test
    public void whawhda() {
        var user = new User(null, LocalDate.of(1999, 1, 1));

        System.out.println(user.getAge());
    }


    @Test
    public void alwaysValid() {
        LocalDate date = LocalDate.of(1991, 11, 26);
        Username username = new Username("nmil");

        var user = new User(username, date);

        System.out.println(user.getUsername());
        System.out.println(user.getAge());
    }

    @Test
    public void entityVsValueObjects() {
        LocalDate date = LocalDate.of(1991, 11, 26);
        Username username = new Username("nmil");

        var firstUser = new User(username, date);
        var secondUser = new User(username, date);


        // Testing equality
        System.out.println(firstUser.equals(secondUser));

        Username otherUsername = new Username("nmil");
        System.out.println(username.equals(otherUsername));
    }

    @Test
    public void dependecyInjection() {
        var totp = new TotpPasswordGenerator();
        var manager = new UserManager(totp);

        String password = manager.generateTotp(new User(new Username("nmil"), LocalDate.of(1991, 11, 26)));
        System.out.println(password);
    }
}

