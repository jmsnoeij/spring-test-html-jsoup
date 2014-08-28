package nl.fd.spring.test.integration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * @author Quinten Krijger
 */
public abstract class DocumentResultMatcher implements ResultMatcher {

    @Override
    public void match(MvcResult result) throws Exception {
        String content = result.getResponse().getContentAsString();
        match(Jsoup.parse(content));
    }

    protected abstract void match(Document document) throws Exception;
}