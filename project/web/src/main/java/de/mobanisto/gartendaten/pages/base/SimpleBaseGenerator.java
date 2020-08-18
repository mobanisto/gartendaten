package de.mobanisto.gartendaten.pages.base;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.webpaths.WebPath;

public abstract class SimpleBaseGenerator extends BaseGenerator
{

	final static Logger logger = LoggerFactory
			.getLogger(SimpleBaseGenerator.class);

	public SimpleBaseGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		menu();

		content();

		footer();
	}

	protected abstract void content();

}
