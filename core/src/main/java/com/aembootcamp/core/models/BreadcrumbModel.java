package com.aembootcamp.core.models;

import com.day.cq.search.result.Hit;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType = "aem-bootcamp/components/general/footer")
public class BreadcrumbModel {

  private static final Logger log = LoggerFactory.getLogger(BreadcrumbModel.class);

  @SlingObject
  private ResourceResolver resourceResolver;

  @Inject
  private Page currentPage;

  public List<Page> parentPages;

  public List<Page> getParentPages(){
    return parentPages;
  }

  public List<String> getListNavDisplayName(){
    ArrayList<String> navDisplayName = new ArrayList<>();
    for(Page page : parentPages) {
      if(page.getNavigationTitle() != null){
        navDisplayName.add(page.getNavigationTitle());
        continue;
      }

      if(page.getPageTitle() != null){
        navDisplayName.add(page.getPageTitle());
        continue;
      }

      navDisplayName.add(page.getTitle());
    }
    return navDisplayName;
  }

  @PostConstruct
  public void init(){
    parentPages = new ArrayList<>();
    Page nextPage = currentPage;

    while(nextPage.getParent() != null){
      if(!nextPage.isHideInNav())
        parentPages.add(nextPage.getParent());

      nextPage = nextPage.getParent();
    }

    Collections.reverse(parentPages);
  }

  private String getResourceTitle(Hit jcrQueryContent){
    try {

      Resource hitJcrParentNode = jcrQueryContent.getResource().getParent();

      assert hitJcrParentNode != null;

      Page queryPage = hitJcrParentNode.adaptTo(Page.class);

      assert queryPage != null;

      return queryPage.getTitle();

    } catch (RepositoryException e) {
      throw new RuntimeException(e);
    }
  }
}
