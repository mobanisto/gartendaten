package de.mobanisto.gartendaten.widgets;

import static de.topobyte.jsoup.HTML.a;
import static de.topobyte.jsoup.HTML.p;

import de.mobanisto.gartendaten.PathHelper;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.bootstrap4.Bootstrap;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.Div;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.UnorderedList;
import de.topobyte.jsoup.feather.Feather;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.pagegen.core.LinkResolver;

public class MainFooter extends Element<MainFooter>
{

	public MainFooter(LinkResolver resolver)
	{
		super("footer");
		attr("class", "footer");

		Div div = ac(Bootstrap.container());

		UnorderedList links = div.ac(HTML.ul());

		String imprintLink = resolver.getLink(PathHelper.imprint());
		A linkAbout = a(imprintLink, "Impressum");
		links.addItem(linkAbout);

		String privacyLink = resolver.getLink(PathHelper.privacy());
		A linkPrivacy = a(privacyLink, "Datenschutz");
		links.addItem(linkPrivacy);

		P p = div.ac(p().addClass("text-muted"));

		p.appendText("Made with ");
		p.ac(Feather.heart("16"));
		p.appendText(" in Berlin");
	}

}
