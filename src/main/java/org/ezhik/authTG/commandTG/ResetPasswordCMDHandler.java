package org.ezhik.authTG.commandTG;

import org.ezhik.authTG.AuthTG;
import org.ezhik.authTG.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ResetPasswordCMDHandler implements CommandHandler {
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
            String password = User.generateConfirmationCode();
            AuthTG.loader.setPasswordHash(user.uuid, password);
            user.sendMessage(AuthTG.getMessage("resetpasssuccess", "TG").replace("{PASSWORD}", password));
        } else {
            AuthTG.bot.sendMessage(chatId,AuthTG.getMessage("resetpassnotactive", "TG"));
        }
    }
}
