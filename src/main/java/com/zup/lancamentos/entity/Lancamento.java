package com.zup.lancamentos.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Lancamento {
  private String idLancamento;
  private String idConta;
  private String tipoPix;
  private String nomeTerceiro;
  private String tipoLancamento;
  private String visao;
  private String literalLancamento;
  private String tipoOperacao;
  private String tipoConta;
  private String idPix;
  private String txId;
  private String e2eid;
  private String chaveEnderecamento;
  private String valorDocumentoPagadorEfetivo;
  private String devolvido;

//  @JsonDeserialize(using = LocalDateDeserializer.class)
//  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate dataEmissao;

//  @JsonDeserialize(using = LocalDateDeserializer.class)
//  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate dataVencimento;

  private Pagamento detalhePagamento;
  private Devolucao detalheDevolucao;

  public Lancamento() {}
}
