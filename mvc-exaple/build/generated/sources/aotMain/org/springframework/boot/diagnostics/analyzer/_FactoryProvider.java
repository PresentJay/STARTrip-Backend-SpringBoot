package org.springframework.boot.diagnostics.analyzer;

public abstract class _FactoryProvider {
  public static BeanCurrentlyInCreationFailureAnalyzer beanCurrentlyInCreationFailureAnalyzer() {
    return new BeanCurrentlyInCreationFailureAnalyzer();
  }

  public static BeanDefinitionOverrideFailureAnalyzer beanDefinitionOverrideFailureAnalyzer() {
    return new BeanDefinitionOverrideFailureAnalyzer();
  }

  public static BindFailureAnalyzer bindFailureAnalyzer() {
    return new BindFailureAnalyzer();
  }

  public static BindValidationFailureAnalyzer bindValidationFailureAnalyzer() {
    return new BindValidationFailureAnalyzer();
  }

  public static UnboundConfigurationPropertyFailureAnalyzer unboundConfigurationPropertyFailureAnalyzer(
      ) {
    return new UnboundConfigurationPropertyFailureAnalyzer();
  }

  public static ConnectorStartFailureAnalyzer connectorStartFailureAnalyzer() {
    return new ConnectorStartFailureAnalyzer();
  }

  public static MutuallyExclusiveConfigurationPropertiesFailureAnalyzer mutuallyExclusiveConfigurationPropertiesFailureAnalyzer(
      ) {
    return new MutuallyExclusiveConfigurationPropertiesFailureAnalyzer();
  }

  public static NoSuchMethodFailureAnalyzer noSuchMethodFailureAnalyzer() {
    return new NoSuchMethodFailureAnalyzer();
  }

  public static NoUniqueBeanDefinitionFailureAnalyzer noUniqueBeanDefinitionFailureAnalyzer() {
    return new NoUniqueBeanDefinitionFailureAnalyzer();
  }

  public static PortInUseFailureAnalyzer portInUseFailureAnalyzer() {
    return new PortInUseFailureAnalyzer();
  }

  public static InvalidConfigurationPropertyNameFailureAnalyzer invalidConfigurationPropertyNameFailureAnalyzer(
      ) {
    return new InvalidConfigurationPropertyNameFailureAnalyzer();
  }

  public static InvalidConfigurationPropertyValueFailureAnalyzer invalidConfigurationPropertyValueFailureAnalyzer(
      ) {
    return new InvalidConfigurationPropertyValueFailureAnalyzer();
  }

  public static PatternParseFailureAnalyzer patternParseFailureAnalyzer() {
    return new PatternParseFailureAnalyzer();
  }
}
