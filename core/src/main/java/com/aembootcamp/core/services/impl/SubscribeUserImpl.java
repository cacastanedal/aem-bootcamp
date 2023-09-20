package com.aembootcamp.core.services.impl;

import com.aembootcamp.core.services.SubscribeUser;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component(service = SubscribeUser.class)
public class SubscribeUserImpl implements SubscribeUser {

  private static final Logger log = LoggerFactory.getLogger(SubscribeUserImpl.class);

  private static final String CONTENT_PATH = "/content/aem-bootcamp/subscribers";

  @SuppressWarnings("ThreadSafeField")
  @Reference
  private ResourceResolver resourceResolver;

  @Override
  public void saveUserSubscription(String email, String firstName, String lastName, String[] articles) {

    // create jcr node under /content/aem-bootcamp/subscribers

    Resource subscribersResource = resourceResolver.getResource(CONTENT_PATH);

    if(subscribersResource != null){
      String newSubscriberName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMuuuu"));

      try {
        Resource newSubscriber = resourceResolver.create(subscribersResource, newSubscriberName, null);

        newSubscriber.adaptTo(ModifiableValueMap.class).put("email", email);
        resourceResolver.commit();
      } catch (PersistenceException e){
        log.error(e.getMessage());
      }

    }

  }

  @Override
  public String greetings(){
    return "Hello from Subscribe Service";
  }

}
