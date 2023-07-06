package dk.nmillard.businessrules;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/*
Business policies & rules

Policies er generelt brede i scope og sætter en overordnet retning. De er guidelines.
En policy kan fx være "et brugernavn skal følge vores regler".

Business rules er mere granulære og specifikke nok til at blive implementeret. Det kan fx være:
Et brugernavn skal:
- have en værdi (må ikke være null eller tom string)
- kan ikke være mindre end 3 karakterer
- må ikke være længere end 50 karakterer
 */

class Username {
    public Username() {

    }

    private String value;
}


class DemoTest {

    @ParameterizedTest
    @MethodSource("getUsernames")
    void validUsername(String value, boolean expected) {

    }

    private static Stream<Arguments> getUsernames() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of(null, false),
                Arguments.of("te", false),
                Arguments.of("t44444444224242424242424231231231345dwdawdawdawdawdawde", false),
                Arguments.of("test", true)
        );
    }
}
