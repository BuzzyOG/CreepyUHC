package dev.creeper.uhc.Teams.AllTeams;

import dev.creeper.uhc.Teams.TeamInterface;

import java.util.ArrayList;
import java.util.List;

public class Team4 implements TeamInterface {

    private List<String> members = new ArrayList<String>();

    @Override
    public String getTeamName() {
        return "§aTeam Four";
    }

    @Override
    public List<String> getMembers() {
        return members;
    }

    @Override
    public int getPlayersLeft() {
        return members.size();
    }
}
