package com.zup.lancamentos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoRequestDTO {
    String id_conta;
    String visao;
    String tipo_lancamento;
    String tipo_operacao;
    String tipo;
    String id_documento_revisao;
    String contas_lancamentos;
    String data_lancamento;
    String data_emissao;
    String id_documento;
    String id_transferencia;
    String txid;
    String e2eid;
    String chaves_enderecamento;
    String valor_documento_pagador_efetivo;
    String data_vencimento;
    String indicador_sem_data_vencimento;
    String devolvido;
    Integer page = 1;
    Integer page_size = 10;
    String order_by;
    String order;
    String view = "basico";

    public LancamentoRequestDTO() {}
}
