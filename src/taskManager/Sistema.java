package taskManager;
import java.io.IOException;


public class Sistema {
    public Sistema() throws IOException {
    	
    	JanelaMenuPrincipal menu = new JanelaMenuPrincipal();
        ListaPessoas lista = new ListaPessoas("tabela.csv");
        lista.lerDoCSV();
    }
}
	
