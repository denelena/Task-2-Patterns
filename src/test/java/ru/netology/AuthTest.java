package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.LoginInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.TestDataGenerator.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        LoginInfo registeredUser = getRegisteredUser("active");

        fillAndSendLoginForm(registeredUser.getLogin(), registeredUser.getPassword());

        $(".heading").shouldBe(visible, Duration.ofSeconds(5));
        $(".heading").shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(5));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        LoginInfo notRegisteredUser = getUser("active");

        fillAndSendLoginForm(notRegisteredUser.getLogin(), notRegisteredUser.getPassword());

        $("[data-test-id='error-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        LoginInfo blockedUser = getRegisteredUser("blocked");

        fillAndSendLoginForm(blockedUser.getLogin(), blockedUser.getPassword());

        $("[data-test-id='error-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        LoginInfo registeredUser = getRegisteredUser("active");
        String wrongLogin = getRandomLogin();

        fillAndSendLoginForm(wrongLogin, registeredUser.getPassword());

        $("[data-test-id='error-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        LoginInfo registeredUser = getRegisteredUser("active");
        String wrongPassword = getRandomPassword();

        fillAndSendLoginForm(registeredUser.getLogin(), wrongPassword);

        $("[data-test-id='error-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(5));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

    private void fillAndSendLoginForm(String login, String pwd) {

        $("[data-test-id='login'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='login'] input").setValue(login);

        $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='password'] input").setValue(pwd);

        $$("[data-test-id='action-login'] .button__text").find(exactText("Продолжить")).click();
    }
}
