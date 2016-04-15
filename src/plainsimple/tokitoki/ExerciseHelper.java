package plainsimple.tokitoki;

import java.util.Random;

public class ExerciseHelper {
    Random random = new Random();
    public String chooseRandom(String[] items) {
        return items[random.nextInt(items.length)];
    }

}
