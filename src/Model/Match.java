package Model;

public class Match
{
    public int id;
    public int year;
    public String team1;
    public String team2;
    public String winner;

    public Match(int id, int year, String team1, String team2, String winner)
    {
     this.id = id;
     this.year = year;
     this.team1 = team1;
     this.team2 = team2;
     this.winner = winner;
    }
}
