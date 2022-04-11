package randomgenerator;

import java.util.Set;

/**
 * This is a customer Random generator to simulate a dungeon for testing.
 */
public class CustomRandomGenerator implements IRandomNumberGenerator {
  int numberCounter = 0;
  int arrayNumberCounter = 0;

  @Override
  public int getRandomNumber(int min, int max) {
    if (numberCounter == 0) {
      numberCounter++;
      return 8;
    } else if (numberCounter == 1 || numberCounter == 11 || numberCounter == 18
            || numberCounter == 33 || numberCounter == 37 || numberCounter == 38
            || numberCounter == 43) {
      numberCounter++;
      return 0;
    } else if (numberCounter == 2 || numberCounter == 3 || numberCounter == 6 || numberCounter == 8
            || numberCounter == 12 || numberCounter == 15 || numberCounter == 17
            || numberCounter == 19 || numberCounter == 20 || numberCounter == 22
            || numberCounter == 23 || numberCounter == 24 || numberCounter == 25
            || numberCounter == 27 || numberCounter == 28 || numberCounter == 30
            || numberCounter == 32 || numberCounter == 34 || numberCounter == 36
            || numberCounter == 42) {
      numberCounter++;
      return 2;
    } else if (numberCounter == 4 || numberCounter == 5 || numberCounter == 7
            || numberCounter == 13 || numberCounter == 16 || numberCounter == 31
            || numberCounter == 35) {
      numberCounter++;
      return 1;
    } else if (numberCounter == 9 || numberCounter == 10 || numberCounter == 14
            || numberCounter == 21 || numberCounter == 26 || numberCounter == 29
            || numberCounter == 39 || numberCounter == 40 || numberCounter == 41) {
      numberCounter++;
      return 3;
    }
    return min;
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count) {
    if (arrayNumberCounter == 0) {
      arrayNumberCounter++;
      return new int[]{38, 26, 54, 60, 28, 32, 7, 55, 53, 62, 50, 18, 23, 67, 0, 9, 78, 66, 49, 19,
          1, 43, 56, 31, 52, 83, 35, 70, 41, 6, 80, 20, 51, 36, 45, 4, 73, 40, 3, 65, 44, 59,
          61, 57, 79, 27, 77, 14, 5, 82, 17, 72, 34, 69, 64, 12, 39, 24, 63, 81, 71, 22, 58,
          37, 74, 10, 11, 29, 33, 42, 25, 13, 16, 8, 68, 76, 47, 21, 2, 15, 46, 48, 30, 75};
    } else if (arrayNumberCounter == 1) {
      arrayNumberCounter++;
      return new int[]{25, 18};
    } else if (arrayNumberCounter == 2) {
      arrayNumberCounter++;
      return new int[]{5, 0, 4, 15, 11, 1, 18};
    } else if (arrayNumberCounter == 3) {
      arrayNumberCounter++;
      return new int[]{5, 11, 17, 8, 14, 4, 0};
    }
    return new int[]{0};
  }

  @Override
  public int[] generateRandomNumbers(int min, int max, int count, Set<Integer> numArray) {
    return new int[]{10, 3, 0, 17, 5, 11, 6, 1, 16, 18, 4, 2, 7, 14, 13, 9, 15};
  }
}
