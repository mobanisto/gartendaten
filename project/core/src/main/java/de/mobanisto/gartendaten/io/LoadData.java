package de.mobanisto.gartendaten.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
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
import de.mobanisto.gartendaten.Licht;
import de.mobanisto.gartendaten.PflanzInterval;
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

	public List<String> loadListe(Path path) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			return loadListe(reader);
		}
	}

	public List<String> loadListe(Reader r) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(r)) {
			return loadListe(reader);
		}
	}

	public List<String> loadListe(BufferedReader reader) throws IOException
	{
		List<String> names = new ArrayList<>();
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			names.add(line);
		}
		return names;
	}

	private static final String keyPflanze = "Pflanze";
	private static final String keyVorkulturVon = "Vorkultur von";
	private static final String keyVorkulturBis = "Vorkultur bis";
	private static final String keyDirektsaatVon = "Direktsaat von";
	private static final String keyDirektsaatBis = "Direktsaat bis";
	private static final String keyPflanzungVon = "Pflanzung von";
	private static final String keyPflanzungBis = "Pflanzung bis";

	public void loadDates(Path path, Data data) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			loadDates(reader, data);
		}
	}

	public void loadDates(Reader reader, Data data) throws IOException
	{
		ICsvMapReader csvReader = new CsvMapReader(reader,
				CsvPreference.EXCEL_PREFERENCE);

		final String[] header = csvReader.getHeader(true);

		Map<String, String> map;
		while ((map = csvReader.read(header)) != null) {
			String pflanze = map.get(keyPflanze);
			PflanzInterval vorkultur = interval(map, keyVorkulturVon,
					keyVorkulturBis);
			PflanzInterval direktsaat = interval(map, keyDirektsaatVon,
					keyDirektsaatBis);
			PflanzInterval pflanzung = interval(map, keyPflanzungVon,
					keyPflanzungBis);

			if (vorkultur != null) {
				data.getVorzucht().put(pflanze, vorkultur);
			}
			if (direktsaat != null) {
				data.getDirektsaat().put(pflanze, direktsaat);
			}
			if (pflanzung != null) {
				data.getPflanzung().put(pflanze, pflanzung);
			}
		}

		csvReader.close();
	}

	private PflanzInterval interval(Map<String, String> map, String keyVon,
			String keyBis)
	{
		MonthDay von = date(map.get(keyVon));
		MonthDay bis = date(map.get(keyBis));
		if (von == null || bis == null) {
			return null;
		}
		return new PflanzInterval(von, bis);
	}

	private Pattern patternDate = Pattern.compile("([0-9]{2,2}).([0-9]{2,2})");

	private MonthDay date(String string)
	{
		string = Util.trim(string);
		if (string == null || string.isEmpty()) {
			return null;
		}
		Matcher matcher = patternDate.matcher(string);
		if (!matcher.matches()) {
			System.out.println("Warn: unmachted date " + string);
			return null;
		}

		int day = Integer.parseInt(matcher.group(1));
		int month = Integer.parseInt(matcher.group(2));

		return MonthDay.of(month, day);
	}

	private static final String keyGemuese = "Gemüse";
	private static final String keyLicht = "Licht";
	private static final String keyKommentar = "Kommentar";

	public void loadLicht(Path path, Data data) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			loadLicht(reader, data);
		}
	}

	public void loadLicht(Reader reader, Data data) throws IOException
	{
		ICsvMapReader csvReader = new CsvMapReader(reader,
				CsvPreference.EXCEL_PREFERENCE);

		final String[] header = csvReader.getHeader(true);

		Map<String, String> map;
		while ((map = csvReader.read(header)) != null) {
			String pflanze = map.get(keyGemuese);
			Licht licht = licht(map.get(keyLicht));
			String kommentar = map.get(keyKommentar);
			if (licht != null) {
				data.getLicht().put(pflanze, licht);
			}
			if (kommentar != null && !kommentar.isEmpty()) {
				data.getLichtKommentar().put(pflanze, kommentar);
			}
		}

		csvReader.close();
	}

	private Licht licht(String licht)
	{
		if (licht == null) {
			return null;
		}
		if (licht.equals("Sonne")) {
			return Licht.SONNE;
		} else if (licht.equals("Halbschatten")) {
			return Licht.HALBSCHATTEN;
		} else if (licht.equals("Schatten")) {
			return Licht.SCHATTEN;
		}
		return null;
	}

}
