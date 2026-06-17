package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pagesPlaywright.LoginPage;
import pagesPlaywright.MessagesRoomPage;
import pagesPlaywright.RoomPage;

import static constants.Constants.*;

public class MatrixTests {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private LoginPage loginPage;
    private RoomPage roomPage;
    private MessagesRoomPage messagesRoomPage;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        context = browser.newContext();
        page = context.newPage();

        loginPage = new LoginPage(page);
        roomPage = new RoomPage(page);
        messagesRoomPage = new MessagesRoomPage(page);
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    @Description("Комплексный тест: Авторизация -> Открытие комнаты -> Отправка  сообщения с проверками")
    void testE2E() {
        loginPage.open();

        loginPage.login(TEST_EMAIL, TEST_PASSWORD);

        roomPage.urlRoomPage();

        roomPage.openExistingRoom(ROOM_NAME);

        messagesRoomPage.sendMessage(TEST_MESSAGE);

        messagesRoomPage.assertLastMessageContains(TEST_MESSAGE);

        messagesRoomPage.assertMessageDisplayed(TEST_MESSAGE);

        messagesRoomPage.assertMessageIsLast(TEST_MESSAGE);
    }
}