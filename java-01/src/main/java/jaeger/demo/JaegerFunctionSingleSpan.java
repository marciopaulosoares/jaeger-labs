package jaeger.demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.opentracing.Span;
import io.opentracing.Tracer;

import static lib.Utils.initTracer;

public class JaegerFunctionSingleSpan {

    private final Tracer tracer;

    private JaegerFunctionSingleSpan(Tracer tracer) {
        this.tracer = tracer;
    }

    public static void main(String[] args){

        if(args.length != 1){
            throw new IllegalArgumentException("One Argument is mandatory");
        }

        String helloServiceArg = args[0];
        Tracer tracer = initTracer("jaeger-logs");
        new JaegerFunctionSingleSpan(tracer).sayHello(helloServiceArg);
    }

    private void sayHello(String helloServiceArg) {
        Span span = tracer.buildSpan("hello-span").start();
        span.log("Span started");

        span.setTag("function param", helloServiceArg);

        String valueAdd = valueAdd(span,helloServiceArg);
        String valueAddOhter = valueAddOther(span,helloServiceArg);

        String frmHello = String.format("Hello, %s", helloServiceArg);
        Multimap<String, String> map = ImmutableMultimap.of("event","sayHello","value",frmHello);
        span.log(map.toString());

        System.out.println(frmHello);
        span.log("Span completed");

        span.finish();
    }

    private String valueAdd(Span span, String helloServiceArg) {
        span.setTag("function name", "valueAdd");
        span.setTag("function param", "helloServiceArg");

        String valueResult = String.format("Value Added 1, %s!",helloServiceArg);
        span.log(ImmutableMap.of("event","value added","value", valueResult));

        return valueResult;
    }

    private String valueAddOther(Span span, String helloServiceArg) {
        span.setTag("function name", "valueAddOther");
        span.setTag("function param", "helloServiceArg");

        String valueResult = String.format("Value Added Other, %s!",helloServiceArg);
        span.log(ImmutableMap.of("event","value added Other","value", valueResult));

        return valueResult;
    }
}
