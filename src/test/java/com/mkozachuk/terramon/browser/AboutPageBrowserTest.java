package com.mkozachuk.terramon.browser;

import com.mkozachuk.terramon.service.PiService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/test-application.properties")
public class AboutPageBrowserTest {

    @MockBean
    private PiService piService;

    @LocalServerPort
    private int port;
    private static HtmlUnitDriver browser;

    @BeforeClass
    public static void setup() {
        browser = new HtmlUnitDriver();

        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void teardown() {
        browser.quit();
    }

    @Test
    public void aboutPageTest() {
        String aboutPage = "http://localhost:" + port;
        browser.get(aboutPage);

        String titleText = browser.getTitle();
        assertEquals("About TerraMon", titleText);

        String h1Text = browser.findElementById("card-title").getText();
        assertEquals("Your Personal Smart Terrarium Monitor", h1Text);

        String imgSrc = browser.findElementByClassName("card-img-top")
                .getAttribute("src");
        assertEquals(aboutPage + "/images/terraMonJungle.png", imgSrc);
    }
}
