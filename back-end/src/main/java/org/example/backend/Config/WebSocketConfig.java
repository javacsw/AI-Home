package org.example.backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import jakarta.websocket.server.ServerContainer;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(ServerContainer.class)
public class WebSocketConfig {

    @Bean
    @Profile("!test") // 关键：测试环境下不创建这个Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    // 如果你有其他的WebSocket相关Bean，也需要添加@Profile("!test")
}