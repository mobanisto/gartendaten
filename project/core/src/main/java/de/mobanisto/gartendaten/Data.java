package de.mobanisto.gartendaten;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import lombok.Getter;

public class Data
{

	@Getter
	private Map<String, Plant> plants = new HashMap<>();
	@Getter
	private Map<String, Fruit> fruits = new HashMap<>();

	@Getter
	private Table<Plant, Plant, Fit> mix = HashBasedTable.create();

}
