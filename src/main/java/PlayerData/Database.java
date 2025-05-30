package PlayerData;

import Network.Server;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  Database implements Serializable {



    private PlayerList allPlayers = new PlayerList();
    private List<Player> sellablePlayers = new ArrayList<>();
    private HashMap<String, String> clubCredentials = new HashMap<>();
    private HashMap<String, List<Player>> playersByClub = new HashMap<>();


    public Database() {

        allPlayers = new PlayerList();
        sellablePlayers = new ArrayList<>();
        clubCredentials = new HashMap<>();
        playersByClub = new HashMap<>();
        //this.clublist = new ArrayList<>();
    }

    public synchronized PlayerList getAllPlayers() {
        return allPlayers;
    }

    public synchronized HashMap<String, String> getClubCredentials() {
        return clubCredentials;
    }

    public synchronized void loadFromFile() throws Exception {

        FileOperations.loadplayers(allPlayers);

        FileOperations.loadmarketplayers(sellablePlayers);

        FileOperations.loadClubCredentials(clubCredentials);


        for (Player player : allPlayers.getPlayers()) {
            playersByClub.putIfAbsent(player.getClub(), new ArrayList<>());
            playersByClub.get(player.getClub()).add(player);
        }
    }

    public  List<Player> getPlayersByClub(String clubname) {
        synchronized (playersByClub) {
            return new ArrayList<>(playersByClub.getOrDefault(clubname, new ArrayList<>()));
        }
    }

    public synchronized void addSellablePlayer(Player player) {
        if (!sellablePlayers.contains(player)) {
            sellablePlayers.add(player);
        }
    }


    public synchronized void removeSellablePlayer(Player player) {
        sellablePlayers.remove(player);
    }


    public synchronized List<Player> getSellablePlayers() {
        return sellablePlayers;
    }

    // Process a buy request (transfer a player to a new club)
    public void buyPlayer(Player boughtplayer, String boughtbyclub) throws Exception {

        Player playerToBuy = boughtplayer;
        sellablePlayers.remove(playerToBuy);

        String currentClub = boughtplayer.getClub();

        if (currentClub != null && playersByClub.containsKey(currentClub)) {
            playersByClub.get(currentClub).remove(boughtplayer);
            System.out.println("Player removed from their current club: " + currentClub);
        }


        if (playerToBuy != null) {
            playerToBuy.setClub(boughtbyclub);
            playersByClub.get(boughtbyclub).add(boughtplayer);

            for(Player p: allPlayers.getPlayers())
            {
                if(p.getName().equalsIgnoreCase(playerToBuy.getName()))
                {
                    p.setClub(boughtbyclub);
                }
            }
        }

        FileOperations.savePlayers(Server.database.getAllPlayers());
    }

    public void setAllPlayers(PlayerList playerlist) {
        allPlayers = playerlist;
    }

    public void clear() {
        allPlayers.getPlayers().clear();
        sellablePlayers.clear();
        playersByClub.clear();
        clubCredentials.clear();
    }

}

