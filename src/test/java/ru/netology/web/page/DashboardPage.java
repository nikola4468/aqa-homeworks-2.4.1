package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    //    private static final SelenideElement buttonReload = $("[data-test-id=\"action-reload\"]");
    private static final ElementsCollection cards = $$(".list__item");

    public DashboardPage() {
        SelenideElement heading = $(withText("Ваши карты"));
        heading.shouldBe(visible);
    }

    public TransferPage transferTo(DataHelper.CardInfo cardInfo) {
        $("[data-test-id=\"" + cardInfo.getId() + "\"]" + " button").click();
        return new TransferPage();
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String textWithId = $("[data-test-id=\"" + cardInfo.getId() + "\"]").getText();
        String text = cards.find(text(textWithId)).getText();
        val start = text.indexOf(", баланс: ");
        val finish = text.indexOf(" р.");
        val value = text.substring(start + ", баланс: ".length(), finish);
        return Integer.parseInt(value);
    }
}