package com.ramyres.protocolo;

public class JsonRpcRequest {
    //{"jsonrpc": "2.0", "method": "subtract", "params": [42, 23], "id": 3}
    public long id;
    public String jsonRpc;
    public String method;
    public String[] params;
}
