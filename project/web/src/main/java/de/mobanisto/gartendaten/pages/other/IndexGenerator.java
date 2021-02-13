package de.mobanisto.gartendaten.pages.other;

import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.bootstrap4.components.ListGroupDiv;
import de.topobyte.webpaths.WebPath;

public class IndexGenerator extends SimpleBaseGenerator
{

	public IndexGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Mobanisto Gartendaten"));

		content.ac(HTML.p()).at("Pflanzen, Gemüse und vieles mehr");

		ListGroupDiv list = content.ac(Bootstrap.listGroupDiv());
		list.addA("/liste/2021").at("2021");
	}

}
