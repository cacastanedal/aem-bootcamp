package com.aembootcamp.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
  resourceType = HeaderModel.RESOURCE_TYPE,
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SubscriptionModel {

  protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/subscription";

  @Inject
  private Page currentPage;

  // page tags
  // redirect URL
  @ValueMapValue(name = "linkURL")
  private String redirectURL;

  private List<String> pageTags;

  @PostConstruct
  protected void init(){
    pageTags = new ArrayList<>();

    for(Tag tag : currentPage.getTags()){
      pageTags.add(tag.getTitle());
    }
  }

  public String getRedirectURL(){
    return redirectURL;
  }

  public List<String> getPageTags(){
    return pageTags;
  }


}
