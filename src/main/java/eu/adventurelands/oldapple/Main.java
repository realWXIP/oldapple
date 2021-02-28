package eu.adventurelands.oldapple;

import eu.adventurelands.oldapple.commands.ReloadCommand;
import eu.adventurelands.oldapple.events.FoodConsumeEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new FoodConsumeEvent(this), this);
        getCommand("oldapple").setExecutor(new ReloadCommand(this));
    }
}
