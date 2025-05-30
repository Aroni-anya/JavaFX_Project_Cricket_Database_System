package DTO;

import PlayerData.Player;

import java.io.Serializable;

public class BuyReqDTO implements Serializable {
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    private Player player;
    private String clubname;
}
