package com.aembootcamp.core.servlets;

import com.aembootcamp.core.services.SubscriptionService;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
  resourceTypes= "aem-bootcamp/components/page",
  methods = {HttpConstants.METHOD_POST, HttpConstants.METHOD_GET},
  selectors = "subscription"
)
public class SubscriptionServlet extends SlingAllMethodsServlet {

  private static final Logger LOG = LoggerFactory.getLogger(SubscriptionServlet.class);

  @Reference
  private SubscriptionService subscriptionService;


  @Override
  protected void doGet(final SlingHttpServletRequest req,
                       final SlingHttpServletResponse resp) throws IOException {
    final Resource resource = req.getResource();

    resp.setContentType("text/plain");
    resp.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
  }

  @Override
  protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
    throws ServletException, IOException {
    ResourceResolver resourceResolver = request.getResourceResolver();

    String email = request.getRequestParameter("emailId").getString();
    String firstName = request.getRequestParameter("firstName").getString();
    String lastName = request.getRequestParameter("lastName").getString();

    subscriptionService.saveUserSubscription(email, firstName, lastName, resourceResolver);

    response.sendRedirect(String.format("%s.html", getRedirectURL(request)));

  }

  private String getRedirectURL(SlingHttpServletRequest request){
    return Optional.ofNullable(request.getRequestParameter("redirectURL"))
      .orElseThrow(() -> new NoSuchElementException("No URL found to redirect")).getString();
  }


}
