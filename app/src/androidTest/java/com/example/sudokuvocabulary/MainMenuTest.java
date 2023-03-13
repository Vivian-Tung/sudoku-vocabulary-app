package com.example.sudokuvocabulary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

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
    private static final String APP_PACKAGE
            = "com.example.sudokuvocabulary";
    private static final int LAUNCH_TIMEOUT = 5000;

    private void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Launch a simple calculator app
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Clear out any previous instances
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    private boolean clickButton(UiSelector selector) throws UiObjectNotFoundException {
        UiObject button = device.findObject(selector);
        boolean buttonIsValid = button.exists() && button.isEnabled();
        if (buttonIsValid) {
            button.click();
        }
        return buttonIsValid;
    }

    private String formatId(String id) {
        return APP_PACKAGE + ":id/" + id;
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.sudokuvocabulary", appContext.getPackageName());
    }

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

    @Test
    public void launchGameFromMainMenu() {
        setUp(); // Launch app and set UiDevice

        boolean gameLaunched = false;
        try {
            // Attempt to find the play button to launch the next activity
            assertTrue(clickButton(new UiSelector()
                    .resourceId(formatId("main_menu_play_button"))
                    .className("android.widget.Button")
            ));
            // Check that the SetSudokuSize activity has been opened
            UiObject selectSizeText = device.findObject(new UiSelector()
                    .text("Select Size")
                    .className("android.widget.TextView")
            );
            assertTrue(selectSizeText.exists());

            // Try to select the 9x9 size option
            assertTrue(clickButton(new UiSelector()
                    .textContains("9x9")
                    .clickable(true)
                    .className("android.widget.Button")
            ));

            // Check that the SudokuView is visible
            UiObject sudokuGrid = device.findObject(new UiSelector()
                    .resourceId(formatId("sudokuGridView"))
            );
            assertTrue(sudokuGrid.exists());
            gameLaunched = sudokuGrid.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(gameLaunched);
    }
}