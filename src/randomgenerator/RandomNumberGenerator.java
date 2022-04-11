package randomgenerator;

import randomgenerator.IRandomNumberGenerator;

import java.util.Random;
import java.util.Set;

/**
 * Random Number Generator gives back a random number within a given range if a single
 * integer is required and distinct array of numbers if a range is given and the count of numbers
 * required.
 */
public class RandomNumberGenerator extends Random implements IRandomNumberGenerator {
  @Override
  public int getRandomNumber(int min, int max) {
    return this.nextInt(max + 1 - min) + min;
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count) {
    return this.ints(min, max + 1).distinct().limit(count).toArray();
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count, Set<Integer> numArray) {
    return this.ints(min, max + 1).filter(x -> !numArray.contains(x)).distinct()
            .limit(count).toArray();
  }

}

