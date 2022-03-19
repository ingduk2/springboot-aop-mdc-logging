### SpringBoot AOP-MDC Logging

* Environment
  * Java 17 (for record)
  * SpringBoot Web, Aop
  * Lombok

* TODO
  * Unit Test
  * Source
  * Document
  * AOP Basic -> AOP Annotation -> Interceptor or Filter

* AOP Annotation
  * @LogFull - mdc, executionTime, exception, returnValue
  * @LogExecutionTime - executionTime
  * @LogReturnValue - returnValue
  * 3개의 Annotation을 만들어 각각 필요한 곳에 적용 할 수 있도록 해봄.
  * Controller Request Param Validate 적용.

* Filter MDC
  * OncePerRequestFilter 사용.
  * requestWrapper 는 HttpServletRequestWrapper 사용.
  * responseWrapper 는 ContentCachingResponseWrapper 사용.

### 방법
|     |AOP| Interceptor                         | Filter                 |
|-----|---|-------------------------------------|------------------------|
| 장점  |간단하다| 인가 같은 공통 처리 가능                      | response header set 적합 |
| 단점  |AOP, Aspect를 타지 않는다면 로그를 못 남김| response header set 하는 경우에는 적합하지 않음 | 적용은 간단하나 동작 원리 이해가 헷갈림 |

### reference
* Aop, MDC
  * [Spring MVC Logging With AOP+MDC](https://lucas-k.tistory.com/8)
  * [MCD에 put만 계속한다면](https://namocom.tistory.com/862)
  * [SpringBoot logback MDC](https://codingdog.tistory.com/entry/spring-boot-logback-MDC%EB%8A%94-%EC%96%B4%EB%96%A4-%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94%EC%A7%80-%EA%B0%84%EB%8B%A8%ED%95%98%EA%B2%8C-%EB%B6%84%EC%84%9D%ED%95%B4-%EB%B4%85%EC%8B%9C%EB%8B%A4)
  * [Filter, Interceptor, AOP](https://prohannah.tistory.com/184?category=870127)
  * [AOP](https://offbyone.tistory.com/34)
  * [AOP - Annotation - Class](https://ryumodrn.tistory.com/19)
  * [MDC ThreadLocal](https://velog.io/@bonjugi/MDC-%EC%9D%98-%EB%8F%99%EC%9E%91%EB%B0%A9%EC%8B%9D-ThreadLocal)
* Spring Bean Validation
  * [Spring-Bean-Validation](https://kapentaz.github.io/spring/Spring-Boo-Bean-Validation-%EC%A0%9C%EB%8C%80%EB%A1%9C-%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90/#)
* Interceptor, Filter
  * [Spring Interceptor request, response body json](https://hirlawldo.tistory.com/44)
  * [Interception posthandle](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-handlermapping-interceptor)
  * [Filter vs OncePerRequestFilter](https://minkukjo.github.io/framework/2020/12/18/Spring-142/)
  * [spring Request 우아하게 logging](https://taetaetae.github.io/2019/06/30/controller-common-logging/)
  * [spring logging request, response](https://prohannah.tistory.com/182)