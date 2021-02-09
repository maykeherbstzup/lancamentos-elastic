package com.zup.lancamentos.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConfiguration.class);
  private RestHighLevelClient restHighLevelClient;

  @Bean
  public RestHighLevelClient createInstance() {
    return buildClient();
  }

  private RestHighLevelClient buildClient() {
    try {
      RestHighLevelClient client = new RestHighLevelClient(
              RestClient.builder(
                      new HttpHost("localhost", 9200, "http"),
                      new HttpHost("localhost", 9201, "http")
              )
      );

      return client;
    } catch (Exception e) {
      LOG.error("deu ruim");
      LOG.error(e.getMessage());
      throw e;
    }
  }
}
