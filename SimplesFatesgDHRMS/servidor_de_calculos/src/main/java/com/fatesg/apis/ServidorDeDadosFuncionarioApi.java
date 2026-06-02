package com.fatesg.apis;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import com.fatesg.biblioteca.dtos.FuncionarioDto;
import com.fatesg.biblioteca.interfaces.ServidorDeDadosFuncionarioInterface;
import com.fatesg.config.RmiConfig;

public class ServidorDeDadosFuncionarioApi implements ServidorDeDadosFuncionarioInterface {
    private ArrayList<ServidorDeDadosFuncionarioInterface> servidores;

    public void Conectar() {
        if (this.servidores != null && !this.servidores.isEmpty()) {
            this.servidores.clear();
        } else {
            this.servidores = new ArrayList<>();
        }

        AddServico(RmiConfig.RMI_SERVICE_NAME, RmiConfig.RMI_HOST, RmiConfig.RMI_PORT);
        
        AddServico(RmiConfig.RMI_SERVICE_NAME, RmiConfig.RMI_HOST_SECOND, RmiConfig.RMI_PORT_SECOND);

        if (this.servidores.isEmpty()) {
            System.err.println("Nenhum servidor de funcionários disponível. Verifique as conexões RMI.");
        }
    }

    private void AddServico(String serviceName, String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            var servico = (ServidorDeDadosFuncionarioInterface) registry.lookup(serviceName);
            this.servidores.add(servico);
        } catch (RemoteException e) {
            System.err.println("Erro na comunicação com o servidor no construtor de FuncionariosService:");
            // e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("Serviço não encontrado no registry no construtor de FuncionariosService:");
            // e.printStackTrace();
        }
    }

    @Override
    public FuncionarioDto obterFuncionarioPorId(int id) throws RemoteException {
        for (var s : this.servidores) {
            try {
                var resultado = s.obterFuncionarioPorId(id);
                if (resultado != null) {
                    return resultado;
                }
            } catch (Exception e) {
                System.err.println("Erro ao obter funcionário com ID " + id);
            }
        }
        return null;
    }

    @Override
    public List<FuncionarioDto> listarFuncionarios(int limite, int offset) throws RemoteException {
        for (var s : this.servidores) {
            try {
                var resultado = s.listarFuncionarios(limite, offset);
                if (resultado != null && !resultado.isEmpty()) {
                    return resultado;
                }
            } catch (Exception e) {
                System.err.println("Erro ao listar funcionários");
            }
        }
        return new ArrayList<>();
    }

    @Override
    public int obterQtdeFuncionarios() throws RemoteException {
        for (var s : this.servidores) {
            try {
                var resultado = s.obterQtdeFuncionarios();
                if (resultado > 0) {
                    return resultado;
                }
            } catch (Exception e) {
                System.err.println("Erro ao obter quantidade de funcionários");
            }
        }
        return 0;
    }
}
