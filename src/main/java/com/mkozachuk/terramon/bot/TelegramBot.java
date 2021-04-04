package com.mkozachuk.terramon.bot;

import com.mkozachuk.terramon.bot.messages.Callbacks;
import com.mkozachuk.terramon.bot.messages.Messages;
import com.mkozachuk.terramon.model.Note;
import com.mkozachuk.terramon.model.TerraData;
import com.mkozachuk.terramon.model.Terrarium;
import com.mkozachuk.terramon.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private Terrarium terrarium;
    private ThreadPoolTaskScheduler taskScheduler;
    private TerraDataService terraDataService;
    private PiService piService;
    private AboutService aboutService;
    private NoteService noteService;
    private CsvService csvService;
    private MonitoringService monitoringService;

    @Autowired
    public TelegramBot(Terrarium terrarium, ThreadPoolTaskScheduler taskScheduler, TerraDataService terraDataService, PiService piService, AboutService aboutService, NoteService noteService, CsvService csvService, MonitoringService monitoringService) {
        this.terrarium = terrarium;
        this.taskScheduler = taskScheduler;
        this.terraDataService = terraDataService;
        this.piService = piService;
        this.aboutService = aboutService;
        this.noteService = noteService;
        this.csvService = csvService;
        this.monitoringService = monitoringService;
    }

    private Callbacks callbacks = new Callbacks();
    private Messages messages = new Messages();

    private ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    private InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
    private SendChatAction sendTypeAction = new SendChatAction();

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String botName;

    @Value(("${bot.welcomeSticker}"))
    private String welcomeStickerId;

    @Value(("${bot.ownerUsername}"))
    private String ownerUsername;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private Long adminChatId;

    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", botName, token);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            if (!update.getMessage().getChat().getUserName().equals(ownerUsername)) {
                sendWelcomeSticker(update.getMessage().getChatId().toString());
                String msg2 = "This bot checks temperature and humidity in terrariums, paludariums, vivariums etc.";
                SendMessage newUser = new SendMessage().enableMarkdown(true).setChatId(update.getMessage().getChatId());
                try {
                    newUser.setText(msg2);
                    execute(newUser);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {

                sendTypeAction.setChatId(update.getMessage().getChatId());
                sendTypeAction.setAction(ActionType.TYPING);

                SendMessage sendMessage = new SendMessage().enableMarkdown(true).setChatId(update.getMessage().getChatId());
                adminChatId = update.getMessage().getChatId();
                String text = update.getMessage().getText();
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setChatId(update.getMessage().getChatId()).getChatId();

                try {
                    sendMessage.setText(getMessage(text, sendMessage));
                    execute(sendTypeAction);
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            int message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            try {
                execute(getCallback(call_data, chat_id, message_id));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String getMessage(String msg, SendMessage message) {
        createKeyboard(keyboardMarkup);

        if (msg.contains("/start") || msg.equals("Menu") || msg.equals("Hello") || msg.equals("menu") || msg.contains("меню") || msg.contains("Меню")) {
            if (!terrarium.isMonitorOn()) {
                sendWelcomeSticker(adminChatId.toString());
                terrarium.setMonitorOn(true);
                terrarium.defaultValues();
                noteService.addDefaultNotes();
                taskScheduler.scheduleWithFixedDelay(new MonitoringService(terrarium, terraDataService, this, piService), terrarium.getDefaultMonitorDaley());
            }
            return messages.startMessage(keyboardMarkup, checkServerIp());
        }

        if (msg.equals("\uD83C\uDF21️ Check \uD83D\uDCA7")) {
            return messages.currentTerraStats(monitoringService.statsCheck());
        }

        if (msg.equals("\uD83D\uDCDD Notes")) {
            return messages.notes(message);
        }

        if (msg.equals("Export \uD83D\uDCBE")) {
            return messages.export(message);
        }
        if (msg.equals("⚙️ Current Settings")) {
            return messages.currentSettingsInfo(terrarium);
        }

        if (msg.equals("\uD83D\uDD0E About")) {
            return messages.about(aboutService.getVersion(), aboutService.checkForUpdate(), aboutService.getUrlToUpdate(), checkServerIp());
        }
        if (msg.equals("\uD83D\uDD0C PowerOff")) {
            return messages.powerOffConfirmation(message);
        }

        if (msg.startsWith("Note:")) {
            Note note = new Note();
            note.setAddAt(new Date());
            note.setTitle("Add from Telegram");
            note.setText(msg.replace("Note:", ""));
            noteService.save(note);
            return "Note has been saved";
        }

        return messages.noAnswer();
    }

    private EditMessageText getCallback(String callData, Long chatId, int msgId) {

        if (callData.equals("poweroff")) {
            piService.powerOff();
            return callbacks.powerOff(chatId, msgId);
        }
        if (callData.equals("cancel")) {
            return callbacks.canceled(chatId, msgId);
        }

        if (callData.equals("addNewNote")) {
            return callbacks.addNewNote(chatId, msgId);
        }

        if (callData.equals("last3notes")) {
            List<Note> last3notes = noteService.last3Notes();
            for (Note note : last3notes) {
                String formattedNote = note.getTitle() + "\n" + note.getAddAt() + "\n" + note.getText();
                sendAlertToUser(formattedNote);
            }
            return callbacks.lastNotes(chatId, msgId);
        }

        if (callData.equals("exportNotes")) {
            List<Note> allNotes = noteService.allNotes();
            try {
                sendFile(chatId, csvService.exportToCsv(allNotes));
            } catch (IOException e) {
                log.warn("IOE when export notes");
                e.printStackTrace();
            }

            return callbacks.export(chatId, msgId);
        }

        if (callData.equals("exportAllTerraData")) {
            List<TerraData> allTerraData = terraDataService.allTerraData();
            try {
                sendFile(chatId, csvService.exportToCsv(allTerraData));
            } catch (IOException e) {
                log.warn("IOE when export notes");
                e.printStackTrace();
            }

            return callbacks.export(chatId, msgId);
        }

        return null;
    }

    private void createKeyboard(ReplyKeyboardMarkup keyboardMarkup) {
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
    }

    private void sendWelcomeSticker(String chatId) {
        SendSticker welcomeSticker = new SendSticker();
        welcomeSticker.setChatId(chatId);
        welcomeSticker.setSticker(welcomeStickerId);
        try {
            execute(welcomeSticker);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendFile(Long chatId, String fileName) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        File file = new File(csvService.getExportPath() + fileName);
        sendDocument.setDocument(file);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAlertToUser(String alertMessage) {
        SendMessage sendMessage = new SendMessage().enableMarkdown(true).setChatId(adminChatId);
        sendMessage.setText(alertMessage);
        sendMessage.setReplyMarkup(inlineMarkup);
        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private String checkServerIp() {
        String ip = "IOException";

        try {
            Socket s = new Socket("www.google.com", 80);
            log.info(s.toString());
            ip = s.getLocalAddress().getHostAddress();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
