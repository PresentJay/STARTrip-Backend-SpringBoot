package org.springframework.aot;

import java.lang.Class;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.aot.test.boot.AotSpringBootConfigContextLoader;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.test.context.SmartContextLoader;

public class TestContextBootstrapInitializer {
  public static Map<String, Supplier<SmartContextLoader>> getContextLoaders() {
    Map<String, Supplier<SmartContextLoader>> entries = new HashMap<>();
    entries.put("com.startrip.codebase.StarTripCodeBaseApplicationTests", () -> new AotSpringBootConfigContextLoader(StarTripCodeBaseApplicationTestsContextInitializer.class, WebApplicationType.SERVLET, SpringBootTest.WebEnvironment.MOCK));
    return entries;
  }

  public static Map<String, Class<? extends ApplicationContextInitializer<?>>> getContextInitializers(
      ) {
    Map<String, Class<? extends ApplicationContextInitializer<?>>> entries = new HashMap<>();
    entries.put("com.startrip.codebase.StarTripCodeBaseApplicationTests", StarTripCodeBaseApplicationTestsContextInitializer.class);
    return entries;
  }
}
