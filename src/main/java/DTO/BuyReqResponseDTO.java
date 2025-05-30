package DTO;

import PlayerData.Player;

import java.io.Serializable;
import java.util.List;

public class BuyReqResponseDTO implements Serializable {
    private List<Player> playerlist;
    private List<Player> marketlist;

    public List<Player> getPlayerList() {
        return playerlist;
    }

    public List<Player> getMarketList() { return marketlist; }

    public void setPlayerList(List<Player> playerList) {
        this.playerlist = playerList;
    }

    public void setMarketList(List<Player> marketList) {
        this.marketlist = marketList;
    }
}
