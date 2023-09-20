package com.aembootcamp.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;


import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
  resourceTypes= "aem-bootcamp/components/page",
  methods = {HttpConstants.METHOD_POST},
  selectors = "subscription",
  extensions = {"txt", "xml"}
)
public class SubscriptionServlet extends SlingAllMethodsServlet {

  private static final Logger LOG = LoggerFactory.getLogger(SubscriptionServlet.class);
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
    try{
      LOG.info("\n ---------------------------STARTED POST-----------------------------");
      for(RequestParameter requestParameter : request.getRequestParameterList()){
        LOG.info("\n ##PARAMETERS===> {} : {}", requestParameter.getName(), requestParameter.getString());
      }
    } catch (Exception e){
      LOG.info("\n ERROR IN REQUEST {}", e.getMessage());
    } finally {
      response.getWriter().write("========FORM SUBMITTED==========");
    }
  }

}
