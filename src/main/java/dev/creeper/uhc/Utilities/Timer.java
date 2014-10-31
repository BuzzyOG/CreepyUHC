package dev.creeper.uhc.Utilities;

import dev.creeper.uhc.Core;
import dev.creeper.uhc.Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Timer implements Runnable {

    public final int lobby_timer = 60;
    public final int pregame_timer = 30;
    public final int no_pvp_timer = 1200;
    public final int game_timer = 3600;
    public final int pre_meetup = 60;
    public final int meet_up = 600;
    public final int result_timer = 30;

    public final int min_players = 8;

    @Override
    public void run() {

        if (Core.getTicks() > 0) {
            Core.setTicks(Core.getTicks() - 1);
        }

        if (Core.getState() == GameState.LOBBY) {

            int online = Bukkit.getOnlinePlayers().length;

            if (Core.getTicks() == 0) {
                if (online >= min_players) {
                    Core.changeState(GameState.PREGAME);
                    Core.setTicks(pregame_timer);
                    // todo
                    // start game
                } else {
                    Core.setTicks(lobby_timer);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage("§7Not enough players to start! Current: §c" + online + " §7Required: §c" + min_players);
                        p.sendMessage("§7The game begins in §c" + lobby_timer + " §7seconds!");
                        p.playSound(p.getLocation(), Sound.CLICK, 1 ,10);
                    }
                }
            }
        }
        if (Core.getState() == GameState.PREGAME) {

            if (Core.getTicks() == 0) {
                Core.changeState(GameState.NOPVP);
                Core.setTicks(no_pvp_timer);
                Bukkit.broadcastMessage("§7The round has begun! There is §c" + no_pvp_timer + "");
                // todo
                // begin no pvp stage
            }
        }
        if (Core.getState() == GameState.NOPVP) {
            if (Core.getTicks() == 0) {
                Core.changeState(GameState.INGAME);
                Core.setTicks(game_timer);
                Bukkit.broadcastMessage("§7Attention! §cPVP §7is now enabled!");
                // todo
                // normal game conditions
            }
        }
        if (Core.getState() == GameState.INGAME) {
            if (Core.getTicks() == 0) {
                Core.changeState(GameState.PREMEETUP);
                Core.setTicks(pre_meetup);
            }
            for (Player left : Bukkit.getOnlinePlayers()) {
                if (TeamManager.getPlayerTeam(left) != TeamManager.getTeam("Spectators")) {
                    List<String> pLeft = new ArrayList<String>();
                    pLeft.add(left.getName());

                    if (pLeft.size() < 6) {
                        Core.changeState(GameState.PREMEETUP);
                        Core.setTicks(pre_meetup);
                        // todo
                        // begin meet up
                    }
                }
            }
        }
        if (Core.getState() == GameState.PREMEETUP) {
            if (Core.getTicks() == 0) {
                Core.changeState(GameState.MEETUP);
                Core.setTicks(meet_up);

                Bukkit.broadcastMessage("§7All remaining teams have been teleported to the meet up location!");

                for (Player left : Bukkit.getOnlinePlayers()) {
                    if (TeamManager.getPlayerTeam(left) != TeamManager.getTeam("Spectators")) {
                        left.sendMessage("§7You have been teleported to the meet up location! Good luck!");
                        if (left.getInventory().contains(Material.SKULL)) {
                            if (left.getHealth() == 20) {
                                left.setHealth(left.getHealth() + 2);
                            }
                        }
                    }
                }

                // todo
                // teleport players to meet up point
            }
        }
        if (Core.getState() == GameState.MEETUP) {
            if (Core.getTicks() == 0) {
                Core.changeState(GameState.ENDING);
                Core.setTicks(result_timer);
                Bukkit.broadcastMessage("No clear winner has been found! Game has ended with more than one victorious team!");
            }
        }
    }
}
