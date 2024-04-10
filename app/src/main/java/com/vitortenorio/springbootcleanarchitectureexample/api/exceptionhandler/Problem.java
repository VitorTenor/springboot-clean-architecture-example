package com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Problem")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {
    @Schema(example = "400")
    private String title;
    @Schema(example = "Invalid parameter")
    private String detail;
    @Schema(example = "Invalid parameter")
    private Integer status;
    @Schema(example = "/invalid-parameter")
    private String userMessage;
    @Schema(example = "2022-07-15T11:21:50")
    private LocalDateTime timestamp;
    @Schema(description = "List of objects or fields that generated the error")
    private String uri;
    @Schema(description = "List of objects or fields that generated the error")
    private List<Object> objects;

    @Builder
    @Getter
    @Schema(name = "ObjectProblem")
    public static class Object {
        @Schema(example = "parameter")
        private String name;
        @Schema(example = "The parameter is invalid")
        private String userMessage;
    }
}
