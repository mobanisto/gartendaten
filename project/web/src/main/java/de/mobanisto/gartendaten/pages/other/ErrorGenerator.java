package de.mobanisto.gartendaten.pages.other;

import java.io.IOException;

import de.mobanisto.gartendaten.pages.base.BaseGenerator;
import de.topobyte.webpaths.WebPath;

public class ErrorGenerator extends BaseGenerator
{

	public ErrorGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		menu();
		footer();
	}

}