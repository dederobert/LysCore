package fr.foacs.mc.lyscore.api.command;

import fr.foacs.mc.lyscore.api.LysPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class CommandHandler implements CommandExecutor {

  private final LysPlugin plugin;
  private final String commandName;

  protected CommandHandler(LysPlugin plugin, String commandName) {
    this.plugin = plugin;
    this.commandName = commandName;
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (!isMatchCommandName(command) || !isMatchParamsNumber(args)) return false;

    return handle(sender, command, label, args);
  }

  protected boolean isMatchCommandName(final Command command) {
    return command.getName().equalsIgnoreCase(this.commandName);
  }

  protected void sendMessage(CommandSender sender, String messageKey) {
    sender.sendMessage(plugin.getMessageHelper().getFormattedMessage(messageKey));
  }

  protected void sendMessage(CommandSender sender, String messageKey, Object args) {
    sender.sendMessage(plugin.getMessageHelper().getFormattedMessage(messageKey, args));
  }

  protected abstract boolean isMatchParamsNumber(String[] args);

  protected abstract boolean handle(
      CommandSender sender, Command command, String label, String[] args);
}
