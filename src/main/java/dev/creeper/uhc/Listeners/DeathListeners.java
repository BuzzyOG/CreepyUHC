package dev.creeper.uhc.Listeners;

import dev.creeper.uhc.Core;
import dev.creeper.uhc.Teams.TeamInterface;
import dev.creeper.uhc.Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class DeathListeners implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        TeamInterface playersTeam = TeamManager.getPlayerTeam(p);

        playersTeam.getMembers().remove(p.getName());
        TeamManager.getTeam("Spectators").getMembers().add(p.getName());
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.playSound(online.getLocation(), Sound.AMBIENCE_THUNDER, 1, 10);
        }

        if (playersTeam.getMembers().isEmpty()) {
            Bukkit.broadcastMessage("§7The team " + playersTeam.getTeamName() + " §7has been eliminated from this round!");
        }

        if (p.getKiller() == null) {
            Bukkit.broadcastMessage("§7The player §c" + p.getName() + " §7from " + playersTeam.getTeamName() + " §7has died!");
            for (Player left : Bukkit.getOnlinePlayers()) {
                // todo
                // check if killer's team is only team left
                // end game if so
            }

        } else {
            Player killer = p.getKiller();

            Bukkit.broadcastMessage("§c" + p.getName() + " §7from " + playersTeam.getTeamName() + " §7has been killed by §c" +
                    killer.getName() + " §7 from " + TeamManager.getPlayerTeam(killer).getTeamName());

            killer.sendMessage("§7The skull of " + p.getName() + " has been dropped upon their death!");

            ItemStack playerSkull = new ItemStack(Material.SKULL, 1);
            ItemMeta skullMeta = playerSkull.getItemMeta();
            skullMeta.setDisplayName("§7§o" + p.getName() + "'s Skull");
            playerSkull.setItemMeta(skullMeta);

            event.getDrops().add(playerSkull);
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        final Player p = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(Core.plugin, new Runnable() {
            @Override
            public void run() {
                p.setAllowFlight(true);
                p.setFlying(true);
                p.setGameMode(GameMode.ADVENTURE);
                p.setVelocity(new Vector(0, 2, 0));

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (TeamManager.getPlayerTeam(online) != TeamManager.getTeam("Spectators")) {
                        Bukkit.getPlayerExact(online.getName()).hidePlayer(p);
                    }
                }

            }
        }, 2L);
    }
}
