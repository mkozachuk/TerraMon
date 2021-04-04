package com.mkozachuk.terramon.bot.messages;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class Callbacks {
    
    public EditMessageText powerOff(Long chat_id, int message_id) {
        String answer = "Wait 25 seconds ⌛\nand unplug";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(answer);
        return new_message;
    }

    public EditMessageText canceled(Long chat_id, int message_id) {
        String answer = "Power Off has been canceled";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(answer);
        return new_message;
    }

    public EditMessageText addNewNote(Long chat_id, int message_id) {
        String answer = "Provide new note starting from \"Note:\"\n" +
                "Example:\n" +
                "Note: Important information that I would like to save";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(answer);
        return new_message;
    }

    public EditMessageText lastNotes(Long chat_id, int message_id) {
        String answer = "Last 3 notes:";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(answer);
        return new_message;
    }

    public EditMessageText export(Long chat_id, int message_id) {
        String answer = "Exporting...";
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(message_id)
                .setText(answer);
        return new_message;
    }
}