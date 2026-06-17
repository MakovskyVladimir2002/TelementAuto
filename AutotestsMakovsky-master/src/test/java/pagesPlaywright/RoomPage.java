package pagesPlaywright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class RoomPage {
    private final Page page;

    private final Locator roomTile;

    private final Locator messageComposer;
    private final Locator lastMessageBody;
    private final Locator roomHeader;

    public RoomPage(Page page) {
        this.page = page;

        this.roomTile = page.getByTestId("room-tile");
        this.messageComposer = page.getByTestId("message-composer");
        this.lastMessageBody = page.getByTestId("message-body").last();
        this.roomHeader = page.getByTestId("room-header");
    }
    @Step("Открытие существующей комнаты: Тестовая комната Маковского")
    public void openExistingRoom(String roomName) {

        Locator targetRoom = roomTile.filter(new Locator.FilterOptions().setHasText("Тестовая комната Маковского"));

        targetRoom.waitFor(new Locator.WaitForOptions().setTimeout(15000));

        assertThat(targetRoom).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(15000));
        targetRoom.click();

        assertThat(roomHeader).containsText("Тестовая комната Маковского");

        assertThat(messageComposer).isVisible();
    }

    @Step("Проверка URL после авторизации")
    public boolean urlRoomPage() {

        page.waitForURL("**/dashboard/**", new Page.WaitForURLOptions().setTimeout(10000)); // Ожидает URL, содержащий '/dashboard/'
        return page.url().equals("https://hostkey.ru/dashboard/");
    }
    @Step("Проверка URL после открытия комнаты")
    public boolean urlRoomPageOpened() {

        page.waitForURL("**/dashboard/room/**", new Page.WaitForURLOptions().setTimeout(10000));
        return page.url().contains("/dashboard/room/");
    }
}
