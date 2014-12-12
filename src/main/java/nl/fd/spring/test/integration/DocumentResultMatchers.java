package nl.fd.spring.test.integration;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static nl.fd.hamcrest.jsoup.ElementWithOwnText.hasOwnText;
import static nl.fd.spring.test.integration.ElementSelectors.selectSingle;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Quinten Krijger
 */
class DocumentResultMatchers {

    public static DocumentResultMatcher title(final String title) {
        return new DocumentResultMatcher() {
            @Override
            public void match(Document document) throws Exception {
                assertThat(
                        selectSingle(document, "head > title"),
                        hasOwnText(title));
            }
        };
    }

    public static DocumentResultMatcher singleElement(final String selector) {
        return new DocumentResultMatcher() {
            @Override
            protected void match(Document document) throws Exception {
                assertThat(selectSingle(document, selector), notNullValue());
            }
        };
    }

    public static DocumentResultMatcher singleElement(final String selector, final Matcher<? super Element> matcher) {
        return new DocumentResultMatcher() {
            @Override
            protected void match(Document document) throws Exception {
                assertThat(selectSingle(document, selector), matcher);
            }
        };
    }

    public static DocumentResultMatcher noElement(final String selector) {
        return new DocumentResultMatcher() {
            @Override
            public void match(Document document) throws Exception {
                Elements elements = document.select(selector);
                assertThat(elements.size(), is(0));
            }
        };
    }

    public static DocumentResultMatcher nthElement(final String selector,
                                                   final int index,
                                                   final Matcher<? super Element> matcher) {
        return new DocumentResultMatcher() {
            @Override
            public void match(Document document) throws Exception {
                Elements elements = document.select(selector);
                assertThat(elements, Matchers.<Element>iterableWithSize(greaterThan(index)));
                assertThat(elements.get(index), matcher);
            }
        };
    }

    public static DocumentResultMatcher elements(final String selector, final Matcher<? super Elements> matcher) {
        return new DocumentResultMatcher() {
            @Override
            protected void match(Document document) throws Exception {
                assertThat(document.select(selector), matcher);
            }
        };
    }

    public static DocumentResultMatcher thatIsPrinted() {
        return new DocumentResultMatcher() {
            @Override
            protected void match(Document document) throws Exception {
                System.out.println(document.outerHtml());
            }
        };
    }
}
