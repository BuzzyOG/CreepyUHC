package dev.creeper.uhc;

import dev.creeper.uhc.Commands.SpecTP;
import dev.creeper.uhc.Listeners.DeathListeners;
import dev.creeper.uhc.Listeners.MiscListeners;
import dev.creeper.uhc.Teams.AllTeams.*;
import dev.creeper.uhc.Teams.TeamInterface;
import dev.creeper.uhc.Utilities.GameState;
import dev.creeper.uhc.Utilities.Timer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {
    
    public static List<TeamInterface> allTeams = new ArrayList<TeamInterface>();
    public static int ticks = 0;
    public static Plugin plugin;

    private static GameState gameState;

    public static GameState getState() {
        return gameState;
    }

    public static void changeState(GameState state) {
        if (getState() != state) {
            gameState = state;
        }
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

        plugin = this;

        Bukkit.getServer().getScheduler().runTaskTimer(this, new Timer(), 20L, 20L);
        
        allTeams.add(new Spectators());
        allTeams.add(new Team1());
        allTeams.add(new Team2());
        allTeams.add(new Team3());
        allTeams.add(new Team4());
        allTeams.add(new Team5());
        allTeams.add(new Team6());
        allTeams.add(new Team7());
        allTeams.add(new Team8());
        allTeams.add(new Team9());
        allTeams.add(new Team10());

        getCommand("spectate").setExecutor(new SpecTP());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new DeathListeners(), this);
        getServer().getPluginManager().registerEvents(new MiscListeners(), this);
    }
}
