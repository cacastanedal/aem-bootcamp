package com.aembootcamp.core.services.impl;

import com.aembootcamp.core.services.SubscriptionService;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component(service = SubscriptionService.class)
public class SubscriptionServiceImpl implements SubscriptionService {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
  private static final String SUBSCRIPTION_FOLDER = "/content/aem-bootcamp/subscribers";

  @Override
  public void saveUserSubscription(String email, String firstName, String lastName, ResourceResolver resourceResolver) {

    log.info("Email: {}\nFirstName: {}\nLastName: {}", email, firstName, lastName);

    try{
      Resource subscribtionResource = ResourceUtil.getOrCreateResource(resourceResolver, SUBSCRIPTION_FOLDER,
        "nt:unstructured", null, true);
    } catch (PersistenceException e){
      log.error(e.getMessage());
    }

  }

}
