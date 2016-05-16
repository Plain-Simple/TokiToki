package plainsimple.tokitoki;

import java.util.ArrayList;
import java.util.List;

public abstract class PartOfSpeechManipulator {
    List<String> rules;

    /**
     * Breaks up a complicated rule (singular rules separated by " and ") into singular rules
     * @param rule - the complicated rule to be broken up
     * @return an array of Strings, each element being a singular rule
     */
    String[] breakRuleUp(String rule) {
        List<String> rule_parts = new ArrayList<>();
        /* parenthase level (how many parenthases in you are), curly braces level */
        int p_level = 0, c_level = 0;
        /* quotes level is true or false, since you are either in or out (in is true) */
        boolean q_level = false;
        rule_parts.add("");
        for (int i = 0; i < rule.length(); i++) {
            int current_rule_index = rule_parts.size() - 1;
            /* if we're not in quotes, adjust p_level and c_level if we find brackets or parenthases */
            if (!q_level) {
                if (rule.charAt(i) == '(') {
                    p_level++;
                } else if (rule.charAt(i) == ')') {
                    p_level--;
                } else if (rule.charAt(i) == '{') {
                    c_level++;
                } else if (rule.charAt(i) == '}') {
                    c_level--;
                }
            }
            if (rule.charAt(i) == '"') {
                q_level = !q_level;
            }
            /* append current_char to the end of the current arg */
            rule_parts.set(current_rule_index, rule_parts.get(current_rule_index) + rule.charAt(i));

            if (rule.length() - i >= 6 && " and ".equals(rule.substring(i + 1, i + 6)) && p_level == 0 && c_level == 0 && !q_level) {
                rule_parts.add("");
                i += 5;
            }
        }
        return rule_parts.toArray(new String[rule_parts.size()]);
    }

    /**
     * Modifies the base part of speech according to the instructions given
     * @param base_part_of_speech - the original, unmodified part of speech
     * @param instructions        - the set of instructions for how to modify the original
     * @param index_sequence      - if the conditional had multi-rules, index_sequence contains the indeces of the instructions in the multirule to follow
     * @return the modified part of speech
     */
    public String doRule(String base_part_of_speech, String instructions, List<Integer> index_sequence) {
        String overall_result = base_part_of_speech;
        if (isMultiFunction(instructions) && getArgs(instructions).length >= 2) {
            instructions = getArgs(instructions)[index_sequence.get(0)];
            index_sequence = index_sequence.subList(1, index_sequence.size());
            return doRule(base_part_of_speech, instructions, index_sequence);
        }
        for (String single_condition : breakRuleUp(instructions)) {
            overall_result = doRulePart(overall_result, getFunctionName(single_condition), getArgs(single_condition));
        }
        return overall_result;
    }

    public abstract String doRulePart(String original, String instruction_name, String[] instruction_args);

    /**
     * Gets the arguments passed to a given function
     * @param function_call - the function call from which arguments will be extracted
     * @return an array of strings where each string is an argument
     */
    public static String[] getArgs(String function_call) {
        /* finds args_string between curly braces or parenthases, whichever is appropriate for the function call */
        String args_string =
                isMultiFunction(function_call) ? function_call.substring(function_call.indexOf('{') + 1, function_call.lastIndexOf('}')) : function_call.substring(function_call.indexOf('(') + 1, function_call.lastIndexOf(')'));
        /* parenthase level (how many parenthases in you are), curly braces level */
        int p_level = 0, c_level = 0;
        /* quotes level is true or false, since you are either in or out (in is true) */
        boolean q_level = false;
        ArrayList<String> args = new ArrayList<String>();
        args.add("");
        for (char current_char : args_string.toCharArray()) {
            int current_arg_index = args.size() - 1;
            /* true indicates that the current character isn't part of the argument */
            boolean skip = false;
            /* if we're not in quotes, adjust p_level and c_level if we find brackets or parenthases */
            if (!q_level) {
                if (current_char == '(') {
                    p_level++;
                } else if (current_char == ')') {
                    p_level--;
                } else if (current_char == '{') {
                    c_level++;
                } else if (current_char == '}') {
                    c_level--;
                    /* if there's a comma that's not in parenthases, braces, or quotes, then start a new arg */
                } else if (current_char == ',' && p_level == 0 && c_level == 0) {
                    args.add("");
                    skip = true;
                }
            }
            if (current_char == '"') {
                q_level = !q_level;
                /* don't start an arg with a space */
            } else if (current_char == ' ' && args.get(current_arg_index).isEmpty()) {
                skip = true;
            }

            if (!skip) {
                /* append current_char to the end of the current arg */
                args.set(current_arg_index, args.get(current_arg_index) + current_char);
            }
        }
        return args.toArray(new String[args.size()]);

    }

    /**
     * Gets the name of the function in the function call
     * @param function_call - the function call to be examined
     * @return the name (whatever is immediately before the parenthases or braces) of the function
     */
    public static String getFunctionName(String function_call) {
        return function_call.substring(0, function_call.indexOf(isMultiFunction(function_call) ? '{' : '('));
    }

    /**
     * Gets the instructions below a given conditional
     * @param line_num - the line number of the conditional
     * @return all instructions concatnated into one line
     */
    public String getInstructions(int line_num) {
        String instructions = "";
        for (int i = line_num + 1; i < rules.size() && i != jumpToNextConditional(line_num); i++) {
            String next_line = rules.get(i);
            if (next_line.length() >= 1 && next_line.charAt(0) != '#') {
                instructions += next_line;
            }
        }
        return instructions;
    }

    /**
     * Finds the indeces of the conditions within the multirule that match the conditional
     * @param attributes - the attributes on which to test the conditional
     * @param multirule  - the multirule to be tested
     * @return an array of ints each containing the index of the multirule that was true
     */
    public int getMultiRuleIndex(String[] attributes, String multirule) {
        String function_name = getFunctionName(multirule);
        String[] args = getArgs(multirule);
        for (int i = 0; i < args.length; i++) {
            if (testRulePart(attributes, function_name + '(' + args[i] + ')')) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Takess a function call and decides whether the function is a multifunction (braces) or not (parenthases)
     * @param function_call - the function call to examine
     * @return true if it is a multifunction, false if not
     */
    public static boolean isMultiFunction(String function_call) {
        int first_curly = function_call.indexOf('{'), first_paren = function_call.indexOf('(');
        if (first_curly == -1 && first_paren == -1) {
            System.out.println("Error: unable to determine function type: " + function_call);
            throw new RuntimeException();
            //System.exit(1);
            //return false;
        } else if (first_curly == -1) {
            return false;
        } else if (first_paren == -1) {
            return true;
        } else {
            return first_curly < first_paren;
        }
    }

    /**
     * Returns the integer index of the next conditional line in rules
     * @param cond_index the index of the last conditional
     * @return the integer index of the next conditional line in rules, -1 if it's the last one
     */
    public int jumpToNextConditional(int cond_index) {
        boolean next_line_is_condition = false;
        for (int i = cond_index + 1; i < rules.size(); i++) {
            if ("".equals(rules.get(i))) {
                next_line_is_condition = true;
            /* ignore lines starting with "#" since they are comments */
            } else if (rules.get(i).charAt(0) == '#') {
            } else if (next_line_is_condition) {
                return i;
            }
        }
        return -1;
    }

    public String performAppropriateRule(PartOfSpeech partofspeech) {
        /* get the very first conditional */
        int line_num = jumpToNextConditional(-1);
        /* while there are still conditionals left */
        while (line_num != -1) {
            List<Integer> index_sequence = testRule(partofspeech.getAttributes(), rules.get(line_num));
            if (index_sequence != null) {
                return doRule(partofspeech.getAttributes()[0], getInstructions(line_num), index_sequence);
            }
            line_num = jumpToNextConditional(line_num);
        }
        System.out.println("unable to find matching instruction");
        System.exit(0);
        return null;
    }

    public static String stripCurlyBraces(String str) {
        return str.length() >= 2 && str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}' ? str.substring(1, str.length() - 1) : str;
    }

    /**
     * Returns original with ending removed from the end if it exists, otherwise returns null.
     * @param original - the original string to be stripped of an ending
     * @param ending   - the ending to be stripped from the original string
     * @return original with ending removed from the end if it exists, otherwise null
     */
    public static String stripFromEnd(String original, String ending) {
        return ending.equals(original.substring(original.length() - ending.length())) ? original.substring(0, original.length() - ending.length()) : null;
    }

    /**
     * Removes quotes around something if they are present
     * @param str - string to strip of quotes
     * @return the string stripped of quotes if they were present, otherwise the original string
     */
    public static String stripQuotes(String str) {
        return str.length() >= 2 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"' ? str.substring(1, str.length() - 1) : str;
    }

    /**
     * Tests an entire line to see if the instructions below should be followed
     * @param attributes  - the attributes of the part of speech (such as gender, tense, etc)
     * @param conditional - the conditional that the attributes are tested on
     * @return a list of integers with the indeces of the multirule that were true
     */
    List<Integer> testRule(String[] attributes, String conditional) {
        ArrayList<Integer> index_sequence = new ArrayList<>();
        for (String single_condition : breakRuleUp(conditional)) {
            if (isMultiFunction(single_condition)) {
                int multiruleindex = getMultiRuleIndex(attributes, single_condition);
                if (multiruleindex == -1) {
                    return null;
                } else {
                    index_sequence.add(multiruleindex);
                }
            } else if (!testRulePart(attributes, single_condition)) {
                return null;
            }
        }
        return index_sequence;
    }

    /**
     * Tests a singular rule (or rule part) as a conditional
     * @param attributes  - the attributes of the part of speech (such as tense, gender, etc)
     * @param conditional - the conditional to test the attributes by
     * @return whether or not the attributes match the conditional requirements
     */
    public abstract boolean testRulePart(String[] attributes, String conditional);
}
