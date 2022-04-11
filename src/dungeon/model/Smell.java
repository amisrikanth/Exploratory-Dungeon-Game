package dungeon.model;

/**
 * The enum Smell is used to check the Smell when the Monster is nearby. If the monster is one
 * position away then it is LESS_PUNGENT, and if it is two positions away then it is MORE_PUNGENT.
 */
public enum Smell {
  NO_SMELL, LESS_PUNGENT, MORE_PUNGENT
}
