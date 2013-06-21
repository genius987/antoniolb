package stemmer;

public class Stemmer {

	public Stemmer()
	{
		
	}
	/*
	 * Metodo che permette di effettuare l'algoritmo di stemming parola per
	 * parola
	 * 
	 * N.B. Come detto inizialmente, abbiamo utilizzato le librerie presenti nel
	 * sito http://snowball.tartarus.org/
	 */
	public static String stemming(String parola)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class stemClass = Class.forName("stemmer.englishStemmer");
		SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
		int repeat = 1;
		stemmer.setCurrent(parola);

		for (int i = repeat; i != 0; i--) {
			stemmer.stem();
		}
		return stemmer.getCurrent();

	}
}
