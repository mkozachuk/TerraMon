Current Version [1.0.1]
<p align="center">
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/terramon-cut.png?raw=true" width="600" alt="TerraMon">
    <br>
    <strong>IoT Terrarium Monitoring Device</strong>
</p>

-----

The TerraMon is a Smart Terrarium Monitoring System that collect temperature and humidity data and sending notification to you to provide the best conditions for your pets or/and plants.

- **Power save**: Power consumption is only 120 mA (0.7 W)
- **Lightweight**: runs smoothly with minimal hardware and software requirements (as Raspberry Pi Zero W)
- **Simplicity**: simple and intuitive settings
- **Insightful**: a beautiful responsive Web Interface dashboard and personal TelegramBot to view and control your TerraMon
- **Completely Independent**: web-server, Telegram Bot and lightweight database - everything on your personal device
- **Easy configuration**: personal configuration in one .properties file
- **Free**: open source software which helps you and your pets/plants

-----


## Hardware

**Requirements:**
- Raspberry Pi ( I use Raspbery Pi Zero W)
- DHT21 (AM2301) Temperature & Humidi sensor
- DS18B20 Temperature Sensor
- 1x 4.7kΩ resistor
- 1x 1kΩ resistor
- 1x 100Ω resistor
- 1x NPN transistor (BC546B or similar)

#

<p align="center">
        <strong>TerraMon Schematic</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/all.jpg" width="600" alt="TerraMon-circuit">
</p>

#

<p align="center">
         <strong>DHT21 (AM2301) Temperature & Humidity Sensor</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/dht21.jpg" width="600" alt="TerraMon-DHT21">
</p>

- Connect red (+) wire to 3V3 pin
- Connect black wire (-) to ground (GND) pin
- Connect data wire to GPIO25 pin
- Place 1kΩ resistor between 3V3 and data wire (pull-up).

#

<p align="center">
        <strong>DS18B20 Temperature Sensor</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/ds18d20.jpg" width="600" alt="TerraMon-DS18B20">
</p>

- Connect red (+) wire to 3V3 pin
- Connect black wire (-) to ground (GND) pin
- Connect data wire to GPIO4 pin
- Place 4.7kΩ resistor between 3V3 and data wire (pull-up).

#

<p align="center">
        <strong>5V Fan</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/fan.jpg" width="600" alt="TerraMon-Fan-Circuit">
</p>

- Transistor's collector: connects to black(-) wire on fan
- Transistor's base: connects to 100 Ohm Resistor and to GPIO13 pin
- Transistor's emitter: connects to ground (GND) pin
- Fan red wire(+): connects to 5V GPIO pin on raspberry pi 3 

#

## Dashboard

**Find your dashboard here:**
- http://terramon.local:8080/
- http://your-rpi-local-ip:8080/

#

<p align="center">
        <strong>Dashboard</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/dashboard.jpg">
</p>

<p align="center">
        <strong>Settings</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/ssettings.jpg">
</p>

<p align="center">
        <strong>Notes</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/nnotes.jpg">
</p>

<p align="center">
        <strong>About</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/aabout.jpg">
</p>

#

## Device

**TerraMon Case available on Thingiverse**
https://www.thingiverse.com/thing:4813437

<p align="center">
        <strong>TerraMon Case</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/terramon-device.jpg">
</p>

<p align="center">
        <strong>TerraMon Device</strong>
        <br>
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/terramon-device1.jpg">
</p>
