package nl.fd.spring.test.integration;

import org.hamcrest.Matcher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quinten Krijger
 */
public class MultipleMatchBuilder implements ResultMatcher {

    private final List<DocumentResultMatcher> documentMatchers = new ArrayList<>();

    private boolean parseAsXml;

    public MultipleMatchBuilder(boolean parseAsXml) {
        this.parseAsXml = parseAsXml;
    }

    public MultipleMatchBuilder title(final String title) {
        documentMatchers.add(DocumentResultMatchers.title(title));
        return this;
    }

    public MultipleMatchBuilder singleElement(String selector) {
        documentMatchers.add(DocumentResultMatchers.singleElement(selector));
        return this;
    }

    public MultipleMatchBuilder singleElement(String selector, Matcher<? super Element> matcher) {
        documentMatchers.add(DocumentResultMatchers.singleElement(selector, matcher));
        return this;
    }

    public MultipleMatchBuilder noElement(String selector) {
        documentMatchers.add(DocumentResultMatchers.noElement(selector));
        return this;
    }

    public MultipleMatchBuilder nthElement(String selector, int index, Matcher<? super Element> matcher) {
        documentMatchers.add(DocumentResultMatchers.nthElement(selector, index, matcher));
        return this;
    }

    public MultipleMatchBuilder elements(String selector, Matcher<? super Elements> matcher) {
        documentMatchers.add(DocumentResultMatchers.elements(selector, matcher));
        return this;
    }

    public MultipleMatchBuilder with(final DocumentResultMatcher documentResultMatcher) {
        documentMatchers.add(documentResultMatcher);
        return this;
    }

    @Override
    public void match(MvcResult mvcResult) throws Exception {
        String content = mvcResult.getResponse().getContentAsString();
        Document document;
        if (parseAsXml) {
            document = Jsoup.parse(content, "", Parser.xmlParser());
        } else {
            document = Jsoup.parse(content);
        }
        for (DocumentResultMatcher documentMatcher : documentMatchers) {
            documentMatcher.match(document);
        }
    }

}