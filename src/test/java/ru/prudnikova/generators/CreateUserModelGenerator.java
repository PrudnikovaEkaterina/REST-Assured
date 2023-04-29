package ru.prudnikova.generators;

import com.github.javafaker.Faker;
import ru.prudnikova.models.CreateUserModel;

public class CreateUserModelGenerator {

    public static CreateUserModel generationUserCreateBody() {
        Faker faker = new Faker();
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setFullName(faker.name().fullName());
        createUserModel.setJob(faker.job().position());
        return createUserModel;
    }
}
