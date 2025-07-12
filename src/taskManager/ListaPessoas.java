package taskManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ListaPessoas {

	private final ArrayList<Pessoa> listaPessoas = new ArrayList<>();
	private final String colunas = "Nome,Idade,Segunda,Terca,Quarta,Quinta,Sexta,Sabado,Domingo";
	private final File arquivoCSV;
	
	public ListaPessoas(String caminho) throws IOException {
		this.arquivoCSV = new File(caminho);
		
	    if (!arquivoCSV.exists()) {
	       
	    	arquivoCSV.createNewFile();
	        JOptionPane.showMessageDialog(null, arquivoCSV + " criado!");
	        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoCSV))) {
	            writer.println(colunas);
	        }
	        
    }
}
	
	public void adicionarPessoa(Pessoa pessoa) {

	    if(pessoa.getIdade() > 110) {
	    	
	    	JOptionPane.showMessageDialog(null, "Idade máxima de 110 anos");
	    	
	    } else {
	    	
	    	criarPessoa(pessoa.getNome(), pessoa.getIdade());

            for (String dia : pessoa.getDiasDaSemana()) {
                ArrayList<String> tarefasDoDia = pessoa.getObrigacoes().get(dia);
                if (tarefasDoDia != null && !tarefasDoDia.isEmpty()) {
                    for (String tarefa : tarefasDoDia) {
                        pessoa.adicionarObrigacao(tarefa, dia);
                    }
                }
            }

            listaPessoas.add(pessoa);
	    }
	}
	
	public void removerPessoa(Pessoa pessoa) {

		    if (listaPessoas.contains(pessoa)) {
		        for (String dia : pessoa.getDiasDaSemana()) {
		            ArrayList<String> tarefasDoDia = pessoa.getObrigacoes().get(dia);
		            if (tarefasDoDia != null && !tarefasDoDia.isEmpty()) {
		                for (String tarefa : new ArrayList<>(tarefasDoDia)) {
		                	pessoa.removerObrigacao(tarefa, dia);
		                }
		            }
		        }

		        listaPessoas.remove(pessoa);
		        reescreverCSV();
		    } else {
		    	
		    	JOptionPane.showMessageDialog(null, "Pessoa não cadastrada!");
		    	return;
		    }
	}
	
	public void reescreverCSV() { // Reescreve o CSV, tirando linhas em branco
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCSV))) {
	        writer.write(colunas);
	        writer.newLine(); 

	        for (Pessoa pessoa : listaPessoas) {
	            StringBuilder linha = new StringBuilder();
	            linha.append(pessoa.getNome()).append(",");
	            linha.append(pessoa.getIdade()).append(",");

	            for (String dia : pessoa.getDiasDaSemana()) {
	                ArrayList<String> tarefas = pessoa.getObrigacoes().get(dia);
	                if (tarefas != null && !tarefas.isEmpty()) {
	                    linha.append(String.join(";", tarefas));
	                }
	                linha.append(","); 
	            }

	            writer.write(linha.toString());
	            writer.newLine();
	        }

	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao tentar reescrever " + arquivoCSV);
	    }
	}
		
	public void lerDoCSV() throws IOException {
	    
		if (!arquivoCSV.exists()) {
	        JOptionPane.showMessageDialog(null, "Arquivo inexistente!\nNovo arquivo criado");
 
	    }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            linha = leitor.readLine();

            while ((linha = leitor.readLine()) != null) {
                String[] arrayDeLinhas = linha.split(",");

                String nome = arrayDeLinhas[0].trim(); // remover os espaços em branco nas extremidades
                int idade = 0;

                idade = Integer.parseInt(arrayDeLinhas[1].trim());

                Pessoa pessoa = criarPessoa(nome, idade);

                if (pessoa.getIdade() > 110) {

                    JOptionPane.showMessageDialog(null, "Idade inválida para: " + pessoa.getNome());

                } else {

                    String[] dias = pessoa.getDiasDaSemana();

                    for (int i = 0; i < dias.length; i++) {
                        String tarefasDoDia = "";

                        if (2 + i < arrayDeLinhas.length) {
                            tarefasDoDia = arrayDeLinhas[2 + i].trim();
                        }

                        if (!tarefasDoDia.isEmpty()) {

                            String[] tarefas = tarefasDoDia.split(";");

                            for (String tarefa : tarefas) {
                                tarefa = tarefa.trim();
                                if (!tarefa.isEmpty()) {
                                    pessoa.adicionarObrigacaoSemAviso(tarefa, dias[i]);
                                }
                            }
                        }
                    }

                    listaPessoas.add(pessoa);
                }

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "O arquivo " + arquivoCSV + " possui informãções inválidas!\nPara o funcionamento correto do programa, por favor checar o arquivo manualmente");
        }
	}

	public void atualizarPessoa(Pessoa pessoaAtualizada) {
	    for (int i = 0; i < listaPessoas.size(); i++) {
	        Pessoa pessoaTemporaria = listaPessoas.get(i);
	        if (pessoaTemporaria.getNome().equals(pessoaAtualizada.getNome()) && pessoaTemporaria.getIdade() == pessoaAtualizada.getIdade()) {
	            listaPessoas.set(i, pessoaAtualizada);
	            return;
	        }
	    }
	}
	
	public Pessoa criarPessoa(String nome, int idade) {

		return new Pessoa(nome, idade);
    }

	public boolean verificarPessoaCSV(Pessoa pessoa) throws IOException {

	    BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivoCSV));
	        String linha;
	        while ((linha = bufferedReader.readLine()) != null) {
	            if (linha.startsWith(pessoa.getNome() + "," + pessoa.getIdade() + ",")) {
	                bufferedReader.close();
	            	return true;
	            }
	        }
	        bufferedReader.close();
	        return false; // Nenhuma duplicata encontrada
	    }

	
	public ArrayList<Pessoa> getListaPessoas() {
		return listaPessoas;
	}
}
