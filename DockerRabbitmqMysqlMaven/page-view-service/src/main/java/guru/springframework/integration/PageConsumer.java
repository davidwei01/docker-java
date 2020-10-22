package guru.springframework.integration;

import guru.springframework.domain.PageView;
import guru.springframework.model.events.PageViewEvent;
import guru.springframework.repositories.PageViewsRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PageConsumer {

    @Autowired
    PageViewsRepository repository;

    @RabbitListener(queues = RabbitConfig.INBOUND_QUEUE_NAME)
    public void listener(String xmlString) {
        System.out.println(xmlString);

        InputStream is = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));

        PageViewEvent pageViewEvent =  JAXB.unmarshal(is, PageViewEvent.class);

        PageView pageView = new PageView();
        pageView.setPageUrl(pageViewEvent.getPageUrl());
        pageView.setPageViewDate(pageViewEvent.getPageViewDate());
        pageView.setCorrelationId(pageViewEvent.getCorrelationId());

        repository.save(pageView);
    }

    //    public MessageHandler pageViewMessageHandler(PageViewsRepository repository) {
//        return new MessageHandler() {
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//
//                System.out.println("Got Message!");
//
//                String xmlString = (String) message.getPayload();
//
//                System.out.println(xmlString);
//
//                InputStream is = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
//
//                PageViewEvent pageViewEvent =  JAXB.unmarshal(is, PageViewEvent.class);
//
//                PageView pageView = new PageView();
//                pageView.setPageUrl(pageViewEvent.getPageUrl());
//                pageView.setPageViewDate(pageViewEvent.getPageViewDate());
//                pageView.setCorrelationId(pageViewEvent.getCorrelationId());
//
//                repository.save(pageView);
//            }
//
//        };
//    }
}
