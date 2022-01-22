package org.springframework.boot.context.properties;

public abstract class _FactoryProvider {
  public static IncompatibleConfigurationFailureAnalyzer incompatibleConfigurationFailureAnalyzer(
      ) {
    return new IncompatibleConfigurationFailureAnalyzer();
  }

  public static NotConstructorBoundInjectionFailureAnalyzer notConstructorBoundInjectionFailureAnalyzer(
      ) {
    return new NotConstructorBoundInjectionFailureAnalyzer();
  }
}
