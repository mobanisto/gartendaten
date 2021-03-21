package de.mobanisto.gartendaten;

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

}
