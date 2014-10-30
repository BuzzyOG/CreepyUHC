package dev.creeper.uhc.Commands;

import dev.creeper.uhc.Teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpecTP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spectate")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§7This command can only be performed by players!");
                return true;
            }
            Player p = (Player) sender;
            if (args.length > 1 || args.length == 0) {
                p.sendMessage("Invalid arguments! Usage: /spectate <player>!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage("§7Could not find player §c" + target.getName() + " §7!");
                return true;
            }
            if (TeamManager.getPlayerTeam(target) == TeamManager.getTeam("Spectators")) {
                p.sendMessage("§7That player is also spectating!");
                return true;
            }
            if (TeamManager.getPlayerTeam(p) != TeamManager.getTeam("Spectators")) {
                p.sendMessage("§7This is a spectator only command!");
                return true;
            }
            Location targetLoc = target.getLocation();
            p.teleport(targetLoc);
            p.sendMessage("§7You are now spectating §c" + target.getName() + "§7!");
            p.setVelocity(new Vector(0, 2, 0));
            p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
        }
        return false;
    }
}
