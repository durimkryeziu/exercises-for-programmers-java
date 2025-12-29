package com.craftsmanshipinsoftware.math.gui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlDivision;
import org.htmlunit.html.HtmlNumberInput;
import org.htmlunit.html.HtmlPage;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
class ApplicationTest {

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

        assertThat(firstInputNumberInput(page).getDefaultValue()).isEqualTo("10");
        assertThat(secondInputNumberInput(page).getDefaultValue()).isEqualTo("5");
        assertThat(additionDiv(page).getTextContent()).isEqualTo("10 + 5 = 15");
        assertThat(subtractionDiv(page).getTextContent()).isEqualTo("10 - 5 = 5");
        assertThat(multiplicationDiv(page).getTextContent()).isEqualTo("10 × 5 = 50");
        assertThat(divisionDiv(page).getTextContent()).isEqualTo("10 ÷ 5 = 2");
    }

    @Test
    void calculationsAreUpdatedOnUserInput() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = firstInputNumberInput(page);
        HtmlNumberInput secondInput = secondInputNumberInput(page);

        firstInput.setValueAttribute("20");
        fireInputEventFor(firstInput);
        secondInput.setValueAttribute("4");
        fireInputEventFor(secondInput);

        assertThat(additionDiv(page).getTextContent()).isEqualTo("20 + 4 = 24");
        assertThat(subtractionDiv(page).getTextContent()).isEqualTo("20 - 4 = 16");
        assertThat(multiplicationDiv(page).getTextContent()).isEqualTo("20 × 4 = 80");
        assertThat(divisionDiv(page).getTextContent()).isEqualTo("20 ÷ 4 = 5");
    }

    @Test
    void resetToDefaultButtonShouldWork() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput firstInput = firstInputNumberInput(page);
        firstInput.setValueAttribute("20");
        fireInputEventFor(firstInput);
        HtmlNumberInput secondInput = secondInputNumberInput(page);
        secondInput.setValueAttribute("4");
        fireInputEventFor(secondInput);

        page = page.getHtmlElementById("resetButton").click();

        assertThat(firstInputNumberInput(page).getValue()).isEqualTo("10");
        assertThat(secondInputNumberInput(page).getValue()).isEqualTo("5");
        assertThat(additionDiv(page).getTextContent()).isEqualTo("10 + 5 = 15");
        assertThat(subtractionDiv(page).getTextContent()).isEqualTo("10 - 5 = 5");
        assertThat(multiplicationDiv(page).getTextContent()).isEqualTo("10 × 5 = 50");
        assertThat(divisionDiv(page).getTextContent()).isEqualTo("10 ÷ 5 = 2");
    }

    @Test
    void divisionByZeroIsHandledProperlyByShowingAMessage() throws IOException {
        HtmlPage page = getHtmlPage();
        HtmlNumberInput secondInput = secondInputNumberInput(page);

        secondInput.setValueAttribute("0");
        fireInputEventFor(secondInput);

        assertThat(divisionDiv(page).getTextContent()).isEqualTo("Cannot divide by zero!");
    }

    private HtmlPage getHtmlPage() throws IOException {
        return this.webClient.getPage("http://localhost/index.html");
    }

    private static void fireInputEventFor(HtmlNumberInput numberInput) {
        numberInput.fireEvent("input");
    }

    private HtmlNumberInput secondInputNumberInput(HtmlPage page) {
        return page.getHtmlElementById("secondInput");
    }

    private HtmlNumberInput firstInputNumberInput(HtmlPage page) {
        return page.getHtmlElementById("firstInput");
    }

    private HtmlDivision additionDiv(HtmlPage page) {
        return page.getHtmlElementById("addition");
    }

    private HtmlDivision subtractionDiv(HtmlPage page) {
        return page.getHtmlElementById("subtraction");
    }

    private HtmlDivision multiplicationDiv(HtmlPage page) {
        return page.getHtmlElementById("multiplication");
    }

    private HtmlDivision divisionDiv(HtmlPage page) {
        return page.getHtmlElementById("division");
    }
}
