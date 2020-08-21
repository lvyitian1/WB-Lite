package top.whitecola.wblite;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public enum TimePeriod {
	AM,
	NOON,
	PM,
	MIDNIGHT;
	private static double getHour(LocalDateTime now)
	{
		double r= now.getHour()+now.getMinute()/60.0+now.getSecond()/60.0/60.0+TimeUnit.NANOSECONDS.toSeconds(now.getNano())/60.0/60.0;
	    return r>=24?0:r;
	}
	public static TimePeriod now()
	{
		LocalDateTime now=LocalDateTime.now();
		if(getHour(now)>0 && getHour(now)<12)
			return AM;
		else if(getHour(now)==12)
			return NOON;
		else if(getHour(now)>12)
			return PM;
		else if(getHour(now)==0)
			return MIDNIGHT;
		else {
			return null;
		}
	}
	@Override
	public String toString()
    {
		if(this==AM)
			return "ÉÏÎç";
		else if(this==NOON)
			return "ÖĞÎç";
		else if(this==PM)
			return "ÏÂÎç";
		else if(this==MIDNIGHT)
			return "ÎçÒ¹";
		else return null;
    }
}
