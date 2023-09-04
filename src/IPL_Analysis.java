import Model.Delivery;
import Model.Match;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IPL_Analysis {
    String line = "";
    Map<Integer, Match> matchMap = new HashMap<>();
    Map<Integer, Delivery> deliveryMap = new HashMap<>();
    public void readMatchData() throws Exception
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/mountblue/IPLProject/matches.csv"));
            boolean flag = false;
            while((line=br.readLine()) != null)
            {
                String[] data = line.split(",");
                if (flag)
                {
                    Match match = new Match(Integer.parseInt(data[0].trim()), Integer.parseInt(data[1]), data[4], data[5], data[10]);
                    matchMap.put(match.id, match);
                }
                flag = true;
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        catch (IOException e) {
            throw new IOException(e);
        }
        line = "";
    }
}
