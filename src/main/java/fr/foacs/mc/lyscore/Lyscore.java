package fr.foacs.mc.lyscore;

import fr.foacs.mc.lyscore.api.LysPlugin;
import org.bukkit.plugin.PluginManager;

public final class Lyscore extends LysPlugin {

  public Lyscore() {
    super("LysCore");
  }

  @Override
  protected void registerListener(PluginManager pluginManager) {
    // No listener to register
  }

  @Override
  protected void registerCommands() {
    // No commands to register
  }
}
