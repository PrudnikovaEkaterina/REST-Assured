package ru.prudnikova.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserModel {
    private Integer id;
    private String job;
    @JsonProperty("name")
    private String fullName;
    private String createdAt;
}
