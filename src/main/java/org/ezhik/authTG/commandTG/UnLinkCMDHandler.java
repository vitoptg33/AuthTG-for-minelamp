package org.ezhik.authTG.commandTG;

import org.bukkit.ChatColor;
import org.ezhik.authTG.AuthTG;
import org.ezhik.authTG.User;
import org.ezhik.authTG.commandMC.CodeCMD;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnLinkCMDHandler implements CommandHandler {
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
        if (AuthTG.notRegAndLogin) {
            if (update.hasMessage()) AuthTG.bot.deleteMessage(update.getMessage());
        } else {
            User user = User.getCurrentUser(chatId);
            if (user != null) {
                String code = User.generateConfirmationCode();
                if (user.player != null) {
                    user.player.sendMessage(ChatColor.translateAlternateColorCodes('&', AuthTG.getMessage("codemsgdeactivated", "MC")));
                    CodeCMD.code.put(user.uuid, code);
                    user.sendMessage(AuthTG.getMessage("unlinkcode", "TG").replace("{CODE}", code));
                } else {
                    user.sendMessage(AuthTG.getMessage("unlinkplntonline", "TG"));
                }
            } else {
                AuthTG.bot.sendMessage(chatId, AuthTG.getMessage("unlinknotactive", "TG"));
            }
        }
    }
}
