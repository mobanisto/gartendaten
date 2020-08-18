package de.mobanisto.gartendaten;

import java.io.IOException;
import java.nio.file.Path;

import de.mobanisto.gartendaten.io.LoadData;
import de.topobyte.system.utils.SystemPaths;

public class TestReadData
{

	public static void main(String[] args) throws IOException
	{
		Path file = SystemPaths.CWD.resolve("../../data/wikidata.csv");

		Data data = new Data();

		LoadData task = new LoadData();
		task.loadWikidata(file, data);

		System.out.println("# Plants");
		for (Plant plant : data.getPlants().values()) {
			System.out.println(String.format("%s Q%d", plant.getName(),
					plant.getWikidata()));
		}

		System.out.println("# Fruits");
		for (Fruit fruit : data.getFruits().values()) {
			System.out.println(String.format("%s Q%d", fruit.getName(),
					fruit.getWikidata()));
		}
	}

}
