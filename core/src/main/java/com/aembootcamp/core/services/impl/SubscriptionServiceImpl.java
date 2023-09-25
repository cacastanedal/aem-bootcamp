package com.aembootcamp.core.services.impl;

import com.aembootcamp.core.services.SubscriptionService;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Component(service = SubscriptionService.class)
public class SubscriptionServiceImpl implements SubscriptionService {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
  private static final String SUBSCRIPTIONS = "/content/aem-bootcamp/subscribers";

  @Override
  public void saveUserSubscription(String email, String firstName, String lastName, String article,
                                   ResourceResolver resourceResolver) {

    log.info("Email: {}\nFirstName: {}\nLastName: {}", email, firstName, lastName);

    try{
      Resource subscriptionsResource = ResourceUtil.getOrCreateResource(resourceResolver, SUBSCRIPTIONS,
        "nt:unstructured", null, true);

      Resource currentDateResource = getOrCreateCurrentDateNode(subscriptionsResource, resourceResolver);

      Resource emailResource = currentDateResource.getChild(email);

      if(emailResource != null){
        log.info("Email already stored");
      } else {

        resourceResolver.create(currentDateResource, email,
          Map.of("firstName", firstName, "lastName", lastName, "article", article));
        resourceResolver.commit();
      }

    } catch (PersistenceException e){
      log.error(e.getMessage());
    }

  }
  private Resource getOrCreateCurrentDateNode(Resource subscriptions, ResourceResolver resourceResolver)
    throws PersistenceException{
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMuuuu"));

    Resource dateResource = subscriptions.getChild(currentDate);

    if(dateResource == null){
      Resource response = resourceResolver.create(subscriptions, currentDate, Map.of());
      resourceResolver.commit();
      return response;
    } else {
      return dateResource;
    }
  }

}
