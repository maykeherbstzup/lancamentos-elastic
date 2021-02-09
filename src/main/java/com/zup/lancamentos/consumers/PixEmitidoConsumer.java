package com.zup.lancamentos.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.lancamentos.entity.Lancamento;
import com.zup.lancamentos.entity.LancamentoEntity;
import com.zup.lancamentos.entity.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;


@Component
public class PixEmitidoConsumer {
  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  public Lancamento handlerPixEmitido(int qt) {
    String e2eid = "aab4e22b-9d40-4515-181e6e47de4a-e2eid-" + qt;
    String idConta = "idConta-" + new Random().nextInt(10000);

    Lancamento lancamento = this.createLancamento(e2eid, idConta, qt);

//    LancamentoEntity lancamentoEntity = new LancamentoEntity();
//
//    lancamentoEntity.setE2eid(e2eid);
//    lancamentoEntity.setIdConta(idConta);
//    lancamentoEntity.setLancamento(lancamento);

//    dynamoDBMapper.save(lancamentoEntity);

    return lancamento;
  }

  public Lancamento createLancamento(String e2eid, String idConta, int qt) {
    Lancamento lancamento = new Lancamento();

    lancamento.setIdLancamento("aab4e22b-9d40-4515-181e6e47de4a-idLancamento-" + qt);
    lancamento.setE2eid(e2eid);
    lancamento.setIdConta(idConta);
    lancamento.setTipoPix("dinamico");
    lancamento.setNomeTerceiro("Joao Carlos");
    lancamento.setTipoLancamento("pagamento");
    lancamento.setTipoOperacao("debito");
    lancamento.setDataEmissao(LocalDate.of(2020, 1, 1));
    lancamento.setIdPix("aab4e22b-9d40-4515-181e6e47de4a-idPix-" + qt);
    lancamento.setChaveEnderecamento("aab4e22b-9d40-4515-181e6e47de4a-chave-" + qt);
    lancamento.setTxId("aab4e22b-9d40-4515-181e6e47de4a-txId-" + qt);

    Pagamento pagamento = new Pagamento();

//    pagamento.setData(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    pagamento.setIdPagamento("aab4e22b-9d40-4515-181e6e47de4a-idPagamento-" + qt);
    pagamento.setIdTransferencia(null);
    pagamento.setValor(new BigDecimal(123.99));

    lancamento.setDetalhePagamento(pagamento);

    return lancamento;
  }

  public HashMap getJsonData() {
    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      URL url = classLoader.getResource("PagamentoDebito.json");
      File file = new File(url.getPath());

      ObjectMapper mapper = new ObjectMapper();
      return (HashMap) mapper.readValue(file, HashMap.class).get("data");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
