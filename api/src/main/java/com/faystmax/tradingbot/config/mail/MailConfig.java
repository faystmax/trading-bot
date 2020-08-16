package com.faystmax.tradingbot.config.mail;

import com.faystmax.tradingbot.service.mail.MailMessageHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public ImapMailReceiver mailReceiver(ApplicationContext context, MailProperties mailProperties) {
        var receiver = new ImapMailReceiver(buildUrl(mailProperties));
        receiver.setShouldDeleteMessages(false);
        receiver.setShouldMarkMessagesAsRead(true);
        receiver.setBeanFactory(context);
        receiver.setJavaMailProperties(getMailProperties());
        receiver.setSimpleContent(false);
        receiver.setAutoCloseFolder(false);
        receiver.afterPropertiesSet();
        return receiver;
    }

    private String buildUrl(MailProperties mailProperties) {
        return "imaps://" + mailProperties.getUsername() + ':' + mailProperties.getPassword() +
            '@' + mailProperties.getHost() + '/' + mailProperties.getFolder();
    }

    public Properties getMailProperties() {
        var props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.store.protocol", "imaps");
        return props;
    }

    @Bean
    public ImapIdleChannelAdapter channelAdapter(ImapMailReceiver mailReceiver, DirectChannel directChannel) {
        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.afterPropertiesSet();

        var channelAdapter = new ImapIdleChannelAdapter(mailReceiver);
        channelAdapter.setAutoStartup(true);
        channelAdapter.setShouldReconnectAutomatically(true);
        channelAdapter.setTaskScheduler(threadPoolTaskScheduler);
        channelAdapter.setOutputChannel(directChannel);
        channelAdapter.start();
        return channelAdapter;
    }

    @Bean
    public DirectChannel directChannel(MailMessageHandler messageHandler) {
        DirectChannel directChannel = new DirectChannel();
        directChannel.subscribe(messageHandler);
        return directChannel;
    }
}
