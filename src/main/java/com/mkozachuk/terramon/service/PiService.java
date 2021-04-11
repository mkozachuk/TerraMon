package com.mkozachuk.terramon.service;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.gpio.*;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PiService {
    private static final String LIB_NOT_PRESENT_MESSAGE = "CAN'T FIND Adafruit Library";
    private static final String ERROR_READING = "ERROR_READING";
    private W1Master master = new W1Master();
    private List<W1Device> w1Devices;
    private Date lastUpdate;

    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "MyFan");

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
            StringBuilder cmdResult = cmdResult(cmd);
            if (cmdResult.length() == 0) // Library is not present
                throw new RuntimeException(LIB_NOT_PRESENT_MESSAGE);
            else {
                if (cmdResult.toString().contains(ERROR_READING)) {
                    String msg = "Error reading the the sensor, maybe is not connected.";
                    throw new Exception(msg);
                } else {
                    log.info("Read completed. Parse and update the values");
                    String[] values = cmdResult.toString().split("\\*");
                    float temperature = Float.parseFloat(values[0].replace("Temp=", "").replace("*", "").trim());
                    float humidity = Float.parseFloat(values[1].replace("Humidity=", "").replace("%", "").trim());
                    float lastTemp = 0.0f;
                    float lastHum = 0.0f;
                    dhtData.put("temp", Double.parseDouble(decimalFormat.format(temperature)));
                    dhtData.put("humidity", Double.parseDouble(decimalFormat.format(humidity)));
                    if ((temperature != lastTemp) || (humidity != lastHum)) {
                        lastUpdate = new Date();
                        lastTemp = temperature;
                        lastHum = humidity;
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

    public StringBuilder cmdResult(String cmd){
        StringBuilder cmdResult = new StringBuilder();
        try {
            String line;
            Process process = Runtime.getRuntime().exec(cmd.split(" "));
            process.waitFor();
            BufferedReader input = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                cmdResult.append(line).append('\n');
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cmdResult;
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

    public void powerOff(){
        String cmd = "sudo poweroff";
        try {
            log.warn("Power Off starting...");
            Process p = Runtime.getRuntime().exec(cmd.split(" "));
            p.waitFor();
        }catch (Exception e){
            log.error("Exception when sudo poweroff");
            e.getStackTrace();
        }

    }
}
