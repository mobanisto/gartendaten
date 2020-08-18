package de.mobanisto.gartendaten.pages.markdown;

import java.io.IOException;
import java.nio.file.Path;

import de.mobanisto.gartendaten.pages.base.BaseGenerator;
import de.topobyte.jsoup.Markdown;
import de.topobyte.webpaths.WebPath;

public class MarkdownFileGenerator extends BaseGenerator
{

	private Path file;

	public MarkdownFileGenerator(WebPath path, Path file)
	{
		super(path);
		this.file = file;
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		menu();

		Markdown.renderFile(content, file);

		footer();
	}

}
