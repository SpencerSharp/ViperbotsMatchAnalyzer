//===============Match Analyzer v2.3====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 1/24/15
//@Contributing Teams: 4545 Ouroboros and 4546 SnakeByte
//@File: Team.java (Team Class)
//
//======================================================

package matchanalyzerv6;

import java.util.ArrayList;
import java.util.TreeMap;

public class Team 
{
    private final String TeamNum;
    private String TeamName;
    private Integer TeamMMR;
    private Integer PartnerMMR;
    private ArrayList<Integer> scores;
    private ArrayList<Team> Partners;
    
    public Team()
    {
        TeamNum = "";
        TeamName = "";
        scores = new ArrayList<Integer>();
        TeamMMR = 1500;
    }
    
    public Team(String s, Integer score)
    {
        TeamNum = s;
        TeamName = "";
        scores = new ArrayList<Integer>();
        scores.add(score);
        TeamMMR = 1500;
    }
    
    public Team(Integer num)
    {
        TeamNum = num.toString();
        TeamName = "";
        scores = new ArrayList<Integer>();
        TeamMMR = 1500;
    }
    
    public boolean equals(Team thing)
    {
        if(Integer.parseInt(TeamNum) == thing.getTeamNum())
            return true;
        return false;
    }
    
    public void setName(String s)
    {
        TeamName = s;
    }
    
    public void setMMR(int num)
    {
        TeamMMR = num;
    }
    
    private void addPartner(Team t)
    {
        Partners.add(t);
    }
    
    private void assignPartners(ArrayList<Match> ray, ArrayList<Team> list)
    {
        for (Match round : ray)
        {
            if (round.red1().equals(TeamNum))
                addPartner(getTeam(list, round.red2()));
            else if (round.red2().equals(TeamNum))
                addPartner(getTeam(list, round.red1()));
            else if (round.blue1().equals(TeamNum))
                addPartner(getTeam(list, round.blue2()));
            else
                addPartner(getTeam(list, round.blue1()));
        }
    }
    
    public void addScore(Integer i)
    {
        scores.add(i);
    }
    
    public void getAvgPartners(ArrayList<Match> ray, ArrayList<Team> list)
    {
        Partners = new ArrayList<Team>();
        assignPartners(ray, list);
        int sum = 0;
        int count = Partners.size();
        for (Team item : Partners)
        {
            sum += item.getMMR();
        }
        PartnerMMR = sum / count;
    }
    
    private Team getTeam(ArrayList<Team> ray, String s)
    {
        for (Team item : ray)
        {
            if(item.getTeamString().equals(s))
                return item;
        }
        return null;
    }
    
    public String getTeamName()
    {
        return TeamName;
    }
    
    public Integer getMMR()
    {
        return TeamMMR;
    }

    public Double getRatio()
    {
        Data data = new Data(this);
        int MMR = TeamMMR;
        int score = data.STDA;
        int con = data.Consistency;
        Double a = 0.0;
        a = ((score*0.5+MMR*0.5)*2)/(Math.pow(con, 1.0/4));
        Double b = 0.0;
        b = (a+score)/2;
        return b;
    }
    
    public Integer getTeamNum()
    {
        return Integer.parseInt(TeamNum);
    }
    
    public String getTeamString()
    {
        return TeamNum;
    }

    public ArrayList<Integer> getScores()
    {
        return scores;
    }
    
    public Integer getPartnerMMR()
    {
        return PartnerMMR;
    }
    
    public String toString()
    {
        return TeamNum;
    }
}