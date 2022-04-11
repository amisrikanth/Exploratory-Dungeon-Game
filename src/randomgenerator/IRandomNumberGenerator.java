package randomgenerator;

import java.util.Set;

/**
 * Random Number Generator gives back a random number within a given range if a single
 * integer is required and distinct array of numbers if a range is given and the count of numbers
 * required.
 */
public interface IRandomNumberGenerator {
  /**
   * This method gives a random number within a given range.
   *
   * @param min The lower bound of the range.
   * @param max The upper bound of the range.
   * @return A random number within the range.
   */
  int getRandomNumber(int min, int max);

  /**
   * This method gives an integer array of distinct numbers with the size of count provided
   * and within the range provided.
   *
   * @param min   The lower bound of the range.
   * @param max   The upper bound of the range.
   * @param count The number of distinct integers required.
   * @return An array of distinct random numbers within the range.
   */
  int[] generateRandomNumbers(int min, int max, int count);

  /**
   * This method gives an integer array of distinct numbers with the size of count provided and
   * exclusive of any numbers from the given set.
   *
   * @param min      The lower bound of the range.
   * @param max      The upper bound of the range.
   * @param numArray The set that the return values should not be a part of.
   * @return An array of distinct random numbers within the range.
   */
  int[] generateRandomNumbers(int min, int max, int count, Set<Integer> numArray);

}
