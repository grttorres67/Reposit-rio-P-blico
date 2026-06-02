package com.ramyres.servicos;

/*
Pesquise sobre os metodos e escolha um
    * Método de Arquimedes
    * Método de Chudnovsky
    * Método de Leibniz
    * Método de Machin
*/
public class PiService implements ServiceInterface {
    private double arctan(double x) {
        double result = 0.0;
        double term = x;
        double x_squared = x * x;
        int n = 1;
        while (Math.abs(term) > 1e-15) {
            result += term / n;
            term = -term * x_squared;
            n += 2;
        }
        return result;
    }

    private double calculatePiMachin() {
        return 16.0 * arctan(1.0 / 5.0) - 4.0 * arctan(1.0 / 239.0);
    }

    @Override
    public String[] Run(String[] params) {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }
        int casas = Integer.parseInt(params[0]);
        double pi = calculatePiMachin();
        
        java.math.BigDecimal bd = new java.math.BigDecimal(pi);
        bd = bd.setScale(casas, java.math.RoundingMode.DOWN);
        return new String[]{ bd.toString() };
    }
}