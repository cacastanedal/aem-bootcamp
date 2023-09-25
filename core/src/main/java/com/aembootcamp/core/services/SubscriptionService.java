package com.aembootcamp.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface SubscriptionService {
  void saveUserSubscription(String email, String firstName, String lastName, String article, ResourceResolver resourceResolver);

}
