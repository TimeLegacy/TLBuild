package net.timelegacy.tlbuild.managers;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.timelegacy.tlbuild.TLBuild;
import net.timelegacy.tlbuild.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginAwareness;

public class FileManager {

  private final TLBuild plugin;

  private final File homeDirectory;
  private final String dataDirectory;
  private final String playerDataDirectory;
  private final String langDirectory;
  private final String logsDirectory;

  private File configYMLFile;
  private FileConfiguration configYMLData;

  private File dataYMLFile;
  public FileConfiguration dataYMLData;

  private File langYMLFile;
  public FileConfiguration langYMLData;

  private Map<String, File> missingDirectories = new HashMap<>();
  private List<String> createdDirectories = new ArrayList<>();
  private List<String> uncreatedDirectories = new ArrayList<>();

  private Map<String, File> missingFiles = new HashMap<>();
  private List<String> createdFiles = new ArrayList<>();
  private List<String> uncreatedFiles = new ArrayList<>();

  private Map<File, FileConfiguration> loadedFiles = new HashMap<>();

  private List<String> necessaryDirectories = new ArrayList<>();

  public FileManager(TLBuild plugin) {
    this.plugin = plugin;
    this.homeDirectory = plugin.getDataFolder();
    this.dataDirectory = homeDirectory.getAbsolutePath() + File.separator + "data";
    this.playerDataDirectory = dataDirectory + File.separator + "playerdata";
    this.langDirectory = homeDirectory + File.separator + "lang";
    this.logsDirectory = homeDirectory + File.separator + "logs";
  }

  public File getHomeDirectory() {
    return homeDirectory;
  }

  public String getDataDirectory() {
    return dataDirectory;
  }

  public String getPlayerDataDirectory() {
    return playerDataDirectory;
  }

  public String getLangDirectory() {
    return langDirectory;
  }

  public String getLogsDirectory() {
    return logsDirectory;
  }

  public void checkForAndCreateFiles() {
    if (!createDirectories()) {
      LogUtils.logError("An error occurred whilst creating the directories.");
      LogUtils.logError("Disabling Plugin");
      Bukkit.getPluginManager().disablePlugin(plugin);
      return;
    }

    if (!createFiles()) {
      LogUtils.logError("An error occurred whilst creating the files.");
      LogUtils.logError("Disabling Plugin");
      Bukkit.getPluginManager().disablePlugin(plugin);
      return;
    }

    loadFiles();
  }

  private void checkForMissingDirectories() {
    File homeDir = homeDirectory;
    File data = new File(dataDirectory);
    File playerData = new File(playerDataDirectory);
    File lang = new File(langDirectory);
    File logs = new File(logsDirectory);

    if (!directoryExists(homeDir)) {
      missingDirectories.put(homeDir.getName(), homeDir);
    }

    if (!directoryExists(lang)) {
      missingDirectories.put("Lang", lang);
    }

    if (!directoryExists(data)) {
      missingDirectories.put("data", data);
    }

    if (!directoryExists(playerData)) {
      missingDirectories.put("Player data", playerData);
    }

    if (!directoryExists(logs)) {
      missingDirectories.put("Logs", logs);
    }
  }

  private boolean createDirectories() {
    checkForMissingDirectories();

    LogUtils.logInfo("-={ Files }=-");

    if (missingDirectories.size() > 0) {
      List<String> missingDirectoriesKeys = new ArrayList<>(missingDirectories.keySet());

      String unaccountedDirectories = getListAsSortedString(missingDirectoriesKeys, "&e");

      LogUtils.logInfo("Missing Directories: " + unaccountedDirectories);
      LogUtils.logInfo("Attempting Creation...");

      for (Map.Entry<String, File> entry : missingDirectories.entrySet()) {
        if (entry.getValue().exists()) {
          continue;
        }

        if (createDirectory(entry.getValue())) {
          createdDirectories.add(entry.getKey());
        } else {
          uncreatedDirectories.add(entry.getKey());
        }
      }

      if (uncreatedDirectories.size() > 0) {
        boolean check = true;

        String uncreatedDirs = getListAsSortedString(uncreatedDirectories, "&c");

        LogUtils.logInfo("Missing Directories: " + uncreatedDirs);

        if (uncreatedDirs.contains(homeDirectory.getName())) {
          check = false;
        }

        if (!check) {
          LogUtils.logError(uncreatedDirs + " directories failed to generate. Disabling plugin.");
          return false;
        }

        return true;
      }

      LogUtils.logInfo("&aSUCCESS: &7All missing directories were created successfully.");
      LogUtils.logInfo(" ");
      return true;
    }

    LogUtils.logInfo("Missing Directories: &aNone");
    return true;
  }

//  private void test () {
//    File file = new File("");
//
//    Path path = null;
//
//
//
//    try {
//      Files.copy(plugin.getResource(""), path);
//
//      Files.copy(path.resolve(file.getAbsolutePath()), new OutputStream() {
//        @Override
//        public void write(int b) {
//
//        }
//      });
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  private boolean createFiles() {

    return true;
  }

  private void checkForMissingFiles() {
    File configYML = new File(homeDirectory, "config.yml");
    File dataYML = new File(dataDirectory, "data.yml");
    File langYML = new File(langDirectory, "lang.yml");

    if (!fileExists(configYML)) {
      missingFiles.put("config.yml", configYML);
    }

    if (!fileExists(dataYML)) {
      missingFiles.put("data.yml", dataYML);
    }

    if (!fileExists(langYML)) {
      missingFiles.put("lang.yml", langYML);
    }

  }

  private boolean createFiles2() {
    checkForMissingFiles();

    if (missingFiles.size() > 0) {
      List<String> missingFilesKeys = new ArrayList<>(missingFiles.keySet());

      String unaccountedFiles = getListAsSortedString(missingFilesKeys, "&e");

      LogUtils.logInfo("Missing files: " + unaccountedFiles);
      LogUtils.logInfo("Attempting Creation...");

      for (Map.Entry<String, File> entry : missingFiles.entrySet()) {
        if (entry.getValue().exists()) {
          continue;
        }

        if (createFile(entry.getValue())) {
          createdFiles.add(entry.getKey());
        } else {
          uncreatedFiles.add(entry.getKey());
        }
      }

      if (uncreatedFiles.size() > 0) {
        boolean check = true;

        String test = getListAsSortedString(uncreatedFiles, "&c");

        LogUtils.logInfo("Missing files: " + test);

        if (test.contains("config.yml")) {
          check = false;
        }

        if (!check) {
          LogUtils.logError(test + " files failed to generate. Disabling plugin.");
          return false;
        }

        return true;
      }

      LogUtils.logInfo("&aSUCCESS: &7All missing files were created successfully.");
      LogUtils.logInfo(" ");
      return true;
    }

    LogUtils.logInfo("Missing files: &aNone");
    return true;
  }

  private void loadFiles() {
//    configYMLData = YamlConfiguration.loadConfiguration(configYMLFile);
//    loadedFiles.put(configYMLFile, configYMLData);
//
//    dataYMLData = YamlConfiguration.loadConfiguration(dataYMLFile);
//    loadedFiles.put(dataYMLFile, dataYMLData);
//
//    langYMLData = YamlConfiguration.loadConfiguration(langYMLFile);
//    loadedFiles.put(langYMLFile, langYMLData);
  }

  private void reloadMessageConfig() {
    langYMLData = YamlConfiguration.loadConfiguration(langYMLFile);

    final InputStream defConfigStream = plugin.getResource("lang/lang_en.yml");
    if (defConfigStream == null) {
      return;
    }

    final YamlConfiguration defConfig;
    if (isStrictlyUTF8() /*|| FileConfiguration.UTF8_OVERRIDE */) {
      defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
    } else {
      final byte[] contents;
      defConfig = new YamlConfiguration();
      try {
        contents = ByteStreams.toByteArray(defConfigStream);
      } catch (final IOException e) {
        LogUtils.logError("Unexpected failure reading lang_en.yml");
        return;
      }

      final String text = new String(contents, Charset.defaultCharset());
      if (!text.equals(new String(contents, Charsets.UTF_8))) {
        //LogUtils.logWarning("Default system encoding may have misread lang_en.yml from plugin jar");
      }

      try {
        defConfig.loadFromString(text);
      } catch (final InvalidConfigurationException e) {
        //LogUtils.logError("Cannot load configuration from jar");
      }
    }

    langYMLData.setDefaults(defConfig);
  }

  @SuppressWarnings("deprecation")
  private boolean isStrictlyUTF8() {
    return plugin.getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
  }

  private String getListAsSortedString(List<String> list, String colorCode) {
    StringBuilder stringBuilder = new StringBuilder();
    // Looping through the list.
    for (int i = 0; i < list.size(); i++) {
      //append the value into the builder
      stringBuilder.append(colorCode + list.get(i));

      // If the value is not the last element of the list then append a comma(,).
      if (i != list.size() - 1) {
        stringBuilder.append("&7, ");
      }
    }
    return stringBuilder.toString();
  }

  private void createLangFile() {
    langYMLFile = new File(getLangDirectory(), "lang_en.yml");
    if (!langYMLFile.exists()) {
      langYMLFile.getParentFile().mkdirs();
      plugin.saveResource("lang_en.yml", false);
    }

    langYMLData = new YamlConfiguration();
    try {
      langYMLData.load(langYMLFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  private boolean fileExists(File file) {
    return directoryExists(file);
  }

  private boolean directoryCreated(File file) {
    boolean isDirectoryCreated = directoryExists(file);

    if (!isDirectoryCreated) {
      isDirectoryCreated = file.mkdirs();
    }

    return isDirectoryCreated;
  }

  private boolean createFile(File file) {
    boolean fileCreated = file.exists();

    if (!fileCreated) {
      try {
        fileCreated = file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (!fileCreated) {
      LogUtils.logError("An error occurred while creating the player's data.");
      return false;
    }

    return true;
  }

  public boolean createDirectory(File file) {
    boolean isDirectoryCreated = directoryExists(file);

    if (!isDirectoryCreated) {
      isDirectoryCreated = file.mkdirs();
    }

    return isDirectoryCreated;
  }

  public boolean deleteDirectory(File file) {
    if (!file.isDirectory()) {
      return false;
    }

    return file.delete();
  }

  private boolean directoryExists(File file) {
    return file.exists();
  }

  public File getConfigFile() {
    return configYMLFile;
  }

  public FileConfiguration getConfig() {
    return loadedFiles.get(configYMLFile);
  }

  public void reloadConfig() {
    configYMLData = YamlConfiguration.loadConfiguration(configYMLFile);

    InputStream defConfigStream = plugin.getResource("config.yml");
    if (defConfigStream != null) {
      configYMLData.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }

    loadedFiles.put(configYMLFile, configYMLData);
  }

  public File getDataFile() {
    return dataYMLFile;
  }

  public FileConfiguration getData() {
    return dataYMLData;
  }

  public void reloadData() {
    dataYMLData = YamlConfiguration.loadConfiguration(dataYMLFile);
  }

  public File getLangFile() {
    return langYMLFile;
  }

  public FileConfiguration getLang() {
    return langYMLData;
  }

  public void reloadLang() {
    langYMLData = YamlConfiguration.loadConfiguration(langYMLFile);
  }

  public void reloadYMLFile(File file, FileConfiguration fileConfiguration, String pathToResource) {
    fileConfiguration = YamlConfiguration.loadConfiguration(file);

    InputStream inputStream = plugin.getResource(pathToResource);
    if (inputStream != null) {
      fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream, Charsets.UTF_8)));
    }
  }

}
