package com.mkozachuk.terramon.bot.messages;

import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Messages {

    private InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
    private ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    private KeyboardRow firstKeyboardRow = new KeyboardRow();
    private KeyboardRow secondKeyboardRow = new KeyboardRow();
    private KeyboardRow thirdKeyboardRow = new KeyboardRow();
    private KeyboardRow fourthKeyboardRow = new KeyboardRow();

    private List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
    private List<InlineKeyboardButton> firstInlineRow = new ArrayList<>();
    private List<InlineKeyboardButton> secondInlineRow = new ArrayList<>();
    private List<InlineKeyboardButton> thirdInlineRow = new ArrayList<>();
    
    public String startMessage(ReplyKeyboardMarkup keyboardMarkup, String checkServerIp) {
        keyboard.clear();
        firstKeyboardRow.clear();
        secondKeyboardRow.clear();
        thirdKeyboardRow.clear();
        fourthKeyboardRow.clear();
        firstKeyboardRow.add("\uD83C\uDF21️ Check \uD83D\uDCA7");
        secondKeyboardRow.add("\uD83D\uDCDD Notes");
        secondKeyboardRow.add("Export \uD83D\uDCBE");
        thirdKeyboardRow.add("⚙️ Current Settings");
        fourthKeyboardRow.add("\uD83D\uDD0E About");
        fourthKeyboardRow.add("\uD83D\uDD0C PowerOff");
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        keyboard.add(thirdKeyboardRow);
        keyboard.add(fourthKeyboardRow);
        keyboardMarkup.setKeyboard(keyboard);

        log.info("Start message");
        return "Welcome to your personal Smart Terrarium Monitor!\n Web is here: \n" + checkServerIp + ":8080" + "\nand here: \nhttp://terramon.local:8080";
    }

    public String currentTerraStats(TerraData currentTerraData) {
        String answer = "***Current Terrarium Statistics:***" +
                "\nTemperature = " + currentTerraData.getTemperature() + " °C" +
                "\nTemperature 2 = " + currentTerraData.getTemperatureFromHumiditySensor() + " °C" +
                "\nHumidity = " + currentTerraData.getHumidity() + "%";
        log.info("curret stats: {}", answer);
        return answer;
    }

    public String currentSettingsInfo(Terrarium terrarium) {

        String answer = "Minimum Temperature (send Alert) :" + terrarium.getTempMinAlert() + " °C\n"
                + "Maximum Temperature (send Alert) :" + terrarium.getTempMaxAlert() + " °C\n"
                + "Minimum Humidity (send Alert) :" + terrarium.getHumidityMinAlert() + " %\n"
                + "Maximum Humidity (send Alert) :" + terrarium.getHumidityMaxAlert() + " %\n"
                + "Maximum Humidity (Fan ON) :" + terrarium.getHumidityMaxOkLevel() + " %\n"
                + "Minimum Humidity (Fan OFF) :" + terrarium.getHumidityMinOkLevel() + " %";
        log.info("currentSettings {}", answer);
        return answer;
    }

    public String powerOffConfirmation(SendMessage message) {
        log.info("poweroffMsg");
        rowsInline.clear();
        firstInlineRow.clear();
        secondInlineRow.clear();
        thirdInlineRow.clear();
        firstInlineRow.add(new InlineKeyboardButton().setText("\uD83D\uDCF4 Power Off").setCallbackData("poweroff"));
        firstInlineRow.add(new InlineKeyboardButton().setText("Cancel ❌").setCallbackData("cancel"));
        rowsInline.add(firstInlineRow);
        inlineMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineMarkup);
        return "Are you sure?";
    }

    public String about(String version, boolean isNewVersionAvailable, String updateLink, String ip) {
        String isActualVersion;

        if (isNewVersionAvailable) {
            isActualVersion = "New version available [here](" + updateLink + ")";
        } else {
            isActualVersion = "Up to date";
        }


        String answer = "***Terramon :***" +
                "\nVersion :" + version + "\n" + isActualVersion +
                "\nYour local web here:" +
                "\n" + ip + ":8080" +
                "\nand here" +
                "\nhttp://terramon.local:8080";
        log.info("about: {}", answer);
        return answer;
    }

    public String notes(SendMessage message) {
        log.info("notes");
        rowsInline.clear();
        firstInlineRow.clear();
        secondInlineRow.clear();
        thirdInlineRow.clear();
        firstInlineRow.add(new InlineKeyboardButton().setText("\uD83D\uDCDD Add new Note").setCallbackData("addNewNote"));
        secondInlineRow.add(new InlineKeyboardButton().setText("Last 3 notes \uD83D\uDDC2️").setCallbackData("last3notes"));
        rowsInline.add(firstInlineRow);
        rowsInline.add(secondInlineRow);
        inlineMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineMarkup);
        return "Here you can add new note or check last 3 notes, if you want check all notes you can do it in your local web";
    }

    public String export(SendMessage message) {
        log.info("export");
        rowsInline.clear();
        firstInlineRow.clear();
        secondInlineRow.clear();
        thirdInlineRow.clear();
        firstInlineRow.add(new InlineKeyboardButton().setText("\uD83D\uDCDD All notes️").setCallbackData("exportNotes"));
        secondInlineRow.add(new InlineKeyboardButton().setText("All sensors data \uD83D\uDDF3️️").setCallbackData("exportAllTerraData"));
        rowsInline.add(firstInlineRow);
        rowsInline.add(secondInlineRow);
        inlineMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(inlineMarkup);
        return "Here you can export all notes or all temperature and humidity data";
    }

    public String noAnswer(){
        return "Sorry I don't understand what you mean, please use buttons";
    }
}
