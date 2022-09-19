package co.zw.sanders.graphqlserver.vo;

import lombok.Data;

import java.util.List;
@Data
public class Swapi {
    private String next;
    private List<People> results;
}