package de.mobanisto.gartendaten;

import java.util.Comparator;

public class ThingByNameComparator implements Comparator<Thing>
{

	@Override
	public int compare(Thing o1, Thing o2)
	{
		return o1.getName().compareTo(o2.getName());
	}

}
