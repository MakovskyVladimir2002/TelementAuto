package pagesPlaywright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MessagesRoomPage {

    private final Page page;
    private final Locator messageComposer;
    private final Locator messageList;
    private final Locator messageItem;
    private final Locator messageBody;

    public MessagesRoomPage(Page page) {
        this.page = page;
        this.messageComposer = page.getByTestId("message-composer");

        this.messageList = page.getByTestId("message-list");

        this.messageItem = page.getByTestId("message-item");

        this.messageBody = page.getByTestId("message-body");
    }

    @Step("Проверка URL на странице сообщений")
    public boolean verifyMessagesPageUrl() {

        page.waitForURL("**/dashboard/room/**", new Page.WaitForURLOptions().setTimeout(10000));

        return page.url().contains("/dashboard/room/");
    }

    @Step("Отправка текстового сообщения")
    public void sendMessage(String text) {
        assertThat(messageComposer).isVisible();
        assertThat(messageComposer).hasText("");

        messageComposer.fill(text);

        messageComposer.press("Enter");

        assertThat(messageComposer).hasText("");
    }

    @Step("Проверка, что последнее сообщение содержит текст")
    public void assertLastMessageContains(String text) {
        Locator lastMessage = messageBody.last();

        assertThat(lastMessage).isVisible(new LocatorAssertions.IsVisibleOptions()
                .setTimeout(10000));

        assertThat(lastMessage).containsText(text);
    }

    @Step("Проверка, что сообщение отображается в ленте")
    public void assertMessageDisplayed(String text) {
        Locator targetMessage = messageBody.filter(new Locator.FilterOptions()
                .setHasText(text));

        assertThat(targetMessage).isVisible(new LocatorAssertions.IsVisibleOptions()
                .setTimeout(10000));

        targetMessage.scrollIntoViewIfNeeded();

        assertThat(targetMessage).isVisible();
    }

    @Step("Получение количества сообщений в ленте")
    public int getMessageCount() {
        assertThat(messageList).isVisible(new LocatorAssertions.IsVisibleOptions()
                .setTimeout(5000));

        return messageItem.count();
    }

    @Step("Проверка, что сообщение является последним в ленте")
    public void assertMessageIsLast(String text) {
        int totalMessages = getMessageCount();
        Locator lastMessage = messageBody.nth(totalMessages - 1);

        assertThat(lastMessage).containsText(text);
    }

}
