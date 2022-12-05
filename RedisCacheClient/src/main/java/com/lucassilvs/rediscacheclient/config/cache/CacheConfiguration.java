package com.lucassilvs.rediscacheclient.config.cache;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class CacheConfiguration {

    private RedisInstance master;
    private List<RedisInstance> slaves;
    public RedisInstance getMaster() {
        return master;
    }

    public void setMaster(RedisInstance master) {
        this.master = master;
    }

    public List<RedisInstance> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<RedisInstance> slaves) {
        this.slaves = slaves;
    }

    @Bean
    public LettuceConnectionFactory cacheRedisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .clientOptions(ClientOptions.builder().autoReconnect(true).pingBeforeActivateConnection(true).build())
                .build();
        RedisStaticMasterReplicaConfiguration redisStaticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(
                this.master.getHost(),
                this.master.getPort()
        );

        slaves.forEach(replica -> redisStaticMasterReplicaConfiguration.addNode(replica.getHost(), replica.getPort()));

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
