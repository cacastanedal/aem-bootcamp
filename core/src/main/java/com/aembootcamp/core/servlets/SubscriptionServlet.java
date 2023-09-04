package com.aembootcamp.core.servlets;

import com.aembootcamp.core.services.SubscribeUser;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

//@Component(service = { Servlet.class })
//@SlingServletResourceTypes(
//  resourceTypes="aem-bootcamp/components/structure/subscription",
//  methods= HttpConstants.METHOD_GET,
//  extensions="text")
//@ServiceDescription("AEM Bootcamp Subscribe Servlet")
@Component(service={Servlet.class}, property={"sling.servlet.methods=get", "sling.servlet.paths=/bin/subscription"})
public class SubscriptionServlet extends SlingSafeMethodsServlet {

  @Reference
  SubscribeUser subscribeUser;

  @Override
  protected void doGet(final SlingHttpServletRequest req,
                       final SlingHttpServletResponse resp) throws ServletException, IOException {
    // final Resource resource = req.getResource();
    resp.setContentType("text/plain");
    resp.getWriter().write("Hello world from Subscription Servlet");
    resp.getWriter().write(subscribeUser.greetings());
    //resp.getWriter().write("Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
  }
}
