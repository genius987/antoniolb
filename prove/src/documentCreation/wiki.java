package documentCreation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;



public class wiki {
	
	public static void downloadcontentfilm(String title) throws IOException, ParserConfigurationException, TransformerException {
    	String text="";
    	URL url = new URL("http://en.wikipedia.org/w/api.php?format=txt&action=query&titles="+title.replaceAll(" ","%20")+"&prop=extracts");
    	title=title.replaceAll("%26","&");
    	title=title.replaceAll(":"," ");
    	title=title.replaceAll("\\."," ");
    	title=title.replaceAll("%27","\'");
    	String titleTopic=title.replaceAll("\\([^\\)]*\\)","").replaceAll("[_]+"," ").replaceAll("[^a-zA-Z0-9]"," ");
      	BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "/src/topics/"+titleTopic),"UTF-8"));
	try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        String line="";
        while ((line = reader.readLine()) != null)
            text+=line+" ";
        reader.close();
        } catch (MalformedURLException e) {
        e.printStackTrace();
    }  catch (IOException e) {
        e.printStackTrace();
    }
	title=title.replaceAll("%26","&");
	title=title.replaceAll(":"," ");
	title=title.replaceAll("\\."," ");
	text=extractOnlyPlot(text);
	text=deleteIntro(text);
	text=deleteCitation(text);
	text=deleteUselessPart(text);
	text=deleteEntities(text);
	text=deleteTitle(text);
	text=deleteEnd(text);
	Documento doc=new Documento(System.getProperty("user.dir") + "/xmlqa/data",title);
	doc.createXMLdoc(text);
	writer.write(titleTopic+" "+text.replaceAll("</?p>",""));
	writer.close();
		   }
	
	public static void downloadcontent(String title) throws IOException, ParserConfigurationException, TransformerException {
    	String text="";
    	URL url = new URL("http://en.wikipedia.org/w/api.php?format=txt&action=query&titles="+title.replaceAll(" ","%20")+"&prop=extracts");
    	title=title.replaceAll("%26","&");
    	title=title.replaceAll(":"," ");
    	title=title.replaceAll("\\."," ");
    	title=title.replaceAll("%27","\'");
    	String titleTopic=title.replaceAll("\\([^\\)]*\\)","").replaceAll("[_]+"," ").replaceAll("[^a-zA-Z0-9]"," ");
      	BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "/src/topics/"+titleTopic),"UTF-8"));
	try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        String line="";
        while ((line = reader.readLine()) != null)
            text+=line+" ";
        reader.close();
        } catch (MalformedURLException e) {
        e.printStackTrace();
    }  catch (IOException e) {
        e.printStackTrace();
    }
	title=title.replaceAll("%26","&");
	title=title.replaceAll(":"," ");
	title=title.replaceAll("\\."," ");
	//text=extractOnlyPlot(text);
	text=deleteIntro(text);
	text=deleteCitation(text);
	text=deleteUselessPart(text);
	text=deleteEntities(text);
	text=deleteTitle(text);
	text=deleteEnd(text);
	Documento doc=new Documento(System.getProperty("user.dir") + "/xmlqa/data",title);
	doc.createXMLdoc(text);
	writer.write(titleTopic+" "+text.replaceAll("</?p>",""));
	writer.close();
		   }

public static String deleteIntro(String text) {
	String intro="^Array \\(     \\[query\\].*?\\[extract\\] => ";
	text=text.replaceAll(intro,"");
	return text;
}
public static String deleteCitation(String text){
	String regex="<sup class=.*?sup>";
	text=text.replaceAll(regex,"");
	return text;
}

public static String extractOnlyPlot(String text) {
	int i=text.indexOf("<h2> Plot</h2>");
	if(i<0) {
		i=text.indexOf("<h2> Synopsis</h2>");
		if(i<0)
			i=text.indexOf("<h2> Plot Summary</h2>");
		if(i<0)
			i=text.indexOf("<h2> Plot details</h2>");
	}
    text=text.substring(i+14);
    i=text.indexOf("</p> <h2>");
    text=text.substring(0,i+4);
    return text;
}

public static String deleteEnd(String text) {
String regex="\\) \\) \\) \\)$";
text=text.replaceAll(regex,"");
text=text.replaceAll("\\s{2,}"," ");
return text; 
}

public static String deleteUselessPart(String text) {
	String regex="h[0-9] Publications/h[0-9].*?";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> See also</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> References</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> Notes/<h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> Further reading</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> External links</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> Sources</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	regex="<h[0-9]> Cultural references</h[0-9]>.*?$";
	text=text.replaceAll(regex,"");
	return text;
}

public static String deleteEntities(String text) {
	text=text.replaceAll("<small class=.*?>", "");
	text=text.replaceAll("</?small>", "");
	text=text.replaceAll("</?sup>", "");
	text=text.replaceAll("</?li>", "");
	text=text.replaceAll("</?ul>", "");
	text=text.replaceAll("</?br>", "");
	text=text.replaceAll("</?&nbsp;", "");
	text=text.replaceAll("</?dt>", "");
	text=text.replaceAll("</?dd>", "");
	text=text.replaceAll("</?ol>", "");
	text=text.replaceAll("</?dl>", "");
	text=text.replaceAll("</?b>", "");
	text=text.replaceAll("</?i>", "");
	text=text.replaceAll("</?u>", "");
	text=text.replaceAll("<p></p>", "");
	text=text.replaceAll("\\s{2,}"," ");
	return text;
}

public static String deleteTitle(String text) {
	String regex="<h[0-9]>.*?</h[0-9]>";
	text=text.replaceAll(regex, "");
	return text;
}


	
	
}
