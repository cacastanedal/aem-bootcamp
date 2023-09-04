package com.aembootcamp.core.services.impl;

import com.aembootcamp.core.services.SubscribeUser;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import java.time.LocalDateTime;
import java.util.List;

@Component(service = SubscribeUser.class)
public class SubscribeUserImpl implements SubscribeUser {


  private static final String CONTENT_PATH = "/content/aem-bootcamp/subscribers/";

  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  @Override
  public void saveUserSubscription(String email, String firstName, String lastName, List<String> articles) {

    ResourceResolver resourceResolver = null;
    try {
      // Obtain a ResourceResolver from the factory
      resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);

      // Use the resourceResolver for your operations here

    } catch (Exception e) {
      // Handle exceptions
    } finally {
      // Always close the resourceResolver when done
      if (resourceResolver != null) {
        resourceResolver.close();
      }
    }


    assert resourceResolver != null;

    // Could fail if content_path doesn't exist
    Resource userSubscriptionContent = resourceResolver.getResource(CONTENT_PATH);

    assert userSubscriptionContent != null;

    try {
      Resource userSubscription = resourceResolver.create(userSubscriptionContent, LocalDateTime.now().toString(), null);
      ValueMap userSubscriptionProperties = userSubscription.adaptTo(ValueMap.class);

      assert userSubscriptionProperties != null;

      userSubscriptionProperties.put("email", email);
      userSubscriptionProperties.put("firstName", firstName);
      userSubscriptionProperties.put("lastName", lastName);
      userSubscriptionProperties.put("articles", articles);
    }
    catch (PersistenceException e){
      System.out.println(e.getMessage());
    }

  }

  @Override
  public String greetings(){
    return "Hello from Subscribe Service";
  }

}
