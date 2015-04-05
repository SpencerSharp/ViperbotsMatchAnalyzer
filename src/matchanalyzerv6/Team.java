//===============Match Analyzer v6====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 4/4/15
//@Contributing Teams: 4545, 4546
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
    private int QP;
    private int RP;
    private int defensive;
    
    public Team()
    {
        TeamNum = "";
        TeamName = "";
        scores = new ArrayList<Integer>();
        TeamMMR = 1500;
        QP = 0;
        RP = 0;
        defensive = 0;
    }
    
    public Team(String name)
    {
        TeamNum = name;
        TeamName = "";
        scores = new ArrayList<Integer>();
        TeamMMR = 1500;
        QP = 0;
        RP = 0;
        defensive = 0;
    }
        
    public Team(String s, Integer score)
    {
        TeamNum = s;
        TeamName = "";
        scores = new ArrayList<Integer>();
        scores.add(score);
        TeamMMR = 1500;
        QP = 0;
        RP = 0;
        defensive = 0;
    }
    
    public Team(String s, Integer theirScore, Integer oppScore)
    {
        TeamNum = s;
        TeamName = "";
        scores = new ArrayList<Integer>();
        scores.add(theirScore);
        TeamMMR = 1500;
        QP = 0;
        RP = 0;
        if(theirScore > oppScore)
        {
            QP += 2;
            RP += oppScore;
        }
        else if(oppScore > theirScore)
            RP += theirScore;
        else
        {
            RP++;
            RP += theirScore;
        }
        defensive = 0;
    }
    
    public Team(Integer num)
    {
        TeamNum = num.toString();
        TeamName = "";
        scores = new ArrayList<Integer>();
        TeamMMR = 1500;
        QP = 0;
        RP = 0;
        defensive = 0;
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
    
    public void setQP(int param1)
    {
        QP = param1;
    }
    
    public void setRP(int param)
    {
        RP = param;
    }
    
    public Integer getQP()
    {
        return QP;
    }
    
    public Integer getRP()
    {
        return RP;
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
    
    public void addMatch(Integer ourScore, Integer theirScore)
    {
        scores.add(ourScore);
        if(ourScore > theirScore)
        {
            QP += 2;
            RP += theirScore;
        }
        else if(ourScore < theirScore)
            RP += ourScore;
        else
        {
            QP += 1;
            RP += ourScore;
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
        Double a = 0.0;
        a = ((score*0.4+MMR*0.6)*0.6+data.QP*0.4)/(data.Consistency/5);
        Double b = 0.0;
        b = a*0.6 + score*0.4;
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