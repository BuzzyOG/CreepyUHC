package dev.creeper.uhc.Utilities;

import dev.creeper.uhc.Core;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Timer implements Runnable {

    public final int lobby_timer = 60;
    public final int pregame_timer = 30;
    public final int no_pvp_timer = 900;
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
                Bukkit.broadcastMessage("§7The round has begun! There is §c" + no_pvp_timer + "")
                // todo
                // begin no pvp stage
            }
        }
        if (Core.getState() == GameState.NOPVP) {

        }
    }
}
