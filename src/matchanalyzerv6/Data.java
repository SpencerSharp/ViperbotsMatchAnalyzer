//===============Match Analyzer v6====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 4/4/15
//@Contributing Teams: 4545, 4546
//@File: Data.java (Holds extra data for the ranking display)
//
//======================================================

package matchanalyzerv6;

import java.util.ArrayList;

public class Data {
    
    public final String teamName;
    public final String range;
    public final int STDA;
    public final int Consistency;
    public final int partnerMMR;
    public final int QP;
    public final int RP;
    
    public Data(Team t)
    {
        ArrayList<Integer> scores = t.getScores();
        MatchRanker mr = new MatchRanker(scores);
        int avg = mr.getAvg();
        int std = mr.getConsistency();
        teamName = t.getTeamName();
        int min = avg - std;
        if (min < 0)
            min = 0;
        range = min + "-" + (avg + std);
        STDA = mr.getSTDA();
        Consistency = mr.getConsistency();
        partnerMMR = t.getPartnerMMR();
        QP = t.getQP();
        RP = t.getRP();
    }
    
}
