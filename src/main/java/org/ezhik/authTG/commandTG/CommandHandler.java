package org.ezhik.authTG.commandTG;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void execute(Update update);
}
