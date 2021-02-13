package de.mobanisto.gartendaten.pages.other;

import java.util.List;
import java.util.Map;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Plant;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ContextualType;
import de.topobyte.jsoup.bootstrap4.components.ListGroupDiv;
import de.topobyte.jsoup.bootstrap4.components.listgroup.ListGroupA;
import de.topobyte.webgun.exceptions.PageNotFoundException;
import de.topobyte.webpaths.WebPath;

public class ListeGenerator extends SimpleBaseGenerator
{

	private String listName;

	public ListeGenerator(WebPath path, String listName)
	{
		super(path);
		this.listName = listName;
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Liste"));

		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());

		Data data = Website.INSTANCE.getData();
		List<String> names = data.getNames().get(listName);
		if (names == null) {
			throw new PageNotFoundException();
		}

		Map<String, Plant> plants = data.getPlants();

		for (String plantName : names) {
			Plant plant = plants.get(plantName);
			ListGroupA item = list.addA("/pflanze/" + plantName, plantName);
			if (plant == null) {
				item.setContext(ContextualType.DANGER);
			} else {
				item.setContext(ContextualType.SUCCESS);
			}
		}
	}

}
