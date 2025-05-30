package Network;

import DTO.*;
import PlayerData.Database;
import PlayerData.FileOperations;
import PlayerData.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public List<SocketWrapper> clientlist;


    public ReadThreadServer(SocketWrapper socketWrapper, List<SocketWrapper> clist) {

        this.clientlist = clist;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        //Server.database.clear();
                        //Server.database.loadFromFile();
                        LoginDTO loginDTO = (LoginDTO) o;
                        String clubname = loginDTO.getUserName();
                        String password = Server.database.getClubCredentials().get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));



                        socketWrapper.setClubName(clubname);
                            try {
                                socketWrapper.write(loginDTO);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                    }

                    if(o instanceof SellReqDTO){
                        new Thread(()-> {
                            //System.out.println("Enter sellreq dto in readthreadserver");
                            SellReqDTO sellreqdto = (SellReqDTO) o;
                            Player player = sellreqdto.getPlayer();
                            String clubname = sellreqdto.getClubname();
                            //System.out.println("Clubname in sellreqdto" + clubname);

                            Server.database.addSellablePlayer(player);


                            SellReqResponseDTO sellreqresDTO = new SellReqResponseDTO();
                            sellreqresDTO.setMarketList(Server.database.getSellablePlayers());

                            try {
                                for(SocketWrapper n: clientlist){
                                    n.write(sellreqresDTO);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }).start();
                    }

                    if(o instanceof GetPlayerDTO)
                    {
                        new Thread(()-> {
                            //System.out.println("Entering getPlayer dto in readthreadserver");
                            GetPlayerDTO getplayerDTO = (GetPlayerDTO) o;
                            String clubname = getplayerDTO.getClubname();

                            SendPlayerDTO sendplayerDTO = new SendPlayerDTO();
                            sendplayerDTO.setPlayerList(Server.database.getPlayersByClub(clubname));
                            sendplayerDTO.setMarketList(Server.database.getSellablePlayers());

                            try {
                                socketWrapper.write(sendplayerDTO);
                                //System.out.println("market playerlist of sendplayerdto insid readthreadserer: "+ sendplayerDTO.getMarketList().size());
                                //System.out.println("Sent player data to client.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                    if(o instanceof BuyReqDTO)
                    {
                        new Thread(()-> {

                            BuyReqDTO buyreqDTO = (BuyReqDTO) o;
                            Player boughtPlayer = buyreqDTO.getPlayer();
                            String buyingClub = buyreqDTO.getClubname();
                            String sellingClub = boughtPlayer.getClub(); // Assuming the player tracks the selling club

                            try {
                                Server.database.buyPlayer(boughtPlayer, buyingClub);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            //System.out.println("Market list after buying: " + Server.database.getSellablePlayers().size());

                            BuyReqResponseDTO buyReqResponseForBuyer = new BuyReqResponseDTO();
                            buyReqResponseForBuyer.setPlayerList(Server.database.getPlayersByClub(buyingClub));
                            buyReqResponseForBuyer.setMarketList(Server.database.getSellablePlayers());

                            BuyReqResponseDTO buyReqResponseForSeller = new BuyReqResponseDTO();
                            buyReqResponseForSeller.setPlayerList(Server.database.getPlayersByClub(sellingClub));
                            buyReqResponseForSeller.setMarketList(Server.database.getSellablePlayers());

                            BuyReqResponseDTO marketUpdateForAll = new BuyReqResponseDTO();
                            marketUpdateForAll.setMarketList(Server.database.getSellablePlayers());
                            marketUpdateForAll.setPlayerList(null); // No player list for generic updates

                            try {
                                synchronized (clientlist) {
                                    for (SocketWrapper client : clientlist) {
                                        if (client.getClubName().equals(buyingClub)) {
                                            client.write(buyReqResponseForBuyer);
                                            //System.out.println("Updated buying club: " + buyingClub);
                                        }
                                        else if (client.getClubName().equals(sellingClub)) {
                                            client.write(buyReqResponseForSeller);
                                            //System.out.println("Updated selling club: " + sellingClub);
                                        }
                                        else {
                                            client.write(marketUpdateForAll);
                                            //System.out.println("Notified market update to other club: " + client.getClubName());
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }).start();
                    }

                    if(o instanceof RefreshDTO)
                    {
                        new Thread(()->{
                            RefreshDTO refreshDTO = (RefreshDTO) o;

                            String clubname = refreshDTO.getClubname();

                            SendPlayerDTO sendplayerDTO = new SendPlayerDTO();
                            sendplayerDTO.setPlayerList(Server.database.getPlayersByClub(clubname));
                            sendplayerDTO.setMarketList(Server.database.getSellablePlayers());


                            try {
                                socketWrapper.write(sendplayerDTO);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
