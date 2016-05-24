package plainsimple.tokitoki;


import java.util.ArrayList;

public class ExerciseHelper {
    public String generateLine(String line, boolean target_language, SetUpLine setup) {
        ArrayList<String> parsed_line = new ArrayList<>();
        int last_value = 0;
        while (line.indexOf('{', last_value + 1) != -1) {
            int start_location = line.indexOf('{', last_value + 1);
            last_value = start_location;
            int end_location = line.indexOf('}', start_location) + 1;
            parsed_line.add(line.substring(last_value, start_location));
            parsed_line.add(line.substring(start_location + 1, end_location - 1));
        }
        if (last_value != line.length() - 1) {
            parsed_line.add(line.substring(last_value));
        }
        String return_line = "";
        for (String line_part : parsed_line) {
            if (line_part.charAt(0) == '{') {
                String function_call = line_part.substring(1, line_part.length() - 1);
                String function_name = PartOfSpeechManipulator.getFunctionName(function_call);
                String[] args = PartOfSpeechManipulator.getArgs(function_call);
                for (int i = 0; i < args.length; i++) {
                    // if no quotes
                    if (PartOfSpeechManipulator.stripQuotes(args[i]).equals(args[i])) {
                        args[i] = (String)setup.variables.get(args[i]);
                    } else {
                        args[i] = PartOfSpeechManipulator.stripQuotes(args[i]);
                    }
                }
                switch (function_name) {
                    case "getPronoun":
                        Pronoun pronoun = new Pronoun(args[0], args[1], args[2], args[3]);
                        line += target_language ? pronoun.getTargetPronoun() : pronoun.getBasePronoun();
                        break;
                    case "getVerb":
                        Verb verb = new Verb(args[0], args[1], args[2], args[3], args[4]);
                        line += target_language ? verb.conjugate_target() : verb.conjugate_base();
                        break;
                    default:
                        System.out.println("unable to parse line");
                        System.exit(1);

                }
                return return_line;
            } else {
                return_line += line_part;
            }
        }
        return return_line;
    }
}
