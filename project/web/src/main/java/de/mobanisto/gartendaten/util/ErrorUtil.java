package de.mobanisto.gartendaten.util;

import org.jsoup.nodes.Element;

import de.topobyte.jsoup.HTML;

public class ErrorUtil
{

	public static void write404(Element content)
	{
		content.appendChild(HTML.h1().text("Seite nicht gefunden"));
		content.appendText("Fehlercode: 404");
	}

	public static void writeError(Element content)
	{
		content.appendChild(HTML.h1().text("Ouch!"));
		content.appendText("Da ist was schiefgleaufen");
	}

}
