package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.command.CommandExecutor;
import com.faystmax.tradingbot.service.mail.MailIdleFactory;
import com.faystmax.tradingbot.service.mail.MimeMessageHandler;
import com.faystmax.tradingbot.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class MailIdleFactoryImpl implements MailIdleFactory {
    private final ApplicationContext context;
    private final MessageService messageService;
    private final CommandExecutor commandExecutor;

    @Override
    public ImapIdleChannelAdapter createIdleChannelAdapter(User user, ThreadPoolTaskScheduler taskScheduler) {
        var channelAdapter = new ImapIdleChannelAdapter(createMailReceiver(context, user));
        channelAdapter.setAutoStartup(true);
        channelAdapter.setReconnectDelay(5000);
        channelAdapter.setShouldReconnectAutomatically(true);
        channelAdapter.setTaskScheduler(taskScheduler);
        channelAdapter.setOutputChannel(createDirectChannel(user));
        return channelAdapter;
    }

    private ImapMailReceiver createMailReceiver(ApplicationContext context, User user) {
        var receiver = new ImapMailReceiver(buildUrl(user));
        receiver.setShouldDeleteMessages(false);
        receiver.setShouldMarkMessagesAsRead(true);
        receiver.setBeanFactory(context);
        receiver.setJavaMailProperties(getMailProperties());
        receiver.setSimpleContent(false);
        receiver.setAutoCloseFolder(false);
        receiver.afterPropertiesSet();
        return receiver;
    }

    private Properties getMailProperties() {
        var props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.store.protocol", "imaps");
        return props;
    }

    private String buildUrl(User user) {
        return "imaps://" + user.getEmailUsername() + ':' + user.getEmailPassword() +
            '@' + user.getEmailHost() + '/' + user.getEmailFolder();
    }

    private DirectChannel createDirectChannel(User user) {
        DirectChannel directChannel = new DirectChannel();
        directChannel.subscribe(new MimeMessageHandler(user, messageService, commandExecutor));
        return directChannel;
    }
}
