package de.mobanisto.gartendaten.pages.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ListGroupDiv;
import de.topobyte.webpaths.WebPath;

public class GemueseGenerator extends SimpleBaseGenerator
{

	public GemueseGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Gemüse"));

		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());

		Data data = Website.INSTANCE.getData();

		List<String> plantNames = new ArrayList<>(data.getPlants().keySet());
		Collections.sort(plantNames);

		for (String plantName : plantNames) {
			list.addA("/pflanze/" + plantName, plantName);
		}
	}

}
