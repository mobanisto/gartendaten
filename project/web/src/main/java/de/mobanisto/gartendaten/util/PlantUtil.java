package de.mobanisto.gartendaten.util;

import java.util.Collection;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import de.mobanisto.gartendaten.Data;
import de.mobanisto.gartendaten.Licht;
import de.mobanisto.gartendaten.PflanzInterval;
import de.mobanisto.gartendaten.Plant;

public class PlantUtil
{

	private Data data;

	public PlantUtil(Data data)
	{
		this.data = data;
	}

	public String getLicht(Plant plant)
	{
		Collection<Licht> values = data.getLicht().get(plant.getName());
		if (values.isEmpty()) {
			return "-";
		}
		Converter<String, String> converter = CaseFormat.UPPER_UNDERSCORE
				.converterTo(CaseFormat.UPPER_CAMEL);
		return Joiner.on("/").join(
				Iterables.transform(values, l -> converter.convert(l.name())));
	}

	public String getVorzucht(Plant plant)
	{
		Collection<PflanzInterval> values = data.getVorzucht()
				.get(plant.getName());
		if (values.isEmpty()) {
			return "-";
		}
		return Joiner.on("/").join(values);
	}

	public String getDirektsaat(Plant plant)
	{
		Collection<PflanzInterval> values = data.getDirektsaat()
				.get(plant.getName());
		if (values.isEmpty()) {
			return "-";
		}
		return Joiner.on("/").join(values);
	}

}
