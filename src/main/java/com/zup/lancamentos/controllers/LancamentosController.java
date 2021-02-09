package com.zup.lancamentos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zup.lancamentos.dto.LancamentoRequestDTO;
import com.zup.lancamentos.entity.Lancamento;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lancamentos")
public class LancamentosController {
  @Autowired
  RestHighLevelClient client;
  
  @GetMapping
  public ResponseEntity<?> getLancamentos(LancamentoRequestDTO lancamentoRequestDTO) throws IOException {
    List<Lancamento> lancamentos = this.getFromES(lancamentoRequestDTO);

    return ResponseEntity.ok(lancamentos);
  }

  public List<Lancamento> getFromES(LancamentoRequestDTO lancamentoRequestDTO) throws IOException {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.size(lancamentoRequestDTO.getPage_size());
    searchSourceBuilder.query(this.buildQueryParameters(lancamentoRequestDTO));

    SearchRequest searchRequest = new SearchRequest("lancamentos");
    searchRequest.source(searchSourceBuilder);

    SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

    SearchHits hits = response.getHits();

    ObjectMapper mapper = new ObjectMapper();

    mapper.registerModule(new JavaTimeModule());

    List<Lancamento> list = Arrays.stream(hits.getHits()).map(l -> {
      try {
        return mapper.readValue(l.getSourceAsString(), Lancamento.class);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    }).collect(Collectors.toList());

    return list;
  }

  public BoolQueryBuilder buildQueryParameters(LancamentoRequestDTO params) {
    BoolQueryBuilder queryBuilder = new BoolQueryBuilder();

    if (params.getId_conta() != null) {
      queryBuilder.must(QueryBuilders.matchQuery("idConta", params.getId_conta()).operator(Operator.AND));
    }

    if (params.getTxid() != null) {
      queryBuilder.must(QueryBuilders.matchQuery("txId", params.getTxid()).operator(Operator.AND));
    }

    if (params.getTipo() != null) {
      queryBuilder.must(QueryBuilders.matchQuery("tipoPix", params.getTipo()).operator(Operator.AND));
    }

    return queryBuilder;
  }
}
