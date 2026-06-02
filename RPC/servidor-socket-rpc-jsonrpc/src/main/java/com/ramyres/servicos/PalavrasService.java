package com.ramyres.servicos;

public class PalavrasService implements ServiceInterface {

    @Override
    public String[] Run(String[] params) {
        if(params == null || params.length == 0) {
            return new String[]{"0","0","0","0","0","0","0","0","0"};
        }
        
        String texto = params[0];
        
        int qtdLetras = 0;
        int qtdVogal = 0;
        int qtdVogalAcentuada = 0;
        int qtdConsoantes = 0;
        int qtdEspacos = 0;
        
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                qtdLetras++;
                char lower = Character.toLowerCase(c);
                if (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u') {
                    qtdVogal++;
                } else if ("áàãâéèêíìîóòõôúùû".indexOf(lower) != -1) {
                    qtdVogalAcentuada++;
                } else {
                    qtdConsoantes++;
                }
            } else if (c == ' ') {
                qtdEspacos++;
            }
        }
        
        String[] palavras = texto.split(" ");
        int qtdPalavras = palavras.length;
        if(texto.isEmpty()) qtdPalavras = 0;
        
        int qtdPalavrasPar = 0;
        int qtdPalavrasImpar = 0;
        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                if (palavra.length() % 2 == 0) {
                    qtdPalavrasPar++;
                } else {
                    qtdPalavrasImpar++;
                }
            }
        }
        
        int qtdDeLinhas = texto.split("\r\n|\n").length;
        if(texto.isEmpty()) qtdDeLinhas = 0;
        
        return new String[]{
            String.valueOf(qtdLetras),
            String.valueOf(qtdPalavras),
            String.valueOf(qtdVogal),
            String.valueOf(qtdVogalAcentuada),
            String.valueOf(qtdConsoantes),
            String.valueOf(qtdEspacos),
            String.valueOf(qtdPalavrasPar),
            String.valueOf(qtdPalavrasImpar),
            String.valueOf(qtdDeLinhas)
        };
    }
}
