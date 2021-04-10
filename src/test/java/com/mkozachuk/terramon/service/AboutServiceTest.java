package com.mkozachuk.terramon.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@TestPropertySource(locations = "/test-application.properties")
class AboutServiceTest {

    @Autowired
    private AboutService aboutService;

    @MockBean
    private PiService piService;

    @Test
    void shouldReturnTrueIfNewVersionAvailable(){
        aboutService.setVersion("v1.0.0");
        assertTrue(aboutService.checkForUpdate());
    }

    @Test
    void shouldReturnFalseIfTheSameVersion(){
        assertFalse(aboutService.checkForUpdate());
    }
}