package co.zw.sanders.graphqlserver.controller;

import co.zw.sanders.graphqlserver.provider.GraphQLProvider;
import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/swapi")
public class GraphQLController {
    private final GraphQLProvider provider;

    @PostMapping
    public String getData(@RequestBody String query) {
        log.info("data: {}",query);
        ExecutionResult execute = provider.initiateGraphQL().execute(query);
        Map<String, String> obj = execute.getData();
        JSONObject jsonObject = new JSONObject(obj);
        return jsonObject.toString();
    }
}
