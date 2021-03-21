package de.mobanisto.gartendaten;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Table;

public class DataUtil
{

	public static Plant plant(Data data, String name, boolean primary)
	{
		Plant plant = data.getPlants().get(name);
		if (plant == null) {
			plant = new Plant(name, primary);
			data.getPlants().put(name, plant);
		} else {
			if (primary) {
				plant.setPrimary(true);
			}
		}
		return plant;
	}

	public static Fruit fruit(Data data, String name)
	{
		Fruit fruit = data.getFruits().get(name);
		if (fruit == null) {
			fruit = new Fruit(name);
			data.getFruits().put(name, fruit);
		}
		return fruit;
	}

	public static void deriveMix(Data data)
	{
		Table<String, String, Fit> rawMix = data.getRawMix();
		// Erst nur Familien zuordnen
		for (String name1 : rawMix.rowKeySet()) {
			Plant plant1 = DataUtil.plant(data, name1, true);
			Map<String, Fit> row = rawMix.row(name1);
			for (String name2 : row.keySet()) {
				Fit fit = row.get(name2);
				Collection<String> members = data.getFamilien().get(name2);
				if (!members.isEmpty()) {
					for (String kultur : members) {
						Plant plant2 = DataUtil.plant(data, kultur, false);
						data.getMix().put(plant1, plant2, fit);
					}
				}
			}
		}
		// Dann nur nicht-Familien zuordnen, um spezifische fits zu
		// überschreiben
		for (String name1 : rawMix.rowKeySet()) {
			Plant plant1 = DataUtil.plant(data, name1, true);
			Map<String, Fit> row = rawMix.row(name1);
			for (String name2 : row.keySet()) {
				Fit fit = row.get(name2);
				Collection<String> members = data.getFamilien().get(name2);
				if (members.isEmpty()) {
					Plant plant2 = DataUtil.plant(data, name2, false);
					data.getMix().put(plant1, plant2, fit);
				}
			}
		}
	}

}
