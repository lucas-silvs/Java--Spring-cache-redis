package com.lucassilvs.rediscacheclient.config.cache;

import io.lettuce.core.ReadFrom;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.redis")
public class RedisCacheConfiguration {

    //@Value("${spring.redis.main}")
    private RedisInstance main;

    //@Value("{spring.redis.replicas}")
    private List<RedisInstance> replicas;


    @Bean
    public LettuceConnectionFactory cacheRedisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA)
                .build();
        RedisStaticMasterReplicaConfiguration redisStaticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(
                this.main.getHost(),
                this.main.getPort()
        );

        replicas.forEach(replica -> redisStaticMasterReplicaConfiguration.addNode(replica.getHost(), replica.getPort()));

        return new LettuceConnectionFactory(redisStaticMasterReplicaConfiguration, clientConfiguration);
    }


    private static class RedisInstance {
        private String host;
        private int port;

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }
}
