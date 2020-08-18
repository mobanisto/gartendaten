package de.mobanisto.gartendaten;

import lombok.Getter;
import lombok.Setter;

public class Config
{

	public static final Config INSTANCE = new Config();

	@Getter
	@Setter
	private String data;

}
