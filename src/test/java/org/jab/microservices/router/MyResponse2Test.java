package org.jab.microservices.router;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

//Help from: http://www.jsonschema2pojo.org/

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "list"
})
public class MyResponse2Test {

    @JsonProperty("list")
    private List<String> list;

    /**
     * No args constructor for use in serialization
     *
     */
    public MyResponse2Test() {
    }

    /**
     *
     * @param list
     */
    public MyResponse2Test(List<String> list) {
        super();
        this.list = list;
    }

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("list")
    public List<String> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(List<String> list) {
        this.list = list;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("list", list).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(list).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MyResponse2Test) == false) {
            return false;
        }
        MyResponse2Test rhs = ((MyResponse2Test) other);
        return new EqualsBuilder().append(list, rhs.list).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}