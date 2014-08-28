package nl.fd.spring.test.integration;

import org.hamcrest.Matcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * @author Quinten Krijger
 */
public final class HtmlResultMatchers {

    /**
     * Use {@link #html()} in order to be consistent with fluent spring testing framework api.
     */
    HtmlResultMatchers() { }

    public static HtmlResultMatchers html() {
        return new HtmlResultMatchers();
    }

    public MultipleMatchBuilder withAllOf() {
        return new MultipleMatchBuilder();
    }

    public ResultMatcher withTitle(final String title) {
        return DocumentResultMatchers.title(title);
    }

    public ResultMatcher withSingleElement(final String selector) {
        return DocumentResultMatchers.singleElement(selector);
    }

    public ResultMatcher withSingleElement(final String selector, final Matcher<? super Element> matcher) {
        return DocumentResultMatchers.singleElement(selector, matcher);
    }

    public ResultMatcher withoutElement(final String selector) {
        return DocumentResultMatchers.noElement(selector);
    }

    public ResultMatcher withNthElement(final String selector, int index, final Matcher<? super Element> matcher) {
        return DocumentResultMatchers.nthElement(selector, index, matcher);
    }

    public ResultMatcher withElements(final String selector, final Matcher<? super Elements> matcher) {
        return DocumentResultMatchers.elements(selector, matcher);
    }

    public ResultMatcher with(final DocumentResultMatcher documentResultMatcher) {
        return documentResultMatcher;
    }

    @Deprecated
    public ResultMatcher thatIsPrinted() {
        return DocumentResultMatchers.thatIsPrinted();
    }
}
