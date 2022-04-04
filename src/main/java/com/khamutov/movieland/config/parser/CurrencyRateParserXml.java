package com.khamutov.movieland.config.parser;

import com.khamutov.movieland.model.CurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CurrencyRateParserXml implements CurrencyRateParser {
    @Override
    public List<CurrencyRate> parse(String ratesAsString) {
        List<CurrencyRate> rates = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            try (Reader reader = new StringReader(ratesAsString)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("currency");

                for (int valuteIdx = 0; valuteIdx < list.getLength(); valuteIdx++) {
                    Node node = list.item(valuteIdx);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        CurrencyRate rate = CurrencyRate.builder()
                                .txt(element.getElementsByTagName("txt").item(0).getTextContent())
                                .rate(element.getElementsByTagName("rate").item(0).getTextContent())
                                .cc(element.getElementsByTagName("cc").item(0).getTextContent())
                                .exchangeDate(element.getElementsByTagName("exchangedate").item(0).getTextContent())
                                .build();
                        rates.add(rate);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("xml parsing error, xml:{}", ratesAsString, ex);
            throw new CurrencyRateParsingException(ex);
        }
        return rates;
    }
}
