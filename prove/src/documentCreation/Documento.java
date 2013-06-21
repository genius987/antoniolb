package documentCreation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Documento {

		public String percorso;
		public String nome;
		public String testo;
		
		
		public Documento(String folder)
		{
			percorso=folder;
			nome=null;
			testo=null;
		}
		public Documento(String folder,String n)
		{
			percorso=folder;
			nome=n;
			testo=null;
		}
		public Documento(String folder,String n,String t)
		{
			percorso=folder;
			nome=n;
			testo=t;
		}
		
		public void scriviDocumento() throws FileNotFoundException
		{
			String path=percorso+"/"+nome;
			 FileOutputStream file = new FileOutputStream(path);
		      PrintStream Output = new PrintStream(file);
		      
		      Output.println(testo);
		      Output.close();
		}
		public void scriviDocumento(String path) throws FileNotFoundException
		{
			 FileOutputStream file = new FileOutputStream(path+nome);
		      PrintStream Output = new PrintStream(file);
		      
		      Output.println(testo);
		      Output.close();
		}
		public void createXMLdoc(String dascrivere) throws ParserConfigurationException, TransformerException
		{
			
			//per scrivere nel file xml
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document docstream = docBuilder.newDocument();
			Element rootElement = docstream.createElement("DOCSTREAM");
			docstream.appendChild(rootElement);
			
			// staff elements
			Element doc = docstream.createElement("DOC");
			rootElement.appendChild(doc);
	 
			// set attribute to staff element
			doc.setAttribute("id", nome);
			doc.setAttribute("type", "story");
			
	 	 
			// lastname elements
			Element testo = docstream.createElement("TEXT");
			doc.appendChild(testo);
			testo.setNodeValue(dascrivere);
			String[] frase=dascrivere.split("<p>");
			for(int i=1;i<frase.length;i++)
			{
			// nickname elements
			Element paragraph = docstream.createElement("P");
			paragraph.appendChild(docstream.createTextNode(frase[i].replaceAll("</p>", "")));
			testo.appendChild(paragraph);
	 		}
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(docstream);
			StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/xmlqa/data/"+nome+".xml"));
						 
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);
			 System.out.println(System.getProperty("user.dir") + "/xmlqa/data/"+nome+".xml");
					 
					transformer.transform(source, result);
						System.out.println("File saved!");
				
			
			
		}
		
		//xml per la domanda
		public void creaXMLdom(String domanda) throws ParserConfigurationException, TransformerException
		{
			//per scrivere nel file xml
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document docstream = docBuilder.newDocument();
			Element rootElement = docstream.createElement("trecqa");
			rootElement.setAttribute("year","2013");
			rootElement.setAttribute("task","main");
			docstream.appendChild(rootElement);
			// staff elements
			Element target = docstream.createElement("target");
			target.setAttribute("id", "1");
			rootElement.appendChild(target);
				 
						
				 
			Element qa = docstream.createElement("qa");
			target.appendChild(qa);
			
			Element q= docstream.createElement("q");
			q.setAttribute("id","1.1");
			q.setAttribute("type","FACTOID");
			q.appendChild(docstream.createTextNode(domanda));
			qa.appendChild(q);
				 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(docstream);
			StreamResult result = new StreamResult(new File(System.getProperty("user.dir") +"/xmlqa/question/demo-question.xml"));
				 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
				 
			transformer.transform(source, result);
				 
			System.out.println("File saved!");
		}
		
}
