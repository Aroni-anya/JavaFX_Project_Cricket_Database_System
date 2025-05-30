package PlayerData;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

public class FileOperations {
    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "players.txt";
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private static final String MARKET_FILE_NAME = "Market.txt";

    public static synchronized void loadplayers(PlayerList playerlist) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String []arr = line.split(",");

            String name = arr[0];
            String country = arr[1];
            int age = Integer.parseInt(arr[2]);
            Double height = Double.parseDouble(arr[3]);
            String club = arr[4];
            String position = arr[5];
            int number = arr.length > 6 && !arr[6].isEmpty() ? Integer.parseInt(arr[6]) : -1;
            int salary = Integer.parseInt(arr[7]);

            playerlist.AddPlayer(name,country,age,height,club,position,number,salary);
        }
        br.close();
    }

    public static synchronized void loadmarketplayers(List<Player> playerlist) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(MARKET_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String []arr = line.split(",");

            String name = arr[0];
            String country = arr[1];
            int age = Integer.parseInt(arr[2]);
            Double height = Double.parseDouble(arr[3]);
            String club = arr[4];
            String position = arr[5];
            int number = arr.length > 6 && !arr[6].isEmpty() ? Integer.parseInt(arr[6]) : -1;
            int salary = Integer.parseInt(arr[7]);

            Player p1 = new Player(name,country, age, height, club, position, number, salary);

            playerlist.add(p1);
        }
        br.close();
    }



    public static synchronized void savePlayers(PlayerList playerlist) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));

        for (Player player : playerlist.getPlayers()) {
            bw.write(player.getName());
            bw.write("," +player.getCountry());
            bw.write("," +player.getAge());
            bw.write("," +player.getHeight());
            bw.write("," +player.getClub());
            bw.write("," + player.getPosition());
            bw.write("," + ((player.getNumber()==-1) ? "" : (player.getNumber()+"")));
            bw.write("," + player.getSalary());

            bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public static synchronized void savesellablePlayers(List<Player> playerlist) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(MARKET_FILE_NAME));

        for (Player player : playerlist) {
            bw.write(player.getName());
            bw.write("," +player.getCountry());
            bw.write("," +player.getAge());
            bw.write("," +player.getHeight());
            bw.write("," +player.getClub());
            bw.write("," + player.getPosition());
            bw.write("," + ((player.getNumber()==-1) ? "" : (player.getNumber()+"")));
            bw.write("," + player.getSalary());

            bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public static synchronized void loadClubCredentials(HashMap<String, String> clubCredentials) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS_FILE_NAME));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(",");
            String clubName = arr[0];
            String password = arr[1];
            clubCredentials.put(clubName, password);  // Add to the provided HashMap
        }
        br.close();
    }
}

