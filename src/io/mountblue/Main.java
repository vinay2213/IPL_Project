package io.mountblue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final int MATCH_ID = 0;
    private static final int MATCH_YEAR = 1;
    private static final int MATCH_CITY = 2;
    private static final int MATCH_DATE = 3;
    private static final int TEAM_1 = 4;
    private static final int TEAM_2 = 5;
    private static final int TOSS_WON = 6;
    private static final int TOSS_DECISION = 7;
    private static final int RESULT = 8;
    private static final int DL_APPLIED = 9;
    private static final int TEAM_WON = 10;
    private static final int WIN_BY_RUNS = 11;
    private static final int WIN_BY_WICKETS = 12;
    private static final int PLAYER_OF_MATCH = 13;
    private static final int MATCH_VENUE = 14;
    private static final int UMPIRE_1 = 15;
    private static final int UMPIRE_2 = 16;

    private static final int INNING = 1;
    private static final int BATTING_TEAM = 2;
    private static final int BOWLING_TEAM = 3;
    private static final int OVER = 4;
    private static final int BALL = 5;
    private static final int BATSMAN = 6;
    private static final int NON_STRIKER = 7;
    private static final int BOWLER = 8;
    private static final int IS_SUPER_OVER = 9;
    private static final int WIDE_RUNS = 10;
    private static final int BYE_RUNS = 11;
    private static final int LEG_BYE_RUNS = 12;
    private static final int NO_BALL_RUNS = 13;
    private static final int PENALTY_RUNS = 14;
    private static final int BATSMAN_RUNS = 15;
    private static final int EXTRA_RUNS = 16;
    private static final int TOTAL_RUNS = 17;
    private static final int PLAYER_DISMISSED = 18;
    private static final int DISMISSAL_KIND = 19;
    private static final int FIELDER = 20;

    private static void topEconomicalBowlers(List<Delivery> deliveries, List<Match> matches) {
        HashMap<String, Integer> totalRunsByBowlers = new HashMap<>();
        HashMap<String, Integer> totalDeliveriesByBowlers = new HashMap<>();
        HashMap<String, Double> economyByBowlers = new HashMap<>();
        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                for (Delivery delivery : deliveries) {
                    if(delivery.getMatch_id().equals(match.getId())) {
                        String bowler = delivery.getBowler();
                        String runs = delivery.getTotal_runs();
                        totalRunsByBowlers.put(bowler, totalRunsByBowlers.getOrDefault(bowler, 0)+Integer.parseInt(runs));
                        totalDeliveriesByBowlers.put(bowler, totalDeliveriesByBowlers.getOrDefault(bowler, 0)+1);
                    }
                }
            }
        }
        for (String bowler : totalRunsByBowlers.keySet()) {
            int runs = totalRunsByBowlers.get(bowler);
            int delivery = totalDeliveriesByBowlers.get(bowler);
            double economy = ((double) runs/(double) delivery )*6;
            economyByBowlers.put(bowler, economy);
        }
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(economyByBowlers.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for(Map.Entry<String, Double> entry : entryList){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        System.out.println("Top economical bowlers in 2015 ");
        System.out.println(sortedMap);
    }

    private static void extraRunsConcededPerTeam(List<Delivery> deliveries, List<Match> matches) {
        Map<String, Integer> extraRunsPerTeam = new HashMap<>();
        for (Match match : matches) {
            if(match.getSeason().equals("2016")) {
                for (Delivery delivery : deliveries) {
                    if (match.getId().equals(delivery.getMatch_id())) {
                        extraRunsPerTeam.put(delivery.getBowling_team(), extraRunsPerTeam.getOrDefault(delivery.getBowling_team(), 0)+Integer.parseInt(delivery.getExtra_runs()));
                    }
                }
            }
        }
        System.out.println("Extra runs conceded per team in 2016: ");
        System.out.println(extraRunsPerTeam);
    }

    private static List<Delivery> getDeliveriesData() throws IOException {
        List<Delivery> deliveries = new ArrayList<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader("deliveries.csv"));
        boolean mover = false;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (mover) {
                Delivery delivery = new Delivery();
                delivery.setMatch_id(data[MATCH_ID]);
                delivery.setInning(data[INNING]);
                delivery.setBatting_team(data[BATTING_TEAM]);
                delivery.setBowling_team(data[BOWLING_TEAM]);
                delivery.setOver(data[OVER]);
                delivery.setBall(data[BALL]);
                delivery.setBatsman(data[BATSMAN]);
                delivery.setNon_striker(data[NON_STRIKER]);
                delivery.setBowler(data[BOWLER]);
                delivery.setIs_super_over(data[IS_SUPER_OVER]);
                delivery.setWide_runs(data[WIDE_RUNS]);
                delivery.setBye_runs(data[BYE_RUNS]);
                delivery.setLegbye_runs(data[LEG_BYE_RUNS]);
                delivery.setNoball_runs(data[NO_BALL_RUNS]);
                delivery.setPenalty_runs(data[PENALTY_RUNS]);
                delivery.setBatsman_runs(data[BATSMAN_RUNS]);
                delivery.setExtra_runs(data[EXTRA_RUNS]);
                delivery.setTotal_runs(data[TOTAL_RUNS]);
                if (data.length - 1 >= PLAYER_DISMISSED)
                    delivery.setPlayer_dismissed(data[PLAYER_DISMISSED]);
                if (data.length - 1 >= DISMISSAL_KIND)
                    delivery.setDismissal_kind(data[DISMISSAL_KIND]);
                if (data.length - 1 >= FIELDER)
                    delivery.setFielder(data[FIELDER]);
                deliveries.add(delivery);
            }
            mover = true;
        }
        return deliveries;
    }

    private static void numberOfMatchesWonByAllTeams(List<Match> matches) {
        HashMap<String, Integer> matchesWonByAllTeams = new HashMap<>();
        for(Match match : matches) {
            String winner = match.getWinner();
            if(winner != "") {
                matchesWonByAllTeams.put(winner, matchesWonByAllTeams.getOrDefault(winner, 0) + 1);
            }
            else{
                matchesWonByAllTeams.put("No result", matchesWonByAllTeams.getOrDefault("No result", 0) + 1);
            }
        }
        System.out.println("Number of matches won by all teams: ");
        System.out.println(matchesWonByAllTeams);
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        HashMap<String, Integer> matchesPlayedPerYear = new HashMap<>();
        for (Match match : matches) {
            String season = match.getSeason();
            matchesPlayedPerYear.put(season, matchesPlayedPerYear.getOrDefault(season,0)+1);
        }
        System.out.println("Number of matches played per year: ");
        System.out.println(matchesPlayedPerYear);
    }

    public static List<Match> getMatchesData() throws IOException {
        List<Match> matches = new ArrayList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("matches.csv"));
        boolean skipper = false;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if(skipper) {
                Match match = new Match();
                match.setId(data[MATCH_ID]);
                match.setSeason(data[MATCH_YEAR]);
                match.setVenue(data[MATCH_CITY]);
                match.setDate(data[MATCH_DATE]);
                match.setTeam1(data[TEAM_1]);
                match.setTeam2(data[TEAM_2]);
                match.setToss_winner(data[TOSS_WON]);
                match.setToss_decision(data[TOSS_DECISION]);
                match.setResult(data[RESULT]);
                match.setDl_applied(data[DL_APPLIED]);
                match.setWinner(data[TEAM_WON]);
                match.setWin_by_runs(data[WIN_BY_RUNS]);
                match.setWin_by_wickets(data[WIN_BY_WICKETS]);
                match.setPlayer_of_match(data[PLAYER_OF_MATCH]);
                match.setVenue(data[MATCH_VENUE]);
                if (data.length - 1 >= UMPIRE_1)
                    match.setUmpire1(data[UMPIRE_1]);
                if (data.length - 1 >= UMPIRE_2)
                    match.setUmpire2(data[UMPIRE_2]);
                matches.add(match);
            }
            skipper = true;
        }
        return matches;
    }

    public static void main(String[] args) throws IOException
    {
        List<Match> matches = getMatchesData();
        findNumberOfMatchesPlayedPerYear(matches);
        numberOfMatchesWonByAllTeams(matches);
        List<Delivery> deliveries = getDeliveriesData();
        extraRunsConcededPerTeam(deliveries, matches);
        topEconomicalBowlers(deliveries, matches);
    }
}
