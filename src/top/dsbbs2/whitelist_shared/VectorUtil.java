package top.dsbbs2.whitelist_shared;

import java.util.ArrayList;
import java.util.Vector;

public class VectorUtil {
	public static <T> ArrayList<T> toArrayList(Vector<T> v)
	{
		ArrayList<T> ret=new ArrayList<>();
		for(T i : v)
		{
			ret.add(i);
		}
		return ret;
	}
	@SafeVarargs
	public static <T> Vector<T> toVector(T... o)
	{
		return new Vector<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8461643752862517970L;

			{
				for(T i : o)
				  this.add(i);
			}
		};
	}
	public static <T> Vector<String> toStringVector(Vector<T> v)
	{
		Vector<String> ret=new Vector<>();
		for(T i : v)
		  ret.add(i.toString());
		return ret;
	}
}
