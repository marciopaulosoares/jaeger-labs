package lib;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;

public final class Utils {
    public  static JaegerTracer initTracer(String service) {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration config = new Configuration(service).withSampler(samplerConfiguration).withReporter(reporterConfiguration);
        return config.getTracer();
    }
}
