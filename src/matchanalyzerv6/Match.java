//===============Match Analyzer v6====================
//
//@Authors: Spencer Sharp and Ethan McCosky
//@Release Date: 4/4/15
//@Contributing Teams: 4545, 4546
//@File: Match.java (Holds match data)
//
//======================================================

package matchanalyzerv6;

public class Match 
{
    private String Red1;
    private String Red2;
    private String Blue1;
    private String Blue2;
    private Integer RedScore;
    private Integer BlueScore;
    
    public Match()
    {
        
    }
    
    public Match(String r1, String r2, String b1, String b2, Double rS, Double bS)
    {
        Red1 = r1;
        Red2 = r2;
        Blue1 = b1;
        Blue2 = b2;
        RedScore = rS.intValue();
        BlueScore = bS.intValue();
    }
    
    public String red1()
    {
        return Red1;
    }
    
    public String red2()
    {
        return Red2;
    }
    
    public String blue1()
    {
        return Blue1;
    }
    
    public String blue2()
    {
        return Blue2;
    }
    
    public Integer getRedScore()
    {
        return RedScore;
    }
    
    public Integer getBlueScore()
    {
        return BlueScore;
    }
    
    public String toString()
    {
        return "[ " + Red1 + " " + Red2 + " ] vs. [ " + Blue1 + " " + Blue2 + " ]" + "\n" + RedScore + " vs. " + BlueScore;
    }
}
