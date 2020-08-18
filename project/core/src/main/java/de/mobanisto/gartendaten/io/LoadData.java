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
			String vegetable = Util.trim(map.get(keyVegetable));
			String idPlant = Util.trim(map.get(keyPlant));
			String idFruit = Util.trim(map.get(keyFruit));

			if (!Util.isEmpty(idPlant)) {
				Matcher matcher = patternWikidataItem.matcher(idPlant);
				if (matcher.matches()) {
					long id = Long.parseLong(matcher.group(1));
					Plant plant = data.getPlants().get(vegetable);
					if (plant == null) {
						plant = new Plant(vegetable);
						data.getPlants().put(vegetable, plant);
					}
					plant.setWikidata(id);
				}
			}

			if (!Util.isEmpty(idFruit)) {
				Matcher matcher = patternWikidataItem.matcher(idFruit);
				if (matcher.matches()) {
					long id = Long.parseLong(matcher.group(1));
					Fruit fruit = data.getFruits().get(vegetable);
					if (fruit == null) {
						fruit = new Fruit(vegetable);
						data.getFruits().put(vegetable, fruit);
					}
					fruit.setWikidata(id);
				}
			}
		}

		csvReader.close();
	}

}
