package dk.nmillard.businessrules;


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


class Demo {

}
