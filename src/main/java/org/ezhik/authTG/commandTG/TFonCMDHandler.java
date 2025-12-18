package org.ezhik.authTG.commandTG;

import org.ezhik.authTG.AuthTG;
import org.ezhik.authTG.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TFonCMDHandler implements CommandHandler {
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
        if (AuthTG.authNecessarily || AuthTG.notRegAndLogin) {
            if (update.hasMessage()) AuthTG.bot.deleteMessage(update.getMessage());
        } else {
            User user = User.getCurrentUser(chatId);
            if (user != null) {
                AuthTG.loader.setTwofactor(user.uuid, true);
                user.sendMessage(AuthTG.getMessage("tfonsuccess", "TG"));
            } else {
                AuthTG.bot.sendMessage(chatId, AuthTG.getMessage("tfonntactive", "TG"));
            }
        }
    }
}
