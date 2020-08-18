package de.mobanisto.gartendaten;

import lombok.Getter;
import lombok.Setter;

public class Website
{

	public static final Website INSTANCE = new Website();

	@Getter
	@Setter
	private CacheBuster cacheBuster;

	@Getter
	@Setter
	private Data data;

}
