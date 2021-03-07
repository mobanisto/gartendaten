package de.mobanisto.gartendaten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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

	@Getter
	private Map<String, List<String>> names = new HashMap<>();

	@Getter
	private Multimap<String, PflanzInterval> vorzucht = HashMultimap.create();
	@Getter
	private Multimap<String, PflanzInterval> direktsaat = HashMultimap.create();
	@Getter
	private Multimap<String, PflanzInterval> pflanzung = HashMultimap.create();

}
