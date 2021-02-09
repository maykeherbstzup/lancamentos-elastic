package com.zup.lancamentos.controllers;

import com.amazonaws.RequestClientOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.lancamentos.consumers.PixEmitidoConsumer;
import com.zup.lancamentos.entity.Lancamento;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

  @Autowired
  PixEmitidoConsumer pixEmitidoConsumer;

  @Autowired
  RestHighLevelClient client;

  @PostMapping
  public void postLancamentos(@PathParam(value = "qt") int qt) throws IOException {
    List<Lancamento> lancamentos = new ArrayList<>();

    for (int i = 0; i <= qt; i++) {
      lancamentos.add(pixEmitidoConsumer.handlerPixEmitido(i));
    }

   this.sendToES(lancamentos);
  }

  public void sendToES(List<Lancamento> lancamentos) throws IOException {

    lancamentos.stream().forEach(lancamento -> {
      ObjectMapper mapper = new ObjectMapper();

      IndexRequest request = new IndexRequest("lancamentos");
      request
              .id(lancamento.getE2eid())
              .source(mapper.convertValue(lancamento, HashMap.class) , XContentType.JSON);

      try {
        client.index(request, RequestOptions.DEFAULT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    client.close();
  }
}
