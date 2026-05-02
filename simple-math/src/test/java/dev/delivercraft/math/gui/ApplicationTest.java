package dev.delivercraft.math.gui;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlNumberInput;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ApplicationTest {

    private static final String BAD_INPUT_MESSAGE = "Enter whole numbers greater than or equal to zero.";

    private static final String FIRST_ID = "firstInput";

    private static final String SECOND_ID = "secondInput";

    private static final String MESSAGE_ID = "validationMessage";

    private static final String ADDITION_ID = "addition";

    private static final String SUBTRACTION_ID = "subtraction";

    private static final String MULTIPLICATION_ID = "multiplication";

    private static final String DIVISION_ID = "division";

    private final MockMvc mockMvc;

    private final WebClient webClient;

    ApplicationTest(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.webClient = MockMvcWebClientBuilder.webAppContextSetup(context).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/index.html"})
    void indexFileIsAccessibleImplicitlyOrExplicitly(String path) throws Exception {
        this.mockMvc.perform(get(path))
                .andExpect(status().isOk());
    }

    @Test
    void defaultCalculationsAreShown() throws IOException {
        HtmlPage page = getHtmlPage();

        assertThat(numberInput(page, FIRST_ID).getDefaultValue()).isEqualTo("10");
        assertThat(numberInput(page, SECOND_ID).getDefaultValue()).isEqualTo("5");
        assertThat(text(page, MESSAGE_ID)).isEmpty();
        assertThat(text(page, ADDITION_ID)).isEqualTo("10 + 5 = 15");
        assertThat(text(page, SUBTRACTION_ID)).isEqualTo("10 - 5 = 5");
        assertThat(text(page, MULTIPLICATION_ID)).isEqualTo("10 × 5 = 50");
        assertThat(text(page, DIVISION_ID)).isEqualTo("10 ÷ 5 = 2");
    }

    @Test
    void pageMetadataAndNumberInputConstraintsAreShown() throws IOException {
        HtmlPage page = getHtmlPage();

        assertThat(page.asXml()).contains("rel=\"icon\"");
        assertThat(numberInput(page, FIRST_ID).hasAttribute("required")).isTrue();
        assertThat(numberInput(page, FIRST_ID).getAttribute("min")).isEqualTo("0");
        assertThat(numberInput(page, FIRST_ID).getAttribute("step")).isEqualTo("1");
        assertThat(numberInput(page, SECOND_ID).hasAttribute("required")).isTrue();
        assertThat(numberInput(page, SECOND_ID).getAttribute("min")).isEqualTo("0");
        assertThat(numberInput(page, SECOND_ID).getAttribute("step")).isEqualTo("1");
    }

    @Test
    void calculationsAreUpdatedOnUserInput() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = numberInput(page, FIRST_ID);
        HtmlNumberInput secondInput = numberInput(page, SECOND_ID);

        firstInput.setValueAttribute("20");
        fireInputEventFor(firstInput);
        secondInput.setValueAttribute("4");
        fireInputEventFor(secondInput);

        assertThat(text(page, ADDITION_ID)).isEqualTo("20 + 4 = 24");
        assertThat(text(page, SUBTRACTION_ID)).isEqualTo("20 - 4 = 16");
        assertThat(text(page, MULTIPLICATION_ID)).isEqualTo("20 × 4 = 80");
        assertThat(text(page, DIVISION_ID)).isEqualTo("20 ÷ 4 = 5");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "-1", "1.5"})
    void calculationsAreHiddenWhenInputIsInvalid(String invalidInput) throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = numberInput(page, FIRST_ID);

        firstInput.setValueAttribute(invalidInput);
        fireInputEventFor(firstInput);

        assertThat(text(page, MESSAGE_ID)).isEqualTo(BAD_INPUT_MESSAGE);
        assertThat(text(page, ADDITION_ID)).isEmpty();
        assertThat(text(page, SUBTRACTION_ID)).isEmpty();
        assertThat(text(page, MULTIPLICATION_ID)).isEmpty();
        assertThat(text(page, DIVISION_ID)).isEmpty();
    }

    @Test
    void validInputClearsValidationMessage() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = numberInput(page, FIRST_ID);

        firstInput.setValueAttribute("-1");
        fireInputEventFor(firstInput);
        firstInput.setValueAttribute("12");
        fireInputEventFor(firstInput);

        assertThat(text(page, MESSAGE_ID)).isEmpty();
        assertThat(text(page, ADDITION_ID)).isEqualTo("12 + 5 = 17");
    }

    @Test
    void resetToDefaultButtonShouldWork() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = numberInput(page, FIRST_ID);
        firstInput.setValueAttribute("20");
        fireInputEventFor(firstInput);
        HtmlNumberInput secondInput = numberInput(page, SECOND_ID);
        secondInput.setValueAttribute("4");
        fireInputEventFor(secondInput);

        page = page.getHtmlElementById("resetButton").click();

        assertThat(numberInput(page, FIRST_ID).getValue()).isEqualTo("10");
        assertThat(numberInput(page, SECOND_ID).getValue()).isEqualTo("5");
        assertThat(text(page, MESSAGE_ID)).isEmpty();
        assertThat(text(page, ADDITION_ID)).isEqualTo("10 + 5 = 15");
        assertThat(text(page, SUBTRACTION_ID)).isEqualTo("10 - 5 = 5");
        assertThat(text(page, MULTIPLICATION_ID)).isEqualTo("10 × 5 = 50");
        assertThat(text(page, DIVISION_ID)).isEqualTo("10 ÷ 5 = 2");
    }

    @Test
    void divisionByZeroIsHandledProperlyByShowingAMessage() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput secondInput = numberInput(page, SECOND_ID);

        secondInput.setValueAttribute("0");
        fireInputEventFor(secondInput);

        assertThat(text(page, DIVISION_ID)).isEqualTo("Cannot divide by zero!");
    }

    private HtmlPage getHtmlPage() throws IOException {
        return this.webClient.getPage("http://localhost/index.html");
    }

    private static void fireInputEventFor(HtmlNumberInput numberInput) {
        numberInput.fireEvent("input");
    }

    private static HtmlNumberInput numberInput(HtmlPage page, String elementId) {
        return page.getHtmlElementById(elementId);
    }

    private static String text(HtmlPage page, String elementId) {
        return page.getHtmlElementById(elementId).getTextContent();
    }
}
