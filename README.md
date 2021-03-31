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

<p align="center">
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/all.jpg" width="600" alt="TerraMon-circuit">
    <br>
    <strong>TerraMon Schematic</strong>
</p>

<p align="center">
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/dht21.jpg" width="600" alt="TerraMon-DHT21">
    <br>
    <strong>DHT21 (AM2301) Temperature & Humidity Sensor</strong>
</p>


<p align="center">
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/ds18d20.jpg" width="600" alt="TerraMon-DS18B20">
    <br>
    <strong>DS18B20 Temperature Sensor</strong>
</p>

<p align="center">
        <img src="https://github.com/mkozachuk/TerraMon/blob/master/docs/fan.jpg" width="600" alt="TerraMon-Fan-Circuit">
    <br>
    <strong>5V Fan</strong>
</p>

- Transistor's collector: connects to black(-) wire on fan
- Transistor's base: connects to 100 Ohm Resistor and to GPIO pin 13
- Transistor's emitter: connects to ground GPIO pin
- Fan red wire(+): connects to 5V GPIO pin on raspberry pi 3 
