package plainsimple.tokitoki;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by mmcmillan on 4/11/16.
 */
public class Pronoun_Getter extends PartOfSpeechManipulator {

    public Pronoun_Getter(String language) {
        try {
            rules = Files.readAllLines(Paths.get("languages/" + language + "/pronouns"));
        } catch (Exception e) {
            System.out.println("error: couldn't find pronoun rules");
            System.exit(1);
        }
    }
    public String getPronoun(Pronoun pronoun) {
        return performAppropriateRule(pronoun);
    }

    @Override
    public boolean testRulePart(String[] attributes, String conditional) {
        /**** PRONOUN ATTRIBUTES (in order): person, number, gender, case */
        String person = attributes[0], number = attributes[1], gender = attributes[2], grammatical_case = attributes[3];
        /* all of this requires only one arg */
        String argument = stripQuotes(getArgs(conditional)[0]);
        switch (getFunctionName(conditional)) {
            case "personIs":
                return person.equals(argument);
            case "genderIs":
                return gender.equals(argument);
            case "numberIs":
                return number.equals(argument);
            case "caseIs":
                return grammatical_case.equals(argument);
            default:
                System.out.println("Error: unknown function in testRulePart: " + getFunctionName(conditional));
                System.exit(1);
                return false;
        }
    }
    @Override
    public String doRulePart(String infinitive, String instruction_name, String[] args) {
        switch (instruction_name) {
            case "return":
            case "replaceAll":
                return stripQuotes(args[0]);
        }
        System.out.println("Error: unable to execute instruction: " + instruction_name);
        System.exit(1);
        return null;
    }

}
