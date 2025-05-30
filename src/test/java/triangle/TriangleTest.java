package triangle;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import triangle.Triangle.Type;

import static triangle.Triangle.Type;
import static triangle.Triangle.Type.*;

/**
 * Test class for the Triangle implementation.
 */
@RunWith(Parameterized.class)
public class TriangleTest {
  static {
    Locale.setDefault(Locale.GERMAN);
  }
  @Parameter(0)
  public int a;
  @Parameter(1)
  public int b;
  @Parameter(2)
  public int c;
  @Parameter(3)
  public Type expected;

  @Before
  public void initialize() {
    Triangle a = new Triangle();
  }

  @Test
  public void testTriangle() {
    Type actual = Triangle.classify(a, b, c);

    assertEquals(expected, actual);
  }

  @Parameters(name = "{index}: ({0} {1} {2})->{3}")
  public static Collection testTable() {
    return Arrays.asList(new Object[][]
      {
      {0, 0, 0, INVALID},
      {1, 0, 0, INVALID},
      {1, 1, 0, INVALID},
      {1, 2, 3, INVALID},
      {1, 3, 2, INVALID},
      {3, 1, 2, INVALID},
      {2, 3, 4, SCALENE},
      {1, 1, 1, EQUILATERAL},
      {2, 2, 1, ISOSCELES},
      {2, 1, 2, ISOSCELES},
      {1, 2, 2, ISOSCELES},
      {2, 2, 5, INVALID},
      {2, 5, 2, INVALID},
      {5, 2, 2, INVALID},
      //new from here
      {1, 1, 2, INVALID}, // 109
      {1, 2, 1, INVALID}, // 126
      {2, 1, 1, INVALID}, // 143
      
      {-1, 1, 1, INVALID}, // 4
      {0, 1, 1, INVALID}, // 2, 3
      {1, 0, 1, INVALID}, // 8, 7
      {1, -1, 1, INVALID}, // 9
      {1, 1, -1, INVALID}, // 18

      {1, 1, 0, INVALID}, // 19
      {0, 0, 1, INVALID}, // 20
      {0, 1, 0, INVALID}, // 21
      {1, 0, 0, INVALID}, // 22
      {0, -1, -1, INVALID}, // 23
      {-1, -1, -1, INVALID}, // 24
      {-1, -1, 0, INVALID}, // 25
      {-1, 0, -1, INVALID}, // 26
      {0, -1, -1, INVALID}, // 27

      {1, 1, 1, EQUILATERAL}, // 11
      {2, 2, 2, EQUILATERAL}, // 59
      {3, 3, 3, EQUILATERAL}, // 63
      {4, 4, 4, EQUILATERAL}, // 70
      {5, 5, 5, EQUILATERAL}, // 74
      {6, 6, 6, EQUILATERAL}, // 76
      {7, 7, 7, EQUILATERAL}, // 80
      {8, 8, 8, EQUILATERAL}, // 81
      {9, 9, 9, EQUILATERAL}, // 85
      {1, -1, -1, INVALID}, //87
      {-1, -1, -1, INVALID}, //101
      {1, 1, 1, EQUILATERAL}, // 105
      {2, 2, 2, EQUILATERAL}, // 111
      {3, 3, 3, EQUILATERAL}, // 122
      {4, 4, 4, EQUILATERAL}, // 128
      {5, 5, 5, EQUILATERAL}, // 136
      {6, 6, 6, EQUILATERAL}, // 139
      {7, 7, 7, EQUILATERAL}, // 145
      }
    );
   }
}