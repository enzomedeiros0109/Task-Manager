package taskManager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Pessoa{

	private final String nome;
	private final int idade;
	private final String[] diasDaSemana = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
	private final HashMap<String, ArrayList<String>> obrigacoes;

	public Pessoa(String nome, int idade) {
	        this.nome = nome;
	        this.idade = idade;   
	        this.obrigacoes = new HashMap<>();

	        for (String dia : diasDaSemana) {
	            obrigacoes.put(dia, new ArrayList<>());
	        }
	    }

	public void adicionarObrigacaoSemAviso(String tarefa, String dia) {

		if (obrigacoes.containsKey(dia)) {
			obrigacoes.get(dia).add(tarefa);
		}

	}

	public void adicionarObrigacao(String tarefa, String dia) {
		if (tarefa.contains(",") || tarefa.contains(";")) {
			JOptionPane.showMessageDialog(null, "O texto possui caracteres inválidos! Remova as vírgulas ou ponto e vírgula");
			return;
		}

		ArrayList<String> tarefasDoDia = obrigacoes.get(dia);

		for (int i = 0; i < tarefasDoDia.size(); i++){

			if(tarefasDoDia.get(i).equalsIgnoreCase(tarefa)){

				JOptionPane.showMessageDialog(null, "Tarefa já foi adicionada!");
				return;
			}
		}

		tarefasDoDia.add(tarefa);
		JOptionPane.showMessageDialog(null, "Tarefa adicionada!");
	}

	public void removerObrigacao(String tarefa, String dia) {

		if(tarefa.contains(",") || tarefa.contains(";")) {
	 		
	 		JOptionPane.showMessageDialog(null, "O texto possui caracteres inválidos! Remova as vírgulas ou ponto e vírgula");
	 		return;
	 	}

		ArrayList<String> tarefasDoDia = obrigacoes.get(dia);

		if (tarefasDoDia == null || tarefasDoDia.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nenhuma obrigação cadastrada para este dia");
			return;
		}

		for (int i = 0; i < tarefasDoDia.size(); i++){

			if(tarefasDoDia.get(i).equalsIgnoreCase(tarefa)){

				tarefasDoDia.remove(i);
				JOptionPane.showMessageDialog(null, "Obrigacão removida");
				return;
			} else JOptionPane.showMessageDialog(null, "Obrigacão não encontrada para o dia");
		}

	    }
	
	public void removerObrigacao(String dia) {
		
		if(obrigacoes.containsKey(dia)) {
			
			obrigacoes.get(dia).clear();
			
		}
		
	}
	    
	public String[] getDiasDaSemana() {
		return diasDaSemana;
	}
	 
	public HashMap<String, ArrayList<String>> getObrigacoes() {
		
		return obrigacoes;
	}

	public String getNome() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	@Override
	public String toString() {
	    return nome + " (" + idade + ")"; // Para o JComboBox de AbaAcessarObrigacoes mostrar o nome da pessoa e não o endereço de memória
	}
}
