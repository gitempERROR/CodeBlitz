package com.example.codeblitz

import android.os.SystemClock.sleep
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.codeblitz.view.MainActivity.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI-тесты
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CodeBlitzInstrumentalTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    /**
     * Проверка перехода на страницу регистрации
     */
    @Test
    fun testNavigationToRegister() {
        composeTestRule.onNodeWithTag("Login").assertIsDisplayed()

        composeTestRule.onNodeWithTag("To Register Button").performClick()

        composeTestRule.onNodeWithTag("Register").assertIsDisplayed()
    }

    /**
     * Проверка отображения сообщений на странице регистрации и доступности кнопки
     */
    @Test
    fun testRegisterWarning() {
        composeTestRule.onNodeWithTag("To Register Button").performClick()

        composeTestRule.onNodeWithTag("Wrong Email").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Weak Password").assertIsDisplayed()

        composeTestRule.onNodeWithTag("Login Register").performClick()
        composeTestRule.onNodeWithTag("Login Register").performTextInput("errorfadeev@yandex.ru")
        composeTestRule.onNodeWithTag("Wrong Email").assertIsNotDisplayed()

        composeTestRule.onNodeWithTag("Password Register").performClick()
        composeTestRule.onNodeWithTag("Password Register").performTextInput("1234567890")
        composeTestRule.onNodeWithTag("Weak Password").assertIsNotDisplayed()

        composeTestRule.onNodeWithTag("Register Button").assertIsNotEnabled()

        composeTestRule.onNodeWithTag("Repeat Password").performClick()
        composeTestRule.onNodeWithTag("Repeat Password").performTextInput("1234567890")

        composeTestRule.onNodeWithTag("Register Button").assertIsEnabled()
    }

    /**
     * Проверка ввода в поля логина и пароля и последующей авторизации
     */
    @Test
    fun testLoginTextFieldsAndLoginIn() {
        composeTestRule.onNodeWithTag("Login Login").performClick()
        composeTestRule.onNodeWithTag("Login Login").performTextInput("errorfadeev@yandex.ru")

        composeTestRule.onNodeWithTag("Password Login").performClick()
        composeTestRule.onNodeWithTag("Password Login").performTextInput("123")

        composeTestRule.onNodeWithTag("Login Button").performClick()
        sleep(10000)
        composeTestRule.onNodeWithTag("Main").assertIsDisplayed()
    }

    /**
     * Проверка отображения плиток заданий у администратора
     */
    @Test
    fun testAdminMainScreenTasksDisplay() {
        composeTestRule.onNodeWithTag("Login Login").performClick()
        composeTestRule.onNodeWithTag("Login Login").performTextInput("errorfadeev@yandex.ru")
        composeTestRule.onNodeWithTag("Password Login").performClick()
        composeTestRule.onNodeWithTag("Password Login").performTextInput("123")
        composeTestRule.onNodeWithTag("Login Button").performClick()
        sleep(10000)
        sleep(10000)
        composeTestRule.onAllNodesWithTag("TaskElement").assertCountEquals(2)
    }

    /**
     * Проверка перехода на страницу добавления задания при нажатии на элемент LazyColumn
     */
    @Test
    fun testLazyColumnOnItemClickNavigation() {
        composeTestRule.onNodeWithTag("Login Login").performClick()
        composeTestRule.onNodeWithTag("Login Login").performTextInput("errorfadeev@yandex.ru")
        composeTestRule.onNodeWithTag("Password Login").performClick()
        composeTestRule.onNodeWithTag("Password Login").performTextInput("123")
        composeTestRule.onNodeWithTag("Login Button").performClick()
        sleep(10000)
        sleep(10000)
        composeTestRule.onNodeWithTag("TaskElementID - 1").performClick()
        composeTestRule.onNodeWithTag("AddTask").assertIsDisplayed()
    }
}