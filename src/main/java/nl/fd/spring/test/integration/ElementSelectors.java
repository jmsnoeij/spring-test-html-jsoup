package nl.fd.spring.test.integration;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Quinten Krijger
 */
public final class ElementSelectors {

    private ElementSelectors() {}

    public static Element selectSingle(Document document, String cssExpression) {
        Elements elements = document.select(cssExpression);
        assertThat(elements.size(), is(1));
        return elements.get(0);
    }

}
