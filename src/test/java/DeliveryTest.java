import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//*[@data-test-id=\"city\"]//self::input").setValue(validUser.getCity());
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.BACK_SPACE, firstMeetingDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue(validUser.getName());
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue(validUser.getPhone());
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//span[text()='Запланировать']").click();
        $x("//*[@data-test-id=\"success-notification\"]").shouldBe(appear).shouldHave(text("Успешно!"), Duration.ofMillis(5000));
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.BACK_SPACE, secondMeetingDate);
        $x("//span[text()='Запланировать']").click();
        $x("//*[@data-test-id=\"replan-notification\"]").shouldBe(appear).shouldHave(text("Необходимо подтверждение"), Duration.ofMillis(5000));
        $x("//span[text()='Перепланировать']").click();
        $x("//*[@data-test-id=\"success-notification\"]").shouldBe(appear).shouldHave(text("Успешно!"), Duration.ofMillis(5000));
    }
}


