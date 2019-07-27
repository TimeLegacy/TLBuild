package net.timelegacy.tlbuild.commands;

import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LevelPerksCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    sender.sendMessage(MessageUtils.colorize("&7&l[&cTAC&7&l] &bhttps://timelegacy.net/threads/leveling-system-perks.15/"));
    return false;
  }
}
