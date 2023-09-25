package com.aembootcamp.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType = "aem-bootcamp/components/structure/article")
public class ArticleModel {

  @SlingObject
  private ResourceResolver resourceResolver;

  @Inject
  private Page currentPage;

  @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
  @Default(values = "Page Title")
  private String titleText;

  @ValueMapValue(name = "text", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String articleText;

  private List<String> articleTagNames;

  @PostConstruct
  protected void init(){
    articleTagNames = new ArrayList<>();

    for(Tag tag : currentPage.getTags()){
      articleTagNames.add(tag.getTitle());
    }


  }

  public List<String> getArticleTagNames(){
    return articleTagNames;
  }

  public String getArticleText(){
    return articleText;
  }

  public List<String> getTags(){
    return articleTagNames;
  }
}
