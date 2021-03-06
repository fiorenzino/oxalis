import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Steinar Overbeck Cook
 *         <p/>
 *         Created by
 *         User: steinar
 *         Date: 26.12.11
 *         Time: 14:08
 */
public class XmlEncodingTest {

    protected static final String EHF_TEST_SEND_REGNING_HELSE_VEST2_XML = "ehf-test-SendRegning-HelseVest2.xml";

    /** Parses an XML file and verifies that the address contained in <code>/Invoice/AccountingSupplierParty/Party/PostalAddress/StreetName/text()</code>
     *  the
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws URISyntaxException
     */
    @Test
    public void testXmlEncoding() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, URISyntaxException {

        URL url = XmlEncodingTest.class.getClassLoader().getResource(EHF_TEST_SEND_REGNING_HELSE_VEST2_XML);
        assertNotNull(url,EHF_TEST_SEND_REGNING_HELSE_VEST2_XML + " not found in classpath");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(false);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        File file = new File(url.toURI());
        Document document = documentBuilder.parse(file);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression expr = xPath.compile("/Invoice/AccountingSupplierParty/Party/PostalAddress/StreetName/text()");
        String s = (String) expr.evaluate(document, XPathConstants.STRING);

        assertEquals(s, "\u00D8stre Aker vei 243H");
    }
}
