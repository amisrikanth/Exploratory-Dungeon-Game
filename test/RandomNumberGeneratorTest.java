import org.junit.Before;
import org.junit.Test;

import randomgenerator.IRandomNumberGenerator;
import randomgenerator.RandomNumberGenerator;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the IRandomNumberGenerator interface and all the classes that implement it.
 */
public class RandomNumberGeneratorTest {
  IRandomNumberGenerator generator;

  @Before
  public void setUp() {
    generator = new RandomNumberGenerator();
  }

  @Test
  public void getRandomNumber() {
    for (int i = 0; i <= 1000; i++) {
      assertTrue(generator.getRandomNumber(1, 6) >= 1
              && generator.getRandomNumber(1, 6) <= 6);
    }
  }

  @Test
  public void generateRandomNumbers() {
    int[] randomIntegers = new int[10];
    for (int i = 0; i <= 1000; i++) {
      randomIntegers = generator.generateRandomNumbers(1, 40, 10);
      for (int num : randomIntegers) {
        assertTrue(num >= 1 && num <= 40);
      }
    }
  }
}