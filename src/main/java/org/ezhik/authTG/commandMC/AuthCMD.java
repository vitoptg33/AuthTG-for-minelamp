package org.ezhik.authTG.commandMC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ezhik.authTG.AuthTG;

public class AuthCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.isOp()) {
                player.sendMessage("You need OP to use this command.");
                return false;
            }
        }
        if (strings.length > 0 && strings[0].equals("reload")) {
            AuthTG plugin = (AuthTG) AuthTG.getInstance();
            plugin.reloadConfig();
            plugin.setupConfiguration();
            plugin.setupMessages();
            plugin.loadConfigParameters();
            commandSender.sendMessage("Config reloaded.");
            return true;
        }
        return false;
    }
}