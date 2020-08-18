package de.mobanisto.gartendaten.pages.other;

import de.mobanisto.gartendaten.pages.base.SimpleBaseGenerator;
import de.topobyte.cachebusting.CacheBusting;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.components.Div;
import de.topobyte.jsoup.components.Img;
import de.topobyte.jsoup.components.P;
import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

public class AboutGenerator extends SimpleBaseGenerator
{

	public AboutGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content()
	{
		content.ac(HTML.h1("Mobanisto Gartendaten"));

		Div row = content.ac(Bootstrap.row());
		row.addClass("align-items-center");

		Div colLeft = row.ac(HTML.div("col-12 col-sm-4"));
		Div colRight = row.ac(HTML.div("col-12 col-sm-8"));

		Img image = colLeft.ac(HTML.img("/"
				+ WebPaths.get(CacheBusting.resolve("images/mobanisto.svg"))));
		image.addClass("img-fluid");
		image.attr("style", "width: 100%; padding: 15%");

		P p = colRight.ac(HTML.p());
		p.appendText(
				"Ein Seite über Pflanzen, Gemüse, Mischkulturen und vieles mehr.");
		p = colRight.ac(HTML.p());
		p.appendText("Ein Projekt von Mo und Sebastian von ");
		p.ac(HTML.a("https://www.mobanisto.de", "Mobanisto"));
		p.appendText(".");
	}

}
