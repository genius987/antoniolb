package documentCreation;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
		
public class estrazionetestoold {
 
	String folderdt; //path file di testo
	String folderdxml; //path file xml
	String nome;  //nomefile
	
	
	public estrazionetestoold(String name)
	{
		nome=name;
		folderdt=System.getProperty("user.dir") + "/src/topics"; //di default
		folderdxml=System.getProperty("user.dir") + "/xmlqa/data"; //di default
	}
	public estrazionetestoold(String name,String p,String dest)
	{
		folderdt=p;
		nome=name;
		folderdxml=dest;
	}
	
	public String filtraggiotaghtml(String text)
	{
		
		
		Whitelist myWL = Whitelist.none();

		String safe = Jsoup.clean(text, myWL);
		//System.out.println("stringa sicura: " + safe);
		return safe;
	}
	
	public String filtraggio(String text)
	{
		
		// naturalmente da sistemare meglio ma per ora era per capire se funzionava
		// |\\[.*?\\]
		String regex="\\{\\|.*?\\|\\}";  //ok
		
		text=text.replaceAll(regex," ");
		
		regex="\\[\\[[^:\\]]*:[^\\]]*?\\]\\]";

		text=text.replaceAll(regex," ");
		
		regex="\\{\\{.*?\\}\\}"; //ok 
		text=text.replaceAll(regex," ");
		
		
		regex="\\[[^\\]]*?\\|";  //ok
		text=text.replaceAll(regex,"[[");
		
		
		regex="\\&.*?\\;";
		text=text.replaceAll(regex,"");
		
		regex="==[^=]*==";

		text=text.replaceAll(regex," ");
		
		
		regex="\\([^)]*\\)";

		text=text.replaceAll(regex," ");
	
		regex="\\[[^\\]]*:[^\\]]*\\]";
		text=text.replaceAll(regex," ");
		
		regex="[^a-zA-Z0-9.;]";
		text=text.replaceAll(regex," ");
	
		text=text.replaceAll("[\\s]+", " ");
		return text;
	}
	
	
	public String filtra(String text)
	{
		
		String testomodificato=filtraggiotaghtml(text);
		
		testomodificato=filtraggio(testomodificato);
		return testomodificato;
	}
	public void extract() throws ParserConfigurationException, TransformerException, SAXException, IOException
	{
		Documento d;
		
		
		File stocks = new File(folderdxml+"/"+nome);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stocks);
		doc.getDocumentElement().normalize();

		
		
		//mi sposto nell'albero del file xml fino a raggiungere il tag "page"
		NodeList nodes = doc.getElementsByTagName("page"); 
		
		
		for (int i = 0; i < nodes.getLength(); i++) {
			
			nodes = doc.getElementsByTagName("page");
			Node node = nodes.item(i);

		Element element = (Element) node;
		//recupero il titolo del documento
		String titolo=getValue("title", element);
				
		//recupero il testo vero e proprio del documento
		nodes=doc.getElementsByTagName("revision");
		String testo=getValue("text", element);
		testo=filtra(testo);
		
		if(stocks.delete())
		{
			System.out.println("File:"+folderdt+"/"+titolo+" cancellato");
		d=new Documento(folderdt,titolo.replaceAll("[^a-zA-Z\\.\\s]", " "),testo);
		d.scriviDocumento(); //file di testo
		
		d.createXMLdoc(testo);
		}
		
					
		//System.out.println("Titolo:"+titolo);
		//System.out.println("Testo:"+testo);
		
		}
		
	}

		private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();

	}
		
		
		
		
		public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException
		{
			/*String nomexml="tutto.xml";
			estrazionetesto ett=new estrazionetesto(nomexml);
			ett.extract();
			*/
			Documento dd=new Documento(System.getProperty("user.dir") + "/src","bu");
			dd.creaXMLdom(" What is A in ISO basic Latin alphabet?");
		}
}

	  
