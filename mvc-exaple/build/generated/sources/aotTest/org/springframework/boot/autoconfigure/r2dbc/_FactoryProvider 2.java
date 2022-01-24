package org.springframework.boot.autoconfigure.r2dbc;

public abstract class _FactoryProvider {
  public static ConnectionFactoryBeanCreationFailureAnalyzer connectionFactoryBeanCreationFailureAnalyzer(
      ) {
    return new ConnectionFactoryBeanCreationFailureAnalyzer();
  }

  public static MissingR2dbcPoolDependencyFailureAnalyzer missingR2dbcPoolDependencyFailureAnalyzer(
      ) {
    return new MissingR2dbcPoolDependencyFailureAnalyzer();
  }

  public static MultipleConnectionPoolConfigurationsFailureAnalzyer multipleConnectionPoolConfigurationsFailureAnalzyer(
      ) {
    return new MultipleConnectionPoolConfigurationsFailureAnalzyer();
  }

  public static NoConnectionFactoryBeanFailureAnalyzer noConnectionFactoryBeanFailureAnalyzer() {
    return new NoConnectionFactoryBeanFailureAnalyzer();
  }
}
