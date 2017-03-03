package ResponseComparision;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XmlComp {
	public boolean isXmlExp(String str) {
//		System.out.println(str);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				Document doc = builder.parse(new ByteArrayInputStream(str.getBytes()));
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return false;
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean compare(String str1, String str2) {
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		try {
			DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(str1, str2));
			return diff.similar();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
	}

}
