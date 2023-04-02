package jaeger.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;

import static lib.Utils.initTracer;

public class JaegerLogs {

    private final Tracer tracer;

    private JaegerLogs(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args){

        if(args.length != 1){
            throw new IllegalArgumentException("One Argument is mandatory");
        }

        String helloServiceArg = args[0];
        Tracer tracer = initTracer("jaeger-logs");
        new JaegerLogs(tracer).sayHello(helloServiceArg);
    }

    private void sayHello(String helloServiceArg) {
        Span span = tracer.buildSpan("hello-span").start();
        span.log("Span started");

        span.setTag("function param", helloServiceArg);
        String frmHello = String.format("Hello, %s", helloServiceArg);
        System.out.println(frmHello);
        span.log("Span completed");

        span.finish();
    }
}
