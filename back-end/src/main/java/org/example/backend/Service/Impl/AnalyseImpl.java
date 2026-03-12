package org.example.backend.Service.Impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jakarta.annotation.Resource;
import org.example.backend.Service.Analyse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class AnalyseImpl implements Analyse {

    @Resource
    ChatModel chatModel;

    ChatClient chatClient;

    public AnalyseImpl(ChatClient.Builder builder, ChatMemory chatMemory){
        this.chatClient = builder.defaultSystem("""
                现在你是一名建筑设计师，需要你根据我提供的设计图，帮我进行分析设计图是否合理，并对我的设计图提出修改意见，请用中文回答。如果我上传的图片不为建筑类型，请回复“该图片不为建筑类设计图”
                """)
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
                .build();
    }

    public String AnalyseImag(String url) {
        String message = "现在你是一名建筑设计师，需要你根据我提供的设计图，帮我进行分析设计图是否合理，并对我的设计图提出修改意见，请用中文回答。如果我上传的图片不为建筑类型，请回复“该图片不为建筑类设计图”，然后简略分析一下这张图片 ";
        String response = ChatClient.create(chatModel).prompt()
                .user(u -> {
                    try {
                        u.text(message)
                                .media(MimeTypeUtils.IMAGE_PNG, new UrlResource(url));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .call()
                .content();
        return response;
    }
}
