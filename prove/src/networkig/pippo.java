package networkig;

public class pippo extends Thread{
	
	String nome;
	
	pippo(String nome){
		
		this.nome=nome;
		
		
	}
	@Override
	
	
	public void run() {
	
		// TODO Auto-generated method stub
		
		while(true){
		System.out.println("sono il thread: "+nome);
		try {
			this.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	

}
