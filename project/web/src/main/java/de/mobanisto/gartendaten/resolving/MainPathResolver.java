package de.mobanisto.gartendaten.resolving;

import javax.servlet.http.HttpServletRequest;

import de.mobanisto.gartendaten.pages.markdown.MarkdownResourceGenerator;
import de.mobanisto.gartendaten.pages.other.GemueseGenerator;
import de.mobanisto.gartendaten.pages.other.IndexGenerator;
import de.topobyte.jsoup.ContentGeneratable;
import de.topobyte.webgun.resolving.pathspec.PathSpec;
import de.topobyte.webgun.resolving.pathspec.PathSpecResolver;
import de.topobyte.webpaths.WebPath;

public class MainPathResolver extends PathSpecResolver<ContentGeneratable, Void>
{

	@Override
	public ContentGeneratable getGenerator(WebPath path,
			HttpServletRequest request, Void data)
	{
		if (path.getNameCount() == 0) {
			return new IndexGenerator(path);
		}
		return super.getGenerator(path, request, data);
	}

	{
		map(new PathSpec("gemüse"), (path, output, request, data) -> {
			return new GemueseGenerator(path);
		});
		map(new PathSpec("kompost"), (path, output, request, data) -> {
			return new IndexGenerator(path);
		});

		map(new PathSpec("impressum"), (path, output, request, data) -> {
			return new MarkdownResourceGenerator(path,
					"markdown/de/impressum.md");
		});
		map(new PathSpec("privacy-policy"), (path, output, request, data) -> {
			return new MarkdownResourceGenerator(path,
					"markdown/de/privacy-policy.md");
		});
	}

}
