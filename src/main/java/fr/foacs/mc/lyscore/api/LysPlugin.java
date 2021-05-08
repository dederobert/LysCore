package fr.foacs.mc.lyscore.api;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of LysPlugin
 *
 * @since 0.1
 */
public abstract class LysPlugin extends JavaPlugin {

  private final MessageHelper messageHelper;
  private final String pluginName;
  private final Set<String> configNames = new HashSet<>();
  private final Set<String> dataNames = new HashSet<>();
  private final Map<String, File> configFiles = new HashMap<>();
  private final Map<String, File> dataFiles = new HashMap<>();
  private final Map<String, YamlConfiguration> configs = new HashMap<>();
  private final Map<String, YamlConfiguration> data = new HashMap<>();
  private LoggerHelper log;
  private boolean lockedConfig = false;

  /**
   * Plugin constructor, set the name of the plugin.
   *
   * @param pluginName The name of the plugin.
   */
  protected LysPlugin(final String pluginName) { this(pluginName, false); }

  /**
   * Plugin constructor, set the name of the plugin and prepare message.
   *
   * @param pluginName The name of the plugin
   * @param withMessage Flag if the plugin should use message
   */
  protected LysPlugin(final String pluginName, boolean withMessage) {
    super();
    this.pluginName = pluginName;
    if (withMessage) {
      addConfig("message");
      this.messageHelper = new MessageHelper(this);
    } else {
      this.messageHelper = null;
    }
  }

  /**
   * Get a config identify by its name. It have to be added before plugin
   * enabling with {@link LysPlugin#addConfig(String)}.
   *
   * @param name The name of the config to get.
   * @return A optional which contains the config.
   */
  public Optional<YamlConfiguration> getConfig(final String name) {
    return Optional.ofNullable(this.configs.get(name));
  }

  /**
   * Get a data config identify by its name.
   *
   * @param name The name of the data config to get.
   * @return A optional which contains the data config.
   */
  public Optional<YamlConfiguration> getData(final String name) {
    return Optional.ofNullable(this.data.get(name));
  }

  /**
   * Check if data config exists.
   *
   * @param name The name of the data to check.
   * @return true if the data exist.
   */
  public boolean existsData(final String name) {
    return this.data.containsKey(name);
  }

  @Override
  public void onEnable() {
    this.log = new LoggerHelper(this.getLogger());
    log.info("Initializing %s.", this.pluginName);
    log.info("Created by %s", getDescription().getAuthors());
    log.config("%s version: %s is loaded.", this.pluginName,
               getDescription().getVersion());
    final PluginManager pluginManager = this.getServer().getPluginManager();
    registerListener(pluginManager);
    registerCommands();
    setupConfig();
  }

  @Override
  public void saveConfig() {
    super.saveConfig();
    saveCollectionOfConfig(this.configNames, this.configs, this.configFiles);
    saveCollectionOfConfig(this.dataNames, this.data, this.dataFiles);
  }

  protected final void addConfig(final String configName) {
    if (this.lockedConfig) {
      this.log.warning("Unable to register config after setup started.");
      return;
    }
    if (!this.configNames.add(configName)) {
      this.log.warning("Unable to add config %s", configName);
    }
  }

  protected final void addData(final String dataName) {
    if (!this.dataNames.add(dataName)) {
      this.log.warning("Unable to add data %s", dataName);
    } else {
      setupData(dataName);
    }
  }

  protected abstract void registerListener(final PluginManager pluginManager);

  protected abstract void registerCommands();

  private void setupConfig() {
    this.lockedConfig = true;
    final File dataFolder = createDataFolder();

    for (String configName : this.configNames) {
      final File file = new File(dataFolder, configName + ".yml");
      if (!file.exists()) {
        this.log.warning("Config file %s.yml doesn't exists. Creating it ....",
                         configName);
        try {
          if (file.createNewFile()) {
            this.log.info("Config file %s.yml created", configName);
          }
        } catch (IOException e) {
          this.log.sever(e, "An error occurred while creating file %s.yml",
                         configName);
        }
      }
      this.configFiles.put(configName, file);
      if (!file.canRead()) {
        this.log.warning("Unable to read file named %s.yml", configName);
        continue;
      }
      final YamlConfiguration configuration =
          YamlConfiguration.loadConfiguration(file);
      this.configs.put(configName, configuration);
      final InputStream resource = getResource(configName + ".yml");
      loadDefaultConfig(resource, configuration);
    }
  }

  private void setupData(final String dataName) {
    final File dataFolder = createDataFolder();
    if (this.dataFiles.containsKey(dataName) &&
        this.data.containsKey(dataName)) {
      return;
    }
    final File file = new File(dataFolder, dataName + ".yml");
    if (!file.exists()) {
      try {
        if (file.createNewFile()) {
          this.log.info("Data file %s.yml created.", dataName);
        }
      } catch (IOException e) {
        this.log.sever(e, "An error occurred while creating file %s.yml",
                       dataName);
      }
    }
    this.dataFiles.put(dataName, file);
    if (!file.canRead()) {
      this.log.warning("Unable to read file named %s.yml", dataName);
      return;
    }
    final YamlConfiguration configuration =
        YamlConfiguration.loadConfiguration(file);
    this.data.put(dataName, configuration);
  }

  private File createDataFolder() {
    final File dataFolder = getDataFolder();
    if (!dataFolder.exists() && !dataFolder.mkdir()) {
      this.log.warning("Unable to create config folder.");
    }
    return dataFolder;
  }

  private void loadDefaultConfig(final InputStream source,
                                 final YamlConfiguration config) {
    if (nonNull(source)) {
      final InputStreamReader reader =
          new InputStreamReader(source, StandardCharsets.UTF_8);
      final YamlConfiguration defaultConfig =
          YamlConfiguration.loadConfiguration(reader);
      config.setDefaults(defaultConfig);
      config.options().copyDefaults(true);
    }
  }

  private void
  saveCollectionOfConfig(final Collection<String> names,
                         final Map<String, YamlConfiguration> configMap,
                         final Map<String, File> files) {
    for (String configName : names) {
      final YamlConfiguration configuration = configMap.get(configName);
      if (isNull(configuration)) {
        this.log.warning(
            "Unable to get configuration named %s! It will not be saved!",
            configName);
        continue;
      }
      final File file = files.get(configName);
      try {
        configuration.save(file);
      } catch (IOException e) {
        this.log.sever(e, "Could not save %s into %s", configName, file);
      }
    }
  }

  public MessageHelper getMessageHelper() { return messageHelper; }

  public LoggerHelper getLog() { return this.log; }
}
