package plainsimple.tokitoki;

import java.util.ArrayList;
import java.util.Random;

public class Wordgroup {
    public Wordgroup(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }
    Random random = new Random();
    private final Coordinate[] coordinates;
    private int introduction_index;
    private Coordinate last_random;
    private ArrayList<Coordinate> to_repeat = new ArrayList<>();

    public void learn() {
        introduceNext();
        introduceNext();
        repeat(0);
        last_random = to_repeat.get(0);
        for (int i = 2; i < coordinates.length; i++) {
            introduceNext();
            repeatNewRandom();
            repeatNewRandom();
        }
        while (repeatNewRandom()) {
        }
    }

    private boolean introduceNext() {
        if (introduction_index < coordinates.length) {
            coordinates[introduction_index].introduce();
            to_repeat.add(coordinates[introduction_index]);
            introduction_index++;
            return true;
        } else {
            return false;
        }
    }

    private void repeat(int index) {
        last_random = to_repeat.get(index);
        if (to_repeat.get(index).quizNoGrade()) {
            to_repeat.get(index).incrementReps();
        }
        if (to_repeat.get(index).isFullyLearned()) {
            to_repeat.remove(index);
        }
    }

    private boolean repeatNewRandom() {
        if (to_repeat.size() <= 0) {
            return false;
        }
        if (to_repeat.size() == 1) {
            // there are no new random nums left
            repeat(0);
        } else {
            // if our last random was deleted, we can just choose anything
            int last_random_index = to_repeat.indexOf(last_random);
            if (last_random_index == -1) {
                repeatRandom();
            } else {
                // if we need to exclude something from our random selection, we take the value
                // we are excluding, add a random number between 1 and to_repeat.size()-1, and
                // then mod the whole thing by to_repeat.size(). This gives us a random number
                // from just after the prohibited value to just before it (looping around because it's mod)
                int random_num = (1 + random.nextInt(to_repeat.size() - 1) + last_random_index) % to_repeat.size();
                repeat(random_num);
            }
        }
        return true;
    }

    private boolean repeatRandom() {
        if (to_repeat.size() <= 0) {
            return false;
        } else {
            int random_num = random.nextInt(to_repeat.size());
            repeat(random_num);
            return true;
        }
    }
}
