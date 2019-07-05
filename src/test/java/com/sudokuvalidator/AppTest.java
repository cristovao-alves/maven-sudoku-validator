package com.sudokuvalidator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.*;

/**
 * Unit test for App.
 */
public class AppTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenFileHasInvalidFileExtension() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_FILE_EXTENSION + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"invalidFileExtension.txt"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenFileDoesNorExists() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.FILE_DOES_NOT_EXISTS + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"nonExistingFile.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenBoardHasInvalidCharacters() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_CHARACTERS + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/invalidCharacters.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenBoardIsEmpty() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_BOARD_SIZE + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/emptyBoard.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenBoardHasInvalidSize() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_BOARD_SIZE + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/invalidBoardSize.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenAnyRowOfTheBoardHasRepeatedNumbers() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_SOLUTION + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/repeatedRowNumber.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenAnyColumnOfTheBoardHasRepeatedNumbers() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_SOLUTION + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/repeatedColumnNumber.csv"});
    }

    @Test
    public void shouldExitWithCodeOneAndShowErrorMessageWhenAnyNumberFromOneToNineIsMissingOrIsRepeatedInAnyInnerBoard() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(ErrorMessages.INVALID_SOLUTION + "\n", systemOutRule.getLogWithNormalizedLineSeparator());
            }
        });

        App.main(new String[] {"src/test/resources/missingOrRepeatedNumberInInnerBoard.csv"});
    }

    @Test
    public void shouldExitWithCodeZeroWhenBoardIsValid() {
        exit.expectSystemExitWithStatus(0);

        App.main(new String[] {"src/test/resources/validBoard.csv"});
    }

}
