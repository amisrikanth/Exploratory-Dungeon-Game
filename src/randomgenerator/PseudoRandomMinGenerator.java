package randomgenerator;

import java.util.Set;

import randomgenerator.IRandomNumberGenerator;

/**
 * This class is for testing all the implementation in the Battle, it implements the
 * IRandomNumberGenerator interface and gives back the lower limit of the bound provided if a single
 * integer is required and consecutive numbers starting from min if a range is provided. This makes
 * testing possible as it takes away the randomness and gives predictable outcomes.
 */
public class PseudoRandomMinGenerator implements IRandomNumberGenerator {
  String order;

  public PseudoRandomMinGenerator(String order) {
    this.order = order;
  }

  @Override
  public int getRandomNumber(int min, int max) {
    if (order.equals("min")) {
      return min;
    } else {
      return max;
    }
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count) {
    if (order.equals("min")) {
      int[] integers = new int[count];
      for (int i = 0; i < count; i++) {
        integers[i] = min + i;
      }
      return integers;
    } else {
      int[] integers = new int[count];
      for (int i = count - 1; i >= 0; i--) {
        integers[i] = min + i;
      }
      return integers;
    }
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count, Set<Integer> numArray) {
    if (order.equals("min")) {
      int[] integers = new int[count];
      int arrCount = 0;
      for (int i = min; i <= max; i++) {
        if (!numArray.contains(i)) {
          integers[arrCount++] = i;
        }
        if (arrCount == count) {
          break;
        }
      }
      return integers;
    } else {
      int[] integers = new int[count];
      int arrCount = 0;
      for (int i = max; i >= min; i--) {
        if (!numArray.contains(i)) {
          integers[arrCount++] = i;
        }
        if (arrCount == count) {
          break;
        }
      }
      return integers;
    }
  }
}
