package DTO;

import java.io.Serializable;

public class GetPlayerDTO implements Serializable {
    String clubname;

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
}
