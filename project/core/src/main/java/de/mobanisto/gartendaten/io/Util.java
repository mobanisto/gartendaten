package de.mobanisto.gartendaten.io;

public class Util
{

	public static String trim(String string)
	{
		if (string == null) {
			return null;
		}
		return string.trim();
	}

	public static boolean isEmpty(String string)
	{
		if (string == null) {
			return true;
		}
		return string.trim().isEmpty();
	}

}
