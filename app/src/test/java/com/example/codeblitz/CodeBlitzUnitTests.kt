package com.example.codeblitz

import androidx.compose.ui.text.input.TextFieldValue
import com.example.codeblitz.domain.AddTaskViewModel
import com.example.codeblitz.domain.LoginViewModel
import com.example.codeblitz.domain.RegisterViewModel
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.model.LoginData
import com.example.codeblitz.view.MainActivity.components.countAndFormatNewLines
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Юнит-тесты
 */
class CodeBlitzUnitTests {
    /**
     * Проверка обновления данных регистрации
     */
    @Test
    fun testUpdateRegisterData() {
        val viewModel = RegisterViewModel()
        assert(viewModel.registerData.password.isEmpty())
        assert(viewModel.registerData.login.isEmpty())

        viewModel.updateRegisterData(
            newRegisterData = LoginData("123", "1234")
        )

        assertEquals("1234", viewModel.registerData.password)
        assertEquals("123", viewModel.registerData.login)
    }

    /**
     * Проверка доступности кнопки входа при вводе данных в поля, отвечающие за логин и пароль
     */
    @Test
    fun testIsLoginButtonActive() {
        val viewModel = LoginViewModel()
        assert(!viewModel.isButtonEnabled)

        viewModel.updateLoginData(
            newLoginData = LoginData("123", "1234")
        )

        assert(viewModel.isButtonEnabled)
    }

    /**
     * Проверка потока навигации на страницу регистрации
     */
    @Test
    fun testToRegisterNavigationFlow() = runTest {
        val viewModel = LoginViewModel()
        viewModel.navigateToRegister()

        assertEquals(Routes.Register, viewModel.navigationStateFlow.value)
    }

    /**
     * Проверка функции подсчета строк
     */
    @Test
    fun testCountLines() {
        val string = """
            # Example usage:
            numbers = list(range(1, 101)) #Test with a small list
            start_time = time.time()
            result = incredibly_inefficient_sum(numbers) # This will break with a longer list due to recursion depth
            end_time = time.time()
            print(f"Sum: {result}")
            print(f"Time taken: {end_time - start_time:.4f} seconds")



            numbers = list(range(1,10)) #Test with a small list that will not cause stack overflow
            start_time = time.time()
            result = incredibly_inefficient_sum(numbers)
            end_time = time.time()
            print(f"Sum: {result}")
            print(f"Time taken: {end_time - start_time:.4f} seconds")
        """.trimIndent()

        val expected = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16"
        val result = countAndFormatNewLines(TextFieldValue(string))

        assertEquals(expected, result)
    }

    /**
     * Проверка записи описания задачи в поле
     */
    @Test
    fun testTaskDesc() {
        val viewModel = AddTaskViewModel()

        val expected = """
            the most beautiful description
        """.trimIndent()

        viewModel.setTaskDesc(expected)

        assertEquals(expected, viewModel.taskDesk)
    }
}