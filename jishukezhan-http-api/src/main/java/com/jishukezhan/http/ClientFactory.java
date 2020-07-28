package com.jishukezhan.http;

import com.jishukezhan.annotation.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Client工厂
 *
 * @author miles.tang
 */
public class ClientFactory {

    private static final Logger log = LoggerFactory.getLogger(ClientFactory.class);

    private static ClientBuilder defaultClientBuilder;

    private static final Map<String, ClientBuilder> allRegistryClientBuilder = new HashMap<>();

    static {
        findClientBuilderImplClasses();
    }

    private static void findClientBuilderImplClasses() {
        ServiceLoader<ClientBuilder> loader = ServiceLoader.load(ClientBuilder.class);
        loader.forEach(clientBuilder -> {
            Class<? extends ClientBuilder> clazz = clientBuilder.getClass();
            String name = parseName(clazz);
            log.info("Registry a client builder {} for {}", name, clazz.getCanonicalName());
            allRegistryClientBuilder.put(name, clientBuilder);
            if (defaultClientBuilder == null) {
                defaultClientBuilder = clientBuilder;
            }
        });
        if (defaultClientBuilder == null) {
            defaultClientBuilder = new ClientBuilder.Default();
        }
    }

    private static String parseName(Class<? extends ClientBuilder> clazz) {
        Name name = clazz.getAnnotation(Name.class);
        if (name != null) {
            String nameStr = name.value();
            nameStr = nameStr.trim();
            if (!nameStr.isEmpty()) {
                return nameStr;
            }
        }
        return clazz.getName();
    }

    public static ClientBuilder get() {
        return defaultClientBuilder;
    }

    public static ClientBuilder get(String name) {
        return allRegistryClientBuilder.get(name);
    }

}
