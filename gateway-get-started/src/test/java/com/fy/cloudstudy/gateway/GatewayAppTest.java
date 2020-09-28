package com.fy.cloudstudy.gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class GatewayAppTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads(){
        stubFor(get(urlEqualTo("/get"))
        .willReturn(aResponse()
        .withBody("{\"headers\":{\"Hello\":\"World\"}}")
        .withHeader("Content-Type", "application/json")));

        stubFor(get(urlEqualTo("/delay/3"))
        .willReturn(aResponse()
        .withBody("no fallback111")
        .withFixedDelay(2000)
        ));

        webTestClient.get()
                .uri("/get")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.headers.Hello").isEqualTo("World");

        webTestClient.get()
                .uri("/delay/3")
                .header("Host", "www.hystrix.com")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(entityExchangeResult ->
                        Assert.assertArrayEquals(entityExchangeResult.getResponseBody(), "fallback".getBytes()));
    }
}
