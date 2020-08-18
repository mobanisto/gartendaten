package de.mobanisto.gartendaten.pages.other;

import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.P;
import de.topobyte.webpaths.WebPath;

public class KompostGenerator extends SimpleBaseGenerator
{

	public KompostGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Kompost"));

		P intro = content.ac(HTML.p());
		intro.appendText(
				"Hier berichten wir über unsere Erfahrungen mit der Kompostierung.");
	}

}
