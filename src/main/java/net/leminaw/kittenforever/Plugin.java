package net.leminaw.kittenforever;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Plugin extends JavaPlugin {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("allowOPs", true);
        config.options().copyDefaults(true);
        saveConfig();
        
        getServer().getPluginManager().registerEvents(new AnimalListener(this), this);
    }

    @Override
    public void onDisable() {}
}
