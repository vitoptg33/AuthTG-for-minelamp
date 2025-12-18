package org.ezhik.authTG.commandTG;

import org.bukkit.configuration.ConfigurationSection;
import org.ezhik.authTG.AuthTG;
import org.ezhik.authTG.nextStep.AskPlayernameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class StartCMDHandler implements CommandHandler{
    @Override
    public void execute(Update update) {
        Long chatId;
        String command;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            command = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            command = "/" + update.getCallbackQuery().getData().split("_")[1];
        } else {
            return;
        }
        if (AuthTG.loader.getPlayerNames(chatId) != null && !AuthTG.loader.getPlayerNames(chatId).isEmpty()) {
            // Has at least one account, show menu with buttons
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            // Add buttons for each command
            addButton(keyboard, "Сбросить пароль", "cmd_resetpassword");
            addButton(keyboard, "Включить 2FA", "cmd_tfon");
            addButton(keyboard, "Выключить 2FA", "cmd_tfoff");
            addButton(keyboard, "Управление аккаунтами", "cmd_accounts");
            addButton(keyboard, "Отвязать аккаунт", "cmd_unlink");
            addButton(keyboard, "Кикнуть себя", "cmd_kickme");
            addButton(keyboard, "Выполнить команду", "cmd_command");
            ConfigurationSection section = AuthTG.macro;
            if (section != null) {
                for (String key : section.getKeys(false)) {
                    addButton(keyboard, section.getString(key + ".nsmsg"), "cmd_" + key);
                }
            }
            markup.setKeyboard(keyboard);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Выберите команду:");
            sendMessage.setReplyMarkup(markup);
            try {
                AuthTG.bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                AuthTG.logger.log(Level.SEVERE, "Error sending menu", e);
            }
        } else {
            // No accounts, proceed with authorization
            if (AuthTG.maxAccountTGCount > 0) {
                if (AuthTG.loader.getPlayerNames(chatId) != null && !AuthTG.loader.getPlayerNames(chatId).isEmpty() && AuthTG.loader.getPlayerNames(chatId).size() >= AuthTG.maxAccountTGCount) {
                    AuthTG.bot.sendMessage(chatId, AuthTG.getMessage("startmaxacc", "TG"));
                    return;
                }
            }
            AuthTG.bot.sendMessage(chatId, AuthTG.getMessage("startlinkacc", "TG"));
            AuthTG.bot.setNextStepHandler(chatId, new AskPlayernameHandler());
        }
    }

    private void addButton(List<List<InlineKeyboardButton>> keyboard, String text, String callbackData) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        row.add(button);
        keyboard.add(row);
    }
}
