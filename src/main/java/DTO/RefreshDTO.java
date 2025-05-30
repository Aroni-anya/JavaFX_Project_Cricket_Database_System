package DTO;

import PlayerData.Player;

import java.io.Serializable;
import java.util.List;

public class RefreshDTO implements Serializable {
    private String clubname;

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
}
