package de.mobanisto.gartendaten.pages.base;

import java.io.IOException;

import org.jsoup.nodes.Element;

import de.mobanisto.gartendaten.CacheBuster;
import de.mobanisto.gartendaten.PageNotFoundException;
import de.mobanisto.gartendaten.Website;
import de.mobanisto.gartendaten.widgets.MainFooter;
import de.mobanisto.gartendaten.widgets.MainMenu;
import de.topobyte.jsoup.ElementBuilder;
import de.topobyte.jsoup.FaviconUtil;
import de.topobyte.jsoup.bootstrap4.components.Menu;
import de.topobyte.jsoup.components.Head;
import de.topobyte.pagegen.bootstrap.Bootstrap4Generator;
import de.topobyte.webpaths.WebPath;

public class BaseGenerator extends Bootstrap4Generator
{

	public BaseGenerator(WebPath path)
	{
		super(path, false);
	}

	@Override
	public void generate() throws IOException, PageNotFoundException
	{
		super.generate();
		Element html = builder.getHtml();

		html.attr("lang", "en");

		Head head = builder.getHead();

		CacheBuster cacheBuster = Website.INSTANCE.getCacheBuster();

		FaviconUtil.addToHeader(head, "/" + cacheBuster.resolve("favicon.ico"));

		FaviconUtil.addToHeader(head,
				"/" + cacheBuster.resolve("images/favicon-16.png"), 16);
		FaviconUtil.addToHeader(head,
				"/" + cacheBuster.resolve("images/favicon-32.png"), 32);
		FaviconUtil.addToHeader(head,
				"/" + cacheBuster.resolve("images/favicon-48.png"), 48);
		FaviconUtil.addToHeader(head,
				"/" + cacheBuster.resolve("images/favicon-64.png"), 64);
		FaviconUtil.addToHeader(head,
				"/" + cacheBuster.resolve("images/favicon-96.png"), 96);

		head.ac(ElementBuilder
				.styleSheet("/" + cacheBuster.resolve("custom.css")));
	}

	protected void menu()
	{
		Menu menu = MainMenu.create(this);
		getBuilder().getBody().prependChild(menu);
	}

	protected void footer()
	{
		getBuilder().getBody().appendChild(new MainFooter(this));
	}

}
