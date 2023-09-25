package com.aembootcamp.core.schedulers;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd=NewsletterScheduler.Config.class)
@Component(service=Runnable.class)
public class NewsletterScheduler implements Runnable {

  @ObjectClassDefinition(name="Newsletter scheduled task",
    description = "Newsletter subscription emails for articles in aem bootcamp")
  public static @interface Config {

    @AttributeDefinition(name = "Cron-job expression")
    String scheduler_expression() default "*/30 * * * * ?";

    @AttributeDefinition(name = "Concurrent task",
      description = "Whether or not to schedule this task concurrently")
    boolean scheduler_concurrent() default false;

    @AttributeDefinition(name = "Newsletter start date",
      description = "Can be configured in /system/console/configMgr")
    String startDate() default "01012023";

    @AttributeDefinition(name = "Newsletter end date",
      description = "Can be configured in /system/console/configMgr")
    String endDate() default "01012024";

    @AttributeDefinition(name = "Newsletter amount of articles",
      description = "The amount of articles to send to subscribers")
    int amountArticles() default 1;
  }

  private final Logger log = LoggerFactory.getLogger(getClass());

  private String startDate;

  private String endDate;

  private int amountArticles;

  @Override
  public void run (){
    log.info("NewsletterScheduler running - sending {} articles from {} to {}", amountArticles, startDate, endDate);
    // Get all article pages from date range, using jcr:createdBy, sorted desc
    // Fetch detail users from /content/aem-bootcamp/subscribers
    // Get articles based on users article tags
    // Log the article information
  }

  @Activate
  protected void activate(final Config config) {

    startDate = config.startDate();
    endDate = config.endDate();
    amountArticles = config.amountArticles();
  }
}
