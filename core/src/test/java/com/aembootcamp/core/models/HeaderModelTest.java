package com.aembootcamp.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
public class HeaderModelTest {

  private final AemContext context = new AemContext();
  public static final String HEADER_RESOURCE = "/aem-bootcamp/components/header";
  public static final String PAGE1 = "/content/aem-bootcamp/en/home-page";
  public static final String PAGE2 = "/content/aem-bootcamp/en/home-page1";

  private Resource headerResource;

  private HeaderModel headerModel;


  @BeforeEach
  public void setup(){
    context.load().json("/sample-header-resource.json", HEADER_RESOURCE);
    context.load().json("/sample-page1.json", PAGE1);
    context.load().json("/sample-page2.json", PAGE2);

    context.addModelsForClasses(HeaderModel.class);

  }

  @Test
  public void testWhenHeaderResourceIsNotEmpty(){

    headerResource = context.currentResource(HEADER_RESOURCE);
    assert headerResource != null;
    headerModel = headerResource.adaptTo(HeaderModel.class);

    assert headerModel != null;
    Assert.assertEquals("sample text", headerModel.getAltText());

    Assert.assertEquals("/content/aem-bootcamp/index", headerModel.getLogoLink());

    Assert.assertEquals("/content/dam/we-retail-instore-logo.png", headerModel.getFileReference());

    Assert.assertEquals(2, headerModel.getHeaderNavigationItemsList().size());

  }
}
