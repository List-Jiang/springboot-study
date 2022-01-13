package com.jdw.springboot.format.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.BigDecimalParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.io.IOException;
import java.math.BigDecimal;

public class MoneyDeSerializer extends NumberDeserializers.BigDecimalDeserializer {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String moneyStr = deserializationContext.readValue(jsonParser, String.class);
        moneyStr = moneyStr.trim().replace("￥ ", "");
        return BigDecimalParser.parse(moneyStr);
    }
}
