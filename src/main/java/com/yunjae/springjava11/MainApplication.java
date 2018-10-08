package com.yunjae.springjava11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(MainApplication.class, args);

        MainApplication main = new MainApplication();
        System.out.println("============= JAVA URLConnection =============");
        main.executeURLConnection();
        System.out.println("============= Spring RestTemplate =============");
        main.executeRestTemplate();
        System.out.println("============= Java 11 HttpClient Blocking =============");
        main.executeHttpClientBlock();
        System.out.println("============= Java 11 HttpClient NonBlocking =============");
        // HttpClient asynchronous 문제가 있음
        main.executeHttpClientNonBlock();
        Thread.sleep(5 * 1000l);
        System.out.println("============= Spring 5 WebClient =============");
        // Spring 5 webClient 사용 추천
        main.executeWebClient();
    }

    /**
     * URL Connection
     * @throws IOException
     */
    private void executeURLConnection() throws IOException {
        URL url = new URL("http://localhost:8080/delay?seconds=3");
        URLConnection connection = url.openConnection();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
    }

    /**
     * RestTemplate
     */
    private void executeRestTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String response = restTemplate.getForObject(("http://localhost:8080/delay?seconds=2"), String.class);
        System.out.println(response);
    }

    /**
     * synchronous HttpClient
     * @throws IOException
     * @throws InterruptedException
     */
    private void executeHttpClientBlock() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build() ;
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/delay?seconds=4")).build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println("print first");
    }

    /**
     * asynchronous HttpClient
     * @throws IOException
     * @throws InterruptedException
     */
    private void executeHttpClientNonBlock() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient() ;
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/delay?seconds=5")).build();
        //CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        var future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        future.thenApply(HttpResponse::body)
                .thenAccept(r -> {
                    System.out.println(r);
                    System.out.println(System.currentTimeMillis());
                });


        System.out.println("print first");
    }

    /**
     * Spring 5 WebClient
     */
    private void executeWebClient() {
        WebClient webClient = WebClient.builder().build();
        Mono<String> mono = webClient.get().uri("http://localhost:8080/delay?seconds=1")
                .retrieve()
                .bodyToMono(String.class);

        mono.subscribe(r -> {
            System.out.println(r);
            System.out.println(System.currentTimeMillis());
        });

    }
}
