package com.aembootcamp.core.services;

import java.util.List;

public interface SubscribeUser {
  void saveUserSubscription(String email, String firstName, String lastName, List<String> articles);

  String greetings();
}
