# Estudo sobre implementação do Jaeger para geração de Tracing distribuídos.
https://www.jaegertracing.io

### Referências
- https://logback.qos.ch (padrão para estruturação de logs)
- https://www.baeldung.com/logback
- https://github.com/google/guava
- https://www.baeldung.com/guava-multimap
## Configuração de logs e tags
```
private void sayHello(String helloServiceArg) {

    Span span = this.tracer.buildSpan("hello-span").start();
    span.log("Span started");
    span.setTag("function param", helloServiceArg);
    
    //algum código....
    
    span.log("Span completed");
    span.finish();
}
```
**Tags** são informações que podem se acrescentadas ao span para ajudar na monitoração da aplicação.

![alt text](./images/span-tags.png)

** Logs **  são informações específicas registradas durante o span.

![alt text](./images/span-logs.png)
