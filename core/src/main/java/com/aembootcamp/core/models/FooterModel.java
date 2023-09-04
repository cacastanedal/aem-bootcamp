package com.aembootcamp.core.models;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType = "aem-bootcamp/components/general/footer")
public class FooterModel {

  @OSGiService
  private QueryBuilder queryBuilder;

  @SlingObject
  private ResourceResolver resourceResolver;

  @ValueMapValue(name = "footerRoot", injectionStrategy = InjectionStrategy.OPTIONAL)
  @Default(values = "/content")
  private String footerRoot;

  public List<Page> navigationList;

  public List<Page> getNavigationList(){
    return navigationList;
  }

  @PostConstruct
  public void init() {

    navigationList = new ArrayList<>();

    Session session = resourceResolver.adaptTo(Session.class);

    Map<String, String> queryPredicate = new HashMap<>();

    queryPredicate.put("path", footerRoot);
    queryPredicate.put("1_property", "sling:resourceType");
    queryPredicate.put("1_property.value", "aem-bootcamp/components/page");
    queryPredicate.put("1_property.operation", "equal");

    Query query = queryBuilder.createQuery(PredicateGroup.create(queryPredicate), session);

    SearchResult result = query.getResult();

    assert !result.getHits().isEmpty();

    if (result.getHits() == null) {

      Hit queryResult = result.getHits().get(0);

      try {
        Page rootPage = queryResult.getResource().getParent().adaptTo(Page.class);
        assert rootPage != null;
        Page childPage = rootPage.listChildren().next();

        navigationList.add(rootPage);
        navigationList.add(childPage);

        Collections.reverse(navigationList);

      } catch (RepositoryException e) {
        throw new RuntimeException(e);
      }

    }
  }


}
