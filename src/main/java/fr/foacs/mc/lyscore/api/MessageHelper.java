package fr.foacs.mc.lyscore.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Optional;

import static java.util.Objects.isNull;

public class MessageHelper {

  private final LysPlugin lysPlugin;
  private YamlConfiguration messageConfig;

  public  MessageHelper(final LysPlugin lysPlugin) {
    this.lysPlugin = lysPlugin;
  }

  public String getFormattedMessage(final String messageKey, final Object... args) {
    return ChatColor.translateAlternateColorCodes('&', getMessage(messageKey, args));
  }

  public String getMessage(final String messageKey, final Object... args) {
    return String.format(getMessage(messageKey), args);
  }

  public String getFormattedMessage(final String messageKey) {
    return ChatColor.translateAlternateColorCodes('&', this.getMessage(messageKey));
  }

  public  String getMessage(final String messageKey, final String defaultM) {
    return getMessageConfig().map(config -> config.getString(messageKey)).orElse(defaultM);
  }

  public String getMessage(final String messageKey) {
    return this.getMessage(messageKey, "Missing message " + messageKey);
  }

  public Optional<YamlConfiguration> getMessageConfig() {
    if (isNull(messageConfig)) {
      this.messageConfig = this.lysPlugin.getConfig("message").orElse(null);
    }
    return Optional.ofNullable(messageConfig);
  }
}
