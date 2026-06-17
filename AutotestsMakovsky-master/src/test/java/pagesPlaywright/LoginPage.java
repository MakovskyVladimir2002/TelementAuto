package pagesPlaywright;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;


public class LoginPage {
    private final Page page;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator submitButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.getByPlaceholder("Ваш email-адрес");
        this.passwordInput = page.getByPlaceholder("Пароль");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"));
    }
    @Step("Страница логирования")
    public void open() {
        page.navigate("https://hostkey.ru/login/");
        emailInput.waitFor();
        passwordInput.waitFor();
        submitButton.waitFor();
    }
    @Step("Авторизация с email")
    public void login(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        submitButton.click();
    }

}