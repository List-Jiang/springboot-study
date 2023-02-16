package com.jdw.springboot.jsonpath;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.cache.Cache;
import com.jayway.jsonpath.spi.cache.CacheProvider;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class JsonPathTest {
    private static final String json1 = """
            {
                "firstName": "John",
                "lastName": "doe",
                "age": 26,
                "address": {
                    "streetAddress": "naist street",
                    "city": "Nara",
                    "postalCode": "630-0192"
                },
                "phoneNumbers": [
                    {
                        "type": "iPhone",
                        "number": "0123-4567-8888"
                    },
                    {
                        "type": "home",
                        "number": "0123-4567-8910"
                    }
                ]
            }
            """;

    private static final String json2 = """
            {
                "store": {
                    "book": [
                        {
                            "category": "reference",
                            "author": "Nigel Rees",
                            "title": "Sayings of the Century",
                            "price": 8.95
                        },
                        {
                            "category": "fiction",
                            "author": "Evelyn Waugh",
                            "title": "Sword of Honour",
                            "price": 12.99
                        },
                        {
                            "category": "fiction",
                            "author": "Herman Melville",
                            "title": "Moby Dick",
                            "isbn": "0-553-21311-3",
                            "price": 8.99
                        },
                        {
                            "category": "fiction",
                            "author": "J. R. R. Tolkien",
                            "title": "The Lord of the Rings",
                            "isbn": "0-395-19395-8",
                            "price": 22.99
                        }
                    ],
                    "bicycle": {
                        "color": "red",
                        "price": 19.95
                    }
                },
                "expensive": 10
            }
            """;


    @BeforeAll
    static void setJsonProvider() {
        Configuration.setDefaults(new Configuration.Defaults() {

            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });
    }

    @Test
    void read() {
        List<String> list = JsonPath.read(json1, "$.phoneNumbers[:2].number");
        list.forEach(System.out::println);
        JsonPath.read(json1, "$.store.book[*].author");
        JsonPath.read(json1, "$..author");
        JsonPath.read(json1, "$.store.*");
        JsonPath.read(json1, "$.store..price");
        JsonPath.read(json1, "$..book[2]");
        JsonPath.read(json1, "$..book[-2]");
        JsonPath.read(json1, "$..book[0,1]");
        JsonPath.read(json1, "$..book[:2]");
        JsonPath.read(json1, "$..book[1:2]");
        JsonPath.read(json1, "$..book[-2:]");
        JsonPath.read(json1, "$..book[2:]");
        JsonPath.read(json1, "$..book[?(@.isbn)]");
        JsonPath.read(json1, "$.store.book[?(@.price < 10)]");
        JsonPath.read(json1, "$..book[?(@.price <= $['expensive'])]");
        JsonPath.read(json1, "$..book[?(@.author =~ /.*REES/i)]");
        JsonPath.read(json1, "$..*");
        JsonPath.read(json1, "$..book.length()");
    }

    @Test
    void getPath1() {
        Configuration conf = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();
        List<String> pathList = JsonPath.using(conf).parse(json2).read("$..author");
        Assertions.assertTrue(CollectionUtils.containsAny(pathList, Arrays.asList(
                "$['store']['book'][0]['author']",
                "$['store']['book'][1]['author']",
                "$['store']['book'][2]['author']",
                "$['store']['book'][3]['author']"
        )), "咋不对呢？");
    }

    @Test
    void cache() {
        CacheProvider.setCache(new Cache() {
            //Not thread safe simple cache
            private final Map<String, JsonPath> map = new HashMap<>();

            @Override
            public JsonPath get(String key) {
                return map.get(key);
            }

            @Override
            public void put(String key, JsonPath jsonPath) {
                map.put(key, jsonPath);
            }
        });
    }
}
