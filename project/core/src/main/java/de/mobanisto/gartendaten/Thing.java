package de.mobanisto.gartendaten;

import lombok.Getter;
import lombok.Setter;

public class Thing
{

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private long wikidata;

	public Thing(String name)
	{
		this.name = name;
	}

}
