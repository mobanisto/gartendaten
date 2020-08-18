package de.mobanisto.gartendaten.pages.other;

import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.jsoup.HTML;
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

		content.appendText("Pflanzen, Gemüse und vieles mehr");
	}

}
