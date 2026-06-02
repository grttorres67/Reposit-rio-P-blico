package com.ramyres.ui.tui;

import java.util.Scanner;

import com.ramyres.servicos.BaskaraService;
import com.ramyres.servicos.PalavrasService;
import com.ramyres.servicos.PiService;
import com.ramyres.model.EstatisticaDoTexto;

public class UserInterfaceTui {

    private void showMenu() {
        System.out.println("Menu:");
        System.out.println("1. Calcular Pi com casas decimais");
        System.out.println("2. Calcular Raízes de uma Equação Quadrática");
        System.out.println("3. Estatísticas de um texto");
        System.out.println("0. Sair");
    }

    private void callPiService(Scanner scanner) {
        System.out.println("Informe a quantidade de casas decimais para Pi:");
        long casasDecimais = scanner.nextLong();

        System.out.println("Calculando Pi com " + casasDecimais + " casas decimais...");
        
        PiService piService = new PiService();
        
        var result = piService.CalcularPi(casasDecimais);
        System.out.println("Resultado: " + result.toString());
    }

    private void callBaskaraService(Scanner scanner) {
        System.out.println("Informe os coeficientes a, b e c da equação quadrática (ax^2 + bx + c = 0):");
        System.out.print("a: ");
        double a = scanner.nextDouble();
        System.out.print("b: ");
        double b = scanner.nextDouble();
        System.out.print("c: ");
        double c = scanner.nextDouble();

        System.out.println("Calculando as raízes da equação...");
        
        BaskaraService baskaraService = new BaskaraService();
        
        var result = baskaraService.CalcularRaizes(a, b, c);
        System.out.println("Resultado: " + result.toString());
    }

    private void callPalavrasService(Scanner scanner) {
        System.out.println("Informe o texto para análise:");
        scanner.nextLine(); // Limpar o buffer do teclado
        String texto = scanner.nextLine();

        System.out.println("Analisando texto...");
        
        PalavrasService palavrasService = new PalavrasService();
        
        EstatisticaDoTexto result = palavrasService.ObterEstatistica(texto);
        
        System.out.println("\n--- Estatísticas do Texto ---");
        System.out.println("Quantidade de letras: " + result.qtdLetras);
        System.out.println("Quantidade de palavras: " + result.qtdPalavras);
        System.out.println("Quantidade de vogais: " + result.qtdVogal);
        System.out.println("Quantidade de vogais acentuadas: " + result.qtdVogalAcentuada);
        System.out.println("Quantidade de consoantes: " + result.qtdConsoantes);
        System.out.println("Quantidade de espaços: " + result.qtdEspacos);
        System.out.println("Quantidade de palavras com tamanho par: " + result.qtdPalavrasPar);
        System.out.println("Quantidade de palavras com tamanho ímpar: " + result.qtdPalavrasImpar);
        System.out.println("Quantidade de linhas: " + result.qtdDeLinhas);
        System.out.println("----------------------------\n");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao cliente de RPC!");
        while (true) {
            showMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    callPiService(scanner);
                    break;
                case 2:
                    callBaskaraService(scanner);
                    break;
                case 3:
                    callPalavrasService(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
