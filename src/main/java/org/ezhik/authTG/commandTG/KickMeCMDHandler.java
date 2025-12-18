package org.ezhik.authTG.commandTG;

import org.ezhik.authTG.AuthTG;
import org.ezhik.authTG.handlers.Handler;
import org.ezhik.authTG.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class KickMeCMDHandler implements CommandHandler {
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
        User user = User.getCurrentUser(chatId);
        if (user != null) {
            if (user.player != null) {
                Handler.kick(user.playername, AuthTG.getMessage("kickmeplayer", "TG"));
                user.sendMessage(AuthTG.getMessage("kickmesuccess", "TG"));
            } else {
                user.sendMessage(AuthTG.getMessage("kickmeplnotonline", "TG"));
            }
        } else {
            AuthTG.bot.sendMessage(chatId,AuthTG.getMessage("kickmenotactive", "TG"));
        }
    }
}
