package PlayerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PlayerList implements Serializable {
    private final List<Player>plist;
    private int player_count;

    public PlayerList()
    {
        plist = new ArrayList<>();
        player_count = 0;
    }


    public List<Player> Search_players(String name, int choice)
    {
        List<Player> temp = new ArrayList<>();
        if(choice == 1)
        {
            for(int i=0; i<player_count; i++)
            {
                if(name.equals(plist.get(i).getName().toLowerCase()))
                {
                    temp.add(plist.get(i));
                }
            }
        }
        if(choice == 3)
        {
            for(int i=0; i<player_count; i++)
            {
                if(name.equals(plist.get(i).getPosition().toLowerCase()))
                {
                    temp.add(plist.get(i));
                }
            }
        }
        return temp;
    }

    public List<Player> Search_players(String club, String country)
    {
        List<Player> temp = new ArrayList<>();
        for(int i=0; i<player_count; i++)
        {
            if(club.equals(plist.get(i).getClub().toLowerCase()) && country.equals(plist.get(i).getCountry().toLowerCase()))
            {
                temp.add(plist.get(i));
            }
        }
        return temp;
    }

    public List<Player> Search_players(int salary_up, int salary_down)
    {
        List<Player> temp = new ArrayList<>();
        if (salary_up > salary_down)
        {
            boolean found = false;
            for (int i = 0; i < player_count; i++) {
                if (plist.get(i).getSalary() >= salary_down && plist.get(i).getSalary() <= salary_up) {
                    temp.add(plist.get(i));
                }
            }
        }
        return temp;
    }


    public HashMap<String,Integer> CountCountryPlayer()
    {
        HashMap<String, Integer> country_map = new HashMap<>();
        String key;

        for(int i=0; i<player_count; i++)
        {
            if(country_map.containsKey(plist.get(i).getCountry()))
            {
                key = plist.get(i).getCountry();
                country_map.put(key, country_map.get(key)+1);
            }
            else
            {
                country_map.put(plist.get(i).getCountry(),1);
            }
        }
        return country_map;
    }

    public List<Player> MaxSalaryPlayer(String club)
    {
        List<Player> temp = new ArrayList<>();
        boolean found = false;

        for(int i=0; i<player_count; i++)
        {
            if(club.equals(plist.get(i).getClub().toLowerCase()))
            {
                found = true;
            }
        }

        if(found) {
            int max_sal = 0;
            for (int i = 0; i < player_count; i++) {
                if (club.equals(plist.get(i).getClub().toLowerCase())) {
                    if (plist.get(i).getSalary() >= max_sal) {
                        max_sal = plist.get(i).getSalary();
                    }
                }
            }

            for (int i = 0; i < player_count; i++) {
                if ((plist.get(i).getSalary() == max_sal) && (plist.get(i).getClub().equalsIgnoreCase(club))) {
                    temp.add(plist.get(i));
                }
            }
        }
        return temp;
    }

    public List<Player> MaxAgePlayer(String club)
    {
        List<Player> temp = new ArrayList<>();
        boolean found = false;

        for(int i=0; i<player_count; i++)
        {
            if(club.equals(plist.get(i).getClub().toLowerCase()))
            {
                found = true;
            }
        }

        if(found) {
            int max_age = 0;
            for (int i = 0; i < player_count; i++) {
                if (club.equals(plist.get(i).getClub().toLowerCase())) {
                    if (plist.get(i).getAge() >= max_age) {
                        max_age = plist.get(i).getAge();
                    }
                }
            }

            for (int i = 0; i < player_count; i++) {
                if ((plist.get(i).getAge() == max_age) && (plist.get(i).getClub().equalsIgnoreCase(club))) {
                    temp.add(plist.get(i));
                }
            }
        }
        return temp;
    }

    public List<Player> MaxHeightPlayer(String club)
    {
        List<Player> temp = new ArrayList<>();
        boolean found = false;

        for(int i=0; i<player_count; i++)
        {
            if(club.equals(plist.get(i).getClub().toLowerCase()) && (plist.get(i).getClub().equalsIgnoreCase(club)))
            {
                found = true;
            }
        }

        if(found) {
            Double max_height = 0.0;
            for (int i = 0; i < player_count; i++) {
                if (club.equals(plist.get(i).getClub().toLowerCase())) {
                    if (plist.get(i).getHeight() >= max_height) {
                        max_height = plist.get(i).getHeight();
                    }
                }
            }

            for (int i = 0; i < player_count; i++) {
                if ((plist.get(i).getHeight() == max_height) && (plist.get(i).getClub().equalsIgnoreCase(club))) {
                    temp.add(plist.get(i));
                }
            }
        }
        return temp;
    }

    public int YearlySalary(String club)
    {
        int sum = 0;
        for(int i=0; i<player_count; i++)
        {
            if(club.equals(plist.get(i).getClub().toLowerCase())) {
                sum += plist.get(i).getSalary();
            }
        }
        return 52*sum;
    }


    public boolean IsPresent(String name)
    {
        for(int i=0; i<player_count; i++)
        {
            if(name.equals(plist.get(i).getName().toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }


    public synchronized void AddPlayer(String name, String country,int age,Double height,String club,String position,int number,int salary)
    {
        Player p1 = new Player(toUpper(name), toUpper(country), age, height, toUpper(club), toUpper(position), number, salary);
        plist.add(p1);
        player_count++;
    }

    public String toUpper(String s)
    {
        String []words = s.split(" ");
        String key = "";
        for(String word: words)
        {
            if(!word.isEmpty())
            {
                word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                key += word + " ";
            }
        }
        return key.trim();
    }

    public void print()
    {
        for(int i=0; i<player_count; i++)
        {
            System.out.println(plist.get(i));
        }
    }

    //public PlayerList getPlayers()
    public List<Player> getPlayers()
    {
        return plist;
    }

    public void remove(String name)
    {
        for(int i=0; i<player_count; i++)
        {
            if(plist.get(i).getName().equalsIgnoreCase(name))
            {
                plist.remove(i);
                player_count--;
            }
        }
    }

    public List<Map.Entry<String, Integer>> SortedByCountry(HashMap<String, Integer> map)
    {
        List<Map.Entry<String, Integer>> maplist = new ArrayList<>(map.entrySet());
        int m, n;
        for(m=0; m<maplist.size(); m++)
        {
            for(n=m+1; n<maplist.size(); n++)
            {
                if(maplist.get(m).getValue()<=maplist.get(n).getValue())
                {
                    Map.Entry<String, Integer>temp = maplist.get(m);
                    maplist.set(m,maplist.get(n));
                    maplist.set(n,temp);

                }
            }
        }

        return maplist;
    }
}
