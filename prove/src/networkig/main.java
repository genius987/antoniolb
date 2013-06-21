package networkig;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

String pippo="ciao	don't io sono;stefano;firenza parigi";

String p[]=pippo.split("[^a-zA-Z0-9']");


for (int j=0;j<p.length;j++) {
	

System.out.println(p[j]);


}

}
	
}
