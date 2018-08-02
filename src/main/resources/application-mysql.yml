
# Spring 数据源 (DataSourceAutoConfiguration & DataSourceProperties)
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/customer_db?useSSL=false
    username: root
    password: root
  jpa:
#    hibernate:
#      ddl-auto: create
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect # Hibernate 属性，SQL 方言使得 Hibernate 为所选数据库生成更好的 SQL
        show_sql: true
        format_sql: true
        use_sql_comments: true
        generate_statistics: false
  jackson:
    serialization:
      write-dates-as-timestamps: true

logging.file: logs/api-stack-base.log
logging.file.max-size: 50MB
logging.file.max-history: 10
# Logging pattern for the console
logging.pattern.console: "%date %level [%thread] %logger{10} [%file:%line] %msg%n"
# Logging pattern for file
logging.pattern.file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
logging:
    level:
      root: info
      org.springframework: debug
  #    org.springframework.orm.jpa.JpaTransactionManager: debug
  #    org.springframework.transaction: trace
      org.hibernate: info
      org.hibernate.SQL: debug
  #    org.hibernate.type.descriptor.sql.BasicBinder: trace
  #    org.hibernate.transaction: debug
  #    org.hibernate.jpa.internal: debug
  #    org.hibernate.event.internal: debug
  #    org.hibernate.engine.transaction.internal: debug
  #    org.hibernate.internal.util: debug
      com.tw.apistackbase: debug

