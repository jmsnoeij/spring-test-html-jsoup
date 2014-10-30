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
public class MultipleMatchBuilder {

    private final List<DocumentResultMatcher> documentMatchers = new ArrayList<>();

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

    public ResultMatcher inTheHtml() {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                Document document = Jsoup.parse(content);
                evaluateMatchers(document);
            }
        };
    }

    public ResultMatcher inTheXml() {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                Document document = Jsoup.parse(content, "", Parser.xmlParser());
                evaluateMatchers(document);
            }
        };
    }

    private void evaluateMatchers(Document document) throws Exception {
        for (DocumentResultMatcher documentMatcher : documentMatchers) {
            documentMatcher.match(document);
        }
    }
}