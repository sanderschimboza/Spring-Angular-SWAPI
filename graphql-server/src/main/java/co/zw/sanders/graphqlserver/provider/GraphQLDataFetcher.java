package co.zw.sanders.graphqlserver.provider;

import co.zw.sanders.graphqlserver.vo.People;
import co.zw.sanders.graphqlserver.vo.Swapi;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GraphQLDataFetcher {
    @Value("${swapi.dev.url}")
    private String SWAPI_URL;
    private final RestTemplate restTemplate;

    public DataFetcher<Swapi> getPeopleList() {
        log.info("Inside getPeopleList");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return dataFetchingEnvironment -> restTemplate
                .exchange(SWAPI_URL + "/api/people", HttpMethod.GET, entity,
                        new ParameterizedTypeReference<Swapi>() {
                        }).getBody();
    }
    public DataFetcher<Swapi> getPersonByName() {
        log.info("Inside getPersonByName");
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return restTemplate.getForObject(SWAPI_URL + "/api/people/?search="
                    + name, Swapi.class);
        };
    }
}
