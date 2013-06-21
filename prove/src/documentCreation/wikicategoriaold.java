package documentCreation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


public class wikicategoriaold {
	
	static File folderxml=new File(System.getProperty("user.dir")+"/xmlqa/data/");
	private static LinkedHashMap<String, String> hm0 = new LinkedHashMap<String, String>();

	
	public static boolean isFile(String name)
	{
		if(name.startsWith("File:")) return true;
		else return false;
	}
	
	public static boolean isCategory(String name) {
		if(name.startsWith("Category:")) return true;
		else return false;
	}
	
	public static boolean isTemplate(String name) {
		if(name.startsWith("Template:")) return true;
		else return false;
	}
	
	public static void generaXml(String title) throws IOException, ParserConfigurationException, TransformerException, SAXException	{
		String stringaxml="";	
	URL url = new URL("http://en.wikipedia.org/w/api.php?format=xml&action=query&titles="+title.replace(" ", "%20")+"&Page&prop=revisions&rvprop=content&export&exportnowrap");
		
	  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	  BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	  String line = read.readLine();
	  while(line!=null) {
		 
				  stringaxml += line+"\n";
				   line = read.readLine(); 
	   
	  }
	  
	  title=title.replaceAll(":", " ");
	  System.out.println("Scrivo il file\t"+title);
	  FileOutputStream file = new FileOutputStream(folderxml.getAbsolutePath()+"/"+title+".xml");
	  OutputStreamWriter osw = new OutputStreamWriter(file, "UTF-8"); //ho messo UTF-8 perchè altrimenti alcuni caratteri non vengono codificati
		
	     osw.write(stringaxml);
	    osw.close();
	   
		estrazionetestoold ett=new estrazionetestoold(title+".xml");
		ett.extract();
	   
	}
	
	public static void cercaWiki(String category) throws IOException {
	
		String url = "http://en.wikipedia.org/w/api.php?format=xml&action=query&list=categorymembers&cmtitle=Category:"
				+ category + "&cmsort=timestamp&cmdir=desc";
		Document doc = Jsoup.connect(url).get();
		Elements id = doc.select("cm[pageid]");
		for (Element x : id) {
			if(isCategory(x.attr("title").toString())) 
				cercaWiki(x.attr("title").toString().substring(9).replace(" ", "%20"));
			else if(!isTemplate(x.attr("title").toString()) && !hm0.containsKey(x.attr("title").toString()) && !isFile(x.attr("title").toString()))
				hm0.put(x.attr("title").toString(), x.attr("pageid"));
		}
	}
	public static void LoadPage() throws IOException, ParserConfigurationException, TransformerException, SAXException {
	Set list  = hm0.keySet();
		Iterator iter = list.iterator();
		while(iter.hasNext()) 
		     generaXml(iter.next().toString());
	}
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
		cercaWiki("Allah");
		LoadPage();
		}
}
	
