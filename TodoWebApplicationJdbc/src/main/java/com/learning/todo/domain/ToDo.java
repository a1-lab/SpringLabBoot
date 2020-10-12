package com.learning.todo.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {
    @NotNull
    private String id;
    @NotNull
    @NotBlank
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo(){
        LocalDateTime now = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = now;
        this.modified = now;
    }

    public ToDo(String description){
        this();
        this.description = description;
    }

//    public static void main(String... args){
//        ToDo todo = new ToDo();
//        todo.setId(null);
//        todo.setDescription("");
//
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
//        for (ConstraintViolation<ToDo> violation: violations) {
//            System.out.println(violation.getMessage());
//        }
//    }
}
