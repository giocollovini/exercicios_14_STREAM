package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class ProgramStream {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);  
		
		System.out.println();
		System.out.println("===========Exercício resolvido - filter, sorted, map, reduce======================");
		System.out.println();
		System.out.println("Fazer um programa que, a partir de uma lista de produtos, arquivo em formato .csv"); 
		System.out.println("Mostrar o preço médio dos produtos. Depois, mostrar os nomes, em ordem decrescente,");
		System.out.println("dos produtos que possuem preço inferior ao preço médio.");
		System.out.println("'Tv', 900.00");
		System.out.println("'Mouse', 50.00");
		System.out.println("'Tablet', 350.50");
		System.out.println("'HD Case', 80.90");
		System.out.println("'Computer', 850.00");
		System.out.println("'Monitor', 290.00");
		System.out.println();		
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			double avg = list.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x,y) -> x + y) / list.size();
			
			System.out.println("Average price: " + String.format("%.2f", avg));
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()); //cria comparador de String
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)  //filtra por valores menores que a média
					.map(p -> p.getName()).sorted(comp.reversed()) //reverse para ordenar decrescente
					.collect(Collectors.toList());
			
			names.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}
}
