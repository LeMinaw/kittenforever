package net.leminaw.kittenforever;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Plugin extends JavaPlugin
{
    FileConfiguration config = getConfig();

    @Override
    public void onEnable()
    {
        config.addDefault("items.stopGrowth.before", "MILK_BUCKET");
        config.addDefault("items.stopGrowth.after", "BUCKET");
        config.addDefault("items.resumeGrowth.before", "MUSHROOM_STEW");
        config.addDefault("items.resumeGrowth.after", "BOWL");
        config.options().copyDefaults(true);
        saveConfig();
        
        getServer().getPluginManager().registerEvents(new AnimalListener(this), this);
    }

    @Override
    public void onDisable() {}
}
