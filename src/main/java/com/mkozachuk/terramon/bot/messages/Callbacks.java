package com.mkozachuk.terramon.bot.messages;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class Callbacks {
    
    public EditMessageText powerOff(Long chatId, int messageId) {
        String answer = "Wait 25 seconds ⌛\nand unplug";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }

    public EditMessageText canceled(Long chatId, int messageId) {
        String answer = "Power Off has been canceled";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }

    public EditMessageText addNewNote(Long chatId, int messageId) {
        String answer = "Provide new note starting from \"Note:\"\n" +
                "Example:\n" +
                "Note: Important information that I would like to save";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }

    public EditMessageText lastNotes(Long chatId, int messageId) {
        String answer = "Last 3 notes:";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }

    public EditMessageText export(Long chatId, int messageId) {
        String answer = "Exporting...";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }

    public EditMessageText callData(String callData, Long chatId, int messageId) {
        String answer = callData;
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setText(answer);
        return new_message;
    }
}