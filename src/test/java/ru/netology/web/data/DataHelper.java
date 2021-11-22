package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getBadAuthInfo() {
        return new AuthInfo("badLogin", "badPassword");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getBadVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("11111");
    }

    @Value
    public static class CardInfo {
        String number;
        String id;
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static CardInfo getBadCard() {
        return new CardInfo("5559 1111 2222 0002", "");
    }

    public static int increaseBalance(int beforeBalanceCardTo, int beforeBalanceCardFrom, int transferAmount) {
        if (transferAmount > beforeBalanceCardFrom) {
            return beforeBalanceCardTo;
        }
        return beforeBalanceCardTo + transferAmount;
    }

    public static int decreaseBalance(int beforeBalance, int transferAmount) {
        if (transferAmount > beforeBalance) {
            return beforeBalance;
        }
        return beforeBalance - transferAmount;
    }
}