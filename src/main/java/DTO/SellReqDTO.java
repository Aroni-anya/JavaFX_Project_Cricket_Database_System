package DTO;

import PlayerData.Player;  // Assuming Player is in the PlayerData package
import java.io.Serializable;

public class SellReqDTO implements Serializable {

    private Player player;  // The player being sold by the club
    private double price;
    private String clubname;// The price at which the player is being sold

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    // Constructor
    //public SellReqDTO(Player player, double price) {
//        this.player = player;
//        this.price = price;
//    }
    public SellReqDTO(){}

    // Getter and setter for player
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Getter and setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SellReqDTO{" +
                "player=" + player +
                ", price=" + price +
                '}';
    }
}
