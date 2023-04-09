package com.example.sudokuvocabulary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MainMenuTest {
    private UiDevice device;
    private Context context;
    private static final String APP_PACKAGE = "com.example.sudokuvocabulary";
    private static final int LAUNCH_TIMEOUT = 5000;

    /**
     * Initializes the UiDevice and launches the main menu
     * of the app
     */
    @Before
    public void setUp() {
        // Initialize the test device
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Launch the sudoku app main menu
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Clear out any previous instances
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Example test of comparing app context
     */
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.sudokuvocabulary", appContext.getPackageName());
    }

    /**
     * Tests whether the app can be launched from the device's
     * home menu
     */
    @Test
    public void startAppFromHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start test from home screen
        device.pressHome();

        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        this.context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    /**
     * Tests the app's ability to launch a 4x4 sudoku game from
     * the main menu through the 'Play' button.
     */
    @Test
    public void launchAndExitNormalGame4x4() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the normal mode button
            assertTrue(clickButton(new UiSelector().textContains("normal")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 4x4 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("4x4")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Tests the app's ability to launch a 4x4 sudoku game from
     * the main menu through the 'Play' button.
     */
    @Test
    public void launchAndExitNormalGame6x6() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the normal mode button
            assertTrue(clickButton(new UiSelector().textContains("normal")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("6x6")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Tests the app's ability to launch a 9x9 sudoku game from
     * the main menu through the 'Play' button.
     */
    @Test
    public void launchAndExitNormalGame9x9() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the normal mode button
            assertTrue(clickButton(new UiSelector().textContains("normal")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("9x9")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Tests the app's ability to launch a 12x12 sudoku game from
     * the main menu through the 'Play' button.
     */
    @Test
    public void launchAndExitNormalGame12x12() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the normal mode button
            assertTrue(clickButton(new UiSelector().textContains("normal")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("12x12")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Tests the app's ability to launch a 4x4 sudoku game from
     * the main menu in listening comprehension mode through the 'Play' button.
     */
    @Test
    public void launchAndExitListeningGame4x4() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the listening mode button
            assertTrue(clickButton(new UiSelector().textContains("listen")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("4x4")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try pressing the grid
            UiObject cellNumber = device.findObject(new UiSelector().resourceId(formatId("sudokuGridView")));
            assertTrue(cellNumber.exists() && cellNumber.click());

            // Confirm that the input card has shown up
            UiObject card = device.findObject(new UiSelector().textContains(context.getString(R.string.game_popup_question_text)));
            assertTrue(card.exists());

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Tests the app's ability to launch a 6x6 sudoku game from
     * the main menu in listening comprehension mode through the 'Play' button.
     */
    @Test
    public void launchAndExitListeningGame6x6() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the listening mode button
            assertTrue(clickButton(new UiSelector().textContains("listen")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("6x6")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try pressing the grid
            UiObject cellNumber = device.findObject(new UiSelector().resourceId(formatId("sudokuGridView")));
            assertTrue(cellNumber.exists() && cellNumber.click());

            // Confirm that the input card has shown up
            UiObject card = device.findObject(new UiSelector().textContains(context.getString(R.string.game_popup_question_text)));
            assertTrue(card.exists());

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }
    /**
     * Tests the app's ability to launch a 9x9 sudoku game from
     * the main menu in listening comprehension mode through the 'Play' button.
     */
    @Test
    public void launchAndExitListeningGame9x9() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the listening mode button
            assertTrue(clickButton(new UiSelector().textContains("listening")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("9x9")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            testDarkModeToggle();

            // Try pressing the grid
            UiObject cellNumber = device.findObject(new UiSelector().resourceId(formatId("sudokuGridView")));
            assertTrue(cellNumber.exists() && cellNumber.click());

            // Confirm that the input card has shown up
            UiObject card = device.findObject(new UiSelector().textContains(context.getString(R.string.game_popup_question_text)));
            assertTrue(card.exists());

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }
    /**
     * Tests the app's ability to launch a 12x12 sudoku game from
     * the main menu in listening comprehension mode through the 'Play' button.
     */
    @Test
    public void launchAndExitListeningGame12x12() {
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
            ));

            // If prompted with the confirmation dialog, press confirm
            UiObject dialogConfirmButton = device.findObject(new UiSelector().textContains("confirm"));
            if (dialogConfirmButton != null) {
                assertTrue(dialogConfirmButton.click());
            }

            testDarkModeToggle();

            // Try to click the listening mode button
            assertTrue(clickButton(new UiSelector().textContains("listen")));

            // Check that the SetSudokuSize activity has been opened
            assertTrue(doesTextViewExist(new UiSelector()
                    .text("Select Size")
            ));

            testDarkModeToggle();

            // Try to select the 12x12 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("12x12")
            ));

            // Check that the SudokuView is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

            // Try pressing the grid
            UiObject cellNumber = device.findObject(new UiSelector().resourceId(formatId("sudokuGridView")));
            assertTrue(cellNumber.exists() && cellNumber.click());

            // Confirm that the input card has shown up
            UiObject card = device.findObject(new UiSelector().textContains(context.getString(R.string.game_popup_question_text)));
            assertTrue(card.exists());

            // Try to exit the game using the back arrow in the toolbar
            UiObject backArrow = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backArrow.exists() && backArrow.click());

            // Click the exit button on the confirmation dialog
            UiObject exitButton = device.findObject(new UiSelector().textContains("exit"));
            assertTrue(exitButton.exists() && exitButton.click());

            // Check that the app has returned to the main menu
            UiObject mainMenu = device.findObject(new UiSelector().textContains(context.getString(R.string.app_name)));
            assertTrue(mainMenu.exists());

        } catch (Exception e) { // Somethings has gone very wrong
            handleException(e);
        }
    }

    /**
     * Test the app's ability to launch a game from the word list
     * menu by selecting the default 'Animals' list.
     */
    @Test
    public void selectListFromWordBank() {
        // Try navigating app, handle exceptions that
        // occur when specific UI elements are not found
        try {
            // Try to find and click the word bank button
            assertTrue(clickButton(new UiSelector()
                    .textContains("Word Bank")
            ));

            // Check that the application has entered the word list activity
            assertTrue(doesTextViewExist(new UiSelector()
                    .textContains("word list")
            ));

            testDarkModeToggle();

            // Try to click the default 'ANIMALS' list button
            assertTrue(clickButton(new UiSelector()
                    .textContains("animals")
            ));

            // Check that the sudoku game has launched
            // and that the board is visible and working
            assertTrue(sudokuBoardIsActive(new UiSelector()));

        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Test the app's  ability to create new word lists by
     * launching the menu for adding words, inputting a new
     * table name, and selecting nine words.
     */
    @Test public void testAddWordsActivity() {
        String listName = "foods"; // Name of new word list
        String category = "food"; // Word category to access

        try {
            // Try to click word bank button in main menu
            assertTrue(clickButton(new UiSelector().textContains("word bank")));

            // Verify that the app is in the word list menu by finding the
            // matching visible text view
            assertTrue(doesTextViewExist(new UiSelector().textContains("word lists")));

            testDarkModeToggle();

            // Try to click the 'create new word list button
            assertTrue(clickButton(new UiSelector().textContains("create new word list")));

            // Verify that we are in the word list name activity
            // by finding text input box and insert new word list name
            assertTrue(fillEditText(new UiSelector()
                    .resourceId(formatId("table_name_input")),
                    listName
            ));

            // Hide the keyboard if it pops up as it hides the confirmation button
            if (isKeyboardOpened()) device.pressBack();

            // Try to press the confirmation button
            assertTrue(clickButton(new UiSelector().textContains("confirm")));

            // Check that app is in word category selection menu
            assertTrue(doesTextViewExist(new UiSelector().textContains("categories")));

            testDarkModeToggle();

            // Try to click the specified word category button
            assertTrue(clickButton(new UiSelector()
                    .textContains(category)
            ));


            // Verify that the app is in the activity for selecting words
            // by checking if the TextView with the category name exists
            assertTrue(doesTextViewExist(new UiSelector().textContains(category)));

            testDarkModeToggle();

            // Find the table layout holding the word buttons
            UiObject wordButtons = device.findObject(new UiSelector()
                    .className("android.widget.TableLayout")
            );

            // Select the first nine word buttons
            int button = 0;
            while (button < 9) {
                UiObject row = wordButtons.getChild(new UiSelector()
                        .className("android.widget.TableRow")
                        .instance((button/3))
                );

                for (int column = 0; column < row.getChildCount(); column++) {
                    // Try to select a word in a toggle button
                    UiObject wordButton = row.getChild(new UiSelector()
                            .clickable(true)
                            .checkable(true)
                            .checked(false)
                            .className("android.widget.ToggleButton")
                    );
                    wordButton.click();
                    assertTrue(wordButton.exists());
                    button++;
                }
            }

            // Check that the display list button works
            UiObject displayButton = device.findObject(new UiSelector().textContains("display"));
            assertTrue(displayButton.exists() && displayButton.click());

            testDarkModeToggle();

            // Check that the back button works
            assertTrue(clickButton(new UiSelector().textContains("back")));

        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Tests the app's menus in landscape by calling all other tests
     * in landscape mode.
     */
    @Test
    public void rotatedOrientationTest() {
        try {
            device.setOrientationRight(); // Rotate the device right

            // Run all test cases in this orientation
            setUp();
            launchAndExitNormalGame4x4();
            setUp();
            launchAndExitNormalGame6x6();
            setUp();
            launchAndExitNormalGame9x9();
            setUp();
            launchAndExitNormalGame12x12();
            setUp();
            launchAndExitListeningGame4x4();
            setUp();
            launchAndExitListeningGame6x6();
            setUp();
            launchAndExitListeningGame9x9();
            setUp();
            launchAndExitListeningGame12x12();
            setUp();
            selectListFromWordBank();
            setUp();
            testAddWordsActivity();

            device.setOrientationNatural(); // Reset device to original position

        } catch (Exception e) { // Device has failed to rotate
            handleException(e);
        }
    }

    /**
     * Checks that the dark mode button works
     */
    @Test
    public void testDarkModeToggle() {
        try {
            // Try to click the dark mode switch in main menu
            assertTrue(toggleSwitch(new UiSelector().resourceId(formatId("action_darkSwitch"))));
            isDarkModeCorrect();

            // Toggle the switch again
            assertTrue(toggleSwitch(new UiSelector().resourceId(formatId("action_darkSwitch"))));
            isDarkModeCorrect();

        } catch (Exception e) {
            handleException(e);
        }
    }

    @Test
    public void testHelpButton() {
        try {
            // Test the help button in the toolbar
            UiObject helpButton = device.findObject(new UiSelector().resourceId(formatId("action_tutorialBtn")));
            assertTrue(helpButton.exists() && helpButton.click());

            // Confirm that the help page has been opened
            UiObject helpPage = device.findObject(new UiSelector().textContains("Help"));
            assertTrue(helpPage.exists());

            // Confirm that the help button has been hidden
            helpButton = device.findObject(new UiSelector().resourceId(formatId("action_tutorialBtn")));
            assertFalse(helpButton.exists() && helpButton.click());

            // Try to go back using the toolbar back arrow
            UiObject backButton = device.findObject(new UiSelector().descriptionContains("Navigate Up"));
            assertTrue(backButton.exists());
            backButton.click();

        } catch (Exception e) {
            handleException(e);
        }
    }
    
    /**
     * Checks that the listening comprehension mode can be opened from
     * the mode select menu activity.
     */
    @Test
    public void testLoadButton() {
        try {
            // Click the load button if it is enabled
            UiObject loadButton = device.findObject(new UiSelector().textContains("load"));
            assertTrue(loadButton.exists());
            if (loadButton.exists() && loadButton.isEnabled()) {
                // try to click the load button
                assertTrue(loadButton.click());

                // Check that the game has launched
                assertTrue(sudokuBoardIsActive(new UiSelector()));
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Tries to click the button specified by the given UiSelector.
     *
     * @param selector UiSelector with parameters of the button to find.
     * @return True if the button exists and is enabled, false otherwise.
     * @throws UiObjectNotFoundException If the specified button could not be found.
     */
    private boolean clickButton(UiSelector selector) throws UiObjectNotFoundException {
        UiObject button = device.findObject(selector.className("android.widget.Button"));
        boolean buttonIsValid = button.exists() && button.isEnabled();
        if (buttonIsValid) {
            button.click();
        }
        return buttonIsValid;
    }

    /**
     * Tries to toggle the switch specified by the given
     * UiSelector.
     *
     * @param selector UiSelector with parameters of the switch to find.
     * @return True if the switch exists and is enabled, false otherwise.
     * @throws UiObjectNotFoundException If the specified switch could not be found.
     */
    private boolean toggleSwitch(UiSelector selector) throws  UiObjectNotFoundException {
        UiObject switchFound = device.findObject(selector);
        if (switchFound.exists()) {
            switchFound.click();
        }
        return switchFound.exists();
    }

    /**
     * @param selector The UiSelector specifying the TextView to find.
     * @return True if the TextView can be found, false otherwise.
     */
    private boolean doesTextViewExist(UiSelector selector) {
        UiObject textView = device.findObject(selector.className("android.widget.TextView"));
        return textView.exists();
    }

    /**
     * @param selector The UiSelector specifying the EditText view to find.
     * @return The UiObject with the found EditText
     */
    private UiObject findEditText(UiSelector selector) {
        return device.findObject(selector.className("android.widget.EditText"));
    }

    /**
     * Tries to find the specified EditText view and fill it in
     * with the given text.
     *
     * @param selector The UiSelector object specifying the EditText to find.
     * @param text The text to fill the text box with.
     * @return True if the EditText view specified could be found, false otherwise.
     * @throws UiObjectNotFoundException If the specified EditText view could not be found.
     */
    private boolean fillEditText(UiSelector selector, String text) throws UiObjectNotFoundException
    {
        if (isKeyboardOpened()) device.pressBack();
        UiObject editable = findEditText(selector);
        boolean doesExist = editable.exists();
        if (doesExist) {
            editable.setText(text);
        }
        return doesExist;
    }

    /**
     * @param selector The UiSelector specifying the Sudoku board.
     * @return True if the Sudoku board exists, false otherwise.
     */
    private boolean sudokuBoardIsActive(UiSelector selector) {
        UiObject sudoku = device.findObject(selector
                .resourceId(formatId("sudokuGridView"))
        );
        return sudoku.exists();
    }

    /**
     * Method called whenever a UiObject could not be found
     * or something else goes wrong.
     *
     * @param e The exception to handle
     */
    private void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * @param id The resource id of the view.
     * @return A resource id string formatted to work with
     * UiSelector.resourceId().
     */
    private String formatId(String id) {
        return APP_PACKAGE + ":id/" + id;
    }

    /**
     * Checks if the keyboard is open, used to close the keyboard in
     * landscape mode as it may block some views necessary to execute
     * certain UI tests.
     *
     * @return True if the keyboard is currently open, false otherwise
     */
    private boolean isKeyboardOpened() {
        for (AccessibilityWindowInfo window: InstrumentationRegistry.getInstrumentation().getUiAutomation().getWindows()) {
            if(window.getType()==AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                return true;
            }
        }
        return false;
    }

    private void isDarkModeCorrect() throws UiObjectNotFoundException {
        // Get the switch's current state
        UiObject darkSwitch = device.findObject(new UiSelector()
                .resourceId(formatId("switchTemplate"))
        );
        boolean isChecked = darkSwitch.isChecked();

        // Check if mode is correct
        boolean isDark = AppCompatDelegate.getDefaultNightMode() ==
                AppCompatDelegate.MODE_NIGHT_YES;
        assertEquals(isDark, isChecked);
    }
}