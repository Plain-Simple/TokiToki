package plainsimple.tokitoki;

import java.util.HashMap;
import java.util.Random;

public class SetUpLine {
    public static Random random = new Random();
    public SetUpLine(String line) {
        for (String part : line.split(" ")) {

        }
    }
    private void loadHashmap(String line) {
        String[] split_line = line.split(" and ");
        String parts[][] = new String[split_line.length][2];
        for (int i = 0; i < split_line.length; i++) {
            parts[i][0] = split_line[i];
        }
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i][0].split(" = ");
        }
        for (String[] part : parts) {
            variables.put(part[0], evaluate(part[1]));
        }
    }
    public static String chooseRandom(String[] items) {
        return items[random.nextInt(items.length)];
    }
    private String evaluate(String part) {
        // if there are no quotes (so it's a function)
        if (PartOfSpeechManipulator.stripQuotes(part).equals(part)) {
            String command_name = PartOfSpeechManipulator.getFunctionName(part);
            String[] args = PartOfSpeechManipulator.getArgs(part);
            switch (command_name) {
                case "chooseRandom":
                    return chooseRandom(args);
                default:
                    System.out.println("Unable to read setup line. Exiting");
                    System.exit(0);
                    return null;
            }
        } else {
            return PartOfSpeechManipulator.stripQuotes(part);
        }
    }
    public static HashMap variables = new HashMap();
}
