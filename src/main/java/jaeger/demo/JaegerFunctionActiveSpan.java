package jaeger.demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.opentracing.Span;
import io.opentracing.Tracer;

import static lib.Utils.initTracer;

public class JaegerFunctionMultipleSpan {

    private final Tracer tracer;

    private JaegerFunctionMultipleSpan(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args){

        if(args.length != 1){
            throw new IllegalArgumentException("One Argument is mandatory");
        }

        String helloServiceArg = args[0];
        Tracer tracer = initTracer("jaeger-subspans");
        new JaegerFunctionMultipleSpan(tracer).sayHello(helloServiceArg);
    }

    private void sayHello(String helloServiceArg) {
        Span span = tracer.buildSpan("hello-span").start();
        span.log("Span started");

        span.setTag("function param", helloServiceArg);

        String value = oneFunction(span,helloServiceArg);
        String otherValue = otherFunction(span,helloServiceArg);

        String frmHello = String.format("Hello, %s", helloServiceArg);
        Multimap<String, String> map = ImmutableMultimap.of("event","sayHello","value",frmHello);
        span.log(map.toString());

        System.out.println(frmHello);
        span.log("Span completed");

        span.finish();
    }

    private String oneFunction(Span parentSpan, String helloServiceArg) {
        Span span = tracer.buildSpan("oneFunction").asChildOf(parentSpan).start();
        try {
            span.setTag("function name", "oneFunction");
            span.setTag("function param", "helloServiceArg");

            String valueResult = String.format("Value added 1 oneFunction, %s!", helloServiceArg);
            span.log(ImmutableMap.of("event", "added", "count", valueResult));
            return valueResult;
        }
        finally {
            span.finish();
        }
    }

    private String otherFunction(Span parentSpan, String helloServiceArg) {
        Span span = tracer.buildSpan("otherFunction").asChildOf(parentSpan).start();
        try {
            span.setTag("function name", "otherFunction");
            span.setTag("function param", "helloServiceArg");

            String valueResult = String.format("Value added otherFunction 1, %s!", helloServiceArg);
            span.log(ImmutableMap.of("event", "added", "count", valueResult));
            return valueResult;
        }
        finally {
            span.finish();
        }
    }
}
