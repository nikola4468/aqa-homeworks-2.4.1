package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Order(1)
    @Test
    void shouldTransferMoneyFromFirstToSecondCards() {
        val transferAmount = 1000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCode(getAuthInfo()));
        val expectedBalance1card = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        val expectedBalance2card = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getSecondCard()), dashboardBefore.getCardBalance(getFirstCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getSecondCard())
                .transfer(transferAmount, getFirstCard());
        val actualBalance1card = dashboardAfter.getCardBalance(getFirstCard());
        val actualBalance2card = dashboardAfter.getCardBalance(getSecondCard());
        assertEquals(expectedBalance1card, actualBalance1card);
        assertEquals(expectedBalance2card, actualBalance2card);
    }

    @Order(2)
    @Test
    void shouldTransferMoneyFromSecondToFirstCards() {
        val transferAmount = 1000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCode(getAuthInfo()));
        val expectedBalance1card = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val expectedBalance2card = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transfer(transferAmount, getSecondCard());
        val actualBalance1card = dashboardAfter.getCardBalance(getSecondCard());
        val actualBalance2card = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(expectedBalance1card, actualBalance1card);
        assertEquals(expectedBalance2card, actualBalance2card);
    }

    @Order(3)
    @Test
    void shouldTransferMoneyBadCard() {
        val transferAmount = 1000;
        val loginPage = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCode(getAuthInfo()))
                .transferTo(getFirstCard());
        loginPage.badCard(transferAmount, getBadCard());
    }

    @Order(4)
    @Test
    void shouldTransferMoneyFromSecondToFirstCardsUnderLimit() {
        val transferAmount = 20000;
        val dashboardBefore = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCode(getAuthInfo()));
        val expectedBalance1card = DataHelper.decreaseBalance(dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val expectedBalance2card = DataHelper.increaseBalance(dashboardBefore.getCardBalance(getFirstCard()), dashboardBefore.getCardBalance(getSecondCard()), transferAmount);
        val dashboardAfter = dashboardBefore
                .transferTo(getFirstCard())
                .transfer(transferAmount, getSecondCard());
        val actualBalance1card = dashboardAfter.getCardBalance(getSecondCard());
        val actualBalance2card = dashboardAfter.getCardBalance(getFirstCard());
        assertEquals(expectedBalance1card, actualBalance1card);
        assertEquals(expectedBalance2card, actualBalance2card);
    }

    @Order(5)
    @Test
    void shouldNotLoginAndPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.notLogin();
    }

    @Order(6)
    @Test
    void shouldNotLoginWithOtherUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin(getBadAuthInfo());
    }

    @Test
    @Order(7)
    public void ShouldNotVerificationCode() {
        val loginPage = new LoginPage();
        loginPage.validLogin(getAuthInfo());
        val verificationPage = new VerificationPage();
        verificationPage.notVerificationCode();
    }

    @Test
    @Order(8)
    public void ShouldBadVerificationCode() {
        val loginPage = new LoginPage();
        loginPage.validLogin(getAuthInfo());
        val verificationPage = new VerificationPage();
        verificationPage.badVerificationCode(getBadVerificationCode(getAuthInfo()));
    }

    @Test
    @Order(9)
    public void ShouldBlocksUserIfVerificationCodeWasWrongThreeTimes() {
        val loginPage = new LoginPage();
        loginPage.validLogin(getAuthInfo());
        val verificationPage = new VerificationPage();
        verificationPage.invalidVerify(getBadVerificationCode(getAuthInfo()));
        verificationPage.invalidVerify(getBadVerificationCode(getAuthInfo()));
        verificationPage.invalidVerify(getBadVerificationCode(getAuthInfo()));
        verificationPage.invalidVerify(getVerificationCode(getAuthInfo()));
        verificationPage.manyAttempts();
    }
}