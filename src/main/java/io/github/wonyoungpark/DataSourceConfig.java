package io.github.wonyoungpark;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties; // 설정한 데이터 소스용 속성들을 저장하는 클래스 주입

    // Spring Data가 제공하는 데이터 소스용 팩토리 클래스를 사용하여 DataSource 인스턴스를 생성
    @Bean
    @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
    DataSource realDataSource() {
        DataSource dataSource = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword())
                .build();
        return dataSource;
    }

    // Log4jdbcProxyDataSource 클래스를 DataSource를 래핑한다. 이 클래스가 DataSource에 구현된 각각의 처리에 로깅 처릴르 끼워 넣습니다.
    @Bean
    @Primary
    DataSource dataSource() {
        return new DataSourceSpy(realDataSource());
    }
}