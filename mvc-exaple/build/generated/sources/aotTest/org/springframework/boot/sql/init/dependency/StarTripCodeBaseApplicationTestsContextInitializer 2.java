package org.springframework.boot.sql.init.dependency;

import org.springframework.aot.beans.factory.BeanDefinitionRegistrar;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;

public final class StarTripCodeBaseApplicationTestsContextInitializer {
  public static void registerDatabaseInitializationDependencyConfigurer_DependsOnDatabaseInitializationPostProcessor(
      DefaultListableBeanFactory beanFactory) {
    BeanDefinitionRegistrar.of("org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer$DependsOnDatabaseInitializationPostProcessor", DatabaseInitializationDependencyConfigurer.DependsOnDatabaseInitializationPostProcessor.class).withConstructor(Environment.class)
        .instanceSupplier((instanceContext) -> instanceContext.create(beanFactory, (attributes) -> new DatabaseInitializationDependencyConfigurer.DependsOnDatabaseInitializationPostProcessor(attributes.get(0)))).register(beanFactory);
  }
}
