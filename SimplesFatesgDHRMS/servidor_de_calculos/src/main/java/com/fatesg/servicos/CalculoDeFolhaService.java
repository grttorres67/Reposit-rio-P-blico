package com.fatesg.servicos;

import java.util.HashMap;
import java.util.List;

import com.fatesg.apis.ServidorDeDadosFuncionarioApi;
import com.fatesg.apis.ServidorDeDadosSalarioApi;
import com.fatesg.biblioteca.dtos.FolhaDto;
import com.fatesg.biblioteca.dtos.FuncionarioDto;
import com.fatesg.biblioteca.dtos.ReciboDto;
import com.fatesg.biblioteca.dtos.SalarioDto;
import com.fatesg.biblioteca.interfaces.ServidorDeCalculoFolhaInterface;

public class CalculoDeFolhaService implements ServidorDeCalculoFolhaInterface {
    private ServidorDeDadosSalarioApi stub;
    private ServidorDeDadosFuncionarioApi stubFuncionario;

    public CalculoDeFolhaService() {
        this.stub = new ServidorDeDadosSalarioApi();
        this.stub.Conectar();
        this.stubFuncionario = new ServidorDeDadosFuncionarioApi();
        this.stubFuncionario.Conectar();
    }

    @Override
    public FolhaDto calcularFolhaDePagamento(byte mes, short ano, HashMap<String, Double> descontos) {
        try {
            FolhaDto folha = new FolhaDto(mes, ano);
            int offset = 0;
            int limit = 50;
            List<SalarioDto> salarios;
            // do {
                salarios = stub.listarSalarios(limit, offset);
                for (SalarioDto salarioDto : salarios) {
                    int idFuncionario = salarioDto.getIdFuncionario();
                    double salarioBruto = salarioDto.getValor() / 12;
                    var recibo = new ReciboDto(
                            mes,
                            ano,
                            idFuncionario,
                            new SalarioDto(idFuncionario, salarioBruto));

                    descontos.forEach((k, v) -> {
                        recibo.addDesconto(k, v);
                    });

                    double salarioLiquido = calcularSalarioLiquido(salarioBruto, descontos);
                    recibo.setSalarioLiquido(salarioLiquido);

                    folha.addRecibo(recibo);
                }
                offset++;
            // } while (salarios.size() > 0);
            return folha;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReciboDto calcularReciboDePagamento(int idFuncionario, byte mesReferencia, short anoReferencia,
            HashMap<String, Double> descontos) {
        try {
            SalarioDto salarioBrutoAnual = stub.obterSalarioPorId(idFuncionario);
            if (salarioBrutoAnual == null)
                throw new Exception("Salario não encontrado para o ID:" + idFuncionario);

            double salarioBruto = salarioBrutoAnual.getValor() / 12;
            var recibo = new ReciboDto(
                    mesReferencia,
                    anoReferencia,
                    idFuncionario,
                    new SalarioDto(idFuncionario, salarioBruto));

            descontos.forEach((k, v) -> {
                recibo.addDesconto(k, v);
            });

            double salarioLiquido = calcularSalarioLiquido(salarioBruto, descontos);
            recibo.setSalarioLiquido(salarioLiquido);

            return recibo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public double calcularSalarioLiquido(double salarioBruto, HashMap<String, Double> descontos) {
        var salarioLiquido = salarioBruto;
        for (var desconto : descontos.values()) {
            var valorDesconto = salarioBruto * (desconto / 100d);
            salarioLiquido -= valorDesconto;
        }
        return salarioLiquido;
    }

    @Override
    public FolhaDto calcularFolhaDePagamentoDoDepartamento(String idDepartamento, byte mes, short ano,
            HashMap<String, Double> descontos) {
        try {
            FolhaDto folha = new FolhaDto(mes, ano);
            int offset = 0;
            int limit = 50;
            List<FuncionarioDto> funcionarios;
            
            // Itera pelos funcionários em paginação
            do {
                funcionarios = stubFuncionario.listarFuncionarios(limit, offset);
                
                for (FuncionarioDto funcionario : funcionarios) {
                    // Verifica se o funcionário pertence ao departamento especificado
                    if (funcionario.getDepartamento() != null && 
                        funcionario.getDepartamento().getId().equals(idDepartamento)) {
                        
                        // Calcula o recibo de pagamento para o funcionário
                        ReciboDto recibo = calcularReciboDePagamento(
                                funcionario.getId(),
                                mes,
                                ano,
                                descontos);
                        
                        if (recibo != null) {
                            folha.addRecibo(recibo);
                        }
                    }
                }
                offset++;
            } while (funcionarios.size() > 0);
            
            return folha;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
