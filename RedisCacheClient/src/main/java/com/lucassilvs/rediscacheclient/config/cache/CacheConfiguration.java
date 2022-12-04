package com.lucassilvs.rediscacheclient.config.cache;

import io.lettuce.core.ReadFrom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class CacheConfiguration {

    private RedisInstance master;

    private ArrayList<RedisInstance> slaves;


    private static final String API_PREFIX = "auth";
    private static final String SEPARATOR = ":";
    private static final Logger LOG = LogManager.getLogger(CacheConfiguration.class);
    public RedisInstance getMaster() {
        return master;
    }

    public void setMaster(RedisInstance master) {
        this.master = master;
    }

    public List<RedisInstance> getSlaves() {
        return slaves;
    }

    public void setSlaves(ArrayList<RedisInstance> slaves) {
        this.slaves = slaves;
    }

    @Bean
    public LettuceConnectionFactory cacheRedisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        RedisStaticMasterReplicaConfiguration redisStaticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(
                this.master.getHost(),
                this.master.getPort()
        );

        slaves.forEach(replica -> redisStaticMasterReplicaConfiguration.addNode(replica.getHost(), replica.getPort()));

        return new LettuceConnectionFactory(redisStaticMasterReplicaConfiguration, clientConfiguration);

    }
    @Bean(value = "cacheManager")
    public CacheManager redisCacheManager(LettuceConnectionFactory cacheRedisConnectionFactory) {
        LOG.info("[Cache] injecting TTL cache: enabled");
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        redisCacheConfiguration.usePrefix();

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cacheRedisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }





    private static class RedisInstance {
        private String host;
        private int port;

        public RedisInstance() {
        }

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
