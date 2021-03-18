package com.mkozachuk.terramon.controller;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.gpio.*;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Controller
public class PiController {
    private static final String LIB_NOT_PRESENT_MESSAGE = "CAN'T FIND Adafruit Library";
    private static final String ERROR_READING = "ERROR_READING";
    private W1Master master = new W1Master();
    private List<W1Device> w1Devices;
    private Date lastUpdate;

    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "MyFan");

    private DecimalFormat decimalFormat = new DecimalFormat("#.#");

    public double checkFromTemp() {
        double currentTemp = 0;
        w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        for (W1Device device : w1Devices) {
            currentTemp = ((TemperatureSensor) device).getTemperature();
            log.info("Temperature: {}", ((TemperatureSensor) device).getTemperature());
        }
        return Double.parseDouble(decimalFormat.format(currentTemp));
    }

    public Map<String, Double> checkFromHumidity() {
        Map<String, Double> dhtData = new HashMap<>();
        String cmd = "sudo python /home/pi/projects/Adafruit_Python_DHT/examples/AdafruitDHT.py 2302 25"; //25 is Pi Pin number
        try {
            StringBuilder ret = new StringBuilder();
            try {
                String line;
                Process p = Runtime.getRuntime().exec(cmd.split(" "));
                p.waitFor();
                BufferedReader input = new BufferedReader
                        (new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    ret.append(line).append('\n');
                }
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (ret.length() == 0) // Library is not present
                throw new RuntimeException(LIB_NOT_PRESENT_MESSAGE);
            else {
                if (ret.toString().contains(ERROR_READING)) {
                    String msg = "Error reading the the sensor, maybe is not connected.";
                    throw new Exception(msg);
                } else {
                    log.info("Read completed. Parse and update the values");
                    String[] vals = ret.toString().split("\\*");
                    float t = Float.parseFloat(vals[0].replace("Temp=", "").replace("*", "").trim());
                    float h = Float.parseFloat(vals[1].replace("Humidity=", "").replace("%", "").trim());
                    float lastTemp = 0.0f;
                    float lastHum = 0.0f;
                    dhtData.put("temp", Double.parseDouble(decimalFormat.format(t)));
                    dhtData.put("humidity", Double.parseDouble(decimalFormat.format(h)));
                    if ((t != lastTemp) || (h != lastHum)) {
                        lastUpdate = new Date();
                        lastTemp = t;
                        lastHum = h;
                        log.info("Last Update : {}", lastUpdate);
                        log.info("Last Temp : {}", lastTemp);
                        log.info("Last Humidity : {}", lastHum);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        return dhtData;

    }

    public boolean startFan() {

        pin.setState(PinState.HIGH);
        pin.setShutdownOptions(true, PinState.LOW);

        return true;

    }

    public boolean stopFan() {
        pin.setShutdownOptions(true, PinState.LOW);
        pin.setState(PinState.LOW);

        return false;

    }


}
