package dev.creeper.uhc.Teams.AllTeams;

import dev.creeper.uhc.Teams.TeamInterface;

import java.util.ArrayList;
import java.util.List;

public class Team7 implements TeamInterface {

    private List<String> members = new ArrayList<String>();

    @Override
    public String getTeamName() {
        return "§1Team Seven";
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
