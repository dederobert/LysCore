package fr.foacs.mc.lyscore.api.command;

import fr.foacs.mc.lyscore.api.LysPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/** Abstract class to handle a command. */
public abstract class CommandHandler implements CommandExecutor {

  private final LysPlugin plugin;
  private final String commandName;

  /**
   * Constructor of command handler.
   *
   * @param plugin The main plugin instance.
   * @param commandName The command's name handled.
   */
  protected CommandHandler(LysPlugin plugin, String commandName) {
    this.plugin = plugin;
    this.commandName = commandName;
  }

  /** {@inheritDoc} */
  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    if (!isMatchCommandName(command) || !isMatchParamsNumber(args)) return false;

    return handle(sender, command, label, args);
  }

  /**
   * Check if the typed command match the command name.
   *
   * @param command The typed command.
   * @return True if match.
   */
  protected boolean isMatchCommandName(final Command command) {
    return command.getName().equalsIgnoreCase(this.commandName);
  }

  /**
   * Send formatted message to command sender.
   *
   * @param sender The command sender to send message.
   * @param messageKey The message's key.
   */
  protected void sendMessage(CommandSender sender, String messageKey) {
    sender.sendMessage(plugin.getMessageHelper().getFormattedMessage(messageKey));
  }

  /**
   * Send formatted message to command sender.
   *
   * @param sender The command sender to send message.
   * @param messageKey The message's key.
   * @param args The message arguments.
   */
  protected void sendMessage(CommandSender sender, String messageKey, Object args) {
    sender.sendMessage(plugin.getMessageHelper().getFormattedMessage(messageKey, args));
  }

  /**
   * Check if typed command as the right number of arguments.
   *
   * @param args The list of typed arguments.
   * @return True if number of type args is correct.
   */
  protected abstract boolean isMatchParamsNumber(String[] args);

  /**
   * Delegate the handling of command to child-class. The check on command name and args number is
   * already done.
   *
   * @param sender The command sender.
   * @param command The typed command.
   * @param label The command's label.
   * @param args The typed args.
   * @return True if command is handled.
   */
  protected abstract boolean handle(
      CommandSender sender, Command command, String label, String[] args);
}
