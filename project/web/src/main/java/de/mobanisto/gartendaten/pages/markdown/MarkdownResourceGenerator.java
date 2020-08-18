package de.mobanisto.gartendaten.pages.markdown;

import java.io.IOException;

import de.mobanisto.gartendaten.pages.base.BaseGenerator;
import de.topobyte.jsoup.Markdown;
import de.topobyte.webpaths.WebPath;

public class MarkdownResourceGenerator extends BaseGenerator
{

	private String resource;

	public MarkdownResourceGenerator(WebPath path, String resource)
	{
		super(path);
		this.resource = resource;
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		menu();

		Markdown.renderResource(content, resource);

		footer();
	}

}
