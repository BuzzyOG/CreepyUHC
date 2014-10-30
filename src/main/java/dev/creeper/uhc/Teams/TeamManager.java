package dev.creeper.uhc.Teams;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {

    public static List<TeamInterface> allTeams = new ArrayList<TeamInterface>();

    public static List<TeamInterface> getTeams() {
        return allTeams;
    }

    public static TeamInterface getTeam(String name) {

        for (TeamInterface ti : getTeams()) {
            if (ti.getTeamName().equalsIgnoreCase(name)) {
                return ti;
            }
        }
        return null;
    }

    public static TeamInterface getPlayerTeam(Player p) {
        for (TeamInterface team : getTeams()) {
            if (team.getMembers().contains(p.getName())) {
                return team;
            }
        }
        return null;
    }
}
