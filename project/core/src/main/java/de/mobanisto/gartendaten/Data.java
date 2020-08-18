package de.mobanisto.gartendaten;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Data
{

	@Getter
	private Map<String, Plant> plants = new HashMap<>();
	@Getter
	private Map<String, Fruit> fruits = new HashMap<>();

}
