package dev.creeper.uhc;

import dev.creeper.uhc.Utilities.GameState;
import dev.creeper.uhc.Utilities.Timer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static int min_players = 8;
    public static int ticks = 0;

    private static GameState state;

    public static GameState getState() {
        return state;
    }

    public static int getTicks() {
        return ticks;
    }

    public static void setTicks(int ticks) {
        Core.ticks = ticks;
    }

    @Override
    public void onEnable() {
        getLogger().info("CreepyUHC has successfully been enabled!");

        registerEvents();

        Bukkit.getServer().getScheduler().runTaskTimer(this, new Timer(), 20L, 20L);
    }

    public void registerEvents() {
        // TODO
        // register events!
    }
}
