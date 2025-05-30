package DTO;

import PlayerData.Player;

import java.io.Serializable;
import java.util.List;

public class SellReqResponseDTO implements Serializable {
    private List<Player> marketlist;

    public List<Player> getMarketList()
    {
        return marketlist;
    }

    public void setMarketList(List<Player> marketList) {
        this.marketlist = marketList;
    }
}
