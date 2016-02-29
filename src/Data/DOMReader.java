package Data;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

import Common.Define;
import Object.Attri;
import Object.RoleAttributes;
import Object.Imp.RoleAttributeImp;


public class DOMReader {
	public DOMReader(String path){
		TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer transFormer;
		try {
			transFormer = transFact.newTransformer();
			DOMResult dom = new DOMResult();
	        transFormer.transform(new StreamSource(new FileInputStream(new File(path))), dom);
		            
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        XPathExpression expression = xpath.compile("//RoleAttributes/Attri");
	        NodeList nodeList = (NodeList)expression.evaluate(dom.getNode(),XPathConstants.NODESET);
	        RoleAttributes imp = new RoleAttributeImp();
	        for(int i = 0; i < nodeList.getLength(); i++){
	            Attri attri = imp.generateAttri(nodeList.item(i));
	            imp.setInitialAttri(attri);
	        }
	        System.out.println(imp.toString());
	        Attri attri1 = new Attri("HP_MAX", Define.HP_MAX, Define.FLOAT_TYPE, 111f);
	        imp.addAttriValue(attri1);
	        System.out.println(imp.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
