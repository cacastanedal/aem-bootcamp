package com.aembootcamp.core.services;

public interface SubscribeUser {
  void saveUserSubscription(String email, String firstName, String lastName, String[] articles);

  String greetings();
}
