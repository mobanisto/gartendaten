package de.mobanisto.gartendaten;

import java.io.IOException;
import java.nio.file.Path;

import de.mobanisto.gartendaten.io.LoadData;
import de.topobyte.system.utils.SystemPaths;

public class TestReadData
{

	public static void main(String[] args) throws IOException
	{
		Path fileWikidata = SystemPaths.CWD.resolve("../../data/wikidata.csv");
		Path fileMix = SystemPaths.CWD.resolve("../../data/mix.csv");
		Path file2021 = SystemPaths.CWD.resolve("../../data/2021/liste");

		Data data = new Data();

		LoadData task = new LoadData();
		task.loadWikidata(fileWikidata, data);
		task.loadMix(fileMix, data);
		data.getNames().put("2021", task.loadListe(file2021));

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

		System.out.println("# List");
		for (String name : data.getNames().get("2021")) {
			System.out.println(name);
		}
	}

}
