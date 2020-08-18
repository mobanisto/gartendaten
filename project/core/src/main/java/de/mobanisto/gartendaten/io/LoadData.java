package de.mobanisto.gartendaten.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.DataUtil;
import de.mobanisto.gartendaten.Fit;
import de.mobanisto.gartendaten.Fruit;
import de.mobanisto.gartendaten.Plant;

public class LoadData
{

	private Pattern patternWikidataItem = Pattern.compile("Q([0-9]+)");

	private static final String keyVegetable = "Gemüse";
	private static final String keyPlant = "Pflanze";
	private static final String keyFruit = "Frucht";

	public void loadWikidata(Path path, Data data) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			loadWikidata(reader, data);
		}
	}

	public void loadWikidata(Reader reader, Data data) throws IOException
	{
		ICsvMapReader csvReader = new CsvMapReader(reader,
				CsvPreference.EXCEL_PREFERENCE);

		final String[] header = csvReader.getHeader(true);

		Map<String, String> map;
		while ((map = csvReader.read(header)) != null) {
			String name = Util.trim(map.get(keyVegetable));
			String idPlant = Util.trim(map.get(keyPlant));
			String idFruit = Util.trim(map.get(keyFruit));

			if (!Util.isEmpty(idPlant)) {
				Matcher matcher = patternWikidataItem.matcher(idPlant);
				if (matcher.matches()) {
					long id = Long.parseLong(matcher.group(1));
					Plant plant = DataUtil.plant(data, name);
					plant.setWikidata(id);
				}
			}

			if (!Util.isEmpty(idFruit)) {
				Matcher matcher = patternWikidataItem.matcher(idFruit);
				if (matcher.matches()) {
					long id = Long.parseLong(matcher.group(1));
					Fruit fruit = DataUtil.fruit(data, name);
					fruit.setWikidata(id);
				}
			}
		}

		csvReader.close();
	}

	private static final String keyVegetable1 = "Gemüse1";
	private static final String keyVegetable2 = "Gemüse2";
	private static final String keyNeighbor = "Nachbar";

	public void loadMix(Path path, Data data) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			loadMix(reader, data);
		}
	}

	public void loadMix(Reader reader, Data data) throws IOException
	{
		ICsvMapReader csvReader = new CsvMapReader(reader,
				CsvPreference.EXCEL_PREFERENCE);

		final String[] header = csvReader.getHeader(true);

		Map<String, String> map;
		while ((map = csvReader.read(header)) != null) {
			String name1 = Util.trim(map.get(keyVegetable1));
			String name2 = Util.trim(map.get(keyVegetable2));
			String neighbor = Util.trim(map.get(keyNeighbor));
			Fit fit = fit(neighbor);

			Plant plant1 = DataUtil.plant(data, name1);
			Plant plant2 = DataUtil.plant(data, name2);
			data.getMix().put(plant1, plant2, fit);
			data.getMix().put(plant2, plant1, fit);
		}

		csvReader.close();
	}

	private Fit fit(String neighbor)
	{
		if (neighbor.equals("gut")) {
			return Fit.GOOD;
		} else if (neighbor.equals("schlecht")) {
			return Fit.BAD;
		}
		return null;
	}

}
