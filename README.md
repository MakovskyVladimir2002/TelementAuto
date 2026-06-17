# Тестовое Telement

Проект представляет собой автоматизированные UI-тесты с использованием Playwright для Java, описывающие сценарий авторизации пользователя и последующего обмена сообщениями в чате.

## Стек технологий

*   **Java**: Язык программирования.
*   **Playwright**: Инструмент для автоматизации браузера.
*   **JUnit 5**: Фреймворк для написания и запуска тестов.
*   **Allure**: Фреймворк для генерации отчетов о результатах тестирования.
*   **Page Object Model (POM)**: Паттерн проектирования для организации кода тестов.

## Структура проекта

*   `pagesPlaywright/`: Пакет, содержащий классы Page Object для различных страниц.
    *   `LoginPage.java`: Объект страницы логина (`https://hostkey.ru/login/`).
    *   `RoomPage.java`: Объект страницы комнаты чата.
    *   `MessagesRoomPage.java`: Объект страницы с сообщениями в комнате.
*   `tests/`: Пакет с тестовыми классами.
    *   `MatrixTests.java`: Класс, содержащий основной тестовый сценарий.
*   `constants/`: Пакет с константами.
    *   `Constants.java`: Файл, содержащий константы (например, учетные данные для теста).

## Тестовый сценарий

**Название теста:** `testLoginOpenRoomAndSendMessage`

**Описание:** Проверяет полный цикл взаимодействия: успешная авторизация пользователя, открытие конкретной комнаты чата, отправка сообщения и проверка его отображения.

**Шаги:**

1.  Открывается страница логина (`LoginPage.open()`).
2.  Выполняется попытка входа с помощью предоставленных учетных данных (`LoginPage.login()`).
3.  Проверяется, что после логина URL изменился на `https://hostkey.ru/dashboard/` (`RoomPage.verifySuccessfulLoginUrl()`).
4.  Открывается существующая комната "Тестовая комната Маковский" (`RoomPage.openExistingRoom()`).
5.  Проверяется, что URL изменился на соответствующий URL комнаты (`RoomPage.urlRoomPageOpened()`).
6.  На странице сообщений (`MessagesRoomPage`) отправляется текстовое сообщение (`MessagesRoomPage.sendMessage()`).
7.  Проверяется, что отправленное сообщение отображается в ленте (`MessagesRoomPage.assertMessageDisplayed()`).
8.  Проверяется, что отправленное сообщение является последним в ленте (`MessagesRoomPage.assertMessageIsLast()`).
9.  Проверяется, что поле ввода сообщения снова пустое после отправки.

## Запуск тестов

1.  **Установка зависимостей:** Убедитесь, что все зависимости, указанные в файле сборки проекта (Gradle `build.gradle` или Maven `pom.xml`), установлены. Необходимые зависимости включают `com.microsoft.playwright:playwright`, `org.junit.jupiter:junit-jupiter`, `io.qameta.allure:allure-junit5`.
2.  **Настройка учетных данных:** Создайте файл `constants/Constants.java` и добавьте в него константы для `TEST_EMAIL` и `TEST_PASSWORD`, используемых в тестах.
    ```java
    package constants;

    public class Constants {
        public static final String TEST_EMAIL = "your_test_email@example.com";
        public static final String TEST_PASSWORD = "your_test_password";
    }
    ```
3.  **Установка браузеров Playwright:** Перед первым запуском выполните команду:
    ```bash
    mvn exec:java -e -D exec.mainClass="com.microsoft.playwright.CLI" -D exec.args="install"
    # или если используется Gradle
    gradle playwright install
    ```
    Это установит необходимые браузеры (Chromium, Firefox, WebKit), с которыми будет работать Playwright.
4.  **Запуск через IDE:** Откройте класс `MatrixTests` в вашей IDE (например, IntelliJ IDEA) и нажмите зелёную стрелку рядом с тестовым методом `testLoginOpenRoomAndSendMessage()` или классом, чтобы запустить его.
5.  **Запуск через командную строку:**
    *   **Maven:**
      ```bash
      mvn clean test -Dtest=MatrixTests#testLoginOpenRoomAndSendMessage
      ```
    *   **Gradle:**
      ```bash
      gradle test --tests MatrixTests.testLoginOpenRoomAndSendMessage
      ```

## Отчеты Allure

После выполнения тестов, если настроено подключение Allure, вы можете сгенерировать HTML-отчет:

1.  Убедитесь, что Allure CLI установлен локально.
2.  Сформируйте отчет:
    ```bash
    allure serve allure-results
    ```
    Эта команда сгенерирует и откроет интерактивный HTML-отчет в браузере, показывающий детали выполнения тестов, включая шаги, аннотированные `@Step`.

## Фактическое потраченное время - около 2 часов    
