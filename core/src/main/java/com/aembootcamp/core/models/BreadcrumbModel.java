package com.aembootcamp.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType = "aem-bootcamp/components/general/footer")
public class BreadcrumbModel {

  private static final Logger log = LoggerFactory.getLogger(BreadcrumbModel.class);
  @OSGiService
  private QueryBuilder queryBuilder;

  @SlingObject
  private ResourceResolver resourceResolver;

  @Inject
  private Page currentPage;

  public List<String> pageLists;

  public List<Page> parentPages;

  public List<String> getPageLists(){
    return pageLists;
  }

  public List<Page> getParentPages(){
    return parentPages;
  }

  @PostConstruct
  public void init(){
    parentPages = new ArrayList<>();
    Page nextPage = currentPage;

    while(nextPage.getParent() != null){
      parentPages.add(nextPage.getParent());
      nextPage = nextPage.getParent();
    }

    Collections.reverse(parentPages);

    Session session = resourceResolver.adaptTo(Session.class);
    Map<String, String> queryPredicate = new HashMap<>();

    queryPredicate.put("path", "/content");
    queryPredicate.put("1_property", "sling:resourceType");
    queryPredicate.put("1_property.value", "aem-bootcamp/components/page");
    queryPredicate.put("1_property.operation", "like");

    Query query = queryBuilder.createQuery(PredicateGroup.create(queryPredicate), session);

    SearchResult result = query.getResult();

    pageLists = result.getHits().stream()
      .map(this::getResourceTitle)
      .collect(Collectors.toList());
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
