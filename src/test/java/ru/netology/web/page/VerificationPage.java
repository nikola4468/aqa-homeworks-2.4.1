package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    private final SelenideElement inputCodeError = $("[data-test-id=code] .input__sub");


    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode badVerificationCode) {
        codeField.doubleClick().setValue(badVerificationCode.getCode());
        verifyButton.click();
    }

    public void notVerificationCode() {
        verifyButton.click();
        inputCodeError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void badVerificationCode(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void manyAttempts() {
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Превышено количество попыток ввода кода!"));
    }
}
