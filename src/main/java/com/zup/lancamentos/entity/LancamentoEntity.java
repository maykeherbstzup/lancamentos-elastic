package com.zup.lancamentos.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "tbep9410_lanc_pgto_insn")
public class LancamentoEntity {
  @DynamoDBHashKey(attributeName = "cod_idef_cont")
  private String idConta;

  @DynamoDBRangeKey(attributeName = "cod_idef_trsf_pix")
  private String e2eid;

  @DynamoDBTypeConvertedJson
  @DynamoDBAttribute(attributeName = "lancamento")
  private Lancamento lancamento;
}