package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "/test-application.properties")
public class MonitoringServiceTest {

    @Autowired
    private MonitoringService monitoringService;

    @MockBean
    private PiService piService;

    private TerraData actualTerraData;

    @BeforeEach
    public void newTerraData(){
        Terrarium terrarium = monitoringService.getTerrarium();
        terrarium.setTempMinAlert(20);
        terrarium.setTempMaxAlert(40);
        terrarium.setHumidityMaxAlert(70);
        terrarium.setHumidityMinAlert(50);
        terrarium.setHumidityMaxOkLevel(65);
        terrarium.setHumidityMinOkLevel(55);

        actualTerraData = new TerraData();
        terrarium.setAlertMsg("");
    }

    @After
    public void clear(){
        actualTerraData = null;
    }



    @Test
    void shouldSetAlertTrueIfToHot() {
        actualTerraData.setTemperature(41);

        monitoringService.checkTempForExtremum(actualTerraData);

        assertTrue(monitoringService.getTerrarium().isAlert());
    }

    @Test
    void shouldSetAlertTrueIfToCold() {
        actualTerraData.setTemperature(19);

        monitoringService.checkTempForExtremum(actualTerraData);

        assertTrue(monitoringService.getTerrarium().isAlert());
    }

    @Test
    void shouldSetAlertMessageIfToHot() {
        actualTerraData.setTemperature(41);

        assertEquals("Too HOT! Current temp is : 41.0 °C",
                monitoringService.checkTempForExtremum(actualTerraData));
    }
    @Test
    void shouldSetAlertMessageIfToCold() {
        actualTerraData.setTemperature(19);

        assertEquals("Too Cold! Current temp is : 19.0 °C",
                monitoringService.checkTempForExtremum(actualTerraData));
    }

    @Test
    void shouldSetAlertTrueIfToWet() {
        actualTerraData.setHumidity(71);

        monitoringService.checkHumidityForExtremum(actualTerraData);

        assertTrue(monitoringService.getTerrarium().isAlert());
    }

    @Test
    void shouldSetAlertTrueIfToDry() {
        actualTerraData.setHumidity(49);

        monitoringService.checkHumidityForExtremum(actualTerraData);

        assertTrue(monitoringService.getTerrarium().isAlert());
    }

    @Test
    void shouldSetAlertMessageIfToWet() {
        actualTerraData.setHumidity(71);

        assertEquals("Too Wet! Current humidity is : 71.0 %",
                monitoringService.checkHumidityForExtremum(actualTerraData));
    }
    @Test
    void shouldSetAlertMessageIfToDry() {
        actualTerraData.setHumidity(49);

        assertEquals("Too Dry! Current humidity is : 49.0 %",
                monitoringService.checkHumidityForExtremum(actualTerraData));
    }

    @Test
    void shouldStartFanIfABitWet() {
        when(piService.startFan()).thenReturn(true);
        actualTerraData.setHumidity(66);

        monitoringService.checkHumidityForVentilation(actualTerraData);

        assertTrue(monitoringService.getTerrarium().getFan().isOn());
    }
    @Test
    void shouldStopFanIfABitDry() {
        when(piService.stopFan()).thenReturn(false);
        actualTerraData.setHumidity(54);

        monitoringService.checkHumidityForVentilation(actualTerraData);

        assertFalse(monitoringService.getTerrarium().getFan().isOn());
    }
}