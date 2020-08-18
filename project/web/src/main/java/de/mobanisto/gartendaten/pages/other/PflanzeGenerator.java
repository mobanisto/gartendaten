package de.mobanisto.gartendaten.pages.other;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.PageNotFoundException;
import de.mobanisto.gartendaten.Plant;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
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

		content.ac(HTML.h1(name));

		long wikidata = plant.getWikidata();
		if (wikidata != 0) {
			content.ac(HTML.a(
					String.format("https://www.wikidata.org/wiki/Q%d",
							wikidata),
					String.format("Wikidata item Q%d", wikidata)));
		}
	}

}
