package org.springframework.boot.autoconfigure.flyway;

public abstract class _FactoryProvider {
  public static FlywayMigrationScriptMissingFailureAnalyzer flywayMigrationScriptMissingFailureAnalyzer(
      ) {
    return new FlywayMigrationScriptMissingFailureAnalyzer();
  }

  public static FlywayMigrationInitializerDatabaseInitializerDetector flywayMigrationInitializerDatabaseInitializerDetector(
      ) {
    return new FlywayMigrationInitializerDatabaseInitializerDetector();
  }
}
