package plainsimple.tokitoki;


public class AdjectiveInflector extends PartOfSpeechManipulator {
    @Override
    public String doRulePart(String base_adjective, String instruction_name, String[] args) {
        switch (getFunctionName(instruction_name)) {
            case "return":
            case "replaceAll":
                return stripQuotes(args[0]);
            case "replaceEnd":
                return stripFromEnd(base_adjective, stripQuotes(args[0])) + stripQuotes(args[1]);
            case "replaceStart":
                return base_adjective.substring(stripQuotes(args[0]).length());
        }
        System.out.println("Error: unable to execute instruction: " + instruction_name);
        System.exit(1);
        return null;
    }

    @Override
    public boolean testRulePart(String[] attributes, String conditional) {

        /**** ADJECTIVE ATTRIBUTES (in order): base_adjective, tense, person, number, gender */
        String infinitive = attributes[0], tense = attributes[1], person = attributes[2], number = attributes[3], gender = attributes[4];
        /* all of this requires only one arg */
        String argument = stripQuotes(getArgs(conditional)[0]);
        switch (getFunctionName(conditional)) {
            case "startsIn":
            case "startsWith":
                int start_length = argument.length();
                return argument.equals(infinitive.substring(0, start_length));
            case "endsIn":
            case "endsWith":
                return stripFromEnd(infinitive, argument) != null;
            case "infinitiveIs":
            case "verbIs":
                return infinitive.equals(argument);
            case "tenseIs":
                return tense.equals(argument);
            case "personIs":
                return person.equals(argument);
            case "genderIs":
                return person.equals(argument);
            case "numberIs":
                return number.equals(argument);
            default:
                System.out.println("Error: unknown function in testRulePart: " + getFunctionName(conditional));
                System.exit(1);
                return false;
        }
    }
}
