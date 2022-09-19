package co.zw.sanders.graphqlserver.controller;

import co.zw.sanders.graphqlserver.provider.GraphQLProvider;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class GraphQLController {
    private final GraphQLProvider provider;

    @RequestMapping(value = "/data")
    public String getData(@RequestBody String query) {
        log.info("data: {}",query);
        ExecutionResult execute = provider.initiateGraphQL().execute(query);
        Map<String, String> obj = execute.getData();
        JSONObject jsonObject = new JSONObject(obj);
        return jsonObject.toString();
    }
}
