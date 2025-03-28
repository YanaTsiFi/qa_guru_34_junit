package guru.qa;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PracticeFormTest extends TestBase {

    // 1. Тест с CSV файлом
    @ParameterizedTest(name = "CSV: Заполнение формы для {0} {1}")
    @CsvFileSource(resources = "/user_data.csv", numLinesToSkip = 1)
    void fillFormWithCsvData(String firstName, String lastName, String email,
                             String phone, String address) {
        fillAndCheckForm(firstName, lastName, email, phone, address);
    }

    // 2. Тест с MethodSource
    @ParameterizedTest(name = "Method: Комбинация {0}")
    @MethodSource("userCombinationsProvider")
    void fillFormWithDifferentCombinations(String fullName, String contact, String location) {
        String[] nameParts = fullName.split(" ");
        String[] contactParts = contact.split(",");
        fillAndCheckForm(
                nameParts[0],
                nameParts.length > 1 ? nameParts[1] : "",
                contactParts[1],
                contactParts[0],
                location
        );
    }

    // 3. Тест с ValueSource
    @ParameterizedTest(name = "Value: Проверка поля с телефоном {0}")
    @ValueSource(strings = {"1234567890", "0987654321", "5551234567"})
    void phoneFieldShouldAcceptDifferentValues(String phone) {
        open("/automation-practice-form");
        $("#userNumber").setValue(phone).shouldHave(value(phone));
    }

    // Общий метод для заполнения и проверки формы
    private void fillAndCheckForm(String firstName, String lastName, String email,
                                  String phone, String address) {
        open("/automation-practice-form");

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#userNumber").setValue(phone);
        $("#currentAddress").setValue(address);

        $("#firstName").shouldHave(value(firstName));
        $("#lastName").shouldHave(value(lastName));
        $("#userEmail").shouldHave(value(email));
        $("#userNumber").shouldHave(value(phone));
        $("#currentAddress").shouldHave(value(address));
    }

    static Stream<Arguments> userCombinationsProvider() {
        return Stream.of(
                arguments("Yana TS", "1234567890,yanats@test.com", "Street 32"),
                arguments("Yana Tes", "0987654321,yana@example.com", "Street 33")
        );
    }
}