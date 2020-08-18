package de.mobanisto.gartendaten;

import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

public class PathHelper
{

	public static WebPath imprint()
	{
		return WebPaths.get("impressum");
	}

	public static WebPath privacy()
	{
		return WebPaths.get("privacy-policy");
	}

}
