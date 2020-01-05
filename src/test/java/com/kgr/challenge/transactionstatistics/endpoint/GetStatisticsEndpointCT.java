package com.kgr.challenge.transactionstatistics.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class GetStatisticsEndpointCT {

    private RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    @Test
    void should_return_statistic_for_added_transactions() throws Exception {
        postTransactions(10);

        StatisticResponse statistics = getStatistics();

        assertThat(statistics.getTotalSalesAmount()).isEqualTo("55.00");
        assertThat(statistics.getAverageAmountPerOrder()).isEqualTo("5.50");
    }

    private StatisticResponse getStatistics() {
        return restTemplate.getForObject("http://localhost:" + port + "/statistics", StatisticResponse.class);
    }

    private void postTransactions(int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            postTransaction(Double.valueOf(i));
        }
    }

    private void postTransaction(double amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("sales_amount", Double.toString(amount));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + "/sales", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
