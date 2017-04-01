package application;

import application.elastic.repository.base.CustomElasticsearchRepositoryImpl;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by huangzebin on 2017/2/13.
 */
@Configuration
@EnableElasticsearchRepositories(repositoryBaseClass = CustomElasticsearchRepositoryImpl.class)
public class Config {

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }

/*    @Value("${elasticsearch.rest.client}")
    String host;*/

/*    @Bean
    public RestClient elasticsearchRestClient(){
        String[] hostPort = host.split(":");
        Assert.isTrue(hostPort.length == 2);
        RestClient restClient = RestClient.builder(
                new HttpHost(hostPort[0], Integer.valueOf(hostPort[1]), "http")).build();
        return restClient;
    }*/


/*    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new IndexServlet());
        registration.addUrlMappings("/hello");
        return registration;
    }*/

    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AccessAllowFilter());
        return registration;
    }
/*    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new IndexListener());
        return servletListenerRegistrationBean;
    }*/
}
