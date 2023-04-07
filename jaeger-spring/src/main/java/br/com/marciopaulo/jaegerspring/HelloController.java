package br.com.marciopaulo.jaegerspring;
import io.jaegertracing.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return  "Hello from spring boot";
    }

    @Bean
    public io.opentracing.Tracer initTracer(){
        Configuration.SamplerConfiguration samplerConfiguration = new Configuration.SamplerConfiguration().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return Configuration.fromEnv("boot-service").withSampler(samplerConfiguration).withReporter(reporterConfiguration).getTracer();
    }
}
