package stemmer;



public class main {

	public enum flag {
		
	    SALVAMATRICEFREQUENZENELDB(1);
	    
	    private flag(int i){
	    	
	    	
	    }
	};  
	
	
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		
		
Stemmer pi=new Stemmer();

System.out.println(pi.stemming("hugs"));

				
		// TODO Auto-generated method stub

	}

}
