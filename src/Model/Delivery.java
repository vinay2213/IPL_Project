package Model;

public class Delivery
{
    public int id;
    public int match_id;
    public String bowling_team;
    public String bowler;
    public int total_runs;
    public int extra_runs;

    public Delivery(int id, int match_id, String bowling_team, String bowler, int total_runs, int extra_runs)
    {
        this.id = id;
        this.match_id = match_id;
        this.bowling_team = bowling_team;
        this.bowler = bowler;
        this.total_runs = total_runs;
        this.extra_runs = extra_runs;
    }
}
