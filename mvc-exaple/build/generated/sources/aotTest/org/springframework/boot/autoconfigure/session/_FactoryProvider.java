package org.springframework.boot.autoconfigure.session;

public abstract class _FactoryProvider {
  public static NonUniqueSessionRepositoryFailureAnalyzer nonUniqueSessionRepositoryFailureAnalyzer(
      ) {
    return new NonUniqueSessionRepositoryFailureAnalyzer();
  }

  public static JdbcIndexedSessionRepositoryDependsOnDatabaseInitializationDetector jdbcIndexedSessionRepositoryDependsOnDatabaseInitializationDetector(
      ) {
    return new JdbcIndexedSessionRepositoryDependsOnDatabaseInitializationDetector();
  }
}
