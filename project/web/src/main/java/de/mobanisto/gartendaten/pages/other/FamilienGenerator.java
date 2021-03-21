package de.mobanisto.gartendaten.pages.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Plant;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ListGroupDiv;
import de.topobyte.webpaths.WebPath;

public class FamilienGenerator extends SimpleBaseGenerator
{

	public FamilienGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Familien"));

		Data data = Website.INSTANCE.getData();

		List<String> familyNames = new ArrayList<>(data.getFamilien().keySet());
		Collections.sort(familyNames);

		List<String> plantNames = new ArrayList<>(data.getPlants().keySet());
		Collections.sort(plantNames);

		Set<String> done = new HashSet<>();

		for (String familienName : familyNames) {
			content.ac(HTML.h2(familienName));
			ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());
			List<String> kulturen = new ArrayList<>(
					data.getFamilien().get(familienName));
			Collections.sort(kulturen);
			for (String kultur : kulturen) {
				list.addA("/pflanze/" + kultur, kultur);
				done.add(kultur);
			}
		}

		content.ac(HTML.h2("Andere"));
		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());
		for (String plantName : plantNames) {
			if (done.contains(plantName)) {
				continue;
			}
			Plant plant = data.getPlants().get(plantName);
			if (!plant.isPrimary()) {
				continue;
			}
			list.addA("/pflanze/" + plantName, plantName);
		}
	}

}
