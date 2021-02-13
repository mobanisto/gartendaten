package de.mobanisto.gartendaten.pages.other;

import static de.mobanisto.gartendaten.Fit.GOOD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Fit;
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
	private Data data;

	public ListeGenerator(WebPath path, String listName)
	{
		super(path);
		this.listName = listName;
		data = Website.INSTANCE.getData();
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Liste"));

		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());

		List<String> names = data.getNames().get(listName);
		if (names == null) {
			throw new PageNotFoundException();
		}

		Map<String, Plant> plantLookup = data.getPlants();

		List<Plant> plants = new ArrayList<>();

		for (String plantName : names) {
			Plant plant = plantLookup.get(plantName);
			ListGroupA item = list.addA("/pflanze/" + plantName, plantName);
			if (plant == null) {
				item.setContext(ContextualType.DANGER);
			} else {
				plants.add(plant);
				item.setContext(ContextualType.SUCCESS);
			}
		}

		group(plants);
	}

	private void group(List<Plant> plants)
	{
		int n = plants.size();

		List<List<Plant>> three = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				for (int k = j; k < n; k++) {
					if (check(plants, i, j, k)) {
						three.add(set(plants, i, j, k));
					}
				}
			}
		}

		Set<Plant> got = new HashSet<>();

		content.ac(HTML.h2("Dreiergruppen")).addClass("mt-3");
		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());
		for (List<Plant> group : three) {
			list.addTextItem(Joiner.on(", ")
					.join(Iterables.transform(group, Plant::getName)));
			for (Plant plant : group) {
				got.add(plant);
			}
		}

		content.ac(HTML.h2("Übrig")).addClass("mt-3");
		list = content.ac(Bootstrap.listGroupDiv());
		for (Plant plant : plants) {
			if (got.contains(plant)) {
				continue;
			}
			list.addTextItem(plant.getName());
		}
	}

	private List<Plant> set(List<Plant> plants, int i, int j, int k)
	{
		List<Plant> selected = new ArrayList<>();
		selected.add(plants.get(i));
		selected.add(plants.get(j));
		selected.add(plants.get(k));
		return selected;
	}

	private boolean check(List<Plant> plants, int i, int j, int k)
	{
		Plant pi = plants.get(i);
		Plant pj = plants.get(j);
		Plant pk = plants.get(k);
		Table<Plant, Plant, Fit> mix = data.getMix();
		Fit fit1 = mix.get(pi, pj);
		Fit fit2 = mix.get(pi, pk);
		Fit fit3 = mix.get(pj, pk);
		if (fit1 == GOOD && fit2 == GOOD && fit3 == GOOD) {
			return true;
		}
		return false;
	}

}
