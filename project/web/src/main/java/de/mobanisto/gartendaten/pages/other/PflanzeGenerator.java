package de.mobanisto.gartendaten.pages.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Fit;
import de.mobanisto.gartendaten.Plant;
import de.mobanisto.gartendaten.ThingByNameComparator;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.mobanisto.gartendaten.util.PlantUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.Table;
import de.topobyte.jsoup.components.TableRow;
import de.topobyte.webgun.exceptions.PageNotFoundException;
import de.topobyte.webpaths.WebPath;

public class PflanzeGenerator extends SimpleBaseGenerator
{

	private String name;

	public PflanzeGenerator(WebPath path, String name)
	{
		super(path);
		this.name = name;
	}

	@Override
	protected void content()
	{
		Data data = Website.INSTANCE.getData();
		Plant plant = data.getPlants().get(name);
		if (plant == null) {
			throw new PageNotFoundException();
		}

		PlantUtil pu = new PlantUtil(data);

		content.ac(HTML.h1(name));

		long wikidata = plant.getWikidata();
		if (wikidata != 0) {
			content.ac(HTML.a(
					String.format("https://www.wikidata.org/wiki/Q%d",
							wikidata),
					String.format("Wikidata item Q%d", wikidata)));
		}

		content.ac(HTML.h2("Eigenschaften"));
		P props = content.ac(HTML.p());

		props.at("Licht: " + pu.getLicht(plant));
		props.ac(HTML.br());
		props.at("Vorzucht: " + pu.getVorzucht(plant));
		props.ac(HTML.br());
		props.at("Direktsaat: " + pu.getDirektsaat(plant));

		Map<Plant, Fit> fits = data.getMix().row(plant);

		content.ac(HTML.h2("Nachbarschaften"));

		if (fits.isEmpty()) {
			content.ac(HTML.p()).appendText("Nichts bekannt");
		} else {
			Table table = content.ac(HTML.table());
			table.addClass("table");

			List<Plant> others = new ArrayList<>(fits.keySet());
			Collections.sort(others, new ThingByNameComparator());

			for (Plant other : others) {
				TableRow row = table.row();
				A link = HTML.a("/pflanze/" + other.getName(), other.getName());
				row.cell(link);
				Fit fit = fits.get(other);
				row.cell(fit == Fit.BAD ? "schlecht" : "gut");
			}
		}
	}

}
