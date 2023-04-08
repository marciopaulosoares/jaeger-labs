package br.com.marciopaulo.jaegerspring;

import io.jaegertracing.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public  RestTemplate restTemplate(){
        return  new RestTemplate();
    }
    @Bean
    public io.opentracing.Tracer initTracer(){
        Configuration.SamplerConfiguration samplerConfiguration = new Configuration.SamplerConfiguration().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return Configuration.fromEnv("boot-service").withSampler(samplerConfiguration).withReporter(reporterConfiguration).getTracer();
    }

    @RequestMapping("/hello")
    public String hello(){
        return  "Hello from spring boot";
    }

    @RequestMapping("/chaining")
    public String chaining(){
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/hello", String.class);
        return "Chaining response"+response.getBody();
    }
}
