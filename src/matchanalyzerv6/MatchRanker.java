//===============Match Analyzer v2.3====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 1/24/15
//@Contributing Teams: 4545 Ouroboros and 4546 SnakeByte
//@File: MatchRanker.java (Provides methods to analyze score data)
//
//======================================================

package matchanalyzerv6;

import java.util.ArrayList;

public class MatchRanker {
	
        private ArrayList<Integer> scores;

        public MatchRanker()
        {
        }

        public MatchRanker(ArrayList<Integer> s)
        {
            scores = s;
        }

        public int getAvg()
        {
            int sum = 0, avg = 0;
            for(int i = 1; i <= scores.size();i++)
            {
                sum+=scores.get(i-1);
                avg = sum / i;
            }
            return avg;
        }

    	private Double std()
    	{
            int avg = getAvg();
            ArrayList<Integer> vars = new ArrayList<Integer>();

            //get differences between each score and avg
            for(Integer score : scores)
            {
                vars.add(Math.abs(score - avg));
            }

            Double var = 0.0;
            for (Integer item : vars)
            {
                var += Math.pow(item, 2);
            }
            Double std = Math.sqrt(var/scores.size());
            return std;
    	}
    	
    	public int getConsistency()
    	{
            int avg = getAvg();
            ArrayList<Integer> vars = new ArrayList<Integer>();

            //get differences between each score and avg
            for(Integer score : scores)
            {
                vars.add(Math.abs(score - avg));
            }

            Double var = 0.0;
            for (Integer item : vars)
            {
                var += item;
            }
            Double std = var/scores.size();
            return std.intValue();
    	}
    	
    	public int getSTDA()
    	{
            Double STDA = 0.0;
            Double sum = 0.0;
            Double count = 0.0;
            int avg = getAvg();
            Double STD = std();
            Double stdLow = (avg - STD);
            Double stdHigh = (avg + STD);
            for (int i = 0; i < scores.size(); i++)
            {
                    if(scores.get(i) > stdLow && scores.get(i) < stdHigh)
                    {
                            sum += scores.get(i);
                            count++;
                    }
            }
            STDA = sum / count;
            int a = (int) Math.round(STDA);
            return a;
    	}
        
        public int calcMMR(int curMMR, int allyMMR, int score)
        {
            double newMMR = (double) curMMR;
            double dAllyMMR = (double) allyMMR;
            double dScore = (double) score;
            double divisor = newMMR;
            double subtract = dAllyMMR * dAllyMMR;
            subtract /= 10000;
            //subtract = subtract / divisor;
            //subtract = subtract / 1100000;
            if(curMMR < 1200)
                divisor = 1200.0 / 1500.0;
            else if(curMMR < 2000)
                divisor = newMMR / 2000;
            else
                divisor = newMMR / 1500;
            //System.out.println(divisor);
            double add = score * 44;
            //System.out.println(add);
            add = add * add;
            //System.out.println(add);
            add = Math.pow(add, 1.0/3);
            add = add / divisor;
            //System.out.println(add);
           // if(divisor < 1)
             //   add = add * (1/divisor);
            //else
              //  add = add / divisor;
            //System.out.println(newMMR);
            //System.out.println(add);
            newMMR += add;
            //System.out.println(newMMR);
            newMMR -= subtract;
            //System.out.println(newMMR);
            int finalMMR = (int) Math.round(newMMR);
            return finalMMR;
        }
        
        public String matchOdds(int teamNum, Team blue1, Team blue2, Team red1, Team red2)
        {
            double odds = 0;
            if(teamNum == blue1.getTeamNum())
            {
                odds = red1.getMMR() + red2.getMMR();
                odds -= blue2.getMMR();
                odds /= 100;
            }
            return odds + "%";
        }
        
        public int matchDifficulty(int allyMMR, int opp1MMR, int opp2MMR)
        {
            double difficulty = opp1MMR + opp2MMR;
            difficulty -= allyMMR;
            difficulty /= 100;
            return (int) difficulty;
        }
}