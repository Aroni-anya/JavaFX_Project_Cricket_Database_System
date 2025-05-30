package Network;

import MainPackage.*;
import DTO.*;
import PlayerData.Database;
import PlayerData.Player;
import PlayerData.PlayerList;
import javafx.application.Platform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Main main;
    public static List<Player> clubplayers;// = new ArrayList<>();
    public static List<Player> sellableplayers; //= new ArrayList<>();

    public ReadThreadClient(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getSocketWrapper().read();
                if (o instanceof PlayerList) {
                    PlayerList receivedPlayerList = (PlayerList) o;
                    Main.setAllPlayerlist(receivedPlayerList);
                }
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        System.out.println(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());
                        // the following is necessary to update JavaFX UI components from user created threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        main.showHomePage(loginDTO.getUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert();
                                }

                            }
                        });
                    }
                    if(o instanceof SendPlayerDTO)
                    {
                        new Thread(()-> {
                            //System.out.println("Enter send playerdto in readthreadClient");
                            SendPlayerDTO sendplayerDTO = (SendPlayerDTO) o;
                            clubplayers = sendplayerDTO.getPlayerList();

                            sellableplayers = sendplayerDTO.getMarketList();

                            Main.setSellableplayers(sellableplayers);
                            Main.setPlayerlist(clubplayers);
                        }).start();
                    }

                    if(o instanceof SellReqResponseDTO)
                    {
                        new Thread(()-> {
                            //System.out.println("Enter sell req dto in readthreadClient");
                            SellReqResponseDTO sellreqresDTO = (SellReqResponseDTO) o;

                            sellableplayers = sellreqresDTO.getMarketList();
                            //System.out.println("Received sellable players in readthreadclient after sell: " + sellableplayers.size());

                            Main.setSellableplayers(sellableplayers);
                            Main.setPlayerlist(clubplayers);
                        }).start();
                    }

                    if(o instanceof BuyReqResponseDTO)
                    {
                        new Thread(()-> {

                            //System.out.println("Entered BuyReqResponseDTO in ReadThreadClient");

                            BuyReqResponseDTO buyReqResponseDTO = (BuyReqResponseDTO) o;

                            if (buyReqResponseDTO.getPlayerList() != null) {
                                clubplayers = buyReqResponseDTO.getPlayerList();
                                //System.out.println("Received club players in ReadThreadClient after buy: " + clubplayers.size());
                                Main.setPlayerlist(clubplayers); // Update main player list
                            }

                            if (buyReqResponseDTO.getMarketList() != null) {
                                sellableplayers = buyReqResponseDTO.getMarketList();
                                //System.out.println("Received sellable players in ReadThreadClient after buy: " + sellableplayers.size());
                                Main.setSellableplayers(sellableplayers); // Update main market list
                            }
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
