spring-test-html-jsoup
======================

This is a small utility library that allows you to use the fluent API of the Spring MVC test framework in combination
with the jsoup library for parsing HTML content. This library works best with our own jsoup matchers project that gives
you a set of matchers for asserting your HTML content.


Example Usage
-------------

We use the following method to boostrap this library in combination with the spring test library:


    public void http(MockHttpServletRequestBuilder builder, boolean printContent, boolean parseAsPlainXml, int expectedResponseStatus) throws Exception {
        resultActions = mockMvc.perform(builder);
        mvcResult = resultActions.andReturn();
        int responseStatus = mvcResult.getResponse().getStatus();
        assertEquals("HTTP response status was " + responseStatus + ", expected " + expectedResponseStatus, expectedResponseStatus, responseStatus);
        String content = mvcResult.getResponse().getContentAsString();
        if (printContent) {
            System.out.println(content);
        }
        if (parseAsPlainXml) {
            document = Jsoup.parse(content, "", Parser.xmlParser());
        } else {
            document = Jsoup.parse(content);
        }
    }

In this method we build an http request, check the http response code against the expected code and parse the content
of the request (the actual HTML) into a JSoup document. We have built several hamcrest matcher to assert different
parts of the HTML (see our [jsoup-hamcrest](https://github.com/FDMediagroep/hamcrest-jsoup) project)