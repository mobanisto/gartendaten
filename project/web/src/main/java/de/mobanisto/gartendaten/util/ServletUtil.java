package de.mobanisto.gartendaten.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;

import de.mobanisto.gartendaten.pages.other.ErrorGenerator;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class ServletUtil
{

	public static void respond404(WebPath output, HttpServletResponse response,
			Void data) throws IOException
	{
		respond(404, output, response, content -> {
			ErrorUtil.write404(content);
		}, data);
	}

	public static void respond404(WebPath output, HttpServletResponse response,
			Consumer<Element> contentGenerator, Void data) throws IOException
	{
		respond(404, output, response, contentGenerator, data);
	}

	public static void respond(int code, WebPath output,
			HttpServletResponse response, Consumer<Element> contentGenerator,
			Void data) throws IOException
	{
		response.setStatus(code);

		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		ErrorGenerator generator = new ErrorGenerator(output);
		generator.generate();
		Element content = generator.getContent();

		contentGenerator.accept(content);

		Document document = generator.getBuilder().getDocument();
		writer.write(document.toString());

		writer.close();
	}

}
